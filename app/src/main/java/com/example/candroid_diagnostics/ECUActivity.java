package com.example.candroid_diagnostics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.os.Handler;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ECUActivity extends AppCompatActivity {

    private Handler mHandler;
    protected TextView ecupagettitle;
    protected ImageButton backecubtn;

    RecyclerView recyclerView;
    ECUrecyclerViewAdapter adapter;

    ArrayList<sensorModel> sensorModels = new ArrayList<>();
    int [] sensorImages = {R.drawable.baseline_hardware_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ecu1);
        setupUI();
        setUpSensorModels();
        ecupagettitle.setText(Globals.selected_ecu + " Activity");

        this.mHandler = new Handler();
        m_Runnable.run();
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run() {
            ECUActivity.this.mHandler.postDelayed(m_Runnable,1000);
            //updateTexts();
        }
    };


    private void setUpSensorModels(){
        String[] sensorNames = getResources().getStringArray(R.array.sensor_names);
        String[] Data1 = getResources().getStringArray(R.array.dummyData1_names);
        String[] Data2 = getResources().getStringArray(R.array.dummyData2_names);

        for(int i=0; i<sensorNames.length; i++){
            sensorModels.add(new sensorModel(sensorNames[i], Data1[i], Data2[i],sensorImages[0]));
        }
    }

   private void setupUI() {
       backecubtn = findViewById(R.id.bck2Btn);
       backecubtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               goToMainActivity();
           }
       });

       recyclerView = findViewById(R.id.recyclerViewECU1);
       setUpSensorModels();

       adapter = new ECUrecyclerViewAdapter(this,sensorModels);
       recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

       ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
           Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
           v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
           return insets;
       });

       ecupagettitle = findViewById(R.id.ecu1title);
    }

   private void goToMainActivity() {
       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
       startActivity(intent);
   }

   private void updateTexts() {
        sensorModels.clear();

        if (Globals.getECUVariables(Globals.selected_ecu) == null) return;
        for (String s : Globals.getECUVariables(Globals.selected_ecu)) {
            sensorModels.add(new sensorModel(s, Globals.getValue(Globals.selected_ecu, s), "units", sensorImages[0]));
        }

        adapter.notifyDataSetChanged();
   }
}