package com.example.candroid_diagnostics;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class graphActivity extends AppCompatActivity {



protected TextView graphTitle;
protected ImageButton backGrphBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        setupUI();

        GraphView graph = (GraphView) findViewById(R.id.graphECU1);
        LineGraphSeries<DataPoint> Series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)});

        //graphTitle.setText();

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
    }

    private void setupUI() {

        backGrphBtn = findViewById(R.id.bckgraphBtn);
        backGrphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToECUActivity();
            }
        });

    }

    private void goToECUActivity() {
        Intent intent = new Intent(getApplicationContext(), ECUActivity.class);
        startActivity(intent);
    }


}

