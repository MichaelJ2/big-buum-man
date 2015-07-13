package at.big_buum_man.server.gui.helper;

import java.awt.Font;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

// Experimentale Klasse

public class Variables 
{
	public static final int WIDTH = Display.getDesktopDisplayMode().getWidth();
	public static final int HEIGHT = Display.getDesktopDisplayMode().getHeight();
	public static final int BLOCKWIDTH=50;														//Feldbreite
	public static final int BLOCKHEIGHT=50;
	
	public static final Color gray=Color.gray;
	public static final Color green=Color.green;
	public static final Color red=Color.red;
	public static final Color blue=Color.blue;
	public static final Color white=Color.white;
	public static final Color yellow=Color.yellow;
	public static final Color magenta=Color.magenta;
	public static final Color darkGray=Color.darkGray;
	public static final Color lightGray=Color.lightGray;
	public static final Color black=Color.black;
	
	public static final int font=Font.BOLD;
	public static final String letter="Arial";
	public static final int charsize=20;	
	
	
}
