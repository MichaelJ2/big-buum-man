package at.big_buum_man.server.gui;

import java.util.ArrayList;

public class Powerdowndummie 
{
	ArrayList<Powerdown> powerdowns= new ArrayList<Powerdown>();
	
	public Powerdowndummie() 
	{		
		// TODO Auto-generated constructor stub
		Powerdown pd1=new Powerdown();
		pd1.setName("Slomo");
		powerdowns.add(pd1);
		
		Powerdown pd2=new Powerdown();
		pd2.setName("Frost");
		powerdowns.add(pd2);
		
		Powerdown pd3=new Powerdown();
		pd3.setName("Silence");
		powerdowns.add(pd3);
	}
	
	public ArrayList<Powerdown> getList()
	{
		return this.powerdowns;	
	}
}
