package com.iwander.iwander;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class BackGroundAction extends Service implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,
LocationListener
{
    Location storedLocation;
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    SharedPreferences sharedPreferences;

    final Context context = this;

    double longitude;
    double latitude;
    int storedRadius = 0;

    String username;

    int locationInterval = 10000;
    boolean hasUserCrossed = false;


    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this, "Service created", Toast.LENGTH_LONG).show();



        googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).
                addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();

        sharedPreferences = getSharedPreferences("iwander", Context.MODE_PRIVATE);


       // Timer timer = new Timer ();
        //imerTask hourlyTask = new TimerTask () {
           // @Override
           // public void run () {
                // your code here...

        int resofAlgo1=0,resofAlgo2=0;

        double age= Double.parseDouble(sharedPreferences.getString("age", "0"));
        //double age=66;
        Date now = new Date();
        Calendar ci = Calendar.getInstance();
        int temp=ci.get(Calendar.HOUR);

        //your input
        double timeofday=temp;            // your input (select only current hour as system input)
        if(timeofday>=22 )//scale time
            timeofday=80;
        if(timeofday<=6 )
            timeofday=90;
        // System.out.println("start");
        keith k = new keith(age,timeofday);
        double avg= k.average();


        double temperature,levelofdementia;
        temperature=32;                 //your input in degree celsius
        temperature=temperature*2;

         levelofdementia = Double.parseDouble(sharedPreferences.getString("dementiaLevel", "1"));

       // levelofdementia=4;            //your input on scale of 1-4
        levelofdementia = levelofdementia*10*2;
        k = new keith(temperature,levelofdementia);
        double avg1= k.average();
        temperature=temperature/2;//scale back for next algo
        levelofdementia = levelofdementia/(10*2);


        if(((avg1+avg)/2)>40)
        {
            Toast.makeText(context, " warning message of first algorithm", Toast.LENGTH_LONG).show();

            System.out.println("send warning message");//your Toast this message
            resofAlgo1=1;//
        }

        //naive bayes
        String age1="",inc1="",time1="",lod1="";
        if(age<=30)
            age1="<=30";
        if(age>30 && age<45)
            age1="31-45";
        if(age>=45 && age<=60)
            age1="45-60";
        if(age>60)
            age1=">=60";

        if(temperature<=20)
            inc1="10";
        if(temperature>10 && temperature<=20)
            inc1="20";
        if(temperature>20 && temperature<=25)
            inc1="25";
        if(temperature>25 && temperature<=30)
            inc1="30";
        if(temperature>30 && temperature<=90)
            inc1="40";

        if(timeofday>=22)
            time1="n";
        if(timeofday<=7)
            time1="n";
        if(timeofday >7 && timeofday<22)
            time1="d";

        if( levelofdementia ==20)

            lod1="1";
        if( levelofdementia ==40)

            lod1="2";
        if( levelofdementia ==60)

            lod1="3";
        if( levelofdementia ==80)

            lod1="4";

        Naive27 n = new Naive27(age1,inc1,time1,lod1);
        String[] args1={};
        try{
            n.main(args1);
        }

        catch(IOException e){}
        String result2 = n.display();
        System.out.println(result2);
        if(result2.equals("yes"))
        {
            Toast.makeText(context,"warning message from second algorithm", Toast.LENGTH_LONG).show();
            //String url="https://www.google.co.in/maps/place/St.+Francis+D'Assisi+High+School/@19.243079,72.8531095,18z/data=!4m2!3m1!1s0x0000000000000000:0xbcb37be822b58dae";
            String url ="http://map.google.com?q=19.243461,72.855475";
            String phoneNumber = "+918108745130";
            String message = "Patient has crossed safe zone  last location is "+url;

            //SmsManager smsManager = SmsManager.getDefault();
            //Log.d("debug", "reached SMS 1");
            //smsManager.sendTextMessage(phoneNumber, null, message, null, null);

            ///System.out.println("send 2nd warning message");//your Toast this message

            // Log.d("debug", "reached SMS 2");
            resofAlgo2=1;//
            kmeans kme = new kmeans();



        }
        if(resofAlgo1==1 && resofAlgo2==1) {


            Toast.makeText(context, "WARNED FROM THIRD ALGORITHM", Toast.LENGTH_LONG).show();

            Toast.makeText(context, "YOU ARE WARNED FROM  ALL  ALGORITHMS", Toast.LENGTH_LONG).show();

            System.out.println("you r warned twicee!!");// your Toast this message


        }

         //   }
       // };
        //timer.schedule (hourlyTask, 0l, 1000*60*60);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        sharedPreferences = getSharedPreferences("iwander", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        double storedLongitude = Double.parseDouble(sharedPreferences.getString("longitude", "0"));
        double storedLatitude = Double.parseDouble(sharedPreferences.getString("latitude", "0"));
        storedRadius = Integer.parseInt(sharedPreferences.getString("radius", "0"));
        Toast.makeText(this, "service started!!", Toast.LENGTH_SHORT).show();
        storedLocation = new Location("Stored Location");
        storedLocation.setLongitude(storedLongitude);
        storedLocation.setLatitude(storedLatitude);
        Toast.makeText(this, "Location value set!!", Toast.LENGTH_SHORT).show();
        googleApiClient.connect();
        Toast.makeText(this, "connected to google api!!", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);







    }

    @Override
    public void onConnected(Bundle bundle) {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(locationInterval);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        hasUserCrossed = false;

        double distance = getDistance(location);

        //update location to server
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Toast.makeText(this,"Distance is "+ String.valueOf(distance), Toast.LENGTH_SHORT).show();
        if(distance>storedRadius)
        {
            hasUserCrossed = true;
            Toast.makeText(this,"you have wandered"+ String.valueOf(distance), Toast.LENGTH_LONG).show();

            //user has crossed radius boundary
            //send notification to caretaker




        }
        new UploadAsync().execute(new ApiConnector());



    }



    public class UploadAsync extends AsyncTask<ApiConnector, Void, String>
    {
        @Override
        protected String doInBackground(ApiConnector... apiConnectors) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss");
            String strDate = sdf.format(c.getTime());

            return apiConnectors[0].uploadData(String.valueOf(longitude), String.valueOf(latitude), username, strDate, hasUserCrossed);
        }

        @Override
        protected void onPostExecute(String s) {
            checkResult(s);
        }
    }

    private void checkResult(String s)
    {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public double getDistance(Location newLocation)
    {
        return storedLocation.distanceTo(newLocation);
    }






}
