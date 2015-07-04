package at.big_buum_man.android.gui;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import at.big_buum_man.android.AndroidMethods;
import at.big_buum_man.android.R;
import at.big_buum_man.android.network.NetworkClient;

public class MainActivity extends Activity implements AndroidMethods, OnNavigationListener
{
	private static String ip="";
	private ArrayAdapter<String> aAdpt;
	private NetworkClient nc = new NetworkClient(this);
	private ArrayList<InetAddress> ips= new ArrayList<InetAddress>();
	
	ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try 
		{
			nc.startListenAnnounce();
			Log.d("Message:","Listen");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		actionBar = getActionBar();
		
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		ArrayList<String> ipadressen= new ArrayList<String>();
		ipadressen.add("Serverliste");
		aAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, ipadressen);
		actionBar.setListNavigationCallbacks(aAdpt, this);

		try 
		 {
			if(getIntent().getExtras()!=null)
			{
				Bundle extras =getIntent().getExtras();
				ip=extras.get("ip").toString();
				setIP();
			}
		 }
		 catch(android.content.ActivityNotFoundException ex)
		 {
			 Log.d("Exception:",ex.getMessage()+"");
		 }
	
		Button b=(Button)findViewById(R.id.button1);
		
        b.setOnClickListener(
				        		new View.OnClickListener() 
						        {
									@Override
									public void onClick(View v) 
									{
										Log.d("Button:", "UP");
										String dp = "up";
										try {
											nc.stopListenAnnounce();
										} catch (UnknownHostException e) {
											e.printStackTrace();
										} catch (Exception e) {
											e.printStackTrace();
										}
										nc.sendMessage(dp);
									}
								}
				        	);
        
        Button b2=(Button)findViewById(R.id.button2);
		
        b2.setOnClickListener(
				        		new View.OnClickListener() 
						        {
									@Override
									public void onClick(View v) 
									{
										Log.d("Button:", "RIGHT");
										String dp = "right";
										try {
											nc.stopListenAnnounce();
										} catch (UnknownHostException e) {
											e.printStackTrace();
										} catch (Exception e) {
											e.printStackTrace();
										}
										nc.sendMessage(dp);
									}
								}
				        	);
        
        Button b3=(Button)findViewById(R.id.button3);
		
        b3.setOnClickListener(
				        		new View.OnClickListener() 
						        {
									@Override
									public void onClick(View v) 
									{
										Log.d("Button:", "LEFT");
										String dp = "left";
										try {
											nc.stopListenAnnounce();
										} catch (UnknownHostException e) {
											e.printStackTrace();
										} catch (Exception e) {
											e.printStackTrace();
										}
										nc.sendMessage(dp);
									}
								}
				        	);
        
        Button b4=(Button)findViewById(R.id.button4);
		
        b4.setOnClickListener(
				        		new View.OnClickListener() 
						        {
									@Override
									public void onClick(View v) 
									{
										Log.d("Button:", "Down");
										String dp = "down";
										try {
											nc.stopListenAnnounce();
										} catch (UnknownHostException e) {
											e.printStackTrace();
										} catch (Exception e) {
											e.printStackTrace();
										}
										nc.sendMessage(dp);
									}
								}
				        	);
        
        Button b5=(Button)findViewById(R.id.button5);
		
        b5.setOnClickListener(
				        		new View.OnClickListener() 
						        {
									@Override
									public void onClick(View v) 
									{
										Log.d("Button:", "A");
										String dp = "a";
										try {
											nc.stopListenAnnounce();
										} catch (UnknownHostException e) {
											e.printStackTrace();
										} catch (Exception e) {
											e.printStackTrace();
										}
										nc.sendMessage(dp);
									}
								}
				        	);
        
        Button b6=(Button)findViewById(R.id.button6);
		
        b6.setOnClickListener(
				        		new View.OnClickListener() 
						        {
									@Override
									public void onClick(View v) 
									{
										Log.d("Button:", "B");
										String dp = "b";
										try {
											nc.stopListenAnnounce();
										} catch (UnknownHostException e) {
											e.printStackTrace();
										} catch (Exception e) {
											e.printStackTrace();
										}
										nc.sendMessage(dp);
									}
								}
				        	);
	}

	private void setIP(){
		TextView ipt=(TextView)findViewById(R.id.textView1);
		ipt.setText("IP: "+this.ip);
		Log.d("text-ip:",""+this.ip);
		Log.d("Info:","socket set : "+ip+":");

		Thread connect = new Thread(new Runnable() {
			  public void run() {
				  try 
					{
					  Log.d("INFO", "start connect");
						nc.connectToServer(ip);
						Log.d("INFO", "end connect");
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					} 
			  }
			}
		);
		connect.start();
		// TODO visualisierung verbessern
		Toast.makeText(getApplicationContext(), "Connecting ...", Toast.LENGTH_SHORT).show();
		while(connect.isAlive());
		Toast.makeText(getApplicationContext(), "Connection established!", Toast.LENGTH_SHORT).show();
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
        	setContentView(R.layout.main);
        	Log.d("change:", "a");
            return true;
        }
        return false;
    }

	@Override
	public void processMessage(InetAddress client, String message) {
		Log.d("Message<"+client.getHostAddress()+">:","["+message+"]");
	}

	@Override
	public void processFoundServer(final InetAddress address) {
		if (ips.contains(address)) {
			return;
		}
		
		ips.add(address);
		Log.d("Message","add "+address.getHostAddress());
		this.runOnUiThread(new Runnable() {
			  public void run() {
				  aAdpt.add(address.getHostAddress()); 
			  }
			});      
	}

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		Log.d("Selected","item");
		String address = aAdpt.getItem(arg0);
		// TODO hübscher machen
		if ("Serverliste".equals(address)) {
			return false;
		}
		ip = address;
		setIP(); 
		return false;
	}
}
