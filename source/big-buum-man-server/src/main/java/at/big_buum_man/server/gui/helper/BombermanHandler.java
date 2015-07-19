package at.big_buum_man.server.gui.helper;

import java.util.ArrayList;

import at.big_buum_man.server.gui.objects.MapListe;
import at.big_buum_man.server.gui.objects.Wand;

public class BombermanHandler 
{
	//neue klasse die alles handln soll.
	//methoden von allen klassen sollen hier gesammelt werden
	
	
	//Game methods
	//Example Method
	private void InitGameMap(ArrayList<ArrayList<Wand>> vl,MapListe ml, ArrayList<Wand> hl)
	{
		int y=0;
		vl=new ArrayList<ArrayList<Wand>>();
		for(String[] s:ml.getMap())
    	{
    		hl=new ArrayList<Wand>();
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
	
}
