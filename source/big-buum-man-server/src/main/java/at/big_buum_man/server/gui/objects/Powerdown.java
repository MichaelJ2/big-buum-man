package at.big_buum_man.server.gui.objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import at.big_buum_man.server.gui.helper.Variables;

public class Powerdown extends Extra
{
	private Integer zeit;
	private long syszeit;
	
	public Powerdown(Image image) 
	{
		super(image);
		super.setObjectName("Powerdown");
		this.zeit=10000;
	}
	
	public Powerdown() 
	{
		setObjectName("Powerdown");
		this.zeit=10000;
	}
	
	@Override
	public void draw(Graphics g) 
	{
		image.draw(point.getX(), point.getY());
		g.setColor(Color.green);
		g.drawRect(point.getX(),point.getY(), Variables.BLOCKWIDTH, Variables.BLOCKHEIGHT);
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
