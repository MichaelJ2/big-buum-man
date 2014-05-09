package at.big_buum_man.server.gui;

import java.awt.Font;
import java.awt.Polygon;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Circle;

public class StartSeite extends BasicGame
{
	public static final int WIDTH = Display.getDesktopDisplayMode().getWidth();
	public static final int HEIGHT = Display.getDesktopDisplayMode().getHeight();
	private int xw;
	private int yw;
	private int pos=1;
	private String text="test \n test \n test";
	private TrueTypeFont trueTypeFont;

	public StartSeite() 
	{
		super("StartSeite");
		xw=250-5-20;
		yw=150-5;
	}
	
	/***
	 * 
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException 
	{
		StartSeite ss = new StartSeite();
		AppGameContainer container = new AppGameContainer(ss);
		container.setDisplayMode(WIDTH, HEIGHT, true);
		container.setClearEachFrame(true);
		container.setMinimumLogicUpdateInterval(25);
		container.setTargetFrameRate(60);
		container.setShowFPS(true);
		container.start();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException 
	{

		g.setColor(Color.white);
		g.fillRect((WIDTH/100*10)+1, (HEIGHT/100*10)+1, WIDTH/100*80 ,HEIGHT/100*80);
		
		g.setColor(Color.red);
		g.fillRoundRect(300-5-20, 130-5, 150, 40, 10);
		
		g.setColor(Color.red);
		g.fillRoundRect(300-5-20, 230-5, 150, 40, 10);
		
		g.setColor(Color.red);
		g.fillRoundRect(300-5-20, 330-5, 150, 40, 10);
		
		g.setColor(Color.red);
		g.fillRoundRect(300-5-20, 430-5, 150, 40, 10);
		
		g.setColor(Color.red);
		g.fillRoundRect(300-5-20, 530-5, 150, 40, 10);
		
		Circle circle =  new Circle(xw, yw, 20);
		g.setColor(Color.red);
		g.fill(circle);
		g.setColor(Color.black);
		g.draw(circle);
		trueTypeFont.drawString(310-20, 132,"Infos", Color.black);
		trueTypeFont.drawString(310-20, 232,"Extras", Color.black);
		trueTypeFont.drawString(310-20, 332,"Impressum", Color.black);
		trueTypeFont.drawString(310-20, 432,"Spiel starten", Color.black);
		trueTypeFont.drawString(310-20, 532,"Spiel beenden", Color.black);
		
		g.setColor(Color.red);
		g.fillRoundRect(500-20, 130-5, 600, 440, 10);
		
		switch(pos)
		{
			case 1:
				trueTypeFont.drawString(510-20, 132, text, Color.black);
			break;
			case 2:
				trueTypeFont.drawString(510-20, 132, "Extras", Color.black);
			break;
			case 3:
				trueTypeFont.drawString(510-20, 132, "lalala", Color.black);
			break;
			case 4:
				trueTypeFont.drawString(510-20, 132, "jfjejve", Color.black);
			break;
			case 5:
				trueTypeFont.drawString(510-20, 132, "Spiel beenden", Color.black);
			break;
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException 
	{

		trueTypeFont = new TrueTypeFont(new Font("Arial", Font.BOLD, 20), true);
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException 
	{
		Input input=container.getInput();
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) 
		{
			container.exit();
		}

		if(pos<5)
		{
			if (input.isKeyPressed(Input.KEY_DOWN)) 
			{
				yw=yw+100;
				pos++;
			}
		}
		
		if(pos>1)
		{
			if (input.isKeyPressed(Input.KEY_UP)) 
			{
				yw=yw-100;
				pos--;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ENTER)) 
		{
			if(pos==5)
				container.exit();
			System.out.println("Enter"+pos);
		}
	}

}
