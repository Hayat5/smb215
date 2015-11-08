package com.restful.gestion.restful;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button getData = (Button) findViewById(R.id.getservicedata);
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String restUrl = "http://192.168.1.103:8080/GestionDesBiens/webresources/model.item/";
                new RestOperation().execute(restUrl);

            }
        });
    }

    private class RestOperation extends AsyncTask<String, Void, Void> {

        //final HttpClient httpClient = new DefaultHttpClient();
        String content;
        String error;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        String data;
        TextView serverDataReceived = (TextView) findViewById(R.id.serverDataReceived);
        TextView rowJsonData = (TextView) findViewById(R.id.rowJsonData);
        TextView showParsedJSON = (TextView) findViewById(R.id.showParsedJSON);
        EditText userinput = (EditText) findViewById(R.id.userinput);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please wait");
            progressDialog.show();
            try {
                data += "&" + URLEncoder.encode("data","UTF-8") + "=" + userinput.getText();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            BufferedReader br = null;
            URL url = null;
            try {
                url = new URL(params[0]);
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                OutputStreamWriter outputStreamWr = new OutputStreamWriter(connection.getOutputStream());
                outputStreamWr.write(data);
                outputStreamWr.flush();

                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = br.readLine())!= null){
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                }
                content = sb.toString();

            } catch (MalformedURLException e) {
                error = e.getMessage();
                e.printStackTrace();
            } catch (IOException e) {
                error = e.getMessage();
                e.printStackTrace();
            }finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

            if(error != null){
                serverDataReceived.setText("Error" + error);
            }
            else{
                serverDataReceived.setText(content);
                String output = "";
                JSONObject jsonResponse = null;
                JSONArray jsonArray = jsonResponse.optJSONArray("Android");
                for (int i = 0; i< jsonArray.length(); i++){
                    try {
                        JSONObject child = jsonArray.getJSONObject(i);
                        String name = child.getString("itemName");
                        String code = child.getString("itemCode");
                        output = "Name : " + name + "Code" + code +System.getProperty("line.separator");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                showParsedJSON.setText(output);
                try {
                    jsonResponse = new JSONObject(content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }


}
