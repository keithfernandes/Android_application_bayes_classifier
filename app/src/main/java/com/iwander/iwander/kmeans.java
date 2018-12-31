package com.iwander.iwander;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.Toast;

public class kmeans  extends Activity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    int dataSet[]= new int[10000];
    static int count=0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            dataSet[count]=(int)x;
            count++;
            if (count==99) {
                kmeans();
                count = 0;
            }
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
        }
       // long curTime = System.currentTimeMillis();

        //if ((curTime - lastUpdate) > 100) {
        //    long diffTime = (curTime - lastUpdate);
        //    lastUpdate = curTime;
        //}


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public  void  kmeans()
    {
        System.out.println("\t\t***Clustering using K-means Algorithm***");

       // BufferedReader br= new BufferedReader(new InputStreamReader(System.in));

        System.out.print("\nEnter the number of elements in the data set :\t");
        int num_Elements= 10000;
       // int[] dataSet= new int[num_Elements];
        System.out.print("Enter the number of clusters to be formed :\t");
        int k= 3;

        Node[] newCluster= new Node[k];
        Node[] prevCluster= new Node[k];


        double[] mean= new double[k];
        //double temp_mean[]= new double[k];    //to temporarily store the mean of the previous case

       // System.out.println("\nEnter the data set :-");
        //for(int i=0; i<num_Elements; i++)
        //    dataSet[i]= Integer.parseInt(br.readLine());

        System.out.println("\nEntered input data set :\t");
        for(int i=0; i<num_Elements; i++)
            System.out.print(dataSet[i]+" ");
        System.out.println();

        //randomly adding the elements to the linked lists in the array
        int temp=0;
        for(int i=0; i<num_Elements; i++)
        {
            if(newCluster[temp] == null)    //if first element in the linked list
            {
                Node p= new Node();
                p.data= dataSet[i];
                newCluster[temp]= p;
            }
            else
            {
                Node p= newCluster[temp];
                while(p.next != null)
                    p=p.next;
                Node q= new Node();
                q.data= dataSet[i];
                p.next= q;
            }
            ++temp;
            if(temp == k)    //resetting the value of temp when it reaches the end of the vector
                temp= 0;
        }    //for i


        boolean match= true;
        do
        {
            System.out.println();
            printCluster(k, newCluster);
            match= true;
            calc_Mean(newCluster, mean, k);

            for(int i=0; i<k; i++)
                System.out.print("m"+(i+1)+"= "+mean[i]+"\t");
            System.out.println();

            System.arraycopy(newCluster, 0, prevCluster, 0, k);    //copying all the refrences of the newly created linked lists in
            //newCluster[] to prevCluster[]

            //segregating the data set by considering the mean
            for(int i=0; i<k; i++)    //emptying newCluster[]
                newCluster[i]= null;

            for(int i=0; i<num_Elements; i++)
            {
                double temp_dist[]= new double[k], min;
                int position;
                for(int j=0; j<k; j++)    //storing the distance of the data set element from each of the mena value
                    temp_dist[j]= Math.abs(mean[j]- dataSet[i]);

                min= temp_dist[0]; position=0;
                for(int j=1; j<k; j++)
                {
                    double r= min;
                    min= (min<temp_dist[j])? min:temp_dist[j];
                    if(r != min)
                        position= j;
                }

                if(newCluster[position] == null)
                {
                    Node p= new Node();
                    p.data= dataSet[i];
                    newCluster[position]= p;
                }
                else
                {
                    Node p= newCluster[position];
                    while(p.next != null)
                        p=p.next;
                    Node q= new Node();
                    q.data= dataSet[i];
                    p.next= q;
                }
            }    //for i

            //now matching the new Cluster with the previous Cluster
            for(int i=0; i<k; i++)
            {
                Node p= prevCluster[i];
                Node q= newCluster[i];

                while(p != null && q != null)    //till the end of either of the linked lists
                {
                    if(p.data != q.data)
                    {
                        match= false;
                        break;
                    }
                    p= p.next;
                    q= q.next;
                }    //while

                if(p != null)    //if it did not reach the end of the linked list
                    match= false;
                if(q != null)    //if it did not reach the end of the linked list
                    match= false;
                if(match == false)
                    break;
            }     //for i
        }while(!match);

        System.out.println("\nClusters :");
        printCluster(k, newCluster);
    }


    static void calc_Mean(Node[] v, double mean[], int k)
    {
        for(int i=0; i<k; i++)
        {
            int sum=0, count=0;
            Node p= v[i];
            while(p != null)
            {
                sum+= p.data;
                ++count;
                p= p.next;
            }    //while
            if(sum == 0 && count == 0)    //no element in the cluster
                mean[i]= 0.0d;
            else
                mean[i]= (double)sum/count;
        }    //for i
    }    //calc_Mean

     void printCluster(int k, Node[] newCluster)
    {
        int clusterSize[]= new int[3];
        for(int i=0; i<k; i++)
        {
            System.out.print("K"+(i+1)+" :\t");
            Node p= newCluster[i];
            while(p != null)
            {
                clusterSize[i]++;
               // Toast.makeText(this, Integer.toString(p.data), Toast.LENGTH_SHORT).show();
                System.out.print(p.data+" ");

                p=p.next;
            }    //while
            System.out.println();
        }  //for i

        if(clusterSize[0]>clusterSize[1] && clusterSize[0]>clusterSize[2])
        {
            Toast.makeText(this,"cluster 0 is biggest", Toast.LENGTH_SHORT).show();

        }
        if(clusterSize[1]>clusterSize[0] && clusterSize[1]>clusterSize[2])
        {
            Toast.makeText(this,"cluster 1 is biggest", Toast.LENGTH_SHORT).show();

        }

        else
            Toast.makeText(this,"cluster 2 is biggest", Toast.LENGTH_SHORT).show();

    }



}
class Node
{
    int data;
    Node next;
}