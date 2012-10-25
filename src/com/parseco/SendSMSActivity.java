package com.parseco;

import oneapi.client.impl.SMSClient;
import oneapi.config.Configuration;
import oneapi.model.SMSRequest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMSActivity extends Activity {
	
	private Button sendButton;
	private EditText editSenderAddress;
	private EditText editRecipientAddress;
	private EditText editMessage;
	
	private String senderAddress;
	private String recipientAddress;
	private String message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_sms);
        
        sendButton = (Button) findViewById(R.id.btnSendSms);
        editSenderAddress = (EditText) findViewById(R.id.editSenderAddress);
        editRecipientAddress = (EditText) findViewById(R.id.editRecipientAddress);
        editMessage = (EditText) findViewById(R.id.messageId);
        
        sendButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				senderAddress = editSenderAddress.getText().toString();
				recipientAddress = editRecipientAddress.getText().toString();
				message = editMessage.getText().toString();
				
				SharedPreferences prefs = getSharedPreferences("Login", 0);
				String username = prefs.getString("username", "");
				String password = prefs.getString("password", "");
				
				if (username.equals("") && password.equals("")) {
					Toast.makeText(SendSMSActivity.this, "Please edit your username and password in application settings.", Toast.LENGTH_LONG).show();
					return;
				}
				
				if (senderAddress.equals("") && recipientAddress.equals("")) {
					Toast.makeText(SendSMSActivity.this, "Please edit sender and recipient address.", Toast.LENGTH_LONG).show();
					return;
				}
					
				Configuration configuration = new Configuration(username, password); 
		        configuration.setApiUrl("http://api.parseco.com");
		        
		        SMSClient smsClient = new SMSClient(configuration);
		        SMSRequest smsRequest = new SMSRequest(senderAddress, message, recipientAddress);
				
//			    // Store request id because we can later query for the delivery status with it:
//		        SendMessageResult response =
			    smsClient.getSMSMessagingClient().sendSMS(smsRequest);
			        
//			    DeliveryInfoList deliveryInfoList = smsClient.getSMSMessagingClient().queryDeliveryStatus(senderAddress, response.getClientCorrelator());
//			    String deliveryStatus = deliveryInfoList.getDeliveryInfo().get(0).getDeliveryStatus();
//			    Log.i("deliverystatus", deliveryStatus);
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
        	Intent intent = new Intent(SendSMSActivity.this, SettingsActivity.class);
	        startActivity(intent); 
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
