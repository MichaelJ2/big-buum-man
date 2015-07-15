package at.big_buum_man.server.gui;

import at.big_buum_man.server.ServerMethods;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Font;

import at.big_buum_man.server.network.NetworkServer;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.SlickException;

import at.big_buum_man.server.gui.enums.GAMEMODE;
import at.big_buum_man.server.gui.helper.Variables;
import at.big_buum_man.server.gui.objects.Bombe;
import at.big_buum_man.server.gui.objects.MapListe;
import at.big_buum_man.server.gui.objects.Player;
import at.big_buum_man.server.gui.objects.Powerdown;
import at.big_buum_man.server.gui.objects.Powerup;
import at.big_buum_man.server.gui.objects.Wand;

/***
 * @version 1.0
 * @author Michael Januschek
 *
 */
public class Bomberman extends BasicGame implements Runnable ,ServerMethods
{
	private NetworkServer ns;														//NetzwerkServer für kommunikation
	private ArrayList<InetAddress> adressen = new ArrayList<InetAddress>();			//IP-Adressen Liste
	
	private GAMEMODE gamemode; 														//Game mode fÃ¼r die bestimmten Spielearten
	
	//public static final int WIDTH = Display.getDesktopDisplayMode().getWidth();		//Breite des Bildschirms
	//public static final int HEIGHT = Display.getDesktopDisplayMode().getHeight();	//Höhe des Bildschirms
	
	private MapListe ml = new MapListe();
	
	//private int rows=ml.getMap().size();
	//private int cols=15;
	
	private long starttime; 														//Startzeit vom Spiel
	private long currenttime; 														//Aktuelle zeit vom Spiel

	private TrueTypeFont trueTypeFont;
	private TrueTypeFont trueTypeFont3;
	
	private ArrayList<TrueTypeFont> pliste;											//Typefont Liste zum Zeichnen von Buchstaben
	private ArrayList<Player> playerliste;											//Playerliste
	//private ArrayList<Player> punkteliste;
	
	private ArrayList<Wand> hl;
	private ArrayList<ArrayList<Wand>> vl; 
	
	private ArrayList<Bombe> bomben; 												//Bombenliste auf dem Feld
	private ArrayList<Powerup> powerups; 											//Powerupsliste auf dem Feld
	private ArrayList<Powerdown> powerdowns; 										//Powerdownsleste auf dem Feld
	
	private Image spriteSheetImage;
	private Image bomb;
	private Image ipower;
	private Image iminder;
	/*
	private int spriteSheetWidth;
	private int spriteSheetHeight;
	private int spriteWidth=50;
	private int spriteHeight=50;
	private int spritesPerRow=4;
	private int spritesPerColumn=4;
	private SpriteSheet spriteSheet;
	*/
	//private int charsize=20;														//Schriftgröße für Ausgabe
	//private int blockwidth=50;														//Feldbreite
	//private int blockheight=50;														//Feldhöhe
	//private int sidebarwidth=Variables.WIDTH/100*20;											//Startposition vom Spielfeld
	//private int ytop=(Variables.HEIGHT/2)-(rows*Variables.BLOCKHEIGHT/2);								
	//private int xleft=sidebarwidth+(((Variables.WIDTH-sidebarwidth)/2)-(cols*Variables.BLOCKWIDTH/2));
	
	/*
	private Color gray=Color.gray;
	private Color green=Color.green;
	private Color red=Color.red;
	private Color blue=Color.blue;
	private Color white=Color.white;
	private Color yellow=Color.yellow;
	private Color magenta=Color.magenta;
	private Color darkGray=Color.darkGray;
	private Color lightGray=Color.lightGray;
	private Color black=Color.black;
	
	private int font=Font.BOLD;
	private String letter="Arial";
	*/
	private static Bomberman instance = null;										//Instanz von Bomberman
	
	/***
	 * 	Summary:
	 * 	Bomberman Konstruktor
	 * 
	 * 	Description:
	 * 
	 */
	private Bomberman() 
	{
		super("Bomberman"); //Window Title
	}
	
