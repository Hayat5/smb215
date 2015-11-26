package com.android.gestiondesbiens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.gestiondesbiens.model.Personnel;
import com.android.gestiondesbiens.model.Transport;
import com.android.gestiondesbiens.parsers.PersonnelXMLParser;
import com.android.gestiondesbiens.parsers.TransportXMLParser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class TransportActivity extends Activity {
	
	List<Transport> transportList;
	List<Personnel> personnelList;
	List<MyTask> tasks;
	EditText txtPersonnelName;
	TextView tv;
	Button bsave, bnew, bdelete;
	Spinner sp;
    String personnelName = null;
    private String array_spinner[];

    
	
	public void btnNewTransport_Click (View v){
		Intent I = new Intent(this, EditTransportDetails.class);
		I.putExtra("transport_id", "0");
		startActivity(I);
	}
	
	private class MyTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
//			updateDisplay("Starting task");
			
			if (tasks.size() == 0) {
				//pb.setVisibility(View.VISIBLE);
			}
			tasks.add(this);
		}
		
		@Override
		protected String doInBackground(String... params) {
			Log.w("params:", params[0]);
			String content = HttpManager.getData(params[0]);
			HttpManager hm = new HttpManager();
			String data = hm.getData(params[0]);
			//HttpManager
			System.out.println("RESULT === "+data);
			return data;
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			tasks.remove(this);
			if (tasks.size() == 0) {
				//pb.setVisibility(View.INVISIBLE);
			}
			
			if (result == null) {
				Toast.makeText(getApplicationContext(), "Web service not available", Toast.LENGTH_LONG).show();
				return;
			}
			
				personnelList = PersonnelXMLParser.parseFeed(result);	
				transportList = TransportXMLParser.parseFeed(result);
	
			LoadGridDetails();
			}

			
		@Override
		protected void onProgressUpdate(String... values) {
//			updateDisplay(values[0]);
		}
		
	}
	
	ArrayList<HashMap<String, String>> arrHeader, arrDetails;
	HashMap<String, String> mapReservedWorkHeader, mapReservedWorkDetails;
	ListAdapter adHeader, adDetails;
	
	ListView lstHeader, lstReservedWorkDetails;

	public static boolean blnReloadGrid = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transport);
		

		
		this.lstHeader = (ListView)findViewById(R.id.lstTransportHeader);
		this.lstReservedWorkDetails = (ListView)findViewById(R.id.lstTransportDetails);
		tasks = new ArrayList<>();
		this.requestTransportData();
		
		this.LoadGridHeader();
		
		this.lstReservedWorkDetails.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent I = new Intent(getApplicationContext(), EditTransportDetails.class);
				I.putExtra("transport_id", arrDetails.get(position).get("LocationID"));
				startActivity(I);
			}
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
		    	while(true){
		    		if(blnReloadGrid){
		    			requestTransportData();
				    	blnReloadGrid = false;
		    		}
		    	}
			}
		}).start();
	}
	
	void requestTransportData(){
		this.requestData("http://" + ClsCommon.SERVER_IP.split(":")[0] + ":8080/GestionDesBiens/webresources/model.transport");
	}

	private void requestData(String uri) {
		MyTask task = new MyTask();
		task.execute(uri);
	}

	
    void LoadGridHeader(){
        try{
        	this.adHeader = null;
        	this.lstHeader.setAdapter(this.adHeader);
            this.arrHeader = new ArrayList<HashMap<String, String>>();
            this.mapReservedWorkHeader = new HashMap<String, String>();
         
                this.mapReservedWorkHeader.put("LocationID", "Transport Id");
                this.mapReservedWorkHeader.put("CenterName", "Personnel name");
                this.mapReservedWorkHeader.put("SalleName", "Transport date");
               
          
            this.arrHeader.add(this.mapReservedWorkHeader);
            this.adHeader = new SimpleAdapter(this, arrHeader, R.layout.grid_template, new String[] {"LocationID", "CenterName", "SalleName", "PersonnelName"}, new int[] {R.id.labLocationID, R.id.labCenterName, R.id.labSalleName, R.id.labPersonnelName});
            this.lstHeader.setAdapter(this.adHeader);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void LoadGridDetails(){
        try{
        	this.adDetails = null;
        	this.lstReservedWorkDetails.setAdapter(this.adDetails);
        	
            this.arrDetails = new ArrayList<HashMap<String, String>>();
            
            for(int i = 0; i < this.transportList.size(); i++){
             this.mapReservedWorkDetails = new HashMap<String, String>();
             
                 this.mapReservedWorkDetails.put("LocationID", Integer.toString(this.transportList.get(i).getTransportId()));
                 this.mapReservedWorkDetails.put("CenterName", this.transportList.get(i).getPersonnelName());
                 this.mapReservedWorkDetails.put("SalleName", this.transportList.get(i).getTransportDate());
                 this.mapReservedWorkDetails.put("PersonnelName", "");
 
      
             this.arrDetails.add(this.mapReservedWorkDetails);
            }
            this.adDetails = new SimpleAdapter(this, arrDetails, R.layout.grid_template, new String[] {"LocationID", "CenterName", "SalleName", "PersonnelName"}, new int[] {R.id.labLocationID, R.id.labCenterName, R.id.labSalleName, R.id.labPersonnelName});
            this.lstReservedWorkDetails.setAdapter(this.adDetails);
    }
    catch(Exception e){
        e.printStackTrace();
    }
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transport, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_get_centers) {
			Intent I = new Intent(getApplicationContext(), CenterActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_type) {
			Intent I = new Intent(getApplicationContext(), TypeActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_personnel) {
			Intent I = new Intent(getApplicationContext(), PersonnelActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_locations) {
			Intent I = new Intent(getApplicationContext(), LocationsActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_users) {
			Intent I = new Intent(getApplicationContext(), UsersActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_groups) {
			Intent I = new Intent(getApplicationContext(), GroupsActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_items) {
			Intent I = new Intent(getApplicationContext(), ItemsActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_salles) {
			Intent I = new Intent(getApplicationContext(), SallesActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_transations) {
			Intent I = new Intent(getApplicationContext(), TransactionsActivity.class);
			startActivity(I);		
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
