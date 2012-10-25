package com.parseco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button sendSMS;
	private Button userLookup;
	Intent intent;
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
	        sendSMS = (Button) findViewById(R.id.btnSendSmsMain);
	        userLookup = (Button) findViewById(R.id.btnUserLookupMain);

	        sendSMS.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					intent = new Intent(MainActivity.this, SendSMSActivity.class);
			        startActivity(intent);
				}
			});
	        
	        userLookup.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					 intent = new Intent(MainActivity.this, LookupActivity.class);
			         startActivity(intent);
				}
			});
	    }
	  
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.activity_main, menu);
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId())
	        {
	        case R.id.menu_settings:
	        	Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
		        startActivity(intent); 
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
}
