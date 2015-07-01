package at.big_buum_man.server.gui;

import at.big_buum_man.server.ServerMethods;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

import at.big_buum_man.server.gui.Bombe;
import at.big_buum_man.server.gui.GAMEMODE;
import at.big_buum_man.server.gui.Player;
import at.big_buum_man.server.gui.Powerdown;
import at.big_buum_man.server.gui.Powerup;
import at.big_buum_man.server.gui.Wand;

/***
 * @version 1.0
 * @author Michael Januschek
 *
 */
public class Bomberman extends BasicGame implements Runnable ,ServerMethods
{
	private NetworkServer ns;
	private ArrayList<InetAddress> adressen = new ArrayList<InetAddress>();
	
	private GAMEMODE gamemode; //Game mode für die bestimmten Spielearten
	
	public static final int WIDTH = Display.getDesktopDisplayMode().getWidth();
	public static final int HEIGHT = Display.getDesktopDisplayMode().getHeight();
	
	private MapListe ml = new MapListe();
	
	private int rows=ml.getMap().size();
	private int cols=15;
	
	private long starttime; // startzeit vom Spiel
	private long currenttime; //aktuelle zeit vom Spiel

	private TrueTypeFont trueTypeFont;
	private TrueTypeFont trueTypeFont3;
	
	private ArrayList<TrueTypeFont> pliste;
	private ArrayList<Player> playerliste;
	private ArrayList<Player> punkteliste;
	
	private ArrayList<Wand> hl;
	private ArrayList<ArrayList<Wand>> vl; 
	
	private ArrayList<Bombe> bomben; //Bomben auf dem Feld
	private ArrayList<Powerup> powerups; //Powerups auf dem Feld
	private ArrayList<Powerdown> powerdowns; //Powerdowns auf dem Feld
	
	private Image spriteSheetImage;
	private Image bomb;
	private Image ipower;
	private Image iminder;
	private int spriteSheetWidth;
	private int spriteSheetHeight;
	private int spriteWidth=50;
	private int spriteHeight=50;
	private int spritesPerRow=4;
	private int spritesPerColumn=4;
	private SpriteSheet spriteSheet;
	
	private int charsize=20;
	private int blockwidth=50;
	private int blockheight=50;
	private int sidebarwidth=WIDTH/100*20;
	private int ytop=(HEIGHT/2)-(rows*blockheight/2);
	private int xleft=sidebarwidth+((WIDTH-sidebarwidth)/2)-(cols*blockheight/2);
	
	private static Bomberman instance == null;
	
	/***
	 * 	Bomberman Konstruktor
	 */
	private Bomberman() 
	{
		super("Bomberman"); //Window Title
	}
	
	/***
	 * 	getInstance
	 * 
	 * 	
	 * 	@return Bomberman Instance
	 */
	public static synchronized Bomberman getInstance () 
	{
	    if (Bomberman.instance == null) 
	    {
	      	Bomberman.instance = new Bomberman ();
	    }
	    return Bomberman.instance;
	 }

	/***
	 * 	Hauptklasse zum starten vom Spiel
	 * 
	 * 	@param args
	 * 	@throws SlickException
	 */
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
	
	/**	
	 * 	Summary: 
	 * 	In dieser Funktion werden alle Resourcen und Grafiken neu gezeichnet in einem bestimmten Intervall
	 * 
	 * 	Description:
	 * 	
	 * 
	 * 	@param container = 
	 * 	@param g = 
	 * 
	 * 	
	 */
	@Override
	public synchronized void render(GameContainer container, Graphics g) throws SlickException 
	{
		//Linke Sidebar : Spielerliste
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, sidebarwidth+1 ,HEIGHT);
		
		trueTypeFont.drawString(20, 100,"Spielerliste:", Color.black);
		
		for(int i=0; i<playerliste.size(); i++) 
		{
			if (i < pliste.size())
			pliste.get(i).drawString(30, 120+20*i,"-"+playerliste.get(i).getName(), Color.black);
		}

