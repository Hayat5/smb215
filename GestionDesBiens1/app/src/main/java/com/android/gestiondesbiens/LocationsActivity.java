package com.android.gestiondesbiens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.gestiondesbiens.model.Locations;
import com.android.gestiondesbiens.parsers.LocationsXMLParser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class LocationsActivity extends Activity {
	
	List<Locations> locationsList;
	List<MyTask> tasks;
	
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
			
	
				locationsList = LocationsXMLParser.parseFeed(result);
	
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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locations);
		
		this.lstHeader = (ListView)findViewById(R.id.lstReservedWorkHeader);
		this.lstReservedWorkDetails = (ListView)findViewById(R.id.lstReservedWorkDetails);
		tasks = new ArrayList<>();
		this.requestData("http://192.168.1.67:8080/GestionDesBiens/webresources/model.location");
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
         
                this.mapReservedWorkHeader.put("LocationID", "Location ID");
                this.mapReservedWorkHeader.put("CenterName", "Center name");
                this.mapReservedWorkHeader.put("SalleName", "Salle name");
                this.mapReservedWorkHeader.put("PersonnelName", "Personnel name");
          
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
        	this.LoadGridHeader();
        	this.adDetails = null;
        	this.lstReservedWorkDetails.setAdapter(this.adDetails);
        	
            this.arrDetails = new ArrayList<HashMap<String, String>>();
            
            for(int i = 0; i < this.locationsList.size(); i++){
             this.mapReservedWorkDetails = new HashMap<String, String>();
             
                 this.mapReservedWorkDetails.put("LocationID", Integer.toString(this.locationsList.get(i).getLocationId()));
                 this.mapReservedWorkDetails.put("CenterName", this.locationsList.get(i).getCenterName());
                 this.mapReservedWorkDetails.put("SalleName", this.locationsList.get(i).getSalleName());
                 this.mapReservedWorkDetails.put("PersonnelName", this.locationsList.get(i).getPersonnelName());
             
      
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
		getMenuInflater().inflate(R.menu.locations, menu);
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
		}else if (id == R.id.action_get_users) {
			Intent I = new Intent(getApplicationContext(), UsersActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_salles) {
			Intent I = new Intent(getApplicationContext(), SallesActivity.class);
			startActivity(I);		
			return true;
		}else if (id == R.id.action_get_transport) {
			Intent I = new Intent(getApplicationContext(), TransportActivity.class);
			startActivity(I);		
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
