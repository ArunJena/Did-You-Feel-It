package com.example.dyfi2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BackGround bg = new BackGround();
        bg.execute();
    }
    private class BackGround extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPostExecute(String jsonString) {

//            TextView textView = (TextView)findViewById(R.id.textView);
//            textView.setText(jsonString);
            Event event = null;

            //Extract feature from text and convert them into Event object;
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray featureArray = jsonObject.getJSONArray("features");
                JSONObject firstJsonObject = featureArray.getJSONObject(0);
                JSONObject properties = firstJsonObject.getJSONObject("properties");

                String title = properties.getString("title");
                String numPeople = properties.getString("felt");
                String felt = properties.getString("cdi");

                event = new Event(title,numPeople,felt);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            TextView titleTv = (TextView)findViewById(R.id.title);
            titleTv.setText(event.getTitle());
            TextView numPeopleTv = (TextView)findViewById(R.id.numPeople);
            numPeopleTv.setText(event.getNumPeople()+" People felt it");
            TextView feltTv = (TextView)findViewById(R.id.felt);
            feltTv.setText(event.getFelt());
        }

        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL(USGS_REQUEST_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            HttpURLConnection httpURLConnection=null;
            InputStream inputStream= null;
            BufferedReader bufferedReader = null;
            String jsonResponse="";
            try {
                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                line = bufferedReader.readLine();
                while(line!=null){
                    stringBuilder.append(line);
                    line=bufferedReader.readLine();
                }

                jsonResponse = stringBuilder.toString();
                int x = httpURLConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }
    }
}