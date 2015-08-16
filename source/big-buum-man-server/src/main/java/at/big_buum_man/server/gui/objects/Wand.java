package at.big_buum_man.server.gui.objects;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import at.big_buum_man.server.gui.helper.Variables;

/***
 * @version 1.0
 * @author Michael Januschek
 *
 */
public class Wand extends SpielObjekt
{
	private int stein;
	private int haltbarkeit;
	
	public Wand(Image image, int paramStein)
	{
		super(image);
		super.setObjectName("Wand");
		stein = paramStein;
	}
	
	public Wand(int paramStein)
	{
		stein = paramStein;
		setObjectName("Wand");
	}
	
	@Override
	public void draw(Graphics g) 
	{
        switch (stein) {
            case 0:
                try {
                    super.setImage(new Image(Variables.res+"walls.png").getSubImage(0,0,Variables.BLOCKWIDTH,Variables.BLOCKHEIGHT));
                } catch (SlickException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    super.setImage(new Image(Variables.res+"walls.png").getSubImage(375,0,Variables.BLOCKWIDTH,Variables.BLOCKHEIGHT));
                } catch (SlickException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    super.setImage(new Image(Variables.res+"saeule.png").getSubImage(0,0,Variables.BLOCKWIDTH,Variables.BLOCKHEIGHT));
                } catch (SlickException e) {
                    e.printStackTrace();
                }
                break;
        }
		image.draw(point.getX(),point.getY());
		g.setColor(Color.green);
		g.drawRect(point.getX(), point.getY(), Variables.BLOCKWIDTH, Variables.BLOCKHEIGHT);
	}
	
	@Override
	public void update(int delta) 
	{
		
	}
	
	public int getStein()
	{
		return stein;
	}
	
	public void setStein(int paramStein)
	{
		stein = paramStein;
	}
	
	public int getHaltbarkeit()
	{
		return haltbarkeit;
	}
	
	public void setHaltbarkeit(Integer haltbarkeit) 
	{
		this.haltbarkeit = haltbarkeit;
	}
}
