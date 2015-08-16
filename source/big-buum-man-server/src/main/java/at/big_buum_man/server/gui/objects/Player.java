package at.big_buum_man.server.gui.objects;

import org.newdawn.slick.Color;

import java.awt.Font;
import java.net.InetAddress;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import at.big_buum_man.server.gui.Game;
import at.big_buum_man.server.gui.helper.Point;
import at.big_buum_man.server.gui.helper.Variables;


/***
 * @version 1.0
 * @author Michael Januschek
 * 
 * @Beschreibung: Spielerklasse
 *
 */
public class Player extends SpielObjekt implements Comparable<Player>
{
	private Game game;
	private ArrayList<ArrayList<Wand>> mapn;
	private String name="";
	private Integer punkte=0;
	private InetAddress adresse;
	private Color farbe=Color.green;
	private TrueTypeFont trueTypeFont = new TrueTypeFont(new Font(Variables.letter, Variables.font ,Variables.charsize), true);
	
	/***
	 * 	Beschreibung: Konstruktor mit Bild
	 */
	public Player(Image image) 
	{
		super(image);
		super.setObjectName("Player");
		game = Game.getInstance();
	}
	
	/***
	 * 	Beschreibung: Position setzen
	 */
	/*
	public void setposition(int x,int y)
	{
		this.y=y;
		this.x=x;
	}
	*/
	
	public void setposition(Point point)
	{
		this.point=point;
	}
	
	@Override
	public void update(int delta)
	{
		//System.out.println("Update: x:"+(x)+" >< y:"+(y));
		try 
		{
			super.setImage(new Image("res/p.png").getSubImage(0,0,95,116));
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw(Graphics g) 
	{
		//image.drawCentered(x, y);
		//System.out.println("Render: x:"+(x)+" >< y:"+(y));
		
		image.draw(point.getX()-Variables.BLOCKWIDTH/2, point.getY()-Variables.BLOCKHEIGHT, Variables.farbe);
		trueTypeFont.drawString(point.getX(), point.getY(), getName(), Variables.red);
		
		g.setColor(Variables.green);
		g.drawRect(point.getX()-1,point.getY()-1, Variables.BLOCKWIDTH+1, Variables.BLOCKHEIGHT+1);
		
		//System.out.println(getName()+": x"+x+" y:"+y);
	}

	/***
	 * 	Beschreibung: Karte setzen
	 */
	public void setMapn(ArrayList<ArrayList<Wand>>mapn)
	{
		this.mapn=mapn;
	}

	
	/***
	 * 	Beschreibung: Namen setzen
	 */
	public void setName(String name)
	{
		this.name=name;
		
	}
	
	/***
	 * 	Beschreibung: Gibt Namen zurück
	 */
	public String getName()
	{
		return this.name;
	}
	
	/***
	 * 	Beschreibung: Plus Punkte
	 */
	public void plus()
	{
		this.punkte++;
	}
	
	/***
	 * 	Beschreibung: Minus Punkte
	 */
	public void minus()
	{
		this.punkte--;
	}
	
	/***
	 * 	Beschreibung: Gibt Punkte zurück
	 */
	public Integer getPunkte()
	{
		return this.punkte;
	}
	
	/***
	 * 	Beschreibung: Setzt die InetAddress
	 */
	public void setAdresse(InetAddress adresse) 
	{
		this.adresse = adresse;
	}
	
	/***
	 * 	Beschreibung: Button Left = Links bewegen
	 */
	public InetAddress getAdresse() 
	{
		return this.adresse;
	}

	/***
	 * 	Beschreibung: Punkte vergleichen
	 */
	public int compareTo(Player p) 
	{
		return p.getPunkte().compareTo(this.getPunkte());
	}

	/***
	 * 	Beschreibung: Button Up = Hinauf bewegen
	 */
	public synchronized void up() 
	{
		int ya=point.getBrettY()-1;
		int xa=point.getBrettX();
		
		//System.out.println("UP: x:"+xa+" y:"+ya +" | Playerx:"+x+" Playery:"+y);
		
		if(mapn.get(ya).get(xa).getStein() == 0)
			point.setY(point.getY());
		else
			point.setY(point.getY()-Variables.BLOCKHEIGHT);
		
		try 
		{
			super.setImage(new Image(Variables.res+"p.png").getSubImage(0,290,92,98));
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}

	/***
	 * 	Beschreibung: Button Down = Hinunter bewegen
	 */
	public synchronized void down() 
	{
		int ya=point.getBrettY()+1;
		int xa=point.getBrettX();
		
		//System.out.println("DOWN: x:"+xa+" y:"+ya+" | Playerx:"+x+" Playery:"+y);
		
		if(mapn.get(ya).get(xa).getStein() == 0)
			point.setY(point.getY());
		else
			point.setY(point.getY()+Variables.BLOCKHEIGHT);
		
		try 
		{
			super.setImage(new Image(Variables.res+"p.png").getSubImage(0,0,95,116));
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}

	/***
	 * 	Beschreibung: Button Right = Rechts bewegen
	 */
	public synchronized void right() 
	{
		int ya=point.getBrettY();
		int xa=point.getBrettX()+1;
		
		//System.out.println("RIGHT: x:"+xa+" y:"+ya+" | Playerx:"+x+" Playery:"+y);
		
		if(mapn.get(ya).get(xa).getStein() == 0)
			point.setX(point.getX());
		else
			point.setX(point.getX()+Variables.BLOCKWIDTH);
		
		try 
		{
			super.setImage(new Image(Variables.res+"p.png").getSubImage(0,222,98,67));
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}

	/***
	 * 	Beschreibung: Button Left = Links bewegen
	 */
	public synchronized void left() 
	{
		int ya=point.getBrettY();
		int xa=point.getBrettX()-1;
		//System.out.println("LEFT: x:"+xa+" y:"+ya +" | Playerx:"+x+" Playery:"+y);
		if(mapn.get(ya).get(xa).getStein() == 0)
			point.setX(point.getX());
		else
			point.setX(point.getX()-Variables.BLOCKWIDTH);
		
		try 
		{
			super.setImage(new Image(Variables.res+"p.png").getSubImage(0,126,92,72));
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}

	/***
	 * 	Beschreibung: Button A = Bombe Legen
	 */
	public synchronized void buttonA() 
	{
		//int ya=((this.y-anfangy)/sprungy*1);
		//int xa=((this.x-anfangx)/sprungx*1);
		try 
		{
			game.addBombe(point.getX(),Variables.BLOCKWIDTH,point.getY(),Variables.BLOCKHEIGHT,this);
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}

	/***
	 * 	Beschreibung: Button B = Spezial Knopf
	 */
	public void buttonB() 
	{
		System.out.println("Info : Not implemented now!");
	}

	public void addPowerup(Powerup p) 
	{
		System.out.println("Powerup gesetzt.");
	}
	
	public void addPowerdown(Powerdown p) 
	{
		System.out.println("Powerdown gesetzt.");
	}
	
	public void setColor(Color farbe)
	{
		this.farbe=farbe;
	}
	
	public Color getColor()
	{
		return this.farbe;
	}
}