	/***
	 * 	Summary:
	 * 	getInstance
	 * 
	 * 	Description:
	 * 	
	 * 	@return Bomberman
	 */
	public static synchronized Bomberman getInstance() 
	{
	    if (Bomberman.instance == null) 
	    {
	    	Bomberman.instance = new Bomberman();
	    }
	    return Bomberman.instance;
	 }

	
	/***
	 * 	Summary:
	 * 	Hauptklasse zum starten vom Spiel
	 * 
	 * 	Description:
	 * 
	 * 	@param args
	 * 	@throws SlickException
	 */
	/*
	public static void main(String[] args) throws SlickException 
	{
		Bomberman bm = new Bomberman();
		AppGameContainer container = new AppGameContainer(bm);
		Thread t = new Thread(bm);
		t.start();
		container.setDisplayMode(WIDTH, HEIGHT, true);
		container.setClearEachFrame(true);
		container.setMinimumLogicUpdateInterval(25);
		container.setTargetFrameRate(60);
		container.setShowFPS(true);
		container.start();
	}
	*/
	
	/**	
	 * 	Summary: 
	 * 	In dieser Funktion werden alle Resourcen und Grafiken neu gezeichnet in einem bestimmten Intervall
	 * 
	 * 	Description:
	 * 
	 * 	@param container = 
	 * 	@param g = 
	 * 
	 * 	
	 */
	@Override
	public synchronized void render(GameContainer container, Graphics g) throws SlickException 
	{
		RenderSidebar(g);		//Render of the Sidebar
		RenderMap(g);			//Render of the Map
		RenderPlayer(g);		//Render of the Players
		RenderBomb(g);			//Render of the Bombs
		RenderPowerups(g);		//Render of the Powerups
		RenderPowerdowns(g);		//Render of the Powerdowns
		RenderTime();
	}

	/**
	 * 	Summary:
	 * 	Initial wird das Spiel hier erstellt und alle Resourcen geladen
	 * 	
	 * 	Description:
	 * 
	 * 	@param container = 
	 * 	
	 */
	@Override
	public synchronized void init(GameContainer container) throws SlickException 
	{
		InitTime();
		InitFonts();
		InitResources();
	    InitLists();
		InitTestPlayer();
		InitFontLists();
		InitMap();
		InitServer();
		InitPlayer();
	}
	
	/**
	 * 	Summary:
	 * 	Hier wird das Spiel geupdated und resourcen verwaltet - Spiellogik
	 * 
	 * 	Description:
	 * 
	 * 	@param container =
	 * 	@param delta = 
	 * 	
	 */
	@Override
	public synchronized void update(GameContainer container, int delta) throws SlickException 
	{
		Eventinput(container); 		//Eventabfrage = Tastaturabfrage
		UpdatePlayer(delta);		//Update Player 
		UpdateBombs();			//Update Bombs
		UpdatePowerups();		//Update Powerups
		UpdatePowerdowns();		//Update Powerdowns
	}
	
	/***
	 * 	Summary:
	 * 	Hier wird eine Bombe hinzugefÃ¼gt in das Spiel
	 * 
	 * 	Description:
	 * 
	 * 	@param x = X Coordinate
	 * 	@param y = Y Coordinate
	 * 	@param sprungy = HÃ¶he eines Feldes
	 * 	@param sprungx = Breite eines Feldes
	 * 	@param p = Player der diese Bombe setzt
	 * */
	public synchronized void addBombe(int x,int sprungx, int y,int sprungy, Player p) throws SlickException
	{
		Bombe bombe=new Bombe(bomb, 2000);
		bombe.setSystemZeit(System.currentTimeMillis());
		bombe.setBesitzer(p);
		bombe.setX(x);
		bombe.setY(y);
		bombe.setRange(2);
		bombe.setSprungX(sprungx);
		bombe.setSprungY(sprungy);
		bombe.setAnfangx(Variables.xleft);
		bombe.setAnfangy(Variables.ytop);
		bomben.add(bombe);
	}