		//Karte laden und zeichnen
		int y=0+ytop;
		//System.out.println("Y:"+(HEIGHT/2)+"-"+(rows*50/2)+"="+((HEIGHT/2)-(rows*50/2)));
		for(ArrayList<Wand> v: vl)
        	{
		   int x=0+xleft;
		   //System.out.println("X:"+(WIDTH/100*20)+"+"+(WIDTH-(WIDTH/100*20))/2+"-"+(cols*50/2)+"="+x);
	     	   for(Wand h:v)
	     	   {
	     		  if(h.getStein().equals("0"))
	     			  g.setColor(Color.gray);
	     		  else if(h.getStein().equals("1"))
	     			  g.setColor(Color.green);
	     		  else if(h.getStein().equals("2"))
	     			  g.setColor(Color.red);
	     		  else if(h.getStein().equals("3"))
	     			  g.setColor(Color.blue);
	     		  else if(h.getStein().equals("4"))
	     			  g.setColor(Color.white);
	     		  else if(h.getStein().equals("5"))
	     			  g.setColor(Color.yellow);
	     		  else if(h.getStein().equals("6"))
	     			  g.setColor(Color.white);
	     		  else if(h.getStein().equals("7"))
	    			  g.setColor(Color.magenta);
	     		  else if(h.getStein().equals("8"))
	    			  g.setColor(Color.blue);
	     		  else if(h.getStein().equals("9"))
	   			  g.setColor(Color.red);
	     		 else if(h.getStein().equals("x"))
	     		 {
	      			  g.setColor(Color.green);
	      			  //System.out.print("(x:"+x+"/y:"+y+")");
	     		 }
     		   	else
     			  g.setColor(Color.darkGray);
     		   
	     		  //g.fillRect(WIDTH/100*20+x+1+50, y+50, 50 ,50);
	     		  h.setX(x+1+blockwidth);
	     		  h.setY(y+blockheight/2);
	     		  h.draw(g);
	     		  
	     		  x=x+blockwidth;
	     	   }
	     	   y=y+blockheight;
	        }
		
		
		g.setColor(Color.red);
		
		//Player zeichnen
		for(Player p:playerliste)
		{
			p.draw(g);
		}
		
		//Bomben zeichnen
		if(bomben!=null)
		{
			for(Bombe bo:bomben)
			{
				bo.draw(g);
			}
		}
		
		//Powerups zeichnen
		if(powerups!=null)
		{
			for(Powerup up:powerups)
			{
				up.draw(g);
			}
		}
		
		//Powerdowns zeichnen
		if(powerdowns!=null)
		{
			for(Powerdown down:powerdowns)
			{
				down.draw(g);
			}
		}
		//animation zeichnen *Test*
		//animation.draw(300,300);
		
		currenttime = System.currentTimeMillis();
		String time = timeconvert(starttime,currenttime);
		trueTypeFont.drawString(20, 40,"Spielezeit ("+time+")", Color.black);
		
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
		starttime = System.currentTimeMillis();
		
		//Bombenliste erstellen
		bomben=new ArrayList<Bombe>();
		
		//Powerupsliste erstellen
		powerups=new ArrayList<Powerup>();
		
		//Powerdownsliste erstellen
		powerdowns=new ArrayList<Powerdown>();
		
		trueTypeFont = new TrueTypeFont(new Font("Arial", Font.BOLD, charsize), true);
		trueTypeFont3 = new TrueTypeFont(new Font("Arial", Font.BOLD, charsize), true);
		
		//Playerliste erstellen
		playerliste =new ArrayList<Player>();
		
		//Fontliste erstellen
		pliste=new ArrayList<TrueTypeFont>();
		
		//TestPlayer 
		//Testplayer1 init
		Player player = new Player(new Image("res/player.png"));
		player.setName("Michael");
		player.setColor(Color.red);
		//player.setposition(1375+50, 540+50);
		player.setposition(980, 300 );
		player.setAnfangx(xleft+blockwidth);
		player.setAnfangy(ytop+25);
		player.setSprungX(blockwidth);
		player.setSprungY(blockheight);
		//player.setBomberman(this);
		player.setMapn(vl);
		
		try 
		{
			player.setAdresse(InetAddress.getByName("192.168.1.1"));
		} 
		catch (UnknownHostException e1) 
		{
			e1.printStackTrace();
		}

		//Testplayer2 init
		Player player1 = new Player(new Image("res/player2.png"));
		player1.setName("Gerald");
		player1.setColor(Color.green);
		try 
		{
			player1.setAdresse(InetAddress.getByName("192.168.1.1"));
		} 
		catch (UnknownHostException e1) 
		{
			e1.printStackTrace();
		}
		
		//Testplayer1 punkt schenken
		player1.plus();
		
		//player hinzufügen zu Playerliste
		playerliste.add(player);
		playerliste.add(player1);
		
