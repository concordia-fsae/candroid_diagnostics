package com.example.candroid_diagnostics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
        ItemConfigList.add(new CustomConfigMenu("Battery Configuration"));

        com.example.candroid_diagnostics.SpinnerAdapter adapter = new com.example.candroid_diagnostics.SpinnerAdapter(this,ItemConfigList);

        //SpinnerAdapter adapter = new SpinnerAdapter(this, ItemConfigList);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                CustomConfigMenu selectedItem = ItemConfigList.get(position);
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
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}