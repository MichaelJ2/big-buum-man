package at.big_buum_man.server.gui;

import java.util.ArrayList;

public class Powerupdummie 
{
	ArrayList<Powerup> powerups = new ArrayList<Powerup>();
	
	public Powerupdummie() 
	{
		
		// TODO Auto-generated constructor stub
		Powerup pu1=new Powerup();
		pu1.setName("Double");
		powerups.add(pu1);
		
		Powerup pu2=new Powerup();
		pu2.setName("Speed");
		powerups.add(pu2);
		
		Powerup pu3=new Powerup();
		pu3.setName("Ghost");
		powerups.add(pu3);
	}
	
	public ArrayList<Powerup> getList()
	{
		return this.powerups;
	}
	
}
