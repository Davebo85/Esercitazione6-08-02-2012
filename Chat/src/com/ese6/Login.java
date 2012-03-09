package com.ese6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity{
	
	EditText user;
	EditText pass;
	EditText utente;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
        utente = (EditText)findViewById(R.id.utente);
        
        
        Button next = (Button)findViewById(R.id.button1);
        next.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		Intent intent = new Intent(Login.this, ChatActivity.class);
        		String userstr = user.getText().toString();
        		String passstr = pass.getText().toString();
        		String utentestr = utente.getText().toString();
        		intent.putExtra("user",userstr);
        		intent.putExtra("pass",passstr);
        		intent.putExtra("utente",utentestr);
        		startActivity(intent);
        	}
        });
	}

}
