package at.big_buum_man.server.gui;

import java.awt.Font;
import java.awt.Polygon;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.gui.TextField;

public class StartSeite extends BasicGame
{
	public static final int WIDTH = Display.getDesktopDisplayMode().getWidth();	//Bildschirm breite
	public static final int HEIGHT = Display.getDesktopDisplayMode().getHeight(); //Bildschirm höhe
	private int xw;
	private int yw;
	private int pos=1;
	private int ppos=1;
	private int cursorh=-1;
	private int cursorv=-1;
	private int[] einstellung={0,0,0,0,0};
	private TrueTypeFont trueTypeFont;

	private STATE state; //Status
	private Image bomb;
	
	Bomberman bm; 
	
	private ArrayList<Karte> maps= new ArrayList<Karte>(); //kartenliste
	private ArrayList<Powerup> powerups= new ArrayList<Powerup>(); //Powerupliste
	private ArrayList<Powerdown> powerdowns= new ArrayList<Powerdown>(); //Powerdownliste
	
	
	/**
	 * 	Startseite - wird als Hauptmenü gestartet
	 * 
	 */
	public StartSeite() 
	{
		super("StartSeite");
		xw=250-5-20;
		yw=150-5;
	}
	
	/***
	 * 	Hauptfunktion um das Spiel zu starten
	 * 
	 * 	@param args
	 * 	@throws SlickException
	 */
	public static void main(String[] args) throws SlickException 
	{
		StartSeite ss = new StartSeite();
		AppGameContainer container = new AppGameContainer(ss);
		container.setDisplayMode(WIDTH, HEIGHT, true);
		container.setClearEachFrame(true);
		container.setMinimumLogicUpdateInterval(25);
		container.setTargetFrameRate(60);
		container.setShowFPS(true);
		container.start();
	}

	/**
	 * 	Hier werden alle Grafiken neu geladen und in einem bestimmten Intervall gezeichnet
	 */
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException 
	{
		if(state==STATE.MENU)
		{
			drawHauptmenue(g);
		}
		
		if(state==STATE.GAME)
		{
			if(bm==null)
			{
				bm= Bomberman.getInstance();
				bm.init(container);
			}
			
			bm.render(container, g);
			//drawWindow(g,"Game");
		}
		
		if(state==STATE.HELP)
		{
			drawWindow(g,"Hilfe");
		}
		
		if(state==STATE.IMPRESS)
		{
			
			drawWindow(g,"Impressum");
		}
		
		if(state==STATE.PAUSE)
		{
			drawWindow(g,"Pause");
			int sideposition = (WIDTH/2)-200;
			int haelftehoehe = (HEIGHT/2);
			
			if(ppos==1)trueTypeFont.drawString(sideposition, haelftehoehe-100,"Resume", Color.black);
			else trueTypeFont.drawString(sideposition, haelftehoehe-100,"Resume", Color.red);
			if(ppos==2)trueTypeFont.drawString(sideposition, haelftehoehe-50,"Einstellungen", Color.black);
			else trueTypeFont.drawString(sideposition, haelftehoehe-50,"Einstellungen", Color.red);
			if(ppos==3)trueTypeFont.drawString(sideposition, haelftehoehe,"Help", Color.black);
			else trueTypeFont.drawString(sideposition, haelftehoehe,"Help", Color.red);
			if(ppos==4)trueTypeFont.drawString(sideposition, haelftehoehe+50,"Hauptmenü", Color.black);
			else trueTypeFont.drawString(sideposition, haelftehoehe+50,"Hauptmenü", Color.red);
			if(ppos==5)trueTypeFont.drawString(sideposition, haelftehoehe+100,"Spiel beenden", Color.black);
			else trueTypeFont.drawString(sideposition, haelftehoehe+100,"Spiel beenden", Color.red);
		}
		
		if(state==STATE.SETTINGS)
		{
			drawWindow(g,"Einstellungen");
		}
		
		if(state==STATE.GAMECONFIGURATION)
		{
			drawGameConfiguration(g,container);
		}
	}

