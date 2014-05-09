package at.big_buum_man.server.gui;

import java.net.InetAddress;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/***
 * @version 1.0
 * @author Michael Januschek
 * 
 * @Beschreibung: Spielerklasse
 *
 */
public class Player extends SpielObjekt implements Comparable<Player>
{
	private ArrayList<ArrayList<Wand>> mapn;
	private Integer anfangx;
	private Integer anfangy;
	private Integer sprungx;
	private Integer sprungy;
	private Bomberman bbm;
	private String name="";
	private Integer punkte=0;
	private InetAddress adresse;

	/***
	 * 	Beschreibung: Konstruktor mit Bild
	 */
	public Player(Image image) 
	{
		super(image);
	}
	
	/***
	 * 	Beschreibung: Position setzen
	 */
	public void setposition(int x,int y)
	{
		this.y=y;
		this.x=x;
	}
	
	@Override
	public void update(int delta)
	{
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
		image.drawCentered(x, y);
	}

	/***
	 * 	Beschreibung: Karte setzen
	 */
	public void setMapn(ArrayList<ArrayList<Wand>>mapn)
	{
		this.mapn=mapn;
	}
	
	/***
	 * 	Beschreibung: Breite eines Feldes setzen
	 */
	public void setSprungX(Integer sprung)
	{
		this.sprungx=sprung;
	}
	
	/***
	 * 	Beschreibung: H�he eines Feldes setzen
	 */
	public void setSprungY(Integer sprung)
	{
		this.sprungy=sprung;
	}
	
	/***
	 * 	Beschreibung: Main setzen
	 */
	public void setBomberman(Bomberman bbm)
	{
		this.bbm=bbm;
	}
	
	/***
	 * 	Beschreibung: Namen setzen
	 */
	public void setName(String name)
	{
		this.name=name;
		
	}
	
	/***
	 * 	Beschreibung: Gibt Namen zur�ck
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
	 * 	Beschreibung: Gibt Punkte zur�ck
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
		System.out.println("1:"+(((this.y-anfangy)/sprungy*1)-1)+" 2:"+(((this.x-anfangx)/sprungx*1)-3));
		
		if(mapn.get(((this.y-anfangy)/sprungy*1)-1).get(((this.x-anfangx)/sprungx*1)-3).getStein().equals("0"))
			this.y=this.y;
		else
			this.y-=sprungy;
		
		try 
		{
			super.setImage(new Image("res/p.png").getSubImage(0,290,92,98));
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
		if(mapn.get(((this.y-anfangy)/sprungy*1)+1).get(((this.x-anfangx)/sprungx*1)-3).getStein().equals("0"))
			this.y=this.y;
		else
			this.y+=sprungy;
		
		try 
		{
			super.setImage(new Image("res/p.png").getSubImage(0,0,95,116));
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
		if(mapn.get(((this.y-anfangy)/sprungy*1)).get(((this.x-anfangx)/sprungx*1)-2).getStein().equals("0"))
			this.x=this.x;
		else
			this.x+=sprungx;
		
		try 
		{
			super.setImage(new Image("res/p.png").getSubImage(0,222,98,67));
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
		//System.out.println("1:"+(((this.y-anfangy)/sprungy*1))+" 2:"+(((this.x-anfangx)/sprungx*1)-4));
		
		if(mapn.get(((this.y-anfangy)/sprungy*1)).get(((this.x-anfangx)/sprungx*1)-4).getStein().equals("0"))
			this.x=this.x;
		else
			this.x-=sprungx;
		
		try 
		{
			super.setImage(new Image("res/p.png").getSubImage(0,126,92,72));
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
		try 
		{
			bbm.addBombe(this.x,this.sprungx,this.y,this.sprungy,this);
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
	
	public void setAnfangx(int x)
	{
		anfangx=x;
	}
	
	public void setAnfangy(int y)
	{
		anfangy=y;
	}
	 
	
	
}
