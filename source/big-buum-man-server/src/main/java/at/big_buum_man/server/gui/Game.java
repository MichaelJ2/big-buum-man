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

	private NetworkServer networkserver;														//NetzwerkServer für kommunikation
	private ArrayList<InetAddress> adresslist = new ArrayList<InetAddress>();			//IP-Adressen Liste
	
	private GAMEMODE gamemode; 														//Game mode fÃ¼r die bestimmten Spielearten

	private MapListe maplist = new MapListe();

	private long starttime = 0; 														//Startzeit vom Spiel 														//Aktuelle zeit vom Spiel

	private TrueTypeFont trueTypeFont=new TrueTypeFont(new Font(Variables.letter, Variables.font, Variables.charsize), true);
	//private TrueTypeFont trueTypeFont3=new TrueTypeFont(new Font(Variables.letter, Variables.font, Variables.charsize), true);
	
	private ArrayList<TrueTypeFont> fontlist=new ArrayList<TrueTypeFont>();											//Typefont Liste zum Zeichnen von Buchstaben
	private ArrayList<Player> playerlist=new ArrayList<Player>();											//Playerliste

	//private ArrayList<Wand> hl=new ArrayList<Wand>();
	private ArrayList<ArrayList<Wand>> blocklist=new ArrayList<ArrayList<Wand>>(); 
	
	private ArrayList<Bombe> bomblist=new ArrayList<Bombe>(); 												//Bombenliste auf dem Feld
	private ArrayList<Powerup> poweruplist=new ArrayList<Powerup>(); 											//Powerupsliste auf dem Feld
	private ArrayList<Powerdown> powerdownlist=new ArrayList<Powerdown>(); 										//Powerdownsleste auf dem Feld
	
	private Image spriteSheetImage;
	private Image bombImage;
	private Image ipowerImage;
	private Image iminderImage;

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
		handler.RenderSidebar(g,playerlist,fontlist);		//Render of the Sidebar
		handler.RenderMap(g,blocklist);			//Render of the Map
		handler.RenderPlayer(g,playerlist);		//Render of the Players
		handler.RenderBomb(g,bomblist);			//Render of the Bombs
		handler.RenderPowerups(g,poweruplist);		//Render of the Powerups
		handler.RenderPowerdowns(g,powerdownlist);		//Render of the Powerdowns
		handler.RenderTime(starttime,trueTypeFont);
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
		System.out.println(this.starttime);
		this.starttime=handler.InitTime();
		System.out.println(this.starttime);
		//handler.InitFonts(trueTypeFont,trueTypeFont3);
		this.InitResources();
		//handler.InitLists(bomben,powerups,powerdowns,playerliste,pliste);
		handler.InitTestPlayer(playerlist,blocklist);
		this.fontlist = handler.InitFontLists(playerlist,fontlist);
		handler.InitMap(blocklist,maplist);
		handler.InitServer(this,networkserver);
		handler.InitPlayer(playerlist,blocklist);
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
		handler.UpdateEventInput(container,playerlist); 		//Eventabfrage = Tastaturabfrage
		handler.UpdatePlayer(delta,playerlist);		//Update Player 
		handler.UpdateBombs(bomblist,playerlist,blocklist);			//Update Bombs
		handler.UpdatePowerups(poweruplist,playerlist,ipowerImage,blocklist);		//Update Powerups
		handler.UpdatePowerdowns(powerdownlist,playerlist,iminderImage,blocklist);		//Update Powerdowns
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
		handler.processMessage(client, message,playerlist);
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
		handler.neuerSpieler(client, adresslist, playerlist, blocklist);
	}
	
	private void InitResources() throws SlickException 
	{
		this.spriteSheetImage = new Image(Variables.res+"p.png");
		this.bombImage = new Image(Variables.res+"bomb.png");
		this.ipowerImage = new Image(Variables.res+"Powerup.png");
		this.iminderImage = new Image(Variables.res+"Powerdown.png");
	}
	

	public void addBombe(int x, int blockwidth, int y, int blockheight, Player player) throws SlickException 
	{
		handler.addBomb(x, blockwidth, y, blockheight, player, bomblist, bombImage);
	}   
}
