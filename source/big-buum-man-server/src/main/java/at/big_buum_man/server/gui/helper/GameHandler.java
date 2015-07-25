package at.big_buum_man.server.gui.helper;

import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import at.big_buum_man.server.gui.Game;
import at.big_buum_man.server.gui.objects.Bombe;
import at.big_buum_man.server.gui.objects.MapListe;
import at.big_buum_man.server.gui.objects.Player;
import at.big_buum_man.server.gui.objects.Powerdown;
import at.big_buum_man.server.gui.objects.Powerup;
import at.big_buum_man.server.gui.objects.Wand;
import at.big_buum_man.server.network.NetworkServer;

public class GameHandler 
{
	/**
	 * 	Summary:
	 *  	Hier wird die Zeit erreichnet die verstrichen ist.
	 * 
	 * 	Description:
	 * 
	 * 	@param start
	 * 	@param current
	 */
	public String timeconvert(long start, long current)
	{
		String time;
		long timestamp = ((current - start)/1000);
		if(timestamp>=60)
		{
			if(timestamp%60==0)
			{
				time = (timestamp/60)+":00";
			}
			else
			{
				if(timestamp%60>0&&timestamp%60<10)
					time = (timestamp/60)+":0"+(timestamp%60);
				else
					time = (timestamp/60)+":"+(timestamp%60);
			}
			time= time +" Minuten";
		}
		else
		{
			time=""+((current - start)/1000)+" Sekunden";
		}
		return time;
	}

