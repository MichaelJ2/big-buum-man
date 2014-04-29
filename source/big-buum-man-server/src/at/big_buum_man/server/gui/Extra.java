package at.big_buum_man.server.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Extra extends SpielObjekt
{
	private String name;

	public Extra(Image image) 
	{
		super(image);
	}
	
	@Override
	public void draw(Graphics g) 
	{
		image.drawCentered(x, y);
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
