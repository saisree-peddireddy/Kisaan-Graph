package com.example.pricegraph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.lang.String;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public class SecondActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

    private LineChart mChart;
    int array[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String result = bundle.getString("Data");
//        TextView tv = findViewById(R.id.result);
//        tv.setText(result);
        array = new int[13][2];
        //1. Split the result into tuples
        String tuples[] = result.split(";");
        try {
            for (String info : tuples) {
                String date[] = info.split(" ")[0].split("/");

                int price = Integer.parseInt(info.split(" ")[1]);
                array[Integer.parseInt(date[1])][0] = (array[Integer.parseInt(date[1])][0] + price);
                array[Integer.parseInt(date[1])][1]++;
            }

            //String op = "";
            for (int i = 1; i < 13; ++i) {
                if(array[i][1]!=0)
                array[i][0] = array[i][0] / array[i][1] ;
            }
//            tv.setText(op);
        }
        catch (Exception e){
//            tv.setText(e+"");
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mChart = (LineChart) findViewById(R.id.linechart);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        setData();

        Legend l = mChart.getLegend();

        l.setForm(Legend.LegendForm.LINE);

        mChart.setDescription("Demo Line Chart");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
//         mChart.setScaleXEnabled(true);
//         mChart.setScaleYEnabled(true);
//
//            LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
//            upper_limit.setLineWidth(4f);
//            upper_limit.enableDashedLine(10f, 10f, 0f);
//            upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//            upper_limit.setTextSize(10f);
//
//            LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
//            lower_limit.setLineWidth(4f);
//            lower_limit.enableDashedLine(10f, 10f, 0f);
//            lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//            lower_limit.setTextSize(10f);
//
//            YAxis leftAxis = mChart.getAxisLeft();
//            leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//            leftAxis.addLimitLine(upper_limit);
//            leftAxis.addLimitLine(lower_limit);
//            leftAxis.setAxisMaxValue(10000f);
//            leftAxis.setAxisMinValue(-50f);
//            leftAxis.enableGridDashedLine(10f, 10f, 0f);
//            leftAxis.setDrawZeroLine(false);
//
//            leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mChart.invalidate();



    }

    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");

        set1.setFillAlpha(110);
//         set1.setFillColor(Color.RED);
        //set1.setFillColor(Color.DKGRAY);
        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
    }

    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Jan");
        xVals.add("Feb");
        xVals.add("Mar");
        xVals.add("Apr");
        xVals.add("May");
        xVals.add("Jun");
        xVals.add("Jul");
        xVals.add("Aug");
        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(){

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        yVals.add(new Entry((float)(array[1][0]), 0));
        yVals.add(new Entry((float)array[2][0], 1));
        yVals.add(new Entry((float)array[3][0], 2));
        yVals.add(new Entry((float)array[4][0], 3));
        yVals.add(new Entry((float)array[5][0], 4));
        yVals.add(new Entry((float)array[6][0], 5));
        yVals.add(new Entry((float)array[7][0], 6));
        yVals.add(new Entry((float)array[8][0], 7));

        return yVals;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}
