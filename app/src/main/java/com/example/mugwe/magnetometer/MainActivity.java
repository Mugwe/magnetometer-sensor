package com.example.mugwe.magnetometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        SensorEventListener{
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private TextView value;
    public static DecimalFormat DECIMAL_FORMATTER;
    private Sensor magneticSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value= (TextView) findViewById(R.id.value);

        sensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        DECIMAL_FORMATTER = new DecimalFormat("#.000", symbols);

        magneticSensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }
    @Override
    public void onSensorChanged(SensorEvent event){
        System.out.println("Proximity sensor changed event");

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            float magX = event.values[0];
            float magY = event.values[1];
            float magZ = event.values[2];
            double magnitude = Math.sqrt((magX * magX) + (magY + magY)
                    +(magZ * magZ));

            value.setText(DECIMAL_FORMATTER.format(magnitude)+ "\u00b5Tesla");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,proximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);

    }
}