	/**
	 * 	Hier werden alle Resourcen angelegt und Initial Produziert
	 */
	@Override
	public void init(GameContainer container) throws SlickException 
	{
		initMaps(); //Dummie Maps
		initPowerdowns(); //Dummie Powerdowns
		initPowerups(); //Dummie Powerups
		trueTypeFont = new TrueTypeFont(new Font("Arial", Font.BOLD, 20), true);
		bomb = new Image("res/bomb.png");
		state=STATE.MENU;
	}

	/**
	 * 	Hier wird die ganze Gamelogik abgearbeitet
	 */
	@Override
	public void update(GameContainer container, int delta) throws SlickException 
	{
		Input input=container.getInput();
		int keyjump=100;
		
		if(state==STATE.GAME) //Game Status -> Spiel update
		{
			bm.update(container, delta);
			if (input.isKeyPressed(Input.KEY_P)) 
			{
				state=STATE.PAUSE;
			}
		}
		
		if(state==STATE.HELP) //Help Status -> Hilfe wird ausgegeben
		{
			if (input.isKeyPressed(Input.KEY_ESCAPE)) 
			{
				state=STATE.MENU;
			}
		}
		
		if(state==STATE.IMPRESS) //Impress Status -> Impressum wird ausgegeben
		{
			if (input.isKeyPressed(Input.KEY_ESCAPE)) 
			{
				state=STATE.MENU;
			}
		}
		
		if(state==STATE.MENU) //Menu Status -> Menü wird ausgeben
		{
			if(pos<5)
			{
				if (input.isKeyPressed(Input.KEY_DOWN)) 
				{
					yw=yw+keyjump;
					pos++;
				}
			}
			
			if(pos>1)
			{
				if (input.isKeyPressed(Input.KEY_UP)) 
				{
					yw=yw-keyjump;
					pos--;
				}
			}
			
			if (input.isKeyPressed(Input.KEY_ENTER)) 
			{
				if(pos==5)
					container.exit();
				if(pos==1)
					state=STATE.GAMECONFIGURATION;
				if(pos==2)
					state=STATE.SETTINGS;
				if(pos==3)
					state=STATE.IMPRESS;
				if(pos==4)
					state=STATE.HELP;
			}
			
			if (input.isKeyPressed(Input.KEY_ESCAPE)) 
			{
				container.exit();
			}
		}
		
		if(state==STATE.PAUSE) //Pause Status -> Spiel wird angehalten
		{
			if(ppos<5)
			{
				if (input.isKeyPressed(Input.KEY_DOWN)) 
				{
					ppos++;
				}
			}
			
			if(ppos>1)
			{
				if (input.isKeyPressed(Input.KEY_UP)) 
				{
					ppos--;
				}
			}
			
			if (input.isKeyPressed(Input.KEY_ENTER)) 
			{
				if(ppos==1)
					state=STATE.GAME;
				if(ppos==2)
					state=STATE.SETTINGS;
				if(ppos==3)
					state=STATE.HELP;
				if(ppos==4)
					state=STATE.MENU;
				if(ppos==5)
					container.exit();
			}
			
			if (input.isKeyPressed(Input.KEY_ESCAPE)) 
			{
				state=STATE.GAME;
			}
			
			if (input.isKeyPressed(Input.KEY_P)) 
			{
				state=STATE.GAME;
			}
		}
		
		if(state==STATE.SETTINGS) //Settings Status -> Einstellungen werden angezeigt
		{
			if (input.isKeyPressed(Input.KEY_ESCAPE)) 
			{
				state=STATE.MENU;
			}
		}
		
		if(state==STATE.GAMECONFIGURATION) //GameConfiguration Status -> Gamemaker wird geöffnet damit ein Spiel gestartet werden kann
		{
			if (input.isKeyPressed(Input.KEY_ESCAPE)) 
			{
				state=STATE.MENU;
			}

			if(input.isKeyPressed(Input.KEY_UP))
			{
				if(cursorv>1)
					cursorv--;
			}
			
			if(input.isKeyPressed(Input.KEY_DOWN))
			{
				if(cursorv<5)
					cursorv++;
			}
			
			if(input.isKeyPressed(Input.KEY_LEFT))
			{
				if(cursorh>0)
				{
					cursorh--;
					einstellung[cursorv-1]--;
				}
			}
			
			if(input.isKeyPressed(Input.KEY_RIGHT))
			{
				if(cursorh<5)
				{
					cursorh++;
					einstellung[cursorv-1]++;
				}
			}
			
			if(input.isKeyPressed(Input.KEY_ENTER))
			{
				if(cursorv==4)
				{
					state=STATE.GAME;
					// werte werden nun übergeben von den einstellungen 
					// Bomberman.setMap(einstellung[0]); hier wird die Map ausgewählt
					// Bomberman.setPowerupPowerdowns(einstellung[1]); hier werden die Powerups und Powerdowns übergeben die erlaubt sind
					// Bomberman.setGamemode(einstellung[2]); hier wird der gamemode ausgewählt
					// Bomberman.setTeammode(einstellung[3]); hier wird der teammodus ausgewählt
					//bm.update(container, delta);
					//bm.init(container);
				}
			}
		}
		input.clearKeyPressedRecord();
	}

