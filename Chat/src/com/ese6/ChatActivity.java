package com.ese6;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
public class ChatActivity extends Activity {
    /** Called when the activity is first created. */
	EditText et;
	ListView lv;
	Connection connection;
	String user;
	String pass;
	String utente;
	ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et = (EditText) findViewById(R.id.editText1);
    	lv = (ListView) findViewById(R.id.listView1);
    	user = getIntent().getExtras().getString("user");
    	pass = getIntent().getExtras().getString("pass");
    	utente = getIntent().getExtras().getString("utente");
    	adapter = new ArrayAdapter<String>(ChatActivity.this,R.layout.row,R.id.rowText);
    	lv.setAdapter(adapter);
    	lv.setSelection(adapter.getCount()-1);
        Button bt = (Button) findViewById(R.id.button1);
        try {
        	ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it", 5222);
        	config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        	connection = new XMPPConnection(config);
        	connection.connect();
        	connection.login(user, pass);
        } catch (XMPPException e) {
        	e.printStackTrace();
        }
        connection.addPacketListener(new PacketListener() {
			
			@Override
			public void processPacket(Packet pkt) {
				Message msg = (Message) pkt;
				String from = msg.getFrom();
				String body = msg.getBody();
				adapter.add(from+":"+body+"\n");
			}
		}, new MessageTypeFilter(Message.Type.normal));
        bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				adapter.add("ME: "+et.getText().toString()+"\n");
				msg.setTo(utente+"@ppl.eln.uniroma2.it");
				msg.setBody(et.getText().toString());
				connection.sendPacket(msg);
			}
		});
    }
}