package com.android.gestiondesbiens;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;



import com.android.gestiondesbiens.model.Users;
import com.android.gestiondesbiens.parsers.UsersXMLParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	EditText txtUserName, txtPassword;

	
	List<MyTask> loginTask;
	List<Users> loginList;
	SharedPreferences prefReader;
	SharedPreferences.Editor prefEditor;
	
	public void btnLogin_Click(View v){
		if(txtUserName.getText().toString().trim().equals("") ||  txtPassword.getText().toString().equals("")){
			if(txtUserName.getText().toString().trim().equals("")) txtUserName.setError("User name is required");
			if(txtPassword.getText().toString().equals("")) txtPassword.setError("Password is required");
			return;
		}

		MyTask task = new MyTask();
		task.execute("http://" + ClsCommon.SERVER_IP.split(":")[0] + ":8080/GestionDesBiens/webresources/model.users");
		 
	}
	
	private class MyTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
//			updateDisplay("Starting task");
			
			if (loginTask.size() == 0) {
				//pb.setVisibility(View.VISIBLE);
			}
			loginTask.add(this);
		}
		
		@Override
		protected String doInBackground(String... params) {
			Log.w("params:", params[0]);
			String content = HttpManager.getData(params[0]);
			//HttpManager
			return content;
		}
		
		@Override
		protected void onPostExecute(String result) {
			Log.w("result", result);
			loginTask.remove(this);
			if (loginTask.size() == 0) {
				//pb.setVisibility(View.INVISIBLE);
			}
			
			if (result == null) {
				Toast.makeText(getApplicationContext(), "Web service not available", Toast.LENGTH_SHORT).show();
				return;
			}
			
		
			loginList = UsersXMLParser.parseFeed(result);
			
			for(int i = 0; i < loginList.size(); i++){
				if(loginList.get(i).getUsername().toUpperCase().equals(txtUserName.getText().toString().trim().toUpperCase())){
					//check for password
					try{
	
						String hash = sha256Digest(txtPassword.getText().toString());
						if(loginList.get(i).getPassword().equals(hash)){
//							Toast.makeText(getApplicationContext(), "Successful Login", Toast.LENGTH_LONG).show();
							ClsUser.CONNECTED_USER_NAME = txtUserName.getText().toString();
						if (loginList.get(i).getGroupname().toString().equals("admin")){
 					Intent I = new Intent(getApplicationContext(), AdminActivity.class);
//							Intent I = new Intent(getApplicationContext(), TestActivity.class);
							startActivity(I);
							return;
						} else {
							Intent I = new Intent(getApplicationContext(), UserTransactionsActivity.class);
							startActivity(I);
							return;
						}
							
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
					
				}
			}
			Toast.makeText(getApplicationContext(), "Invalid user name or bad password", Toast.LENGTH_LONG).show();
			}

			
		@Override
		protected void onProgressUpdate(String... values) {
//			updateDisplay(values[0]);
		}
		
	}

	
	public String sha256Digest (String data) throws SignatureException {
		 return getDigest("SHA-256", data, true);
		}

		private String getDigest(String algorithm, String data, boolean toLower) 
		 throws SignatureException {
		  try {
		 MessageDigest mac = MessageDigest.getInstance(algorithm);
		 mac.update(data.getBytes("UTF-8"));
		 return toLower ? 
		  new String(toHex(mac.digest())).toLowerCase() : new String(toHex(mac.digest()));
		  } catch (Exception e) {
		 throw new SignatureException(e);
		  }
		}

		private String toHex(byte[] bytes) {
		 BigInteger bi = new BigInteger(1, bytes);
		 return String.format("%0" + (bytes.length << 1) + "X", bi);
	}
	
		

	void LoadServerSettings(){
		ClsCommon.SERVER_IP = this.prefReader.getString("SERVER_IP_ADDRESS", "");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.prefReader = getSharedPreferences("GestionDesBiens", MODE_PRIVATE);
		this.prefEditor = this.prefReader.edit();
		this.LoadServerSettings();
		this.txtUserName = (EditText)findViewById(R.id.txtUserName);
		this.txtPassword = (EditText)findViewById(R.id.txtPassword);
		this.loginTask=new ArrayList<>();
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
    void SetServerSettings(){
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);

    	alert.setTitle("Server configuration");
    	alert.setMessage("Specify the server IP address and port.\nFor example: 192.168.1.1:80");

    	// Set an EditText view to get user input 
    	final EditText txtServerIP = new EditText(this);
    	alert.setView(txtServerIP);
    	txtServerIP.setText(ClsCommon.SERVER_IP);
    	
    	
    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    	  String strCurrencyValue = txtServerIP.getText().toString();
    	  if(strCurrencyValue.trim().equals("")){
    		  Toast.makeText(getApplicationContext(), "The server IP address is required", Toast.LENGTH_LONG).show();
    		  return;
    	  }
    	  prefEditor.clear();
    	  prefEditor.putString("SERVER_IP_ADDRESS", strCurrencyValue);
    	  if(prefEditor.commit())
    		  LoadServerSettings();
    		  Toast.makeText(getApplicationContext(), "Server IP address is saved.", Toast.LENGTH_LONG).show();
    	}
    	});
    	alert.show();
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			SetServerSettings();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