	/**
	 * 	Fenster wird gezeichnet mit Text
	 * 
	 * 	@param g
	 * 	@param text
	 */
	public void drawWindow(Graphics g, String text)
	{
		int haelftebreite = (WIDTH/2);
		int haelftehoehe = (HEIGHT/2);
		
		g.setColor(Color.red);
		g.fillRoundRect(haelftebreite-250, haelftehoehe-150, 500, 300, 20);
		
		g.setColor(Color.white);
		g.fillRoundRect(haelftebreite-240, haelftehoehe-140, 480, 280, 20);
		
		trueTypeFont.drawString(haelftebreite-trueTypeFont.getWidth(text)/2, haelftehoehe-140+trueTypeFont.getHeight()/2, text, Color.black);
	}
	
	/**
	 * 	Hauptmenü wird gezeichnet
	 * 
	 * 	@param g
	 */
	public void drawHauptmenue(Graphics g)
	{
		int positionleft = 300-5-20;
		int positiontop = 130;
		int valueadd = 100;
		int breite_10 = (WIDTH/100*10);
		int hoehe_10 = (HEIGHT/100*10);
		
		g.setColor(Color.white);
		g.fillRect(breite_10+1, hoehe_10+1, WIDTH/100*80 ,HEIGHT/100*80);
		
		g.setColor(Color.red);
		g.fillRoundRect(positionleft, positiontop-5, 150, 40, 10);
		
		g.setColor(Color.red);
		g.fillRoundRect(positionleft, positiontop+(valueadd*1)-5, 150, 40, 10);
		
		g.setColor(Color.red);
		g.fillRoundRect(positionleft, positiontop+(valueadd*2)-5, 150, 40, 10);
		
		g.setColor(Color.red);
		g.fillRoundRect(positionleft, positiontop+(valueadd*3)-5, 150, 40, 10);
		
		g.setColor(Color.red);
		g.fillRoundRect(positionleft, positiontop+(valueadd*4)-5, 150, 40, 10);
		
		positionleft = 310-20;
		positiontop = 132;
		g.setColor(Color.black);
		trueTypeFont.drawString(positionleft, positiontop,"Spiel Start", Color.black);
		trueTypeFont.drawString(positionleft, positiontop+valueadd,"Einstellungen", Color.black);
		trueTypeFont.drawString(positionleft, positiontop+valueadd*2,"Impressum", Color.black);
		trueTypeFont.drawString(positionleft, positiontop+valueadd*3,"Help", Color.black);
		trueTypeFont.drawString(positionleft, positiontop+valueadd*4,"Spiel beenden", Color.black);
		
		g.setColor(Color.red);
		g.fillRoundRect(500-20, 130-5, 600, 440, 10);
		
		positionleft = 510-20;
		switch(pos)
		{
			case 1:
				trueTypeFont.drawString(positionleft, positiontop, "Hauptmenü von Big-Bum-Bang", Color.black);
			break;
			case 2:
				trueTypeFont.drawString(positionleft, positiontop, "Extras", Color.black);
			break;
			case 3:
				trueTypeFont.drawString(positionleft, positiontop, "Impressum zum Spiel", Color.black);
			break;
			case 4:
				trueTypeFont.drawString(positionleft, positiontop, "Über das Spiel und die Steurung", Color.black);
			break;
			case 5:
				trueTypeFont.drawString(positionleft, positiontop, "Spiel beenden", Color.black);
			break;
		}
		
		bomb.drawCentered(xw, yw);
	}
	
