package com.example.candroid_diagnostics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationActivity extends AppCompatActivity {

    protected ImageButton backMain;
    protected TextView temperatureCell;
    protected TextView lowTemp;
    protected TextView highTemp;
    protected TextView lowTempVoltageCell;
    protected TextView highTempVoltageCell;

    protected TextView lowTempCell1;
    protected TextView highTempCell1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuration);
        setupUI();

        Spinner spinner = findViewById(R.id.spinner);

        List<CustomConfigMenu> ItemConfigList = new ArrayList<>();
        ItemConfigList.add(new CustomConfigMenu("Default Student Configuration"));
        ItemConfigList.add(new CustomConfigMenu("Electrical Team Manager Configuration"));
        ItemConfigList.add(new CustomConfigMenu("Computer Team Manager Configuration"));

        com.example.candroid_diagnostics.SpinnerAdapter adapter = new com.example.candroid_diagnostics.SpinnerAdapter(this,ItemConfigList);

        //SpinnerAdapter adapter = new SpinnerAdapter(this, ItemConfigList);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                CustomConfigMenu selectedItem = ItemConfigList.get(position);

                String configName = selectedItem.getDisplayText();

                // Assuming you have methods to apply different configurations based on the selected option
                switch (configName) {
                    case "Default Student Configuration":
                        applyDefaultStudentConfiguration();
                        break;
                    case "Electrical Team Manager Configuration":
                        applyElectricalTeamManagerConfiguration();
                        break;
                    case "Computer Team Manager Configuration":
                        applyComputerTeamManagerConfiguration();
                        break;
                    default:
                        // Handle unknown configuration option
                        break;
                }
                Toast.makeText(getApplicationContext(), "Selected: " + selectedItem.getDisplayText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    private void setupUI() {
        backMain = findViewById(R.id.bckConfBtn);
        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToMainActivity();
            }
        });

        temperatureCell = findViewById(R.id.txttemp);
        lowTemp = findViewById(R.id.lowvaluetemp);
        highTemp = findViewById(R.id.highvaltemp);
        lowTempVoltageCell = findViewById(R.id.lowvaluetemp2);
        highTempVoltageCell=findViewById(R.id.highvaltemp2);
        lowTempCell1 = findViewById(R.id.lowvaluetemp3);
        highTempCell1=findViewById(R.id.highvaltemp3);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void applyDefaultStudentConfiguration() {
        // Example: Setting values for Default Student Configuration
        Globals.low_temp = 20f;
        Globals.high_temp = 60f;
        Globals.low_avg_cell = 2.5f;
        Globals.high_avg_cell = 4.2f;
        Globals.low_cell1_voltage = 3.0f;
        Globals.high_cell1_voltage = 4.0f;

        lowTemp.setText("20°C");
        highTemp.setText("60°C");
        lowTempVoltageCell.setText("2.5 V");
        highTempVoltageCell.setText("4.2 V");
        lowTempCell1.setText("3 V");
        highTempCell1.setText("4 V");
    }

    private void applyElectricalTeamManagerConfiguration() {
        // Example: Setting values for Electrical Team Manager Configuration
        Globals.low_temp = 25f;
        Globals.high_temp = 55f;
        Globals.low_avg_cell = 2.8f;
        Globals.high_avg_cell = 4.0f;
        Globals.low_cell1_voltage = 3.0f;
        Globals.high_cell1_voltage = 4.0f;

        lowTemp.setText("25°C");
        highTemp.setText("55°C");
        lowTempVoltageCell.setText("2.8 V");
        highTempVoltageCell.setText("4.0 V");
        lowTempCell1.setText("3 V");
        highTempCell1.setText("4 V");
    }

    private void applyComputerTeamManagerConfiguration() {
        // Example: Setting values for Battery Configuration
        Globals.low_temp = 15f;
        Globals.high_temp = 50f;
        Globals.low_avg_cell = 2.7f;
        Globals.high_avg_cell = 4.15f;
        Globals.low_cell1_voltage = 3.0f;
        Globals.high_cell1_voltage = 4.0f;

        lowTemp.setText("15°C");
        highTemp.setText("50°C");
        lowTempVoltageCell.setText("2.7 V");
        highTempVoltageCell.setText("4.15 V");
        lowTempCell1.setText("3 V");
        highTempCell1.setText("4 V");
    }

}