		//Drei Fontlisten erstellen *Test*
		for(int i=0;i<8;i++)
			pliste.add(new TrueTypeFont(new Font("Arial", Font.BOLD, charsize), true));

		
		//Map in eine ArrayList laden
		int y=0;
		vl=new ArrayList<ArrayList<Wand>>();
		for(String[] s:ml.getMap())
        	{
        		hl=new ArrayList<Wand>();
		   	int x=0;
     	   		for(String o:s)
     	   		{
     		  		Wand w=new Wand(o);
     		  		x=x+blockwidth;
     				if(o.equals("x"))
      		 		{
      			  		for(Player p:playerliste)
      					p.setposition(sidebarwidth+x-blockwidth,y+blockheight);
      		 		}
     			 	hl.add(w);
     	   		}
     	   		y=y+blockheight;
     	   		vl.add(hl);
        	}
		
		ns=new NetworkServer(this);
		try 
		{
			ns.startServer();
			//System.out.println("start Server");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			ns.startAnnounce();
			//System.out.println("start Announce");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		//Playersetzen aus liste
		for(Player p:playerliste)
		{
			p.setSprungX(blockwidth);
			//p.setBomberman(this);
			p.setSprungY(blockheight);
			p.setMapn(vl);
		}
		
		//tests
		 spriteSheetImage = new Image("res/p.png");
		 bomb = new Image("res/bomb.png");
		 ipower = new Image("res/Powerup.png");
		 iminder = new Image("res/Powerdown.png");
	    	
	    	//Enlarge the sprite sheet.
	    	Image temp = spriteSheetImage.getScaledCopy(300,300);
	    	spriteSheetImage = temp;
	    	spriteSheetWidth = spriteSheetImage.getWidth();
	    	spriteSheetHeight = spriteSheetImage.getHeight();
	    	spriteWidth = (int)(spriteSheetWidth/spritesPerRow);
	    	spriteHeight =(int)(spriteSheetHeight/spritesPerColumn);
	    	spriteSheet = new SpriteSheet(spriteSheetImage,spriteWidth, spriteHeight);
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
		// Eingabe abfragen
		int pl=0;
		Input input=container.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) 
		{
			container.exit();
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT)) 
		{
			//System.out.println(playerliste.get(pl).getName());
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
		
		//Player updaten
		for(Player p:playerliste)
		{
			p.update(delta);
		}
		
		//Bomben updaten
		if(!(bomben.isEmpty()))
		{
			for (int i=0;i<bomben.size();i++) 
			{
				Bombe b = bomben.get(i);
				if(b.getZeit()+b.getSystemZeit()<System.currentTimeMillis())
				{
					//System.out.println("x:"+b.getX()+" / sprungx:"+b.getSprungX()+" | y:"+b.getY()+" / sprungy:"+b.getSprungY());
					setbombe(((b.getX()-(xleft))/b.getSprungX()),((b.getY()-(ytop))/b.getSprungY()),b);
					bomben.remove(b);
				}	
			}
		}
		
		//Powerups updaten
		if(!(powerups.isEmpty()))
		{
			for (int i=0;i<powerups.size();i++) 
			{
				Powerup up = powerups.get(i);
				for (int j=0;j<playerliste.size();j++) 
				{
					Player u = playerliste.get(j);
					//System.out.println("Player "+j+":"+u.getX()+" - "+u.getY());
					//System.out.println("Powerup "+i+":"+up.getX()+" - "+up.getY());
					if(up.getX()==u.getX() && up.getY()==u.getY())
					{
						u.addPowerup(up);
						//System.out.println(u.getName()+" hat ein Powerup aufgenommen");
						//setbombe((up.getX()/up.getSprungX()-3),up.getY()/up.getSprungY(),up);
						powerups.remove(up);
					}	
				}
			}
			
			for (int i=0;i<powerups.size();i++) 
			{
				Powerup up = powerups.get(i);
				if(up.getZeit()+up.getSystemZeit()<System.currentTimeMillis())
				{
					//setbombe((up.getX()/up.getSprungX()-3),up.getY()/up.getSprungY(),up);
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
				//System.out.println(randomzahl+" ist ein Powerup");
				Random rax= new Random();
				int rx = rax.nextInt(cols);
				
				Random ray= new Random();
				int ry = ray.nextInt(rows);
				
				if(!vl.get(ry).get(rx).getStein().equals("0"))
				{
					Powerup powerup=new Powerup(ipower);
					powerup.setSystemZeit(System.currentTimeMillis());
					
					//System.out.println("Down --x"+rx+" - y"+ry+"--");
					powerup.setX(xleft+rx*blockwidth+blockwidth);
					powerup.setY(ytop+ry*blockheight+25);
					//System.out.println(powerup.getX()/blockwidth+"<-x | y->"+powerup.getY()/blockheight);
					powerups.add(powerup);
				}
			}
			else
			{
				//System.out.println(randomzahl+" keine Powerup");
			}
		}
		
		//Powerdowns updaten
		if(!(powerdowns.isEmpty()))
		{
			for (int i=0;i<powerdowns.size();i++) 
			{
				Powerdown down = powerdowns.get(i);
				for (int j=0;j<playerliste.size();j++) 
				{
					Player u = playerliste.get(j);
					//System.out.println("Player "+j+":"+u.getX()+" - "+u.getY());
					//System.out.println("Powerup "+i+":"+up.getX()+" - "+up.getY());
					if(down.getX()==u.getX() && down.getY()==u.getY())
					{
						u.addPowerdown(down);
						//System.out.println(u.getName()+" hat ein Powerdown aufgenommen");
						//setbombe((up.getX()/up.getSprungX()-3),up.getY()/up.getSprungY(),up);
						powerdowns.remove(down);
					}	
				}
			}
			
			for (int i=0;i<powerdowns.size();i++) 
			{
				Powerdown down = powerdowns.get(i);
				if(down.getZeit()+down.getSystemZeit()<System.currentTimeMillis())
				{
					//setbombe((up.getX()/up.getSprungX()-3),up.getY()/up.getSprungY(),up);
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
				//System.out.println(randomzahl+" ist ein Powerdown");
				Random rax= new Random();
				int rx = rax.nextInt(cols);
				
				Random ray= new Random();
				int ry = ray.nextInt(rows);
				if(!vl.get(ry).get(rx).getStein().equals("0"))
				{
					Powerdown powerdown=new Powerdown(iminder);
					powerdown.setSystemZeit(System.currentTimeMillis());
					
					//System.out.println("Power --x"+rx+" - y"+ry+"--");
					powerdown.setX(xleft+rx*blockwidth+blockwidth);
					powerdown.setY(ytop+ry*blockheight+25);
					///System.out.println(powerdown.getX()/blockwidth+"<-x | y->"+powerdown.getY()/blockheight);
					powerdowns.add(powerdown);
				}
			}
			else
			{
				//System.out.println(randomzahl+" keine Powerdown");
			}
		}
	}
	
	/***
	 * 	Summary:
	 * 	Hier wird eine Bombe hinzugefügt in das Spiel
	 * 
	 * 	Description:
	 * 
	 * 	@param x = X Coordinate
	 * 	@param y = Y Coordinate
	 * 	@param sprungy = Höhe eines Feldes
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
		bombe.setAnfangx(xleft+blockwidth);
		bombe.setAnfangy(ytop+25);
		bomben.add(bombe);
	}

	/***
	 * 	Summary:
	 * 	Bombe wird gesetzt
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
		x=x-1;
		//System.out.println(b.getBesitzer().getName()+": x"+x+" | "+"y:"+y);
		int side1=0;
		int side2=0;
		int side3=0;
		int side4=0;
		
		for(int i=0;i<b.getRange();i++)
		{
			if(y-i-1>0) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=((playerliste.get(p).getX()-sidebarwidth)/(WIDTH/100*60/15));
					int py=(playerliste.get(p).getY()/(HEIGHT/10));
					
					//System.out.println("px:"+px+" py:"+py+" x:"+x+" y:"+y);
					if(px==x&&py==y-i-1&&side1==0)
					{
						b.getBesitzer().plus();
						side1=1;
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y-i-1).get(x).getStein().equals("0")&&side1==0) 
					{
						b.getBesitzer().plus();
						side1=1;
						vl.get(y-i-1).get(x).setStein(1+"");
					}
			}
			
			if(y+i+1<ml.getMap().size()-1) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=((playerliste.get(p).getX()-sidebarwidth)/(WIDTH/100*60/15));
					int py=(playerliste.get(p).getY()/(HEIGHT/10));
					if(px==x&&py==y+i+1&&side2==0)
					{
						b.getBesitzer().plus();
						side2=1;
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y+i+1).get(x).getStein().equals("0")&&side2==0)
				{
					b.getBesitzer().plus();
					side2=1;
					vl.get(y+i+1).get(x).setStein(1+"");
				}
			}
			
			if(x-i-1>0) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=((playerliste.get(p).getX()-sidebarwidth)/(WIDTH/100*60/15));
					int py=(playerliste.get(p).getY()/(HEIGHT/10));
					if(px==x-i-1&&py==y&&side3==0)
					{
						b.getBesitzer().plus();
						side3=1;
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y).get(x-i-1).getStein().equals("0")&&side3==0)
				{
					b.getBesitzer().plus();
					side3=1;
					vl.get(y).get(x-i-1).setStein(1+"");
				}
			}
			
			if(x+i+1<ml.getMap().size()-1) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=((playerliste.get(p).getX()-sidebarwidth)/(WIDTH/100*60/15));
					int py=(playerliste.get(p).getY()/(HEIGHT/10));
					//int py=(playerliste.get(p).getY()-((HEIGHT/2)-(rows*50/2))/50);
					
					if(px==x+i+1&&py==y&&side4==0)
					{
						b.getBesitzer().plus();
						side4=1;
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y).get(x+i+1).getStein().equals("0")&&side4==0)
				{
					b.getBesitzer().plus();
					side4=1;
					vl.get(y).get(x+i+1).setStein(1+"");
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

	/***
	 * 	Summary:
	 * 	Neuer Spieler wird dem Spiel hinzugefügt
	 * 
	 * 	Description:
	 * 
	 * 	@param adresse = InetAddress wird übergeben für den Player
	 * 	@return Player
	 */
	private synchronized Player neuerSpieler(InetAddress adresse)  {
		
		Player p=null;
		
		try 
		{
			p = new Player(new Image("res/player.png"));
			p.setAdresse(adresse);
			//p.setSprungX((WIDTH-WIDTH/100*40)/cols);
			p.setSprungX(blockwidth);
			p.setSprungY(blockheight);
			p.setAnfangx(xleft+blockwidth);
			//System.out.println("Anfangx:"+((WIDTH/100*20)+((WIDTH-(WIDTH/100*20))/2)-(cols*50/2)+50));
			p.setAnfangy(ytop+25);
			//System.out.println("Anfangy:"+((HEIGHT/2)-(rows*50/2)+25));
			//p.setBomberman(this);
			//p.setSprungY(HEIGHT/rows);
			p.setMapn(vl);
			//p.setXPos(1);
			//p.setYPos(1);
			//p.setposition(600,400);
			for(int px=0;px<15;px++)
				for(int py=0;py<10;py++)
					if(vl.get(py).get(px).getStein().equals("x"))
						{
						
						p.setposition(	xleft+px*blockwidth+blockwidth, 
										ytop+py*blockheight+25);
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

	/**
	 * 	Summary:
	 * 	Nachricht wird von Spieler erhalten und Spieler wird bewegt/verändert
	 * 	
	 * 	Description:
	 * 
	 * 	@param client =
	 * 	@param message =
	 */
	@Override
	public void processMessage(InetAddress client, String message) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		Timestamp tim = new Timestamp(System.currentTimeMillis());
		String time = sdf.format(tim);
		//System.out.println(time+" Message<"+client.getHostAddress()+">:"+message);
		//System.out.println(client);
		for(int p=0;p<playerliste.size();p++)
		{
			if(playerliste.get(p).getAdresse().getHostAddress().equals(client.getHostAddress()))
			{
				if(message.equals("left"))
				{
					//System.out.println(playerliste.get(p).getName()+": left");
					playerliste.get(p).left();
				}
				else if(message.equals("right"))
				{
					//System.out.println(playerliste.get(p).getName()+": right");
					playerliste.get(p).right();
				}
				else if(message.equals("up"))
				{
					//System.out.println(playerliste.get(p).getName()+": up");
					playerliste.get(p).up();
				}
				else if(message.equals("down"))
				{
					//System.out.println(playerliste.get(p).getName()+": down");
					playerliste.get(p).down();
				}
				else if(message.equals("a"))
				{
					//System.out.println(playerliste.get(p).getName()+": a");
					playerliste.get(p).buttonA();
				}
				else if(message.equals("b"))
				{
					//System.out.println(playerliste.get(p).getName()+": b");
					playerliste.get(p).buttonB();
				}
			}
		}
	}

	/**
	 * 	Summary:
	 *  	neuer Client hinzufügen in Liste und ins Spiel
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
		//System.out.println(client.getHostAddress());
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
}
