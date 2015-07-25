package at.big_buum_man.server.gui.objects;

import org.newdawn.slick.*;

import at.big_buum_man.server.gui.helper.Point;

/***
 * @version 1.0
 * @author Michael Januschek
 *
 */
public abstract class SpielObjekt 
{
	protected Point point;
	protected Image image;
	public String objectName;

	public abstract void draw(Graphics g);
	public void update(int delta){};

	public SpielObjekt(Point point, Image image) 
	{
		this(point.getX(),point.getY());
		this.image = image;
	}

	
	public SpielObjekt(int x, int y) 
	{
		this.point.setX(x);
		this.point.setY(y);
	}
	
	public SpielObjekt(Image image) 
	{
		this.image = image;
	}

	public SpielObjekt() 
	{
	}
	
	public int getX() 
	{
		return point.getX();
	}

	public int getY() 
	{
		return point.getY();
	}
	
	public int getBrettX()
	{
		return point.getBrettX();
	}
	
	public int getBrettY()
	{
		return point.getBrettY();
	}
	
	public Point getPoint() 
	{
		return point;
	}

	public void setPoint(Point point) 
	{
		this.point=point;
	}
	
	public void setImage(Image image)
	{
		this.image=image;
	}
	
	public void setObjectName(String objectname)
	{
		this.objectName=objectname;		
	}
	
	public String getObjectName()
	{
		return this.objectName;		
	}

}
