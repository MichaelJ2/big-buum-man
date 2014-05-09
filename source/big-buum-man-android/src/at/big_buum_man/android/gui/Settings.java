package at.big_buum_man.android.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import at.big_buum_man.android.R;


public class Settings extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	  public boolean onOptionsItemSelected(MenuItem item)
	    {
		  switch (item.getItemId())
	        {
	        case R.id.action_settings:
	        	startActivity(new Intent("com.example.big_bumm_bang_client.Settings"));
	            return true;
	        case R.id.action_new:
	        	startActivity(new Intent("com.example.big_bumm_bang_client.Verbinden"));
	            return true;
	        case R.id.action_delete:
	        	startActivity(new Intent("com.example.big_bumm_bang_client.Ueber"));
	            return true;
	        case R.id.action_main:
	        	startActivity(new Intent("com.example.big_bumm_bang_client.MainActivity2"));
	            return true;
	        }
	        return false;
	    }
}
