package com.android.gestiondesbiens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.gestiondesbiens.model.Personnel;
import com.android.gestiondesbiens.parsers.PersonnelXMLParser;

public class PersonnelActivity extends Activity {
	
	List<Personnel> personnelList;
	List<MyTask> tasks;
	EditText txtPersonnelName;

	int intPersonnelID = 0;

	
	
	public void btnNewPersonnel_Click(View v){
		Intent I = new Intent(this, EditPersonnelDetails.class);
		I.putExtra("personnel_id", "0");
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
		setContentView(R.layout.activity_personnel);
	
		this.txtPersonnelName = (EditText)findViewById(R.id.txtPersonnelName);

		this.lstHeader = (ListView)findViewById(R.id.lstPersonnelHeader);
		this.lstReservedWorkDetails = (ListView)findViewById(R.id.lstPersonnelDetails);
		tasks = new ArrayList<>();
		LoadGridHeader();
		
		this.requestData();

		this.lstReservedWorkDetails
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent I = new Intent(getApplicationContext(), EditPersonnelDetails.class);
						I.putExtra("personnel_id", arrDetails.get(position).get("PersonnelID"));

						I.putExtra("personnel_name", arrDetails.get(position).get("PersonnelName"));

						startActivity(I);
					}
				});
		
		new Thread() {
			public void run() {
				
				//LoadGridDetails();
				while (true) {
					if (blnReloadGrid) {
						requestData();
						blnReloadGrid = false;
					}
				}

			}
		}.start();
	}

	public static boolean blnReloadGrid = false;
	
	void requestData(){
		this.requestData("http://" + ClsCommon.SERVER_IP.split(":")[0] + ":8080/GestionDesBiens/webresources/model.personnel");
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
           
            this.mapReservedWorkHeader.put("PersonnelID", "Personnel ID");
            this.mapReservedWorkHeader.put("PersonnelName", "Personnel name");
            
            this.arrHeader.add(this.mapReservedWorkHeader);
            this.adHeader = new SimpleAdapter(this, arrHeader, R.layout.grid_template, new String[] {"PersonnelID", "PersonnelName"}, new int[] {R.id.labLocationID, R.id.labCenterName});
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
            
            for(int i = 0; i < this.personnelList.size(); i++){
             this.mapReservedWorkDetails = new HashMap<String, String>();
             
				this.mapReservedWorkDetails.put("PersonnelID", Integer.toString(this.personnelList.get(i).getPersonnelId()));
                 this.mapReservedWorkDetails.put("PersonnelName", this.personnelList.get(i).getPersonnelName());
          

             this.arrDetails.add(this.mapReservedWorkDetails);
            }
            this.adDetails = new SimpleAdapter(this, arrDetails, R.layout.grid_template, new String[] {"PersonnelID", "PersonnelName"}, new int[] {R.id.labLocationID, R.id.labCenterName});
            this.lstReservedWorkDetails.setAdapter(this.adDetails);
    }
    catch(Exception e){
        e.printStackTrace();
    }
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personnel, menu);
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
			Intent I = new Intent(getApplicationContext(), UserTransactionsActivity.class);
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