	/***
	 * 	Summary:
	 * 	Bombe wird gesetzt und explodiert nun richtig
	 * 
	 * 	Description:
	 * 
	 * 	@param x = 
	 * 	@param y =
	 * 	@param b =
	 * 
	 * */
	public synchronized void setbombe(int x, int y,Bombe b) 
	{
		int side1=0;
		int side2=0;
		int side3=0;
		int side4=0;
		
		for(int i=0;i<=b.getRange();i++)
		{
			if(y-i>0) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=((playerliste.get(p).getX()-Variables.xleft)/Variables.BLOCKWIDTH);
					int py=((playerliste.get(p).getY()-Variables.ytop)/Variables.BLOCKHEIGHT);
					
					if(px==x&&py==y-i&&side1==0)
					{
						b.getBesitzer().plus();
						side1=1;
						//System.out.println("1 Bomb find: X:"+((b.getX()-xleft)/blockwidth)+" Y:"+(((b.getY()-ytop)/blockheight)-i)+" - Player "+playerliste.get(p).getName()+" X:"+((playerliste.get(p).getX()-xleft)/blockwidth)+" Y:"+(((playerliste.get(p).getY()-ytop)/blockheight)));
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y-i).get(x).getStein().equals("0")&&side1==0) 
				{
					b.getBesitzer().plus();
					side1=1;
					//System.out.println("2 Bomb find: X:"+((b.getX()-xleft)/blockwidth)+" Y:"+(((b.getY()-ytop)/blockheight)-i));
					vl.get(y-i).get(x).setStein(1+"");
				}
			}
			
			if(y+i<Variables.rows-1) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=((playerliste.get(p).getX()-Variables.xleft)/Variables.BLOCKWIDTH);
					int py=((playerliste.get(p).getY()-Variables.ytop)/Variables.BLOCKHEIGHT);
					
