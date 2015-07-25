package at.big_buum_man.server.gui;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import at.big_buum_man.server.gui.dummies.Mapdummie;
import at.big_buum_man.server.gui.dummies.Powerdowndummie;
import at.big_buum_man.server.gui.dummies.Powerupdummie;
import at.big_buum_man.server.gui.enums.GAMEMODE;
import at.big_buum_man.server.gui.enums.STATE;
import at.big_buum_man.server.gui.helper.Variables;
import at.big_buum_man.server.gui.objects.Karte;
import at.big_buum_man.server.gui.objects.MapListe;
import at.big_buum_man.server.gui.objects.Powerdown;
import at.big_buum_man.server.gui.objects.Powerup;
import at.big_buum_man.server.gui.objects.Wand;

public class StartSeite extends BasicGame
{

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
	
	private Game bm; 
	
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
	/*
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
	*/
	
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
			RenderStateGame(container, g);
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
			RenderStatePause(g);
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
		initFont();
		initBomb();
		initState(STATE.MENU);
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
			UpdateStateGame(container,delta,input);
		}
		
		if(state==STATE.HELP) //Help Status -> Hilfe wird ausgegeben
		{
			UpdateStateHelp(input);
		}
		
		if(state==STATE.IMPRESS) //Impress Status -> Impressum wird ausgegeben
		{
			UpdateStateImpress(input);
		}
		
		if(state==STATE.MENU) //Menu Status -> Menü wird ausgeben
		{
			UpdateStateMenu(input,keyjump,container);
		}
		
		if(state==STATE.PAUSE) //Pause Status -> Spiel wird angehalten
		{
			UpdateStatePause(input,container);
		}
		
		if(state==STATE.SETTINGS) //Settings Status -> Einstellungen werden angezeigt
		{
			UpdateStateSettings(input);
		}
		
		if(state==STATE.GAMECONFIGURATION) //GameConfiguration Status -> Gamemaker wird geöffnet damit ein Spiel gestartet werden kann
		{
			UpdateStateGameconfiguration(input);
		}
		
		input.clearKeyPressedRecord();
	}

	/**
	 * 	Fenster wird gezeichnet mit Text
	 * 
	 * 	@param g
	 * 	@param text
	 */
	private void drawWindow(Graphics g, String text)
	{
		int haelftebreite = (Variables.WIDTH/2);
		int haelftehoehe = (Variables.HEIGHT/2);
		
		g.setColor(Variables.red);
		g.fillRoundRect(haelftebreite-250, haelftehoehe-150, 500, 300, 20);
		
		g.setColor(Variables.white);
		g.fillRoundRect(haelftebreite-240, haelftehoehe-140, 480, 280, 20);
		
		trueTypeFont.drawString(haelftebreite-trueTypeFont.getWidth(text)/2, haelftehoehe-140+trueTypeFont.getHeight()/2, text, Variables.black);
	}
	
	/**
	 * 	Hauptmenü wird gezeichnet
	 * 
	 * 	@param g
	 */
	private void drawHauptmenue(Graphics g)
	{
		int positionleft = 300-5-20;
		int positiontop = 130;
		int valueadd = 100;
		int breite_10 = (Variables.WIDTH/100*10);
		int hoehe_10 = (Variables.HEIGHT/100*10);
		int buttonbreite = 150;
		int buttonhoehe = 40;
		int buttonround = 10;
		
		g.setColor(Variables.white);
		g.fillRect(breite_10+1, hoehe_10+1, Variables.WIDTH/100*80 ,Variables.HEIGHT/100*80);
		
		g.setColor(Variables.red);
		g.fillRoundRect(positionleft, positiontop-5, buttonbreite, buttonhoehe, buttonround);
		
		g.setColor(Variables.red);
		g.fillRoundRect(positionleft, positiontop+(valueadd*1)-5, buttonbreite, buttonhoehe, buttonround);
		
		g.setColor(Variables.red);
		g.fillRoundRect(positionleft, positiontop+(valueadd*2)-5, buttonbreite, buttonhoehe, buttonround);
		
		g.setColor(Variables.red);
		g.fillRoundRect(positionleft, positiontop+(valueadd*3)-5, buttonbreite, buttonhoehe, buttonround);
		
		g.setColor(Variables.red);
		g.fillRoundRect(positionleft, positiontop+(valueadd*4)-5, buttonbreite, buttonhoehe, buttonround);
		
		positionleft = 310-20;
		positiontop = 132;
		g.setColor(Variables.black);
		trueTypeFont.drawString(positionleft, positiontop,"Spiel Start", Variables.black);
		trueTypeFont.drawString(positionleft, positiontop+valueadd,"Einstellungen", Variables.black);
		trueTypeFont.drawString(positionleft, positiontop+valueadd*2,"Impressum", Variables.black);
		trueTypeFont.drawString(positionleft, positiontop+valueadd*3,"Help", Variables.black);
		trueTypeFont.drawString(positionleft, positiontop+valueadd*4,"Spiel beenden", Variables.black);
		
		g.setColor(Variables.red);
		g.fillRoundRect(500-20, 130-5, 600, 440, 10);
		
		positionleft = 510-20;
		switch(pos)
		{
			case 1:
				trueTypeFont.drawString(positionleft, positiontop, "Hauptmenü von Big-Bum-Bang", Variables.black);
			break;
			case 2:
				trueTypeFont.drawString(positionleft, positiontop, "Extras", Variables.black);
			break;
			case 3:
				trueTypeFont.drawString(positionleft, positiontop, "Impressum zum Spiel", Variables.black);
			break;
			case 4:
				trueTypeFont.drawString(positionleft, positiontop, "Über das Spiel und die Steurung", Variables.black);
			break;
			case 5:
				trueTypeFont.drawString(positionleft, positiontop, "Spiel beenden", Variables.black);
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
	private void drawGameConfiguration(Graphics g, GameContainer gc)
	{
		if(cursorh ==-1) cursorh=0;
		else cursorh = cursorh;
		if(cursorv ==-1) cursorv=1;
		else cursorv = cursorv;
		
		int links=200;
		int oben=200;
		int breite_10 = (Variables.WIDTH/100*10);
		int hoehe_10 = (Variables.HEIGHT/100*10);
		int valueadd = 50;
		int valuelinksadd = 200;
		
		g.setColor(Variables.white);
		g.fillRect(breite_10+1, hoehe_10+1, Variables.WIDTH/100*80 ,Variables.HEIGHT/100*80);
		trueTypeFont.drawString(links, oben, "Spielmodus:", Variables.black);
		
		//g.setColor(Color.black);
		//g.fillRect(links+200,oben, 300 ,trueTypeFont.getHeight());
		
		//TextField field2;
		//field2 = new TextField(gc,null,links+200,oben, 300 ,trueTypeFont.getHeight());
		//field2.render(gc, g);
		//field2.setBounds(links+200,oben, 300 ,trueTypeFont.getHeight());
		oben+=valueadd;
		
		trueTypeFont.drawString(links, oben, "Map:", Variables.black);
		
		if (cursorv!=1) g.setColor(Variables.black);
		else g.setColor(Variables.red);
		g.fillRect(links+valuelinksadd,oben, 300 ,trueTypeFont.getHeight());
		trueTypeFont.drawString(links+valuelinksadd, oben, maps.get(einstellung[0]).getName(), Variables.white);
		
		oben+=valueadd;
		
		trueTypeFont.drawString(links, oben, "Powerups/Powerdowns:", Variables.black);
		
		if (cursorv!=2) g.setColor(Variables.black);
		else g.setColor(Variables.red);
		g.fillRect(links+valuelinksadd,oben, 300 ,trueTypeFont.getHeight());
		trueTypeFont.drawString(links+valuelinksadd, oben, powerdowns.get(einstellung[1]).getName(), Variables.white);
		
		oben+=valueadd;
		
		trueTypeFont.drawString(links, oben, "Gamemode:", Variables.black);
		
		if (cursorv!=3) g.setColor(Variables.black);
		else g.setColor(Variables.red);
		g.fillRect(links+valuelinksadd,oben, 300 ,trueTypeFont.getHeight());
		trueTypeFont.drawString(links+valuelinksadd, oben, GAMEMODE.values()[einstellung[2]].toString(), Variables.white);
		oben+=valueadd;
		
		if (cursorv!=4) g.setColor(Variables.black);
		else g.setColor(Variables.red);
		g.fillRect(links+valuelinksadd,oben, 300 ,trueTypeFont.getHeight());
		trueTypeFont.drawString(links+valuelinksadd, oben, "Start Game", Variables.white);
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
		g.setColor(Variables.black);
		g.fillRect(x,y,w ,h);
		
		g.setColor(Variables.white);
		g.fillRect(x+5,y+5,w-10 ,h-10);
		
		g.setColor(Variables.black);
		g.fillRect(x+10,y+10,w-20 ,h-20);
			
		MapListe ml = new MapListe();
		//int rows=ml.getMap().size();
		//int cols=15;
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
		float hoehe=(h-20)/vl.size();

		y=y+10-hoehe+((h-20-hoehe*vl.size())/2);
		x=x+10-breite+((w-20-breite*vl.get(1).size())/2);
		for(ArrayList<Wand> v: vl)
        {
		   float yx=0+x;
     	   for(Wand b:v)
     	   {
     		  if(b.getStein().equals("0"))
     		  {
     			 g.setColor(Variables.gray);
     			 g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		     			  
     		  }
     		  else if(b.getStein().equals("1"))
     		  {
     			 g.setColor(Variables.green);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);			  
     		  }
     		  else if(b.getStein().equals("2"))
     		  {
     			 g.setColor(Variables.red);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		    			  
     		  }
     		  else if(b.getStein().equals("3"))
     		  {
     			 g.setColor(Variables.blue);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);					  
     		  }
     		  else if(b.getStein().equals("4"))
     		  {
     			 g.setColor(Variables.white);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);					  
     		  }
     		  else if(b.getStein().equals("5"))
     		  {
     			 g.setColor(Variables.yellow);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		   			  
     		  }
     		  else if(b.getStein().equals("6"))
     		  {
     			 g.setColor(Variables.white);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		  			  
     		  }
     		  else if(b.getStein().equals("7"))
     		  {
     			 g.setColor(Variables.magenta);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		     			  
     		  }
     		  else if(b.getStein().equals("8"))
     		  {
     			 g.setColor(Variables.blue);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		 			  
     		  }
     		  else if(b.getStein().equals("9"))
     		  {
     			 g.setColor(Variables.red);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		   			  
     		  }
     		 else if(b.getStein().equals("x"))
     		 {
     			 g.setColor(Variables.green);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);	
     		 }
     		 else
     		 {
     			 g.setColor(Variables.darkGray);
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
		g.setColor(Variables.black);
		g.fillRect(x,y,w ,h);
		
		g.setColor(Variables.white);
		g.fillRect(x+5,y+5,w-10 ,h-10);
		
		g.setColor(Variables.black);
		g.fillRect(x+10,y+10,w-20 ,h-20);
			
		
		//int rows=k.getKarte().length;
		//int cols=k.getKarte()[0].length;
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
		float hoehe=(h-20)/vl.size();

		y=y+10-hoehe+((h-20-hoehe*vl.size())/2);
		x=x+10-breite+((w-20-breite*vl.get(1).size())/2);
		for(ArrayList<Wand> v: vl)
        {
		   float yx=0+x;
     	   for(Wand b:v)
     	   {
     		  if(b.getStein().equals("0"))
     		  {
     			 g.setColor(Variables.gray);
     			 g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		     			  
     		  }
     		  else if(b.getStein().equals("1"))
     		  {
     			 g.setColor(Variables.green);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);			  
     		  }
     		  else if(b.getStein().equals("2"))
     		  {
     			 g.setColor(Variables.red);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		    			  
     		  }
     		  else if(b.getStein().equals("3"))
     		  {
     			 g.setColor(Variables.blue);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);					  
     		  }
     		  else if(b.getStein().equals("4"))
     		  {
     			 g.setColor(Variables.white);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);					  
     		  }
     		  else if(b.getStein().equals("5"))
     		  {
     			 g.setColor(Variables.yellow);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		   			  
     		  }
     		  else if(b.getStein().equals("6"))
     		  {
     			 g.setColor(Variables.white);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		  			  
     		  }
     		  else if(b.getStein().equals("7"))
     		  {
     			 g.setColor(Variables.magenta);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		     			  
     		  }
     		  else if(b.getStein().equals("8"))
     		  {
     			 g.setColor(Variables.blue);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		 			  
     		  }
     		  else if(b.getStein().equals("9"))
     		  {
     			 g.setColor(Variables.red);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);		   			  
     		  }
     		 else if(b.getStein().equals("x"))
     		 {
     			 g.setColor(Variables.black);
     			g.fillRect(yx+breite,y+hoehe,breite ,hoehe);	
     		 }
     		 else
     		 {
     			 g.setColor(Variables.darkGray);
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
	private void initMaps()
	{
		Mapdummie mp= new Mapdummie();
		this.maps=mp.getList();
	}
	
	/**
	 * 	Dummie Powerups werden geladen
	 */
	private void initPowerups()
	{
		Powerupdummie pud=new Powerupdummie();
		this.powerups=pud.getList();
	}
	
	/**
	 * 	Dummie Powerdowns werden geladen
	 */
	private void initPowerdowns()
	{
		Powerdowndummie pdd=new Powerdowndummie();
		this.powerdowns=pdd.getList();
	}

	private void initBomb() throws SlickException
	{
		bomb = new Image(Variables.res+"bomb.png");
	}

	private void initState(STATE state) 
	{
		this.state=state;
	}
	
	private void initFont() 
	{
		trueTypeFont = new TrueTypeFont(new Font(Variables.letter, Variables.font, Variables.charsize), true);
	}
	
	private void RenderStateGame(GameContainer container, Graphics g) throws SlickException
	{
		if(bm==null)
		{
			bm = Game.getInstance();
			bm.init(container);
		}
		
		bm.render(container, g);
	}
	
	private void RenderStatePause(Graphics g)
	{
		drawWindow(g,"Pause");
		int sideposition = (Variables.WIDTH/2)-200;
		int haelftehoehe = (Variables.HEIGHT/2);
		
		if(ppos==1)trueTypeFont.drawString(sideposition, haelftehoehe-100,"Resume", Variables.black);
		else trueTypeFont.drawString(sideposition, haelftehoehe-100,"Resume", Variables.red);
		if(ppos==2)trueTypeFont.drawString(sideposition, haelftehoehe-50,"Einstellungen", Variables.black);
		else trueTypeFont.drawString(sideposition, haelftehoehe-50,"Einstellungen", Variables.red);
		if(ppos==3)trueTypeFont.drawString(sideposition, haelftehoehe,"Help", Variables.black);
		else trueTypeFont.drawString(sideposition, haelftehoehe,"Help", Variables.red);
		if(ppos==4)trueTypeFont.drawString(sideposition, haelftehoehe+50,"Hauptmenü", Variables.black);
		else trueTypeFont.drawString(sideposition, haelftehoehe+50,"Hauptmenü", Variables.red);
		if(ppos==5)trueTypeFont.drawString(sideposition, haelftehoehe+100,"Spiel beenden", Variables.black);
		else trueTypeFont.drawString(sideposition, haelftehoehe+100,"Spiel beenden", Variables.red);
	}
	
	private void UpdateStateGame(GameContainer container,int delta,Input input) throws SlickException 
	{
		bm.update(container, delta);
		if (input.isKeyPressed(Input.KEY_P)) 
		{
			state=STATE.PAUSE;
		}
	}
	
	private void UpdateStateHelp(Input input)
	{
		if (input.isKeyPressed(Input.KEY_ESCAPE)) 
		{
			state=STATE.MENU;
		}
	}
	
	private void UpdateStateImpress(Input input)
	{
		if (input.isKeyPressed(Input.KEY_ESCAPE)) 
		{
			state=STATE.MENU;
		}
	}
	
	private void UpdateStateMenu(Input input,int keyjump, GameContainer container)
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
	
	private void UpdateStatePause(Input input, GameContainer container)
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
	
	private void UpdateStateSettings(Input input)
	{
		if (input.isKeyPressed(Input.KEY_ESCAPE)) 
		{
			state=STATE.MENU;
		}
	}
	
	private void UpdateStateGameconfiguration(Input input)
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
}
