package at.big_buum_man.server.gui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import at.big_buum_man.server.gui.helper.Variables;

public class Main 
{
	/***
	 * 	Hauptfunktion um das Spiel zu starten
	 * 
	 * 	@param args
	 * 	@throws SlickException
	 */
	public static void main(String[] args) throws SlickException 
	{
		StartSeite ss = new StartSeite();
		AppGameContainer container = new AppGameContainer(ss);
		container.setDisplayMode(Variables.WIDTH, Variables.HEIGHT, true);
		container.setClearEachFrame(true);
		container.setMinimumLogicUpdateInterval(25);
		container.setTargetFrameRate(60);
		container.setShowFPS(true);
		container.start();
	}
}
