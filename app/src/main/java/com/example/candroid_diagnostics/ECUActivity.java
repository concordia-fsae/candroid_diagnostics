package com.example.candroid_diagnostics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ECUActivity extends AppCompatActivity {

    protected TextView variableTxt;
    protected TextView ecupagettitle;
    protected ImageButton backecubtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ecu1);
        //setupUI();
        backecubtn = findViewById(R.id.bck2Btn);
        backecubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToMainActivity();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        variableTxt = findViewById(R.id.variableName);
        ecupagettitle = findViewById(R.id.ecu1title);
    }

   /* private void setupUI() {
        variableTxt = findViewById(R.id.variableName);

    }

    */
   private void goToMainActivity() {
       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
       startActivity(intent);
   }
}