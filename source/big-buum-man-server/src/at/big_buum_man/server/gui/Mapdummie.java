package at.big_buum_man.server.gui;

import java.util.ArrayList;


public class Mapdummie 
{
	private ArrayList<Karte> karten = new ArrayList<Karte>();
	
	public Mapdummie() 
	{
		//Dummiekarte 1
		String[][] karte1={	{"1","2","3","4","5"},
							{"11","12","13","14","15"},
							{"21","22","23","24","25"}};
		
		karten.add(new Karte("Todesschlucht",4,karte1));
		
		
		//Dummiekarte 2
		String[][] karte2={	{"1","1","1","1","1"},
							{"1","x","x","x","1"},
							{"1","1","1","1","1"}};
		
		karten.add(new Karte("Fabrik",8,karte2));
		
		//Dummiekarte 3
		String[][] karte3={	{"1","1","1","1","1"},
							{"1","x","x","x","1"},
							{"1","1","1","1","1"}};
		
		karten.add(new Karte("Zimmer",8,karte3));
	}
	
	public ArrayList<Karte> getList()
	{
		return this.karten;
	}
}
