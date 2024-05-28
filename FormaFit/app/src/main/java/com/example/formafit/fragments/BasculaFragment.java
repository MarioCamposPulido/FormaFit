package com.example.formafit.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.formafit.R;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.example.formafit.java.EntradaPeso;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class BasculaFragment extends Fragment {

    private LineChart lineChart;
    private LineData lineData;
    private FloatingActionButton fabAniadirEntrada;
    private BaseDatosHelper dbHelper;
    private TextView diarioButton, userNameBascula, pesoUltimo;


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
        fabAniadirEntrada = view.findViewById(R.id.fabAdd);
        diarioButton = view.findViewById(R.id.diarioButton);
        userNameBascula = view.findViewById(R.id.userNameBascula);
        pesoUltimo = view.findViewById(R.id.pesoUltimo);

        dbHelper = new BaseDatosHelper(getContext());

        userNameBascula.setText(dbHelper.getUserName(MainActivity.email));

        LinkedList<Integer> pesosInt = new LinkedList<>();
        for (EntradaPeso entradaPeso : dbHelper.getAllEntradasPeso(MainActivity.email)) {
            pesosInt.add(entradaPeso.getPeso());
        }
        pesoUltimo.setText(pesosInt.getLast() + " kg");
        LineDataSet lineDataSet = new LineDataSet(getAllEntradasEnGrafico(pesosInt), "Pesos");

        lineDataSet.setColor(ContextCompat.getColor(getContext(), R.color.buttons_color_rosa));
        lineDataSet.setLineWidth(4f);
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setDrawValues(false);

        YAxis yAxis = lineChart.getAxisLeft();

        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }
        });

        lineDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);

        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setDrawLabels(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineData = new LineData(lineDataSet);

        lineChart.setData(lineData);
        lineChart.animateY(750, Easing.EasingOption.EaseInOutQuad);
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
                Toast.makeText(getContext(), (int) yValue  + " kg", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {
            }
        });

        diarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new DiarioFragment()).commit();
            }
        });

        return view;
    }
}