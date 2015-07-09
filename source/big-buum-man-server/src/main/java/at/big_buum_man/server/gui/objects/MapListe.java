package at.big_buum_man.server.gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/***
 * @version 1.0
 * @author Michael Januschek
 *
 */
public class MapListe 
{
	ArrayList<String[]> v=new ArrayList<String[]>();
	
	public static void main(String[] args)
	{
		
	}
	
	public MapListe()
	{
       FileReader myFile= null;
       BufferedReader buff= null;
       ArrayList<String[]> values=new ArrayList<String[]>();
       
       try 
       {
           myFile =new FileReader("maps/Beginning.txt");
           buff =new BufferedReader(myFile);
           int o=0;
           while (true) 
           {              
        	   String [] valuesSplited = null;
               String line = buff.readLine();
               if (line == null)
                   break;                 
               valuesSplited = line.split(","); // Spliten nach dem Sonderzeichen ","
               values.add(valuesSplited);
               o++;
           }
           
           v= values;
          
       } 
       catch (IOException e) 
       {
           System.err.println("Error2 :"+e);
       }
       finally 
       {
           try
           {
               buff.close();
               myFile.close();
           }
           catch (IOException e) 
           {
        	   System.err.println("Error2 :"+e);
           }
       }
	}

	public ArrayList<String[]> getMap()
	{
		return this.v;
	}
}
