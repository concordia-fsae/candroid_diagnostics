package com.example.candroid_diagnostics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class graphActivity extends AppCompatActivity {


    private Handler mHandler;
    protected TextView graphTitle;
    protected ImageButton backGrphBtn;

    LineGraphSeries<DataPoint> Series;
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        graph = (GraphView) findViewById(R.id.graphECU1);
        Series = new LineGraphSeries<>();

        setupUI();

        graphTitle.setText(Globals.selected_variable);

//// styling series



        Series.setColor(Color.GREEN);
        Series.setDrawDataPoints(true);
        Series.setDataPointsRadius(10);
        Series.setThickness(8);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollableY(true);
//
// custom paint to make a dotted line
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        Series.setCustomPaint(paint);


        graph.addSeries(Series);

        //to add new point
        //the following function below adds automatically to the last data and hence should increase graph.
        //Series.appendData(new DataPoint(LastXValue, value from the mttq server), true, 40);
        //graph.addSeries(Series);

        this.mHandler = new Handler();
        m_Runnable.run();
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run() {
            graphActivity.this.mHandler.postDelayed(m_Runnable,1000);
            updateTexts();
        }
    };

    private void updateTexts() {
        Series.appendData(new DataPoint(Series.getHighestValueX() + 1, Float.valueOf(Globals.getValue(Globals.selected_ecu, Globals.selected_variable))), true, 40);
        //graph.addSeries(Series);
    }

    private void setupUI() {
        graphTitle = findViewById(R.id.graph_textView);
        backGrphBtn = findViewById(R.id.bckgraphBtn);
        backGrphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToECUActivity();
            }
        });

        for (int i = 0; i < Globals.getValues(Globals.selected_ecu, Globals.selected_variable).size(); i++)
        {
            Series.appendData(new DataPoint(i, Float.valueOf(Globals.getValues(Globals.selected_ecu, Globals.selected_variable).get(i))), true, 40);
        }
    }

    private void goToECUActivity() {
        Intent intent = new Intent(getApplicationContext(), ECUActivity.class);
        startActivity(intent);
    }
}

