package com.parseco;

import oneapi.client.impl.SMSClient;
import oneapi.config.Configuration;
import oneapi.model.common.Roaming;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LookupActivity extends Activity {
	
	private Button lookupButton;
	private EditText editLookupNumber;
	private TextView textViewHlrResponse;
	private String lookupNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookup);
        
        lookupButton = (Button) findViewById(R.id.btnLookup);
        editLookupNumber = (EditText) findViewById(R.id.editLookupNumber);
        textViewHlrResponse = (TextView) findViewById(R.id.textViewHlrResponse);
        
        lookupButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				SharedPreferences prefs = getSharedPreferences("Login", 0);
				String username = prefs.getString("username", "");
				String password = prefs.getString("password", "");
				
				if (username.equals("") && password.equals("")) {
					Toast.makeText(LookupActivity.this, "Please edit your username and password in application settings.", Toast.LENGTH_LONG).show();
					return;
				}
				
				lookupNumber = editLookupNumber.getText().toString();
				
				if (lookupNumber.equals("")) {
					Toast.makeText(LookupActivity.this, "Please edit lookup number.", Toast.LENGTH_LONG).show();
					return;
				}
				
				Configuration configuration = new Configuration(username, password); 
		        configuration.setApiUrl("http://api.parseco.com");
		        
		        SMSClient smsClient = new SMSClient(configuration);
		        Roaming roaming = smsClient.getHLRClient().queryHLR(lookupNumber);
		        Log.i("deliverystatus", roaming.toString()); 
		        
		        String roamingResult="unavailable";
		        if (roaming.getCurrentRoaming() != null) {
		        	roamingResult = roaming.getCurrentRoaming();
		        }
		        
		        textViewHlrResponse.setText(
		        		"Original network prefix: " + roaming.getExtendedData().getOriginalNetworkPrefix()+"\n"+
		        		"Ported network prefix: " + roaming.getExtendedData().getPortedNetworkPrefix() + "\n" +
		        		"Original network name: " + roaming.getExtendedData().getOriginalNetworkName()+ "\n" +
		        		"Ported network name: " + roaming.getExtendedData().getPortedNetworkName() + "\n" +
		        		"Original country name: " + roaming.getExtendedData().getOriginalCountryName() + "\n" +
		        		"Current roaming: " + roamingResult
		        		);
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
        	Intent intent = new Intent(LookupActivity.this, SettingsActivity.class);
	        startActivity(intent); 
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    } 
}
