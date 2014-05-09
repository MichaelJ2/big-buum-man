package at.big_buum_man.server.gui;

import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;

import at.big_buum_man.server.ServerMethods;
import at.big_buum_man.server.network.NetworkServer;

/***
 * @version 1.0
 * @author Michael Januschek
 *
 */
public class Bomberman extends BasicGame implements Runnable ,ServerMethods
{
	private NetworkServer ns;
	private ArrayList<InetAddress> adressen = new ArrayList<InetAddress>();
	
	public static final int WIDTH = Display.getDesktopDisplayMode().getWidth();
	public static final int HEIGHT = Display.getDesktopDisplayMode().getHeight();
	
	private MapListe ml = new MapListe();
	
	private int rows=ml.getMap().size();
	private int cols=15;
	
	private TrueTypeFont trueTypeFont;
	private TrueTypeFont trueTypeFont3;
	
	private ArrayList<TrueTypeFont> pliste;
	private ArrayList<Player> playerliste;
	private ArrayList<Player> punkteliste;
	
	private ArrayList<Wand> hl;
	private ArrayList<ArrayList<Wand>> vl;
	
	private ArrayList<Bombe> bomben;
	private ArrayList<Powerup> powerups;
	private ArrayList<Powerdown> powerdowns;
	
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
	//private Animation animation;
	
	/***
	 * 	Bomberman Konstruktor
	 */
	public Bomberman() 
	{
		//Bomberman Window Title
		super("Bomberman");
	}

