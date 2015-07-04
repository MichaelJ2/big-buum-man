package at.big_buum_man.android.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import at.big_buum_man.android.R;


public class Verbinden extends Activity 
{
	String ip ="";
	
	public void verbinden()
	{
	    EditText txtip =  (EditText) findViewById(R.id.editText1);       
	    ip = txtip.getText().toString();
	    Log.d("IP-Adresse: ",ip);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verbinden);
		
		Button b=(Button)findViewById(R.id.buttonverbinden);
		
        b.setOnClickListener(
				        		new View.OnClickListener() 
						        {
									@Override
									public void onClick(View v) 
									{
										verbinden();
										Intent meinIntent = new Intent(getApplicationContext(), MainActivity.class);
										meinIntent.putExtra("ip",ip);
										
										try
										{
											startActivity(meinIntent);
										}
										catch(android.content.ActivityNotFoundException ex)
										{
											Log.d("Exception:",ex.getMessage()+"");
										}
									}
								}
				        	);
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
