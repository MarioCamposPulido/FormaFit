package com.example.formafit.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.formafit.R;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class BasculaFragment extends Fragment {

    private LineChart lineChart;
    private LineData lineData;
    private BaseDatosHelper dbHelper;

    public BasculaFragment() {
        // Required empty public constructor
    }

    private LinkedList<Entry> getAllEntradasEnGrafico(LinkedList<Integer> pesos){
        LinkedList<Entry> entries = new LinkedList<>();
        for (int i = 0; i < pesos.size(); i++) {
            entries.add(new Entry(i, pesos.get(i)));
        }

        return entries;
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

        dbHelper = new BaseDatosHelper(getContext());
        LineDataSet lineDataSet = new LineDataSet(getAllEntradasEnGrafico(dbHelper.getAllEntradasPeso(MainActivity.email)), "Peso");

        lineDataSet.setColor(ContextCompat.getColor(getContext(), R.color.buttons_color_rosa));
        lineDataSet.setLineWidth(4f);
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setDrawValues(false);

        lineDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);

        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.backgroud_rounded));
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setDrawLabels(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineData = new LineData(lineDataSet);

        lineChart.setBackgroundColor(Color.WHITE);

        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh

        fabAniadirEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new EntradaPesoFragment()).commit();
            }
        });

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                float yValue = e.getY();
                Toast.makeText(getContext(), "Valor: " + yValue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
            }
        });

        return view;
    }
}