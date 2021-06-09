package com.example.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

public class myRecieve extends BroadcastReceiver implements SensorEventListener {
    SensorManager sensorManager;
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;

        sensorManager=(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

    }
    int counterRead=0;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            Toast.makeText(context,getAccelerometer(sensorEvent),Toast.LENGTH_SHORT).show();
            counterRead++;
            if(counterRead<=5) {
                Log.d("SENSOR CHANGED REACHED","REACHED SAFELY AND NOT LISTENING");
                sensorManager.unregisterListener(this);
                counterRead=0;
        }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private String getAccelerometer(SensorEvent sensorEvent) {

        float[] values=sensorEvent.values;


        float x=values[0];
        float y=values[1];
        float z=values[2];
        String s="";
        if((x>5||x<-5) && y<2 && z<2){
            s="Screen facing you and horizontal phone";
        }
        else if((y>5||y<-5) && x<2 && z<2)
        {
            s="Screen facing you and verticle phone";
        }
        else if((z>5||z<-5) && y<2 && x<2){
            s="screen bottom//screen top";
        }
        else if(y>5 && z>5 && x<2)
        {
            s="Viewing angle!";
        }
        else if(x>5 && z>5 && y<2)
        {
            s="Movie Angle!";
        }
        s+="\nX="+x+"\nY="+y+"\nZ="+z;




        return s;



    }
}
