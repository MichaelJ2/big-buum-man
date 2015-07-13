package at.big_buum_man.server.gui.objects;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/***
 * @version 1.0
 * @author Michael Januschek
 * @category 
 * 
 * Beschreibung: Dies ist die Bombe mit der W�nde und Spieler zerst�rt werden.
 */
public class Bombe extends SpielObjekt
{
	private Integer zeit;
	private long syszeit;
	private Player besitzer;
	private int sprungx;
	private int sprungy;
	private Integer anfangx;
	private Integer anfangy;
	private int range;
	private String powerup;
	private String powerdown;
	
	public Bombe(Image image, int zeit) 
	{
		super(image);
		super.setObjectName("Bombe");
		this.zeit=zeit;
	}
	
	@Override
	public void draw(Graphics g) 
	{
		image.draw(x-sprungx/2, y-sprungy/2);
		g.setColor(Color.green);
		g.drawRect(x-1,y-1, 50+1, 50+1);
	}

	@Override
	public void update(int delta) 
	{
		
	}
	
	public void setZeit(int zeit)
	{
		this.zeit=zeit;
	}
	
	public int getZeit()
	{
		return this.zeit;
	}
	
	public void setSystemZeit(long syszeit)
	{
		this.syszeit=syszeit;
	}
	
	public long getSystemZeit()
	{
		return this.syszeit;
	}
	
	public Player getBesitzer()
	{
		return this.besitzer;
	}
	
	public void setBesitzer(Player besitzer)
	{
		this.besitzer=besitzer;
	}
	
	public void setSprungX(int sprungx)
	{
		this.sprungx=sprungx;
	}
	
	public int getSprungX()
	{
		return this.sprungx;
	}
	
	public void setSprungY(int sprungy)
	{
		this.sprungy=sprungy;
	}
	
	public int getSprungY()
	{
		return this.sprungy;
	}
	
	public int getRange()
	{
		return this.range;
	}
	
	public void setRange(int range)
	{
		this.range=range;
	}
	
	public String getPowerdown() 
	{
		return this.powerdown;
	}
	
	public void setPowerdown(String powerdown) 
	{
		this.powerdown = powerdown;
	}
	
	public String getPowerup() 
	{
		return this.powerup;
	}
	
	public void setPowerup(String powerup) 
	{
		this.powerup = powerup;
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
