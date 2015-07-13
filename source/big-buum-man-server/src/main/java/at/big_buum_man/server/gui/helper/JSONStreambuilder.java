package at.big_buum_man.server.gui.helper;

import org.json.simple.JSONObject;

public class JSONStreambuilder 
{
	private String stream;
	private JSONObject obj;

	
	public JSONStreambuilder() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public void createJSONObject()
	{
		obj=new JSONObject();
	}
	
	public JSONObject getJSONObject()
	{
		System.out.println(this.obj);
		return this.obj;
	}
	
	public String getStream()
	{
		return this.stream;
	}
	
	public void setStream(String stream)
	{
		this.stream=stream;
	}
	
	public void setInt(int value,String name)
	{
		obj.put(name,new Integer(value));
		// TODO Auto-generated constructor stub
	}
	
	public void setString(String value,String name)
	{
		obj.put(name,value);
		// TODO Auto-generated constructor stub
	}
	
	public void setDouble(double value,String name)
	{
		obj.put(name,new Double(value));
		// TODO Auto-generated constructor stub
	}
	
	public void setBoolean(boolean value,String name)
	{
		obj.put(name,new Boolean(value));
		// TODO Auto-generated constructor stub
	}
	
	public void setNull(String name)
	{
		obj.put(name,null);
		// TODO Auto-generated constructor stub
	}
}