	/***
	 * 	Summary:
	 * 	Neuer Spieler wird dem Spiel hinzugefÃ¼gt
	 * 
	 * 	Description:
	 * 
	 * 	@param adresse = InetAddress wird Ã¼bergeben fÃ¼r den Player
	 * 	@return Player
	 */
	public synchronized Player neuerSpieler(InetAddress adresse,ArrayList<InetAddress> adressen,ArrayList<Player> playerliste,ArrayList<ArrayList<Wand>> vl)  
	{
		adressen.add(adresse);
		Player p=null;
		try 
		{
			p = new Player(new Image(Variables.res+"player.png"));
			p.setAdresse(adresse);
			p.setMapn(vl);
			
			for(int px=0;px<Variables.cols;px++)
				for(int py=0;py<Variables.rows;py++)
					if(vl.get(py).get(px).getStein().equals("x"))
						{
						Point point= new Point(	Variables.xleft+px*Variables.BLOCKWIDTH+Variables.BLOCKWIDTH,
												Variables.xleft+px*Variables.BLOCKWIDTH+Variables.BLOCKWIDTH,
												Variables.xleft,
												Variables.ytop);
						p.setposition(point);
						}
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
		p.setName("Neuer Spieler");
		
		playerliste.add(p);
		return p;
	}

	
	/////////////////////////////////////////////////
	// Hier werden die Methoden Definiert die dann in der Update routine verwendet werden
	/////////////////////////////////////////////////
	public void UpdateEventInput(GameContainer container,ArrayList<Player> playerliste)
	{
		int pl=0;
		Input input=container.getInput();
		if (input.isKeyPressed(Settings.ESCAPE)) 
		{
			container.exit();
		}
		
		if (input.isKeyPressed(Settings.LEFT)) 
		{
			playerliste.get(pl).left();
		}
		
		if (input.isKeyPressed(Settings.RIGHT)) 
		{
			playerliste.get(pl).right();
		}
		
		if (input.isKeyPressed(Settings.UP)) 
		{
			playerliste.get(pl).up();
		}
		
		if (input.isKeyPressed(Settings.DOWN)) 
		{
			playerliste.get(pl).down();
		}
		
		if (input.isKeyPressed(Settings.SPACE)) 
		{
			playerliste.get(pl).buttonA();
		}
	}

	public void UpdatePlayer(int delta,ArrayList<Player> playerliste)
	{
		for(Player p:playerliste)
		{
			p.update(delta);
		}
	}
	
	public void UpdateBombs(ArrayList<Bombe> bomben,ArrayList<Player> playerliste,ArrayList<ArrayList<Wand>> vl)
	{
		if(!(bomben.isEmpty()))
		{
			for (int i=0;i<bomben.size();i++) 
			{
				Bombe b = bomben.get(i);
				if(b.getZeit()+b.getSystemZeit()<System.currentTimeMillis())
				{
					activateBomb(b.getBrettX(),b.getBrettY(),b,playerliste, vl);
					//createExplosions();
					bomben.remove(b);
				}	
			}
		}
	}
	
	public void UpdatePowerups(ArrayList<Powerup> powerups,ArrayList<Player> playerliste,Image ipower,ArrayList<ArrayList<Wand>> vl)
	{
		if(!(powerups.isEmpty()))
		{
			for (int i=0;i<powerups.size();i++) 
			{
				Powerup up = powerups.get(i);
				for (int j=0;j<playerliste.size();j++) 
				{
					Player u = playerliste.get(j);
					if(up.getX()==u.getX() && up.getY()==u.getY())
					{
						u.addPowerup(up);
						powerups.remove(up);
					}	
				}
			}
			
			for (int i=0;i<powerups.size();i++) 
			{
				Powerup up = powerups.get(i);
				if(up.getZeit()+up.getSystemZeit()<System.currentTimeMillis())
				{
					powerups.remove(up);
				}	
			}
		}
		else
		{
			Random ra= new Random();
			int randomzahl = ra.nextInt(1000);
			
			if(randomzahl>950)
			{
				Random rax= new Random();
				int rx = rax.nextInt(Variables.cols);
				
				Random ray= new Random();
				int ry = ray.nextInt(Variables.rows);
				
				if(!vl.get(ry).get(rx).getStein().equals("0"))
				{
					Powerup powerup=new Powerup(ipower);
					powerup.setSystemZeit(System.currentTimeMillis());
					Point point = new Point(	Variables.xleft+rx*Variables.BLOCKWIDTH,
												Variables.ytop+ry*Variables.BLOCKHEIGHT,
												Variables.xleft,
												Variables.ytop);
					powerup.setPoint(point);
					powerups.add(powerup);
				}
			}
			else
			{
				//System.out.println(randomzahl+" keine Powerup");
			}
		}
	}

	public void UpdatePowerdowns(ArrayList<Powerdown> powerdowns,ArrayList<Player> playerliste,Image iminder,ArrayList<ArrayList<Wand>> vl)
	{
		if(!(powerdowns.isEmpty()))
		{
			for (int i=0;i<powerdowns.size();i++) 
			{
				Powerdown down = powerdowns.get(i);
				for (int j=0;j<playerliste.size();j++) 
				{
					Player u = playerliste.get(j);
					if(down.getX()==u.getX() && down.getY()==u.getY())
					{
						u.addPowerdown(down);
						powerdowns.remove(down);
					}	
				}
			}
			
			for (int i=0;i<powerdowns.size();i++) 
			{
				Powerdown down = powerdowns.get(i);
				if(down.getZeit()+down.getSystemZeit()<System.currentTimeMillis())
				{
					powerdowns.remove(down);
				}	
			}
		}
		else
		{
			Random ra= new Random();
			int randomzahl = ra.nextInt(1000);
			
			if(randomzahl>980)
			{
				Random rax= new Random();
				int rx = rax.nextInt(Variables.cols);
				
				Random ray= new Random();
				int ry = ray.nextInt(Variables.rows);
				if(!vl.get(ry).get(rx).getStein().equals("0"))
				{
					Powerdown powerdown=new Powerdown(iminder);
					powerdown.setSystemZeit(System.currentTimeMillis());
					Point point = new Point(	Variables.xleft+rx*Variables.BLOCKWIDTH,
												Variables.ytop+ry*Variables.BLOCKHEIGHT,
												Variables.xleft,
												Variables.ytop);
					powerdown.setPoint(point);
					powerdowns.add(powerdown);
				}
			}
			else
			{
				//System.out.println(randomzahl+" keine Powerdown");
			}
		}
	}

	/////////////////////////////////////////////////
	// Hier werden die Methoden Definiert die dann in der Init routine verwendet werden
	/////////////////////////////////////////////////
	public void InitMap(ArrayList<ArrayList<Wand>> vl, MapListe ml)
	{
		int y=0;
		//vl=new ArrayList<ArrayList<Wand>>();
		for(String[] s:ml.getMap())
    	{
			ArrayList<Wand> hl=new ArrayList<Wand>();
    		int x=0;
	   		for(String o:s)
	   		{
		  		Wand w=new Wand(o);
		  		x=x+Variables.BLOCKWIDTH;
				if(o.equals("x"))
		 		{
			  		//for(Player p:playerliste)
			  			//p.setposition(sidebarwidth+x-blockwidth,y+blockheight);
		 		}
			 	hl.add(w);
	   		}
   		y=y+Variables.BLOCKHEIGHT;
   		vl.add(hl);
    	}
	}

	public void InitServer(Game game,NetworkServer ns)
	{
		ns=new NetworkServer(game);
		try 
		{	ns.startServer();		} 
		catch (Exception e) 
		{	e.printStackTrace();	}
		
		try 
		{	ns.startAnnounce();		} 
		catch (Exception e) 
		{	e.printStackTrace();	}
	}

	public void InitPlayer(ArrayList<Player> playerliste,ArrayList<ArrayList<Wand>> vl)
	{
		for(Player p:playerliste)
		{
			p.setMapn(vl);
		}
	}

	public ArrayList<TrueTypeFont> InitFontLists(ArrayList<Player> playerliste,ArrayList<TrueTypeFont> pliste)
	{
		for(int i=0;i<playerliste.size();i++)
			pliste.add(new TrueTypeFont(new Font(Variables.letter, Variables.font, Variables.charsize), true));
		
		return pliste;
	}
	
	public void InitTestPlayer(ArrayList<Player> playerliste,ArrayList<ArrayList<Wand>> vl) throws SlickException 
	{
		Point point= new Point(975, 340,Variables.xleft,Variables.ytop);
		Point point2= new Point(975, 340,Variables.xleft,Variables.ytop);
		
		Player player = new Player(new Image(Variables.res+"player.png"));
		player.setName("Michael");
		player.setColor(Variables.red);
		player.setposition(point);
		player.setMapn(vl);
		
		try 
		{ player.setAdresse(InetAddress.getByName("192.168.1.1")); } 
		catch (UnknownHostException e1) 
		{ e1.printStackTrace(); }

		Player player1 = new Player(new Image(Variables.res+"player2.png"));
		player1.setName("Gerald");
		player1.setColor(Variables.green);
		player1.setposition(point2);
		player1.setMapn(vl);
		try 
		{ player1.setAdresse(InetAddress.getByName("192.168.1.1")); } 
		catch (UnknownHostException e1) 
		{ e1.printStackTrace(); }
		
		player1.plus();
		
		playerliste.add(player);
		playerliste.add(player1);
	}	
	
	/*
	public void InitLists(ArrayList<Bombe> bomben,ArrayList<Powerup>powerups,ArrayList<Powerdown> powerdowns,ArrayList<Player> playerliste,ArrayList<TrueTypeFont> pliste)
	{
		bomben=new ArrayList<Bombe>(); //Bombenliste erstellen
		powerups=new ArrayList<Powerup>(); //Powerupsliste erstellen
		powerdowns=new ArrayList<Powerdown>(); //Powerdownsliste erstellen
		playerliste =new ArrayList<Player>(); //Playerliste erstellen
		pliste=new ArrayList<TrueTypeFont>(); //Fontliste erstellen
	}
	*/
	
	/*
	public void InitResources(Image spriteSheetImage,Image bomb,Image ipower,Image iminder) throws SlickException 
	{
		spriteSheetImage = new Image(Variables.res+"p.png");
		bomb = new Image(Variables.res+"bomb.png");
		ipower = new Image(Variables.res+"Powerup.png");
		iminder = new Image(Variables.res+"Powerdown.png");
	}   	
	
	*/
	/*
	public void InitFonts(TrueTypeFont trueTypeFont,TrueTypeFont trueTypeFont3)
	{
		trueTypeFont = new TrueTypeFont(new Font(Variables.letter, Variables.font, Variables.charsize), true);
		trueTypeFont3 = new TrueTypeFont(new Font(Variables.letter, Variables.font, Variables.charsize), true);
	}
	*/
	
	public long InitTime()
	{
		return System.currentTimeMillis();
	}	
	
	/////////////////////////////////////////////////
	// Hier werden die Methoden Definiert die dann in der render routine verwendet werden
	/////////////////////////////////////////////////
	public void RenderMap(Graphics g,ArrayList<ArrayList<Wand>> vl)
	{
		g.setColor(Variables.green);
		g.drawRect(Variables.xleft-5,Variables.ytop-5, (Variables.cols*Variables.BLOCKWIDTH)+10, (Variables.rows*Variables.BLOCKHEIGHT)+10);
		
		int y=0+Variables.ytop;
		for(ArrayList<Wand> v: vl)
	    	{
			int x=0+Variables.xleft;
	     	   	for(Wand h:v)
	     	   	{
		     		  if(h.getStein().equals("0"))
		     			  g.setColor(Variables.gray);
		     		  else if(h.getStein().equals("1"))
		     			  g.setColor(Variables.green);
		     		  else if(h.getStein().equals("2"))
		     			  g.setColor(Variables.red);
		     		  else if(h.getStein().equals("3"))
		     			  g.setColor(Variables.blue);
		     		  else if(h.getStein().equals("4"))
		     			  g.setColor(Variables.white);
		     		  else if(h.getStein().equals("5"))
		     			  g.setColor(Variables.yellow);
		     		  else if(h.getStein().equals("6"))
		     			  g.setColor(Variables.white);
		     		  else if(h.getStein().equals("7"))
		    			  g.setColor(Variables.magenta);
		     		  else if(h.getStein().equals("8"))
		    			  g.setColor(Variables.blue);
		     		  else if(h.getStein().equals("9"))
		     			  g.setColor(Variables.red);
		     		 else if(h.getStein().equals("x"))
		     		 {
		      			  g.setColor(Variables.green);
		     		 }
	 		   		else
		 			  g.setColor(Variables.darkGray);
		     		  
		     		  Point point = new Point(x,y,Variables.xleft,Variables.ytop);
		     		  h.setPoint(point);
		     		  h.draw(g);
		     		  
		     		  x=x+Variables.BLOCKWIDTH;
	     	   	}
	     	   	y=y+Variables.BLOCKHEIGHT;
	        }
	}

	public void RenderPlayer(Graphics g,ArrayList<Player> playerliste)
	{
		if(playerliste!=null&&!playerliste.isEmpty())
		{
			g.setColor(Variables.red);
			for(Player p:playerliste)
			{
				p.draw(g);
			}
		}
	}

	public void RenderBomb(Graphics g,ArrayList<Bombe> bomben)
	{
		if(bomben!=null&&!bomben.isEmpty())
		{
			for(Bombe bo:bomben)
			{
				bo.draw(g);
			}
		}
	}
	
	public void RenderPowerups(Graphics g,ArrayList<Powerup> powerups)
	{
		if(powerups!=null&&!powerups.isEmpty())
		{
			for(Powerup up:powerups)
			{
				up.draw(g);
			}
		}
	}

	public void RenderPowerdowns(Graphics g,ArrayList<Powerdown> powerdowns)
	{
		if(powerdowns!=null&&!powerdowns.isEmpty())
		{
			for(Powerdown down:powerdowns)
			{
				down.draw(g);
			}
		}
	}
	
	public void RenderSidebar(Graphics g,ArrayList<Player> playerliste,ArrayList<TrueTypeFont> pliste)
	{
		g.setColor(Variables.lightGray);
		g.fillRect(0, 0, Variables.sidebarwidth+1 ,Variables.HEIGHT);
		
		for(int i=0; i<playerliste.size(); i++) 
		{
			if (i < pliste.size())
				pliste.get(i).drawString(30, 120+20*i,"-"+playerliste.get(i).getName(), Variables.black);
			
		}
	}
	
	public void RenderTime(long starttime,TrueTypeFont trueTypeFont)
	{
		long currenttime = System.currentTimeMillis();
		String time = timeconvert(starttime,currenttime);
		trueTypeFont.drawString(20, 40,"Spielezeit ("+time+")", Variables.black);	
	}
	
	/***
	 * 	Summary:
	 * 	Hier wird eine Bombe hinzugefÃ¼gt in das Spiel
	 * 
	 * 	Description:
	 * 
	 * 	@param x = X Coordinate
	 * 	@param y = Y Coordinate
	 * 	@param sprungy = HÃ¶he eines Feldes
	 * 	@param sprungx = Breite eines Feldes
	 * 	@param p = Player der diese Bombe setzt
	 * */
	public synchronized void addBomb(int x,int sprungx, int y,int sprungy, Player p,ArrayList<Bombe> bomben,Image bomb) throws SlickException
	{
		Point point=new Point(x,y,Variables.xleft,Variables.ytop);
		Bombe bombe=new Bombe(bomb, 2000);
		bombe.setSystemZeit(System.currentTimeMillis());
		bombe.setBesitzer(p);
		bombe.setRange(2);
		bombe.setPoint(point);
		bomben.add(bombe);
	}
	

	/***
	 * 	Summary:
	 * 	Bombe wird gesetzt und explodiert nun richtig
	 * 
	 * 	Description:
	 * 
	 * 	@param x = 
	 * 	@param y =
	 * 	@param b =
	 * 
	 * */
	public synchronized void activateBomb(int x, int y,Bombe b,ArrayList<Player> playerliste,ArrayList<ArrayList<Wand>> vl) 
	{
		int side1=0;
		int side2=0;
		int side3=0;
		int side4=0;
		
		for(int i=0;i<=b.getRange();i++)
		{
			if(y-i>0) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=playerliste.get(p).getBrettX();
					int py=playerliste.get(p).getBrettY();
					
					if(px==x&&py==y-i&&side1==0)
					{
						b.getBesitzer().plus();
						side1=1;
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y-i).get(x).getStein().equals("0")&&side1==0) 
				{
					b.getBesitzer().plus();
					side1=1;
					vl.get(y-i).get(x).setStein(1+"");
				}
			}
			
			if(y+i<Variables.rows-1) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=playerliste.get(p).getBrettX();
					int py=playerliste.get(p).getBrettY();
					
					if(px==x&&py==y+i&&side2==0)
					{
						b.getBesitzer().plus();
						side2=1;
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y+i).get(x).getStein().equals("0")&&side2==0)
				{
					b.getBesitzer().plus();
					side2=1;
					vl.get(y+i).get(x).setStein(1+"");
				}
			}
			
			if(x-i>0) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=playerliste.get(p).getBrettX();
					int py=playerliste.get(p).getBrettY();
					
					if(px==x-i&&py==y&&side3==0)
					{
						b.getBesitzer().plus();
						side3=1;
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y).get(x-i).getStein().equals("0")&&side3==0)
				{
					b.getBesitzer().plus();
					side3=1;
					vl.get(y).get(x-i).setStein(1+"");
				}
			}
			
			if(x+i<Variables.cols-1) 
			{
				for(int p=0;p<playerliste.size();p++)
				{
					int px=playerliste.get(p).getBrettX();
					int py=playerliste.get(p).getBrettY();
					
					if(px==x+i&&py==y&&side4==0)
					{
						b.getBesitzer().plus();
						side4=1;
						playerliste.remove(p);
					}
				}
				
				if(vl.get(y).get(x+i).getStein().equals("0")&&side4==0)
				{
					b.getBesitzer().plus();
					side4=1;
					vl.get(y).get(x+i).setStein(1+"");
				}
			}			
		}
		
		side1=0;
		side2=0;
		side3=0;
		side4=0;
	}


	public void processMessage(InetAddress client, String message,ArrayList<Player> playerliste) 
	{
		for(int p=0;p<playerliste.size();p++)
		{
			if(playerliste.get(p).getAdresse().getHostAddress().equals(client.getHostAddress()))
			{
				if(message.equals("left"))
				{
					playerliste.get(p).left();
				}
				else if(message.equals("right"))
				{
					playerliste.get(p).right();
				}
				else if(message.equals("up"))
				{
					playerliste.get(p).up();
				}
				else if(message.equals("down"))
				{
					playerliste.get(p).down();
				}
				else if(message.equals("a"))
				{
					playerliste.get(p).buttonA();
				}
				else if(message.equals("b"))
				{
					playerliste.get(p).buttonB();
				}
			}
		}
	}
	
	public void addListElement(ArrayList<String> list,String value)
	{
		list.add(value);
	}
	
	public ArrayList<String> addListElementAndReturn(ArrayList<String> list,String value)
	{
		list.add(value);
		return list;
	}
}
