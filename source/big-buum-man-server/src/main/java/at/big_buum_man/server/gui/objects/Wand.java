package at.big_buum_man.server.gui.objects;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/***
 * @version 1.0
 * @author Michael Januschek
 *
 */
public class Wand extends SpielObjekt
{
	private String stein;
	private Integer haltbarkeit;
	
	public Wand(Image image, String stein) 
	{
		super(image);
		super.setObjectName("Wand");
		this.stein=stein;
	}
	
	public Wand(String stein)
	{
		this.stein=stein;
		setObjectName("Wand");
	}
	
	@Override
	public void draw(Graphics g) 
	{
		int blockwidth=50;
		int blockheight=50;
		if(this.getStein().equals("0"))
		{
			try 
			{
				super.setImage(new Image("res/walls.png").getSubImage(0,0,blockwidth,blockheight));
			} 
			catch (SlickException e) 
			{
				e.printStackTrace();
			}
		}
		else if(this.getStein().equals("1"))
		{
			try 
			{
				super.setImage(new Image("res/walls.png").getSubImage(375,0,blockwidth,blockheight));
			} 
			catch (SlickException e) 
			{
				e.printStackTrace();
			}
		}
		else 
		{
			try 
			{
				super.setImage(new Image("res/saeule.png").getSubImage(0,0,blockwidth,blockheight));
			} 
			catch (SlickException e) 
			{
				e.printStackTrace();
			}
		}
		image.draw(x,y);
		g.setColor(Color.green);
		g.drawRect(x,y, 50, 50);
	}
	
	@Override
	public void update(int delta) 
	{
		
	}
	
	public String getStein()
	{
		return this.stein;
	}
	
	public void setStein(String stein)
	{
		this.stein=stein;
	}
	
	public Integer getHaltbarkeit() 
	{
		return haltbarkeit;
	}
	
	public void setHaltbarkeit(Integer haltbarkeit) 
	{
		this.haltbarkeit = haltbarkeit;
	}
}
