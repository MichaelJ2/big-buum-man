package at.big_buum_man.server.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Powerup extends Extra
{
	private Integer zeit;
	private long syszeit;
	
	public Powerup(Image image) 
	{
		super(image);
		this.zeit=10000;
	}
	
	@Override
	public void draw(Graphics g) 
	{
		image.drawCentered(x, y);
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
}
