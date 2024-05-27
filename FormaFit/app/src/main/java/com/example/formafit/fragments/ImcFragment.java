package com.example.formafit.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.formafit.R;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.LinkedList;

public class ImcFragment extends Fragment {

    private PieChart pieChartIMC;
    private TextView alturaIMC, pesoIMC;
    private BaseDatosHelper dbHelper;

    public ImcFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_imc, container, false);

        pieChartIMC = view.findViewById(R.id.pieChartIMC);
        alturaIMC = view.findViewById(R.id.alturaIMC);
        pesoIMC = view.findViewById(R.id.pesoIMC);

        dbHelper = new BaseDatosHelper(getContext());

        alturaIMC.setText(dbHelper.getAltura(MainActivity.email) + " cm");
        pesoIMC.setText(dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getPeso() + " kg");

        LinkedList<PieEntry> entries = new LinkedList<>();
        entries.add(new PieEntry(20.0f, getString(R.string.muyDelgado)));
        entries.add(new PieEntry(20.0f, getString(R.string.bajoPeso)));
        entries.add(new PieEntry(20.0f, getString(R.string.pesoNormal)));
        entries.add(new PieEntry(20.0f, getString(R.string.sobrePeso)));
        entries.add(new PieEntry(20.0f, getString(R.string.obesidad)));

        PieDataSet dataSet = new PieDataSet(entries, "Frutas");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        pieChartIMC.getLegend().setEnabled(false);
        pieChartIMC.getDescription().setEnabled(false);
        pieChartIMC.setCenterText("{" + dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getImc() + "}");
        pieChartIMC.setCenterTextSize(35f);

        // Obtiene el objeto Legend del PieChart
        Legend legend = pieChartIMC.getLegend();

// Configura la apariencia de la leyenda
        legend.setForm(Legend.LegendForm.CIRCLE); // Establece la forma de la leyenda
        legend.setTextSize(12f); // Establece el tamaño del texto de la leyenda
        legend.setTextColor(Color.WHITE); // Establece el color del texto de la leyenda
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }
        });

        pieChartIMC.setData(data);

        pieChartIMC.highlightValue(0, 0);
        pieChartIMC.setTouchEnabled(false);

        // Configurar animación de entrada
        pieChartIMC.animateY(1000, Easing.EasingOption.EaseInOutQuad); // Duración de la animación y tipo de interpolación
        pieChartIMC.invalidate(); // refresh

        return view;
    }
}