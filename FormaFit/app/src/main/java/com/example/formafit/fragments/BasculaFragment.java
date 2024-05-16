package com.example.formafit.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.formafit.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasculaFragment extends Fragment {

    private LineChart lineChart;
    private LineData lineData;
    private List<Entry> entries = new ArrayList<>();

    public BasculaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bascula, container, false);

        lineChart = view.findViewById(R.id.lineChart);
        FloatingActionButton fabAniadirEntrada = view.findViewById(R.id.fabAdd);

        entries.add(new Entry(0, 10));
        entries.add(new Entry(1, 20));
        entries.add(new Entry(2, 15));
        entries.add(new Entry(6, 25));

        LineDataSet dataSet = new LineDataSet(entries, "Peso");
        dataSet.setColor(Color.GREEN);
        lineData = new LineData(dataSet);

        lineChart.setBackgroundColor(Color.WHITE);

        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh

        fabAniadirEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineData lineData = lineChart.getLineData();
                LineDataSet dataSet = null;
                if (lineData != null && lineData.getDataSetCount() > 0) {
                    dataSet = (LineDataSet) lineData.getDataSetByIndex(0);
                }
                if (dataSet == null) {
                    // Si no hay ningún conjunto de datos, muestra un mensaje de error o haz algo apropiado
                    return;
                }
                dataSet.addEntry(new Entry(7, 50)); // Aquí puedes ajustar los valores xValue e yValue
                lineData.notifyDataChanged();
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();
            }
        });

        return view;
    }
}