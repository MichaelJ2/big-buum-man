import at.big_buum_man.server.gui.helper.Point;

public class TestHandlingPoint 
{
	private static int brettx;
	private static int bretty;
	private static int anfangx;
	private static int anfangy;
	private static int x;
	private static int y;
	
	public static void main(String[] args) 
	{
		coordstopancoords();
		pancoordstocoords();
	}

	private static void pancoordstocoords() 
	{
		System.out.println("Test von Brettkoordinaten zu Koordinaten");
		
		brettx=10;
		bretty=5;
		anfangx=50;
		anfangy=100;
		
		Point p2 = new Point();
		p2.setAnfangX(anfangx);
		p2.setAnfangY(anfangy);
		p2.setBrettX(brettx);
		p2.setBrettY(bretty);
		System.out.println("(brettx:"+brettx+"*50) + anfangx:"+anfangx+" = X:"+p2.getX()+" = ["+p2.getBrettX()+"]");
		System.out.println("(bretty:"+bretty+"*50) + anfangy:"+anfangy+" = Y:"+p2.getY()+" = ["+p2.getBrettY()+"]");
	}

	private static void coordstopancoords() 
	{
		System.out.println("Test von Koordinaten zu Brettkoordinaten");
		
		x=200;
		y=400;
		anfangx=50;
		anfangy=100;
		
		Point p = new Point();
		p.setX(x);
		p.setY(y);
		p.setAnfangX(anfangx);
		p.setAnfangY(anfangy);
		System.out.println("x:"+x+" - anfangx:"+anfangx+" = BrettX:"+p.getBrettX());
		System.out.println("y:"+y+" - anfangy:"+anfangy+" = BrettY:"+p.getBrettY());
	}

	
}
