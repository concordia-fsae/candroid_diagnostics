package com.example.candroid_diagnostics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    protected ImageButton bckMainbtn;
    protected Switch connectionSwitch;
    protected TextView statustxt;
    protected EditText mqttTxt;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        setupUI();
        mqttTxt.setText(Globals.MQTT_SERVER_URI);

        updateTexts();

        this.mHandler = new Handler();
        m_Runnable.run();
    }

    private void updateTexts() {
        if (Communications.isConnecting()) {
            connectionSwitch.setText("DISCONNECT");
            statustxt.setText("Status: Connecting");
            connectionSwitch.setChecked(true);
        } else if (Communications.isConnected()) {
            connectionSwitch.setText("DISCONNECT");
            statustxt.setText("Status: Connected");
            connectionSwitch.setChecked(true);
        }
        else {
            connectionSwitch.setText("CONNECT");
            statustxt.setText("Status: Disconnected");
            connectionSwitch.setChecked(false);
        }
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run() {
            Settings.this.mHandler.postDelayed(m_Runnable,1000);
            updateTexts();
        }
    };

    private void setupUI() {
        bckMainbtn= findViewById(R.id.bck5Btn);
        bckMainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });

        connectionSwitch = findViewById(R.id.switch1);
        statustxt = findViewById(R.id.StatusText);
        mqttTxt = findViewById(R.id.mqqtText);
        statustxt.setText("");
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void onSwitchClick(View view){
        if(connectionSwitch.isChecked()){
            connectionSwitch.setText("DISCONNECT");
            statustxt.setText("Status: Connecting");
            Globals.MQTT_SERVER_URI = mqttTxt.getText().toString();
            setupCommunications();
        }
        else{
            connectionSwitch.setText("CONNECT");
            statustxt.setText("Status: Disconnected Succesfully");
            Globals.MQTT_SERVER_URI = mqttTxt.getText().toString();
            Communications.destroy();
        }
    }

    private void setupCommunications() {
        String[] sensorNames = getResources().getStringArray(R.array.sensor_names);
        ArrayList<Tuple<String, String>> vars = new ArrayList<>();
        for(int i=0; i<sensorNames.length; i++){
            vars.add(new Tuple<String, String>("ecu1", sensorNames[i]));
        }

//        vars.add(new Tuple<String, String>("ecu1", "Min Cell Voltage"));
//        vars.add(new Tuple<String, String>("ecu1", "Max Cell Voltage"));
//        vars.add(new Tuple<String, String>("ecu1", "Avg Cell Voltage"));
//        vars.add(new Tuple<String, String>("ecu1", "Min Relative SOC"));
//        vars.add(new Tuple<String, String>("ecu1", "Max Relative SOC"));
//        vars.add(new Tuple<String, String>("ecu1", "Avg Relative SOC"));
//        vars.add(new Tuple<String, String>("ecu1", "CCL"));
//        vars.add(new Tuple<String, String>("ecu1", "DCL"));
//        vars.add(new Tuple<String, String>("ecu1", "Pack Voltage"));
//        vars.add(new Tuple<String, String>("ecu1", "Cell 1 Voltage"));
//        vars.add(new Tuple<String, String>("ecu1", "Cell 2 Voltage"));
//        vars.add(new Tuple<String, String>("ecu1", "Cell 3 Voltage"));
//        vars.add(new Tuple<String, String>("ecu1", "Cell 4 Voltage"));
//        vars.add(new Tuple<String, String>("ecu1", "Max Cell Temp"));
//        vars.add(new Tuple<String, String>("ecu1", "MCU Temp"));
//        vars.add(new Tuple<String, String>("ecu1", "Board Temp 1"));
//        vars.add(new Tuple<String, String>("ecu1", "Board Temp 2"));
//        vars.add(new Tuple<String, String>("ecu1", "Fan 1 Speed"));
//        vars.add(new Tuple<String, String>("ecu1", "Fan 2 Speed"));
//
//        vars.add(new Tuple<String, String>("ecu1", ""));


        Communications.start(Communications.Comm_Class_E.MQTT, vars);
    }

}