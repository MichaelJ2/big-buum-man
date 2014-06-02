package at.big_buum_man.server.gui;

import org.newdawn.slick.*;

/***
 * @version 1.0
 * @author Michael Januschek
 *
 */
public abstract class SpielObjekt {

	protected int x;
	protected int y;
	protected int virx;
	protected int viry;
	protected Image image;

	public abstract void draw(Graphics g);
	public void update(int delta){};

	public SpielObjekt(int x, int y, int virx, int viry, Image image) {
		this(x, y, virx, viry);
		this.image = image;
	}
	
	public SpielObjekt(int x, int y, Image image) {
		this(x, y);
		this.image = image;
	}

	public SpielObjekt(int x, int y, int virx, int viry) {
		this.x = x;
		this.y = y;
		this.virx = virx;
		this.viry = viry;
	}
	
	public SpielObjekt(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public SpielObjekt(Image image) {
		this.image = image;
	}

	public SpielObjekt() {
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getViry() {
		return viry;
	}
	
	public void setViry(int viry) {
		this.viry = viry;
	}
	
	public int getVirx() {
		return virx;
	}
	
	public void setVirx(int virx) {
		this.virx = virx;
	}
	
	public void setImage(Image image)
	{
		this.image=image;
	}

}
