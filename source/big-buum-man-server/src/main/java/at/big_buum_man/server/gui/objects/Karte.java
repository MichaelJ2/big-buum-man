package at.big_buum_man.server.gui.objects;

import org.newdawn.slick.Graphics;

public class Karte extends SpielObjekt
{
	private String name;
	private String[][] karte;
	private int anzahlSpieler;
	
	public Karte(String name, int anzahlSpieler,String[][] karte) 
	{
		setObjectName("Karte");
		setName(name);
		setAnzahlSpieler(anzahlSpieler);
		setKarte(karte);
	}
	
	public Karte() 
	{
		setObjectName("Karte");
		setName("Default Karte");
		setAnzahlSpieler(4);
		String[][] k= {	{"1","2","3","4","5"},
						{"11","12","13","14","15"},
						{"21","22","23","24","25"}};
		setKarte(k);
	}

	@Override
	public void draw(Graphics g) 
	{
		//image.drawCentered(x, y);
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setAnzahlSpieler(int anzahlSpieler)
	{
		this.anzahlSpieler=anzahlSpieler;
	}
	
	public int getAnzahlSpieler()
	{
		return this.anzahlSpieler;
	}
	
	public void setKarte(String[][] karte)
	{
		this.karte=karte;		
	}
	
	public String[][] getKarte()
	{
		return this.karte;
	}
}
