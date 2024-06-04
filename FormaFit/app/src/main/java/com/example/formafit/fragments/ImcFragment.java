package com.example.formafit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.formafit.R;
import com.example.formafit.activities.MainActivity;
import com.example.formafit.base_datos.BaseDatosHelper;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.LinkedList;

/**
 * Class ImcFragment
 * Muestra el IMC con todos sus datos para calcularlo
 */
public class ImcFragment extends Fragment {

    LinearLayout muyDelgadoLayout, bajoPesoLayout, pesoNormalLayout, sobrePesoLayout, obesidadLayout;

    public ImcFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_imc, container, false);

        PieChart pieChartIMC = view.findViewById(R.id.pieChartIMC);
        TextView alturaIMC = view.findViewById(R.id.alturaIMC);
        TextView pesoIMC = view.findViewById(R.id.pesoIMC);

        muyDelgadoLayout = view.findViewById(R.id.muyDelgadoLayout);
        bajoPesoLayout = view.findViewById(R.id.bajoPesoLayout);
        pesoNormalLayout = view.findViewById(R.id.pesoNormalLayout);
        sobrePesoLayout = view.findViewById(R.id.sobrePesoLayout);
        obesidadLayout = view.findViewById(R.id.obesidadLayout);

        BaseDatosHelper dbHelper = new BaseDatosHelper(getContext());

        alturaIMC.setText(dbHelper.getAltura(MainActivity.email) + " cm");
        pesoIMC.setText(dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getPeso() + " kg");

        // Datos del gráfico
        LinkedList<PieEntry> entries = new LinkedList<>();
        entries.add(new PieEntry(20.0f, getString(R.string.muyDelgado)));
        entries.add(new PieEntry(20.0f, getString(R.string.bajoPeso)));
        entries.add(new PieEntry(20.0f, getString(R.string.pesoNormal)));
        entries.add(new PieEntry(20.0f, getString(R.string.sobrePeso)));
        entries.add(new PieEntry(20.0f, getString(R.string.obesidad)));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        pieChartIMC.getLegend().setEnabled(false);
        pieChartIMC.getDescription().setEnabled(false);
        pieChartIMC.setCenterText("{" + dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getImc() + "}");
        pieChartIMC.setCenterTextSize(35f);
        dataSet.setSliceSpace(2f);
        dataSet.setSliceSpace(2f);

        // Establece los colores específicos para cada entrada
        LinkedList<Integer> colors = new LinkedList<>();
        colors.add(ContextCompat.getColor(requireContext(), R.color.azul));
        colors.add(ContextCompat.getColor(requireContext(), R.color.violeta));
        colors.add(ContextCompat.getColor(requireContext(), R.color.verde));
        colors.add(ContextCompat.getColor(requireContext(), R.color.naranja));
        colors.add(ContextCompat.getColor(requireContext(), R.color.rojo));

        dataSet.setColors(colors);

        data.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> "");

        pieChartIMC.setData(data);

        if (dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getImc() <= 16.99) {
            pieChartIMC.highlightValue(0, 0);
            muyDelgadoLayout.setBackgroundResource(R.drawable.backgroud_white_rounded);
            muyDelgadoLayout.setPadding(16, 16, 16, 16);
        } else if (dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getImc() >= 17.00
                && dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getImc() <= 18.49) {
            pieChartIMC.highlightValue(1, 0);
            bajoPesoLayout.setBackgroundResource(R.drawable.backgroud_white_rounded);
            bajoPesoLayout.setPadding(16, 16, 16, 16);
        } else if (dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getImc() >= 18.50
                && dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getImc() <= 24.99) {
            pieChartIMC.highlightValue(2, 0);
            pesoNormalLayout.setBackgroundResource(R.drawable.backgroud_white_rounded);
            pesoNormalLayout.setPadding(16, 16, 16, 16);
        } else if (dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getImc() >= 25.00
                && dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getImc() <= 29.99) {
            pieChartIMC.highlightValue(3, 0);
            sobrePesoLayout.setBackgroundResource(R.drawable.backgroud_white_rounded);
            sobrePesoLayout.setPadding(16, 16, 16, 16);
        } else if (dbHelper.getAllEntradasPeso(MainActivity.email).getLast().getImc() >= 30.00) {
            pieChartIMC.highlightValue(4, 0);
            obesidadLayout.setBackgroundResource(R.drawable.backgroud_white_rounded);
            obesidadLayout.setPadding(16, 16, 16, 16);
        }


        pieChartIMC.setTouchEnabled(false);

        // Configurar animación de entrada
        pieChartIMC.animateY(850, Easing.EasingOption.EaseInOutQuad); // Duración de la animación y tipo de interpolación
        pieChartIMC.invalidate(); // Refresca el gráfico

        return view;
    }
}