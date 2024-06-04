package com.example.formafit.fragments;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.formafit.R;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.example.formafit.java.EntradaPeso;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;

import java.util.LinkedList;

/**
 * Class BasculaFragment
 * Visualizas tu nombre de usuario, pinta las Entradas de Peso en el gráfico,
 * asigna metas, accede a otros fragmentos
 */
public class BasculaFragment extends Fragment {

    private BaseDatosHelper dbHelper;
    private TextView pesoKgObjetivo;
    private int objetivoPeso;


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

    private void showDialogObjetivo() {

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_objetivo, null);

        pesoKgObjetivo = dialogLayout.findViewById(R.id.pesoKgObjetivo);
        Slider sliderPesoObjetivo = dialogLayout.findViewById(R.id.sliderPesoObjetivo);

        sliderPesoObjetivo.addOnChangeListener((slider, value, fromUser) -> {
            pesoKgObjetivo.setText((int) value + " kg");
            objetivoPeso = (int) value;
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogLayout)
                .setTitle(null)
                .setPositiveButton(getResources().getText(R.string.aceptar), (dialog, id) -> {
                    dbHelper = new BaseDatosHelper(getContext());
                    dbHelper.upgradeCambiarObjetivo(MainActivity.email, objetivoPeso);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new BasculaFragment()).commit();
                })
                .setNegativeButton(getResources().getText(R.string.cancelar), (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bascula, container, false);

        LineChart lineChart = view.findViewById(R.id.lineChart);
        FloatingActionButton fabAniadirEntrada = view.findViewById(R.id.fabAdd);
        TextView diarioButton = view.findViewById(R.id.diarioButton);
        TextView userNameBascula = view.findViewById(R.id.userNameBascula);
        TextView pesoUltimo = view.findViewById(R.id.pesoUltimo);
        TextView obetivoButton = view.findViewById(R.id.obetivoButton);

        dbHelper = new BaseDatosHelper(getContext());

        userNameBascula.setText(dbHelper.getUserName(MainActivity.email));

        LinkedList<Integer> pesosInt = new LinkedList<>();
        for (EntradaPeso entradaPeso : dbHelper.getAllEntradasPeso(MainActivity.email)) {
            pesosInt.add(entradaPeso.getPeso());
        }
        pesoUltimo.setText(pesosInt.getLast() + " kg");
        LineDataSet lineDataSet = new LineDataSet(getAllEntradasEnGrafico(pesosInt), "");

        lineDataSet.setColor(ContextCompat.getColor(requireContext(), R.color.buttons_color_rosa));
        lineDataSet.setLineWidth(4f);
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setDrawValues(false);

        YAxis yAxis = lineChart.getAxisLeft();

        float objetivo = 200f;

        float rango = yAxis.getAxisMaximum() - yAxis.getAxisMinimum();

        float margen = 10f;
        float minimo = Math.min(yAxis.getAxisMinimum(), objetivo - rango / 2f - margen);
        float maximo = Math.max(yAxis.getAxisMaximum(), objetivo + rango / 2f + margen);
        yAxis.setAxisMinimum(minimo);
        yAxis.setAxisMaximum(maximo);

        yAxis.setValueFormatter((value, axis) -> String.valueOf((int) value));

        lineDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.rojo));

        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getXAxis().setDrawLabels(false);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        LineData lineData = new LineData(lineDataSet);

        if (dbHelper.getObjetivoByUser(MainActivity.email) != 0){
            LimitLine limitLine = new LimitLine(dbHelper.getObjetivoByUser(MainActivity.email), getResources().getString(R.string.objetivoPeso));
            limitLine.setLineWidth(3f);
            limitLine.setLineColor(ContextCompat.getColor(requireContext(), R.color.buttons_color_verde));
            limitLine.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            limitLine.setTextSize(16f);
            limitLine.setTypeface(Typeface.DEFAULT_BOLD);
            yAxis.addLimitLine(limitLine);
        }

        lineChart.setData(lineData);
        lineChart.animateY(750, Easing.EasingOption.EaseInOutQuad);
        lineChart.invalidate(); // Refresca el gráfico

        fabAniadirEntrada.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new EntradaPesoFragment()).commit());

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

        diarioButton.setOnClickListener(view12 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new DiarioFragment()).commit());

        obetivoButton.setOnClickListener(view13 -> showDialogObjetivo());

        return view;
    }
}