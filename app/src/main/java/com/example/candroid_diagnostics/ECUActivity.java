package com.example.candroid_diagnostics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ECUActivity extends AppCompatActivity implements RecyclerViewInterface {

    private Handler mHandler;
    protected TextView ecupagettitle;
    protected ImageButton backecubtn;
    protected TextView countVar;

    RecyclerView recyclerView;
    ECUrecyclerViewAdapter adapter;

    ArrayList<sensorModel> sensorModels = new ArrayList<>();
    int [] sensorImages = {R.drawable.baseline_hardware_24, R.drawable.check_ok_symbol, R.drawable.error_symbol, R.drawable.baseline_hardware_25};

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
            updateTexts();
        }
    };



    private void setUpSensorModels(){
        String[] sensorNames = getResources().getStringArray(R.array.sensor_names);
        String[] Data1 = getResources().getStringArray(R.array.dummyData1_names);
        String[] Data2 = getResources().getStringArray(R.array.units_names);

        for(int i=0; i<sensorNames.length; i++){
            //sensorModels.add(new sensorModel(sensorNames[i], Data1[i], Data2[i],sensorImages[i%3]));
            sensorModels.add(new sensorModel(sensorNames[i], Data1[i], Data2[i],sensorImages[i%3]));
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

       //countVar = findViewById(R.id.countVariables);
       //countVar.setText("Number of variables displayed: " + sensorModels.size());
        //countVar.setText(" ");

       recyclerView = findViewById(R.id.recyclerViewECU1);

       adapter = new ECUrecyclerViewAdapter(this,sensorModels, this);
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
        /*sensorModels.clear();

        if (Globals.getECUVariables(Globals.selected_ecu) == null) return;
        for (String s : Globals.getECUVariables(Globals.selected_ecu)) {
            //sensorModels.add(new sensorModel(s, Globals.getValue(Globals.selected_ecu, s), "units", sensorImages[0]));
            sensorModels.add(new sensorModel(s, Globals.getValue(Globals.selected_ecu, s), sensorImages[0])); //constructor override !!!
        }*/

       String[] sensorNames = getResources().getStringArray(R.array.sensor_names);

       for(int i=0; i<sensorNames.length; i++){
           //sensorModels.add(new sensorModel(sensorNames[i], Data1[i], Data2[i],sensorImages[i%3]));
           sensorModels.get(i).dataValue1 = Globals.getValue(Globals.selected_ecu, sensorNames[i]);
           //if (i != )
           if (sensorNames[i].equals("Average Cell Voltage") && !sensorModels.get(i).dataValue1.equals(""))
           {
               if (Float.valueOf(sensorModels.get(i).dataValue1) > Globals.high_avg_cell ||
                       Float.valueOf(sensorModels.get(i).dataValue1) < Globals.low_avg_cell)
               {
                   sensorModels.get(i).image = sensorImages[2];
               }
               else
               {
                   sensorModels.get(i).image = sensorImages[1];
               }
           }
           else if (sensorNames[i].equals("Cell 1 Voltage") && !sensorModels.get(i).dataValue1.equals(""))
           {
               if (Float.valueOf(sensorModels.get(i).dataValue1) > Globals.high_cell1_voltage ||
                       Float.valueOf(sensorModels.get(i).dataValue1) < Globals.low_cell1_voltage)
               {
                   sensorModels.get(i).image = sensorImages[2];
               }
               else
               {
                   sensorModels.get(i).image = sensorImages[1];
               }
           }
           else if (sensorNames[i].equals("Maximum Cell Temperature") && !sensorModels.get(i).dataValue1.equals(""))
           {
               if (Float.valueOf(sensorModels.get(i).dataValue1) > Globals.high_temp ||
                    Float.valueOf(sensorModels.get(i).dataValue1) < Globals.low_temp)
               {
                   sensorModels.get(i).image = sensorImages[2];
               }
               else
               {
                   sensorModels.get(i).image = sensorImages[1];
               }
           }
           else
           {
               sensorModels.get(i).image = sensorImages[3];
           }
       }

        adapter.notifyDataSetChanged();
   }


    @Override
    public void onItemClick(int position) {
        String[] sensorNames = getResources().getStringArray(R.array.sensor_names);

        Intent intent = new Intent(ECUActivity.this, graphActivity.class);
        Globals.selected_variable = sensorNames[position];
        startActivity(intent);
    }
}