	/**
	 * 	Gamemaker wird gezeichnet
	 * 
	 * 	@param g
	 *	@param gc
	 */
	public void drawGameConfiguration(Graphics g, GameContainer gc)
	{
		if(cursorh ==-1) cursorh=0;
		else cursorh = cursorh;
		if(cursorv ==-1) cursorv=1;
		else cursorv = cursorv;
		
		int links=200;
		int oben=200;
		int breite_10 = (WIDTH/100*10);
		int hoehe_10 = (HEIGHT/100*10);
		int valueadd = 50;
		int valuelinksadd = 200;
		
		g.setColor(Color.white);
		g.fillRect(breite_10+1, hoehe_10+1, WIDTH/100*80 ,HEIGHT/100*80);
		trueTypeFont.drawString(links, oben, "Spielmodus:", Color.black);
		
		//g.setColor(Color.black);
		//g.fillRect(links+200,oben, 300 ,trueTypeFont.getHeight());
		
		//TextField field2;
		//field2 = new TextField(gc,null,links+200,oben, 300 ,trueTypeFont.getHeight());
		//field2.render(gc, g);
		//field2.setBounds(links+200,oben, 300 ,trueTypeFont.getHeight());
		oben+=valueadd;
		
		trueTypeFont.drawString(links, oben, "Map:", Color.black);
		
		if (cursorv!=1) g.setColor(Color.black);
		else g.setColor(Color.red);
		g.fillRect(links+valuelinksadd,oben, 300 ,trueTypeFont.getHeight());
		trueTypeFont.drawString(links+valuelinksadd, oben, maps.get(einstellung[0]).getName(), Color.white);
		
		oben+=valueadd;
		
		trueTypeFont.drawString(links, oben, "Powerups/Powerdowns:", Color.black);
		
		if (cursorv!=2) g.setColor(Color.black);
		else g.setColor(Color.red);
		g.fillRect(links+valuelinksadd,oben, 300 ,trueTypeFont.getHeight());
		trueTypeFont.drawString(links+valuelinksadd, oben, powerdowns.get(einstellung[1]).getName(), Color.white);
		
		oben+=valueadd;
		
		trueTypeFont.drawString(links, oben, "Gamemode:", Color.black);
		
		if (cursorv!=3) g.setColor(Color.black);
		else g.setColor(Color.red);
		g.fillRect(links+valuelinksadd,oben, 300 ,trueTypeFont.getHeight());
		trueTypeFont.drawString(links+valuelinksadd, oben, GAMEMODE.values()[einstellung[2]].toString(), Color.white);
		oben+=valueadd;
		

		if (cursorv!=4) g.setColor(Color.black);
		else g.setColor(Color.red);
		g.fillRect(links+valuelinksadd,oben, 300 ,trueTypeFont.getHeight());
		trueTypeFont.drawString(links+valuelinksadd, oben, "Start Game", Color.white);
		oben+=valueadd;

		drawvorschaumap(g,maps.get(einstellung[0]),1000,200,500,500,1);
	}
	
