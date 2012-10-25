package com.parseco;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity {

	private EditText username;
	private EditText password;
	private Button saveButton;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        saveButton = (Button) findViewById(R.id.btnSave);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        
        final SharedPreferences prefs = getSharedPreferences("Login", 0);
        username.setText(prefs.getString("username", ""));
        password.setText(prefs.getString("password", ""));
        
        saveButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {				
				Editor editor = prefs.edit();
				editor.putString("username", username.getText().toString());
				editor.putString("password", password.getText().toString());
				editor.commit();
				finish();
			}
		}); 
	}
}
