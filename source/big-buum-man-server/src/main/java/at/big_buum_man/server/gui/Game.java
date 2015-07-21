package at.big_buum_man.server.gui;

import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import at.big_buum_man.server.ServerMethods;
import at.big_buum_man.server.gui.enums.GAMEMODE;
import at.big_buum_man.server.gui.helper.BombermanHandler;
import at.big_buum_man.server.gui.helper.GameHandler;
import at.big_buum_man.server.gui.helper.Variables;
import at.big_buum_man.server.gui.objects.Bombe;
import at.big_buum_man.server.gui.objects.MapListe;
import at.big_buum_man.server.gui.objects.Player;
import at.big_buum_man.server.gui.objects.Powerdown;
import at.big_buum_man.server.gui.objects.Powerup;
import at.big_buum_man.server.gui.objects.Wand;
import at.big_buum_man.server.network.NetworkServer;

public class Game extends BasicGame implements Runnable ,ServerMethods
{

	private NetworkServer ns;														//NetzwerkServer für kommunikation
	private ArrayList<InetAddress> adressen = new ArrayList<InetAddress>();			//IP-Adressen Liste
	
	private GAMEMODE gamemode; 														//Game mode fÃ¼r die bestimmten Spielearten

	private MapListe ml = new MapListe();

	private long starttime; 														//Startzeit vom Spiel
	private long currenttime; 														//Aktuelle zeit vom Spiel

	private TrueTypeFont trueTypeFont;
	private TrueTypeFont trueTypeFont3;
	
	private ArrayList<TrueTypeFont> pliste;											//Typefont Liste zum Zeichnen von Buchstaben
	private ArrayList<Player> playerliste;											//Playerliste

	private ArrayList<Wand> hl;
	private ArrayList<ArrayList<Wand>> vl; 
	
	private ArrayList<Bombe> bomben; 												//Bombenliste auf dem Feld
	private ArrayList<Powerup> powerups; 											//Powerupsliste auf dem Feld
	private ArrayList<Powerdown> powerdowns; 										//Powerdownsleste auf dem Feld
	
	private Image spriteSheetImage;
	private Image bomb;
	private Image ipower;
	private Image iminder;

	private static GameHandler handler= new GameHandler();
	private static Game instance = null;										//Instanz von Bomberman
	
	/***
	 * 	Summary:
	 * 	Bomberman Konstruktor
	 * 
	 * 	Description:
	 * 
	 */
	private Game() 
	{
		super("Bomberman"); //Window Title
	}
	
	/***
	 * 	Summary:
	 * 	getInstance
	 * 
	 * 	Description:
	 * 	
	 * 	@return Bomberman
	 */
	public static synchronized Game getInstance() 
	{
	    if (Game.instance == null) 
	    {
	    	Game.instance = new Game();
	    }
	    return Game.instance;
	 }

	/**	
	 * 	Summary: 
	 * 	In dieser Funktion werden alle Resourcen und Grafiken neu gezeichnet in einem bestimmten Intervall
	 * 
	 * 	Description:
	 * 
	 * 	@param container = 
	 * 	@param g = 
	 * 
	 * 	
	 */
	@Override
	public synchronized void render(GameContainer container, Graphics g) throws SlickException 
	{
		handler.RenderSidebar(g,playerliste,pliste);		//Render of the Sidebar
		handler.RenderMap(g,vl);			//Render of the Map
		handler.RenderPlayer(g,playerliste);		//Render of the Players
		handler.RenderBomb(g,bomben);			//Render of the Bombs
		handler.RenderPowerups(g,powerups);		//Render of the Powerups
		handler.RenderPowerdowns(g,powerdowns);		//Render of the Powerdowns
		handler.RenderTime(currenttime,trueTypeFont,starttime);
	}

	/**
	 * 	Summary:
	 * 	Initial wird das Spiel hier erstellt und alle Resourcen geladen
	 * 	
	 * 	Description:
	 * 
	 * 	@param container = 
	 * 	
	 */
	@Override
	public synchronized void init(GameContainer container) throws SlickException 
	{
		handler.InitTime(starttime);
		handler.InitFonts(trueTypeFont,trueTypeFont3);
		handler.InitResources(spriteSheetImage,bomb,ipower,iminder);
		handler.InitLists(bomben,powerups,powerdowns,playerliste,pliste);
		handler.InitTestPlayer(playerliste,vl);
		handler.InitFontLists(playerliste,pliste);
		handler.InitMap(vl,hl,ml);
		handler.InitServer(this,ns);
		handler.InitPlayer(playerliste,vl);
	}
	
	/**
	 * 	Summary:
	 * 	Hier wird das Spiel geupdated und resourcen verwaltet - Spiellogik
	 * 
	 * 	Description:
	 * 
	 * 	@param container =
	 * 	@param delta = 
	 * 	
	 */
	@Override
	public synchronized void update(GameContainer container, int delta) throws SlickException 
	{
		handler.Eventinput(container,playerliste); 		//Eventabfrage = Tastaturabfrage
		handler.UpdatePlayer(delta,playerliste);		//Update Player 
		handler.UpdateBombs(bomben,playerliste,vl);			//Update Bombs
		handler.UpdatePowerups(powerups,playerliste,ipower,vl);		//Update Powerups
		handler.UpdatePowerdowns(powerdowns,playerliste,iminder,vl);		//Update Powerdowns
	}
	
	@Override
	public void run() 
	{
		
	}


	/**
	 * 	Summary:
	 * 	Nachricht wird von Spieler erhalten und Spieler wird bewegt/verÃ¤ndert
	 * 	
	 * 	Description:
	 * 
	 * 	@param client =
	 * 	@param message =
	 */
	@Override
	public void processMessage(InetAddress client, String message) 
	{
		handler.processMessage(client, message,playerliste);
	}

	/**
	 * 	Summary:
	 *  	neuer Client hinzufÃ¼gen in Liste und ins Spiel
	 * 
	 * 	Description:
	 * 
	 * 	@param client
	 */
	@Override
	public void registerNewClient(InetAddress client) 
	{
		handler.neuerSpieler(client, adressen, playerliste, vl);
	}
}
