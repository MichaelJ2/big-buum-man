package at.big_buum_man.server.gui.objects;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import at.big_buum_man.server.gui.helper.Variables;

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
	private int sprungx=Variables.BLOCKWIDTH;
	private int sprungy=Variables.BLOCKHEIGHT;
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
		image.draw(point.getX()-sprungx/2, point.getY()-sprungy/2);
		g.setColor(Variables.green);
		g.drawRect(point.getX()-1,point.getY()-1, Variables.BLOCKWIDTH+1, Variables.BLOCKHEIGHT+1);
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
}
