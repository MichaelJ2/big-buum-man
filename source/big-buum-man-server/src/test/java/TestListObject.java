import java.util.ArrayList;

import at.big_buum_man.server.gui.helper.GameHandler;

public class TestListObject 
{
	public static void main(String[] args) 
	{
		createListAndOutput();
		createListReturnAndOutput();
	}

	private static void createListReturnAndOutput() 
	{
		ArrayList<String> liste =new ArrayList<String>();
		liste.add("a");
		liste.add("b");
		GameHandler gh=new GameHandler();
		liste=gh.addListElementAndReturn(liste, "c");
		
		for(String s:liste)
		{
			System.out.print(s);
		}
	}

	private static void createListAndOutput() 
	{
		ArrayList<String> liste =new ArrayList<String>();
		liste.add("a");
		liste.add("b");
		GameHandler gh=new GameHandler();
		gh.addListElement(liste, "c");
		
		for(String s:liste)
		{
			System.out.print(s);
		}
	}
}
