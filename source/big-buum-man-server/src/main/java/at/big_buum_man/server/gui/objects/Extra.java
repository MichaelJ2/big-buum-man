package at.big_buum_man.server.gui.objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Extra extends SpielObjekt
{
	private String name;

	public Extra(Image image) 
	{
		super(image);
	}
	
	public Extra() 
	{
		
	}
	
	@Override
	public void draw(Graphics g) 
	{
		image.draw(x, y);
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
}
