package at.big_buum_man.server.gui.helper;

import java.util.ArrayList;

import org.newdawn.slick.TrueTypeFont;

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
		  		Wand w=new Wand(Integer.valueOf(o));
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
	
	public void RenderTime(long currenttime,long starttime,TrueTypeFont trueTypeFont)
	{
		currenttime = System.currentTimeMillis();
		String time = timeconvert(starttime,currenttime);
		trueTypeFont.drawString(20, 40,"Spielezeit ("+time+")", Variables.black);	
	}
	
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
}