	/**
	 * 	Vorschaumap wird gezeichnet für Gamemaker
	 * 	noch statisch
	 * 	@param g
	 * 	@param x
	 *	@param y
	 *	@param w
	 *	@param h
	 * 	@param i
	 */
	private void drawvorschaumap(Graphics g,float x, float y, int w, int h, int i)
	{
		g.setColor(Color.black);
		g.fillRect(x,y,w ,h);
		
		g.setColor(Color.white);
		g.fillRect(x+5,y+5,w-10 ,h-10);
		
		g.setColor(Color.black);
		g.fillRect(x+10,y+10,w-20 ,h-20);
			
		MapListe ml = new MapListe();
		int rows=ml.getMap().size();
		int cols=15;
		ArrayList<Wand> hl;
		ArrayList<ArrayList<Wand>> vl;
		
		int ya=0;
		vl=new ArrayList<ArrayList<Wand>>();
		for(String[] s:ml.getMap())
        	{
			hl=new ArrayList<Wand>();
		   	int xa=0;
     	   		for(String o:s)
     	   		{
     		  		Wand wa=new Wand(o);
     		  		xa=xa+2;
     		  		hl.add(wa);
     	   		}
     	   		ya=ya+2;
     	   		vl.add(hl);
        	}
		
		float breite=(w-20)/vl.get(1).size();
		System.out.println("breite:"+breite+" anzahl:"+vl.get(1).size() + " w:"+(w-20));
	
		float hoehe=(h-20)/vl.size();
		System.out.println("hoehe:"+hoehe+" anzahl:"+vl.size()+" h:"+(h-20));

		y=y+10-hoehe+((h-20-hoehe*vl.size())/2);
		x=x+10-breite+((w-20-breite*vl.get(1).size())/2);
		for(ArrayList<Wand> v: vl)
        {
		   float yx=0+x;
     	   for(Wand b:v)
     	   {
     		  if(b.getStein().equals("0"))
     		  {
     			 g.setColor(Color.gray);
     			 g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		     			  
     		  }
     		  else if(b.getStein().equals("1"))
     		  {
     			 g.setColor(Color.green);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);			  
     		  }
     		  else if(b.getStein().equals("2"))
     		  {
     			 g.setColor(Color.red);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		    			  
     		  }
     		  else if(b.getStein().equals("3"))
     		  {
     			 g.setColor(Color.blue);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);					  
     		  }
     		  else if(b.getStein().equals("4"))
     		  {
     			 g.setColor(Color.white);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);					  
     		  }
     		  else if(b.getStein().equals("5"))
     		  {
     			 g.setColor(Color.yellow);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		   			  
     		  }
     		  else if(b.getStein().equals("6"))
     		  {
     			 g.setColor(Color.white);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		  			  
     		  }
     		  else if(b.getStein().equals("7"))
     		  {
     			 g.setColor(Color.magenta);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		     			  
     		  }
     		  else if(b.getStein().equals("8"))
     		  {
     			 g.setColor(Color.blue);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		 			  
     		  }
     		  else if(b.getStein().equals("9"))
     		  {
     			 g.setColor(Color.red);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		   			  
     		  }
     		 else if(b.getStein().equals("x"))
     		 {
     			 g.setColor(Color.green);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);	
     		 }
     		 else
     		 {
     			 g.setColor(Color.darkGray);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		
     		 }
     		  yx=yx+breite;
     	   }
     	   y=y+hoehe;
        }
	}
	
	/**
	 * 	Vorschaumap wird gezeichnet für Gamemaker
	 * 	dynamisch mit Karte
	 */
	private void drawvorschaumap(Graphics g,Karte k,float x, float y, int w, int h, int i)
	{
		g.setColor(Color.black);
		g.fillRect(x,y,w ,h);
		
		g.setColor(Color.white);
		g.fillRect(x+5,y+5,w-10 ,h-10);
		
		g.setColor(Color.black);
		g.fillRect(x+10,y+10,w-20 ,h-20);
			
		
		int rows=k.getKarte().length;
		int cols=k.getKarte()[0].length;
		ArrayList<Wand> hl;
		ArrayList<ArrayList<Wand>> vl;
		
		
		int ya=0;
		vl=new ArrayList<ArrayList<Wand>>();
		for(String[] s:k.getKarte())
	        {
			hl=new ArrayList<Wand>();
			int xa=0;
		     	for(String o:s)
		     	{
				Wand wa=new Wand(o);
		     		xa=xa+2;
		     		hl.add(wa);
		     	}
		     	ya=ya+2;
		     	vl.add(hl);
	        }
		
		float breite=(w-20)/vl.get(1).size();
		System.out.println("breite:"+breite+" anzahl:"+vl.get(1).size() + " w:"+(w-20));
	
		float hoehe=(h-20)/vl.size();
		System.out.println("hoehe:"+hoehe+" anzahl:"+vl.size()+" h:"+(h-20));

		y=y+10-hoehe+((h-20-hoehe*vl.size())/2);
		x=x+10-breite+((w-20-breite*vl.get(1).size())/2);
		for(ArrayList<Wand> v: vl)
        {
		   float yx=0+x;
     	   for(Wand b:v)
     	   {
     		  if(b.getStein().equals("0"))
     		  {
     			 g.setColor(Color.gray);
     			 g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		     			  
     		  }
     		  else if(b.getStein().equals("1"))
     		  {
     			 g.setColor(Color.green);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);			  
     		  }
     		  else if(b.getStein().equals("2"))
     		  {
     			 g.setColor(Color.red);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		    			  
     		  }
     		  else if(b.getStein().equals("3"))
     		  {
     			 g.setColor(Color.blue);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);					  
     		  }
     		  else if(b.getStein().equals("4"))
     		  {
     			 g.setColor(Color.white);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);					  
     		  }
     		  else if(b.getStein().equals("5"))
     		  {
     			 g.setColor(Color.yellow);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		   			  
     		  }
     		  else if(b.getStein().equals("6"))
     		  {
     			 g.setColor(Color.white);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		  			  
     		  }
     		  else if(b.getStein().equals("7"))
     		  {
     			 g.setColor(Color.magenta);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		     			  
     		  }
     		  else if(b.getStein().equals("8"))
     		  {
     			 g.setColor(Color.blue);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		 			  
     		  }
     		  else if(b.getStein().equals("9"))
     		  {
     			 g.setColor(Color.red);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		   			  
     		  }
     		 else if(b.getStein().equals("x"))
     		 {
     			 g.setColor(Color.black);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);	
     		 }
     		 else
     		 {
     			 g.setColor(Color.darkGray);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		
     		 }
     		  yx=yx+breite;
     	   }
     	   y=y+hoehe;
        }
	}
	
	
	
	
	//Hier die Dummieklassen - Testklassen für Testobjekte
	
	/**
	 * 	Dummie Maps werden geladen
	 */
	public void initMaps()
	{
		Mapdummie mp= new Mapdummie();
		
		this.maps=mp.getList();
	}
	
	/**
	 * 	Dummie Powerups werden geladen
	 */
	public void initPowerups()
	{
		Powerupdummie pud=new Powerupdummie();
		
		this.powerups=pud.getList();
	}
	
	/**
	 * 	Dummie Powerdowns werden geladen
	 */
	public void initPowerdowns()
	{
		Powerdowndummie pdd=new Powerdowndummie();
		
		this.powerdowns=pdd.getList();
	}
}
