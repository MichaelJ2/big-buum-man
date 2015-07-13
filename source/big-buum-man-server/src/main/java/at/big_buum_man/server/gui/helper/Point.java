package at.big_buum_man.server.gui.helper;

public class Point 
{
	private int x;
	private int y;
	private int anfangx;
	private int anfangy;
	private int blockwidth=50;
	private int blockheight=50;
	
	public Point() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public Point(int x,int y,int anfangx,int anfangy)
	{
		setX(x);
		setY(y);
		setAnfangX(anfangx);
		setAnfangY(anfangy);
	}
	
	public Point(int x,int y) 
	{
		setX(x);
		setY(y);
	}
	
	//setter
	public void setX(int x)
	{
		this.x=x;
	}
	
	public void setY(int y)
	{
		this.y=y;
	}
	
	public void setBrettX(int brettx)
	{
		this.x=this.anfangx+(brettx*blockwidth);
		// TODO Auto-generated constructor stub
	}
	
	public void setBrettY(int bretty)
	{
		this.y=this.anfangy+(bretty*blockheight);
		// TODO Auto-generated constructor stub
	}
	
	public void setAnfangX(int anfangx)
	{
		this.anfangx=anfangx;
	}
	
	public void setAnfangY(int anfangy)
	{
		this.anfangy=anfangy;
	}
	
	//getter
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getBrettX()
	{
		return (this.x-this.anfangx)/blockwidth;
		// TODO Auto-generated constructor stub
	}
	
	public int getBrettY()
	{
		return (this.y-this.anfangy)/blockheight;
		// TODO Auto-generated constructor stub
	}
	
	public int getAnfangX()
	{
		return this.anfangx;
	}
	
	public int getAnfangY()
	{
		return this.anfangy;
	}
}
