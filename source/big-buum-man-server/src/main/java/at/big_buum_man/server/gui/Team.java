package at.big_buum_man.server.gui;

import java.util.ArrayList;

/***
 * 
 * @author Michael
 * @version 1.0
 */
public class Team 
{
	private String name;
	private String farbe;
	private ArrayList<Player> player;
	
	public Team() 
	{
		player = new ArrayList<Player>();
	}
	
	public String getFarbe() 
	{
		return farbe;
	}
	
	public void setFarbe(String farbe) 
	{
		this.farbe = farbe;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public ArrayList<Player> getPlayer() 
	{
		return player;
	}
	
	public void addPlayer(Player player) {
		this.player.add(player);
	}
}
