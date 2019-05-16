package com.example.spart.sensoresdeproximidadygiroscopio;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ProximityActivity extends AppCompatActivity {
    //Un
    private static final String TAG = "Sensor de proximidad";
//Manejador del sensor
    private SensorManager sensorManager;
//El sensor que vamos a usar y su respectivo listener
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);
//Se inicializar el manejador del sensor
        sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
//Se inicializa el sensor de proximidad
        proximitySensor =
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        //En el dado caso que no encuentre el sensor de proximidad termine la aplicacion
        if(proximitySensor == null) {
            Log.e(TAG, "No se encuentra el sensor.");
            finish();
        }
        //Inicializamos el listener
        proximitySensorListener = new SensorEventListener() {
            //Detecta cambios en el sensor
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                //Si el sensor tiene algo enfrente la pantalla se pondra roja
                if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
        //Registra lo que le dice el listener cada dos segundos
        sensorManager.registerListener(proximitySensorListener,
                proximitySensor, 2 * 1000 * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
    }
}
