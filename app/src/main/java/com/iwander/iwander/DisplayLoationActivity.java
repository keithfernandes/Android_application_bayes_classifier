package com.iwander.iwander;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class DisplayLoationActivity extends ActionBarActivity {

    ListView locationListView;
    Context context = this;
    String username;

    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_loation);

        locationListView = (ListView) findViewById(R.id.locationListView);

        username = getSharedPreferences("iwander", Context.MODE_PRIVATE).getString("username", " ");

        new FetchlocationAsync().execute(new ApiConnector());



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_loation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            getSharedPreferences("iwander", Context.MODE_PRIVATE).edit().clear().commit();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FetchlocationAsync extends AsyncTask<ApiConnector, Void, JSONArray>
    {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd= new ProgressDialog(context);
            pd.setMessage("Please Wait while we are fetching locaiton details");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
            pd.show();
        }

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].fetchLocationDetails(username);
        }

        @Override
        protected void onPostExecute(JSONArray s) {
            pd.dismiss();
            checkResult(s);

        }

    }

    private void checkResult(final JSONArray result)
    {
        JSONObject jsonObject = new JSONObject();
        items = new ArrayList<>();

         boolean tempBool = false;

        for(int i=0;i<result.length();i++)
        {
            try {

                jsonObject = result.getJSONObject(i);

                String content = jsonObject.getString("longitude") + " " + jsonObject.getString("latitude") + " "
                        + jsonObject.getString("time")+ " \n  user crossed safe zone? "
                        + jsonObject.getString("hasUserCrossed");
                String cross =jsonObject.getString("hasUserCrossed");
                if(cross.equals("true"));
                {

                    Toast.makeText(context,"Patient has crossed safe zone",Toast.LENGTH_SHORT).show();



                }

                items.add(content);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
                locationListView.setAdapter(arrayAdapter);

                locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            Float longitude = Float.parseFloat(result.getJSONObject(i).getString("longitude"));
                            Float latitude = Float.parseFloat(result.getJSONObject(i).getString("latitude"));
                            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            startActivity(intent);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();

                        }
                    }
                });
            }

            catch(Exception e)
            {
               e.printStackTrace();

            }

        }


    }
}