	/***
	 * 
	 * @param args
	 * @throws SlickException
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
	
	@Override
	public synchronized void render(GameContainer container, Graphics g) throws SlickException 
	{
		//Linke Sidebar
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, WIDTH/100*20+1 ,HEIGHT);
		
		trueTypeFont.drawString(20, 100,"Spielerliste:", Color.black);
		
		for(int i=0; i<playerliste.size(); i++) {
			if (i < pliste.size())
			pliste.get(i).drawString(30, 120+20*i,"-"+playerliste.get(i).getName(), Color.black);
		}

		//Karte laden und zeichnen
		int y=0+(HEIGHT/2)-(rows*50/2);
		//System.out.println("Y:"+(HEIGHT/2)+"-"+(rows*50/2)+"="+((HEIGHT/2)-(rows*50/2)));
		for(ArrayList<Wand> v: vl)
        {
		   int x=0+(WIDTH/100*20)+((WIDTH-(WIDTH/100*20))/2)-(cols*50/2);
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
     		 }
     		   else
     			  g.setColor(Color.darkGray);
     		   
     		  //g.fillRect(WIDTH/100*20+x+1+50, y+50, 50 ,50);
     		  h.setX(x+1+50);
     		  h.setY(y+50/2);
     		  h.draw(g);
     		  
     		  x=x+50;
     	   }
     	   y=y+50;
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
	}

	@Override
	public synchronized void init(GameContainer container) throws SlickException 
	{
		//Testplayer1 init
		Player player = new Player(new Image("res/player.png"));
		player.setName("Michael");
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
		
		//Bombenliste erstellen
		bomben=new ArrayList<Bombe>();
		
		//Powerupsliste erstellen
		powerups=new ArrayList<Powerup>();
		
		//Powerdownsliste erstellen
		powerdowns=new ArrayList<Powerdown>();
		
		trueTypeFont = new TrueTypeFont(new Font("Arial", Font.BOLD, 20), true);
		trueTypeFont3 = new TrueTypeFont(new Font("Arial", Font.BOLD, 20), true);
		
		//Playerliste erstellen
		playerliste =new ArrayList<Player>();
		
		//Fontliste erstellen
		pliste=new ArrayList<TrueTypeFont>();
		
		//player hinzuf�gen zu Playerliste
		playerliste.add(player);
		playerliste.add(player1);
		
		//Drei Fontlisten erstellen *Test*
		for(int i=0;i<8;i++)
			pliste.add(new TrueTypeFont(new Font("Arial", Font.BOLD, 20), true));

		
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
     		  x=x+50;
     		 if(o.equals("x"))
      		 {
      			  for(Player p:playerliste)
      				  p.setposition(WIDTH/100*20+x-50,y+50);
      		 }
     		   hl.add(w);
     	   }
     	   y=y+50;
     	   vl.add(hl);
        }
		
		ns=new NetworkServer(this);
		try 
		{
			ns.startServer();
			System.out.println("start Server");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			ns.startAnnounce();
			System.out.println("start Announce");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		//Playersetzen aus liste
		for(Player p:playerliste)
		{
			p.setSprungX(50);
			p.setBomberman(this);
			p.setSprungY(50);
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

	    //Get, save, and display the width and the height
	    // of the sprite sheet.
	    spriteSheetWidth = spriteSheetImage.getWidth();
	    spriteSheetHeight = spriteSheetImage.getHeight();
	    
		//Compute the width and height of the individual 
		// sprite images.
	    spriteWidth = (int)(spriteSheetWidth/spritesPerRow);
	    spriteHeight =(int)(spriteSheetHeight/spritesPerColumn);
		
	    spriteSheet = new SpriteSheet(spriteSheetImage,spriteWidth, spriteHeight);
	}
	
	@Override
	public synchronized void update(GameContainer container, int delta) throws SlickException 
	{
		// Eingabe abfragen
		Input input=container.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) 
		{
			container.exit();
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
					setbombe((b.getX()-3),b.getY()/b.getSprungY(),b);
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
					
					System.out.println("Down --x"+rx+" - y"+ry+"--");
					powerup.setX((WIDTH/100*20)+((WIDTH-(WIDTH/100*20))/2)-(cols*50/2)+rx*50+50);
					powerup.setY((HEIGHT/2)-(rows*50/2)+ry*50+25);
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
					
					System.out.println("Power --x"+rx+" - y"+ry+"--");
					powerdown.setX((WIDTH/100*20)+((WIDTH-(WIDTH/100*20))/2)-(cols*50/2)+rx*50+50);
					powerdown.setY((HEIGHT/2)-(rows*50/2)+ry*50+25);
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
	 * 	@param x = X Coordinate
	 * 	@param y = Y Coordinate
	 * 	@param sprungy = H�he eines Feldes
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
		bomben.add(bombe);
	}

	/***
	 * 
	 * 
	 * */
	public synchronized void setbombe(int x, int y,Bombe b) 
	{
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
					int px=((playerliste.get(p).getX()-WIDTH/100*20)/(WIDTH/100*60/vl.size()));
					int py=(playerliste.get(p).getY()/(HEIGHT/vl.size()));
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
					int px=((playerliste.get(p).getX()-WIDTH/100*20)/(WIDTH/100*60/vl.size()));
					int py=(playerliste.get(p).getY()/(HEIGHT/vl.size()));
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
					int px=((playerliste.get(p).getX()-WIDTH/100*20)/(WIDTH/100*60/vl.size()));
					int py=(playerliste.get(p).getY()/(HEIGHT/vl.size()));
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
					int px=((playerliste.get(p).getX()-WIDTH/100*20)/(WIDTH/100*60/vl.size()));
					int py=(playerliste.get(p).getY()/(HEIGHT/vl.size()));
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
	 * 
	 * @param adresse = InetAddress wird �bergeben f�r den Player
	 * @return Player
	 */
	private synchronized Player neuerSpieler(InetAddress adresse)  {
		
		Player p=null;
		
		try 
		{
			p = new Player(new Image("res/player.png"));
			p.setAdresse(adresse);
			//p.setSprungX((WIDTH-WIDTH/100*40)/cols);
			p.setSprungX(50);
			p.setSprungY(50);
			p.setAnfangx((WIDTH/100*20)+((WIDTH-(WIDTH/100*20))/2)-(cols*50/2)+50);
			//System.out.println("Anfangx:"+((WIDTH/100*20)+((WIDTH-(WIDTH/100*20))/2)-(cols*50/2)+50));
			p.setAnfangy((HEIGHT/2)-(rows*50/2)+25);
			//System.out.println("Anfangy:"+((HEIGHT/2)-(rows*50/2)+25));
			p.setBomberman(this);
			//p.setSprungY(HEIGHT/rows);
			p.setMapn(vl);
			//p.setXPos(1);
			//p.setYPos(1);
			//p.setposition(600,400);
			for(int px=0;px<15;px++)
				for(int py=0;py<10;py++)
					if(vl.get(py).get(px).getStein().equals("x"))
						{
						
						p.setposition(	(WIDTH/100*20)+((WIDTH-(WIDTH/100*20))/2)-(cols*50/2)+px*50+50, 
										(HEIGHT/2)-(rows*50/2)+py*50+25);
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

	@Override
	public void processMessage(InetAddress client, String message) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		Timestamp tim = new Timestamp(System.currentTimeMillis());
		String time = sdf.format(tim);
		System.out.println(time+" Message<"+client.getHostAddress()+">:"+message);
		System.out.println(client);
		for(int p=0;p<playerliste.size();p++)
		{
			if(playerliste.get(p).getAdresse().getHostAddress().equals(client.getHostAddress()))
			{
				if(message.equals("left"))
				{
					System.out.println(playerliste.get(p).getName()+": left");
					playerliste.get(p).left();
				}
				else if(message.equals("right"))
				{
					System.out.println(playerliste.get(p).getName()+": right");
					playerliste.get(p).right();
				}
				else if(message.equals("up"))
				{
					System.out.println(playerliste.get(p).getName()+": up");
					playerliste.get(p).up();
				}
				else if(message.equals("down"))
				{
					System.out.println(playerliste.get(p).getName()+": down");
					playerliste.get(p).down();
				}
				else if(message.equals("a"))
				{
					System.out.println(playerliste.get(p).getName()+": a");
					playerliste.get(p).buttonA();
				}
				else if(message.equals("b"))
				{
					System.out.println(playerliste.get(p).getName()+": b");
					playerliste.get(p).buttonB();
				}
			}
		}
	}

	@Override
	public void registerNewClient(InetAddress client) 
	{
		adressen.add(client);
		neuerSpieler(client);
		System.out.println(client.getHostAddress());
		
	}
}