					if(px==x&&py==y+i&&side2==0)
					{
						//System.out.println("PX:"+px+" x:"+x+" PY:"+py+" y:"+(y+i));
						b.getBesitzer().plus();
						side2=1;
						//System.out.println("3 Bomb find: X:"+((b.getX()-xleft)/blockwidth)+" Y:"+(((b.getY()-ytop)/blockheight)+i) +" - Player "+playerliste.get(p).getName()+" X:"+((playerliste.get(p).getX()-xleft)/blockwidth)+" Y:"+(((playerliste.get(p).getY()-ytop)/blockheight)));
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y+i).get(x).getStein().equals("0")&&side2==0)
				{
					b.getBesitzer().plus();
					side2=1;
					//System.out.println("4 Bomb find: X:"+((b.getX()-xleft)/blockwidth)+" Y:"+(((b.getY()-ytop)/blockheight)+i));
					vl.get(y+i).get(x).setStein(1+"");
				}
			}
			
			if(x-i>0) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=((playerliste.get(p).getX()-Variables.xleft)/Variables.BLOCKWIDTH);
					int py=((playerliste.get(p).getY()-Variables.ytop)/Variables.BLOCKHEIGHT);
					
					if(px==x-i&&py==y&&side3==0)
					{
						b.getBesitzer().plus();
						side3=1;
						//System.out.println("5 Bomb find: X:"+(((b.getX()-xleft)/blockwidth)-i)+" Y:"+((b.getY()-ytop)/blockheight)+" - Player "+playerliste.get(p).getName()+" X:"+((playerliste.get(p).getX()-xleft)/blockwidth)+" Y:"+(((playerliste.get(p).getY()-ytop)/blockheight)));
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y).get(x-i).getStein().equals("0")&&side3==0)
				{
					b.getBesitzer().plus();
					side3=1;
					//System.out.println("6 Bomb find: X:"+(((b.getX()-xleft)/blockwidth)-i)+" Y:"+((b.getY()-ytop)/blockheight));
					vl.get(y).get(x-i).setStein(1+"");
				}
			}
			
			if(x+i<Variables.cols-1) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=((playerliste.get(p).getX()-Variables.xleft)/Variables.BLOCKWIDTH);
					int py=((playerliste.get(p).getY()-Variables.ytop)/Variables.BLOCKHEIGHT);
					
					if(px==x+i&&py==y&&side4==0)
					{
						b.getBesitzer().plus();
						side4=1;
						//System.out.println("7 Bomb find: X:"+(((b.getX()-xleft)/blockwidth)+i)+" Y:"+((b.getY()-ytop)/blockheight)+" - Player "+playerliste.get(p).getName()+" X:"+((playerliste.get(p).getX()-xleft)/blockwidth)+" Y:"+(((playerliste.get(p).getY()-ytop)/blockheight)));
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y).get(x+i).getStein().equals("0")&&side4==0)
				{
					b.getBesitzer().plus();
					side4=1;
					//System.out.println("8 Bomb find: X:"+(((b.getX()-xleft)/blockwidth)+i)+" Y:"+((b.getY()-ytop)/blockheight));
					vl.get(y).get(x+i).setStein(1+"");
				}
			}			
		}
		
		side1=0;
		side2=0;
		side3=0;
		side4=0;
	}
	
	@Override
	public void run() 
	{
		
	}


	/**
	 * 	Summary:
	 * 	Nachricht wird von Spieler erhalten und Spieler wird bewegt/verÃ¤ndert
	 * 	
	 * 	Description:
	 * 
	 * 	@param client =
	 * 	@param message =
	 */
	@Override
	public void processMessage(InetAddress client, String message) 
	{
		for(int p=0;p<playerliste.size();p++)
		{
			if(playerliste.get(p).getAdresse().getHostAddress().equals(client.getHostAddress()))
			{
				if(message.equals("left"))
				{
					playerliste.get(p).left();
				}
				else if(message.equals("right"))
				{
					playerliste.get(p).right();
				}
				else if(message.equals("up"))
				{
					playerliste.get(p).up();
				}
				else if(message.equals("down"))
				{
					playerliste.get(p).down();
				}
				else if(message.equals("a"))
				{
					playerliste.get(p).buttonA();
				}
				else if(message.equals("b"))
				{
					playerliste.get(p).buttonB();
				}
			}
		}
	}

	/**
	 * 	Summary:
	 *  	neuer Client hinzufÃ¼gen in Liste und ins Spiel
	 * 
	 * 	Description:
	 * 
	 * 	@param client
	 */
	@Override
	public void registerNewClient(InetAddress client) 
	{
		adressen.add(client);
		neuerSpieler(client);
	}

	/**
	 * 	Summary:
	 *  	Hier wird die Zeit erreichnet die verstrichen ist.
	 * 
	 * 	Description:
	 * 
	 * 	@param start
	 * 	@param current
	 */
	private String timeconvert(long start, long current)
	{
		String time;
		long timestamp = ((current - start)/1000);
		if(timestamp>=60)
		{
			if(timestamp%60==0)
			{
				time = (timestamp/60)+":00";
			}
			else
			{
				if(timestamp%60>0&&timestamp%60<10)
					time = (timestamp/60)+":0"+(timestamp%60);
				else
					time = (timestamp/60)+":"+(timestamp%60);
			}
			time= time +" Minuten";
		}
		else
		{
			time=""+((current - start)/1000)+" Sekunden";
		}
		return time;
	}

	/***
	 * 	Summary:
	 * 	Neuer Spieler wird dem Spiel hinzugefÃ¼gt
	 * 
	 * 	Description:
	 * 
	 * 	@param adresse = InetAddress wird Ã¼bergeben fÃ¼r den Player
	 * 	@return Player
	 */
	private synchronized Player neuerSpieler(InetAddress adresse)  
	{
		Player p=null;
		try 
		{
			p = new Player(new Image("res/player.png"));
			p.setAdresse(adresse);
			p.setSprungX(Variables.BLOCKWIDTH);
			p.setSprungY(Variables.BLOCKHEIGHT);
			p.setAnfangx(Variables.xleft+Variables.BLOCKWIDTH);
			p.setAnfangy(Variables.ytop+25);
			p.setMapn(vl);
			
			for(int px=0;px<15;px++)
				for(int py=0;py<10;py++)
					if(vl.get(py).get(px).getStein().equals("x"))
						{
						
						p.setposition(	Variables.xleft+px*Variables.BLOCKWIDTH+Variables.BLOCKWIDTH, 
								Variables.ytop+py*Variables.BLOCKHEIGHT+25);
						//System.out.println("Position:"+((WIDTH/100*20)+((WIDTH-(WIDTH/100*20))/2)-(cols*50/2)+px*50+50)+" | "+((HEIGHT/2)-(rows*50/2)+py*50+25));
						}
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
		p.setName("Neuer Spieler");
		
		playerliste.add(p);
		return p;
	}

	
	/////////////////////////////////////////////////
	// Hier werden die Methoden Definiert die dann in der Update routine verwendet werden
	/////////////////////////////////////////////////
	private void Eventinput(GameContainer container)
	{
		int pl=0;
		Input input=container.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) 
		{
			container.exit();
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT)) 
		{
			playerliste.get(pl).left();
		}
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) 
		{
			playerliste.get(pl).right();
		}
		
		if (input.isKeyPressed(Input.KEY_UP)) 
		{
			playerliste.get(pl).up();
		}
		
		if (input.isKeyPressed(Input.KEY_DOWN)) 
		{
			playerliste.get(pl).down();
		}
		
		if (input.isKeyPressed(Input.KEY_SPACE)) 
		{
			playerliste.get(pl).buttonA();
		}
	}

	private void UpdatePlayer(int delta)
	{
		for(Player p:playerliste)
		{
			p.update(delta);
		}
	}
	
	private void UpdateBombs()
	{
		if(!(bomben.isEmpty()))
		{
			for (int i=0;i<bomben.size();i++) 
			{
				Bombe b = bomben.get(i);
				if(b.getZeit()+b.getSystemZeit()<System.currentTimeMillis())
				{
					setbombe(((b.getX()-(Variables.xleft))/b.getSprungX()),((b.getY()-(Variables.ytop))/b.getSprungY()),b);
					//createExplosions();
					bomben.remove(b);
				}	
			}
		}
	}
	
	private void UpdatePowerups()
	{
		if(!(powerups.isEmpty()))
		{
			for (int i=0;i<powerups.size();i++) 
			{
				Powerup up = powerups.get(i);
				for (int j=0;j<playerliste.size();j++) 
				{
					Player u = playerliste.get(j);
					if(up.getX()==u.getX() && up.getY()==u.getY())
					{
						u.addPowerup(up);
						powerups.remove(up);
					}	
				}
			}
			
			for (int i=0;i<powerups.size();i++) 
			{
				Powerup up = powerups.get(i);
				if(up.getZeit()+up.getSystemZeit()<System.currentTimeMillis())
				{
					powerups.remove(up);
				}	
			}
		}
		else
		{
			Random ra= new Random();
			int randomzahl = ra.nextInt(1000);
			
			if(randomzahl>950)
			{
				Random rax= new Random();
				int rx = rax.nextInt(Variables.cols);
				
				Random ray= new Random();
				int ry = ray.nextInt(Variables.rows);
				
				if(!vl.get(ry).get(rx).getStein().equals("0"))
				{
					Powerup powerup=new Powerup(ipower);
					powerup.setSystemZeit(System.currentTimeMillis());
					powerup.setX(Variables.xleft+rx*Variables.BLOCKWIDTH);
					powerup.setY(Variables.ytop+ry*Variables.BLOCKHEIGHT);
					powerups.add(powerup);
				}
			}
			else
			{
				//System.out.println(randomzahl+" keine Powerup");
			}
		}
	}

	private void UpdatePowerdowns()
	{
		if(!(powerdowns.isEmpty()))
		{
			for (int i=0;i<powerdowns.size();i++) 
			{
				Powerdown down = powerdowns.get(i);
				for (int j=0;j<playerliste.size();j++) 
				{
					Player u = playerliste.get(j);
					if(down.getX()==u.getX() && down.getY()==u.getY())
					{
						u.addPowerdown(down);
						powerdowns.remove(down);
					}	
				}
			}
			
			for (int i=0;i<powerdowns.size();i++) 
			{
				Powerdown down = powerdowns.get(i);
				if(down.getZeit()+down.getSystemZeit()<System.currentTimeMillis())
				{
					powerdowns.remove(down);
				}	
			}
		}
		else
		{
			Random ra= new Random();
			int randomzahl = ra.nextInt(1000);
			
			if(randomzahl>980)
			{
				Random rax= new Random();
				int rx = rax.nextInt(Variables.cols);
				
				Random ray= new Random();
				int ry = ray.nextInt(Variables.rows);
				if(!vl.get(ry).get(rx).getStein().equals("0"))
				{
					Powerdown powerdown=new Powerdown(iminder);
					powerdown.setSystemZeit(System.currentTimeMillis());
					powerdown.setX(Variables.xleft+rx*Variables.BLOCKWIDTH);
					powerdown.setY(Variables.ytop+ry*Variables.BLOCKHEIGHT);
					powerdowns.add(powerdown);
				}
			}
			else
			{
				//System.out.println(randomzahl+" keine Powerdown");
			}
		}
	}

	/////////////////////////////////////////////////
	// Hier werden die Methoden Definiert die dann in der Init routine verwendet werden
	/////////////////////////////////////////////////
	private void InitMap()
	{
		int y=0;
		vl=new ArrayList<ArrayList<Wand>>();
		for(String[] s:ml.getMap())
    	{
    		hl=new ArrayList<Wand>();
    		int x=0;
	   		for(String o:s)
	   		{
		  		Wand w=new Wand(o);
		  		x=x+Variables.BLOCKWIDTH;
				if(o.equals("x"))
		 		{
			  		//for(Player p:playerliste)
			  			//p.setposition(sidebarwidth+x-blockwidth,y+blockheight);
		 		}
			 	hl.add(w);
	   		}
   		y=y+Variables.BLOCKHEIGHT;
   		vl.add(hl);
    	}
	}

	private void InitServer()
	{
		ns=new NetworkServer(this);
		try 
		{	ns.startServer();		} 
		catch (Exception e) 
		{	e.printStackTrace();	}
		
		try 
		{	ns.startAnnounce();		} 
		catch (Exception e) 
		{	e.printStackTrace();	}
	}

	private void InitPlayer()
	{
		for(Player p:playerliste)
		{
			p.setSprungX(Variables.BLOCKWIDTH);
			p.setSprungY(Variables.BLOCKHEIGHT);
			p.setMapn(vl);
		}
	}

	private void InitFontLists()
	{
		for(int i=0;i<playerliste.size();i++)
			pliste.add(new TrueTypeFont(new Font(Variables.letter, Variables.font, Variables.charsize), true));
	}
	
	private void InitTestPlayer() throws SlickException 
	{
		Player player = new Player(new Image("res/player.png"));
		player.setName("Michael");
		player.setColor(Variables.red);
		player.setposition(975, 340);
		player.setAnfangx(Variables.xleft);
		player.setAnfangy(Variables.ytop);
		player.setSprungX(Variables.BLOCKWIDTH);
		player.setSprungY(Variables.BLOCKHEIGHT);
		player.setMapn(vl);
		
		try 
		{ player.setAdresse(InetAddress.getByName("192.168.1.1")); } 
		catch (UnknownHostException e1) 
		{ e1.printStackTrace(); }

		Player player1 = new Player(new Image("res/player2.png"));
		player1.setName("Gerald");
		player1.setColor(Variables.green);
		player1.setposition(975, 440);
		player1.setAnfangx(Variables.xleft);
		player1.setAnfangy(Variables.ytop);
		player1.setSprungX(Variables.BLOCKWIDTH);
		player1.setSprungY(Variables.BLOCKHEIGHT);
		player1.setMapn(vl);
		try 
		{ player1.setAdresse(InetAddress.getByName("192.168.1.1")); } 
		catch (UnknownHostException e1) 
		{ e1.printStackTrace(); }
		
		player1.plus();
		
		playerliste.add(player);
		playerliste.add(player1);
	}	
	
	private void InitLists()
	{
		bomben=new ArrayList<Bombe>(); //Bombenliste erstellen
		powerups=new ArrayList<Powerup>(); //Powerupsliste erstellen
		powerdowns=new ArrayList<Powerdown>(); //Powerdownsliste erstellen
		playerliste =new ArrayList<Player>(); //Playerliste erstellen
		pliste=new ArrayList<TrueTypeFont>(); //Fontliste erstellen
	}
	
	private void InitResources() throws SlickException 
	{
		spriteSheetImage = new Image("res/p.png");
		bomb = new Image("res/bomb.png");
		ipower = new Image("res/Powerup.png");
		iminder = new Image("res/Powerdown.png");
	}   	
	
	private void InitFonts()
	{
		trueTypeFont = new TrueTypeFont(new Font(Variables.letter, Variables.font, Variables.charsize), true);
		trueTypeFont3 = new TrueTypeFont(new Font(Variables.letter, Variables.font, Variables.charsize), true);
	}
	
	private void InitTime()
	{
		starttime = System.currentTimeMillis();
	}	
	
	/////////////////////////////////////////////////
	// Hier werden die Methoden Definiert die dann in der render routine verwendet werden
	/////////////////////////////////////////////////
	private void RenderMap(Graphics g)
	{
		g.setColor(Variables.green);
		g.drawRect(Variables.xleft-5,Variables.ytop-5, (Variables.cols*Variables.BLOCKWIDTH)+10, (Variables.rows*Variables.BLOCKHEIGHT)+10);
		
		int y=0+Variables.ytop;
		for(ArrayList<Wand> v: vl)
	    	{
			int x=0+Variables.xleft;
	     	   	for(Wand h:v)
	     	   	{
		     		  if(h.getStein().equals("0"))
		     			  g.setColor(Variables.gray);
		     		  else if(h.getStein().equals("1"))
		     			  g.setColor(Variables.green);
		     		  else if(h.getStein().equals("2"))
		     			  g.setColor(Variables.red);
		     		  else if(h.getStein().equals("3"))
		     			  g.setColor(Variables.blue);
		     		  else if(h.getStein().equals("4"))
		     			  g.setColor(Variables.white);
		     		  else if(h.getStein().equals("5"))
		     			  g.setColor(Variables.yellow);
		     		  else if(h.getStein().equals("6"))
		     			  g.setColor(Variables.white);
		     		  else if(h.getStein().equals("7"))
		    			  g.setColor(Variables.magenta);
		     		  else if(h.getStein().equals("8"))
		    			  g.setColor(Variables.blue);
		     		  else if(h.getStein().equals("9"))
		     			  g.setColor(Variables.red);
		     		 else if(h.getStein().equals("x"))
		     		 {
		      			  g.setColor(Variables.green);
		     		 }
	 		   		else
		 			  g.setColor(Variables.darkGray);
		     		  
		     		  h.setX(x);//+blockwidth);
		     		  h.setY(y);//+blockheight/2);
		     		  h.draw(g);
		     		  
		     		  x=x+Variables.BLOCKWIDTH;
	     	   	}
	     	   	y=y+Variables.BLOCKHEIGHT;
	        }
	}

	private void RenderPlayer(Graphics g)
	{
		g.setColor(Variables.red);
		for(Player p:playerliste)
		{
			p.draw(g);
		}
	}

	private void RenderBomb(Graphics g)
	{
		if(bomben!=null)
		{
			for(Bombe bo:bomben)
			{
				bo.draw(g);
			}
		}
	}
	
	private void RenderPowerups(Graphics g)
	{
		if(powerups!=null)
		{
			for(Powerup up:powerups)
			{
				up.draw(g);
			}
		}
	}

	private void RenderPowerdowns(Graphics g)
	{
		if(powerdowns!=null)
		{
			for(Powerdown down:powerdowns)
			{
				down.draw(g);
			}
		}
	}

	private void RenderSidebar(Graphics g)
	{
		g.setColor(Variables.lightGray);
		g.fillRect(0, 0, Variables.sidebarwidth+1 ,Variables.HEIGHT);
		
		for(int i=0; i<playerliste.size(); i++) 
		{
			if (i < pliste.size())
				pliste.get(i).drawString(30, 120+20*i,"-"+playerliste.get(i).getName(), Variables.black);
		}
	}
	
	private void RenderTime()
	{
		currenttime = System.currentTimeMillis();
		String time = timeconvert(starttime,currenttime);
		trueTypeFont.drawString(20, 40,"Spielezeit ("+time+")", Variables.black);	
	}
}
