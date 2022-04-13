package me.hongui.learngerman.user;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import me.hongui.learngerman.R;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Correct;
import me.hongui.learngerman.utils.DateUtil;
import me.hongui.learngerman.viewmodel.ChartViewModel;

public class ChartFragment extends AppFragment {

    @Override
    public int layout() {
        return R.layout.fragment_chart;
    }

    @Override
    public void setting(Bundle saveInstance) {
        setTitle(getString(R.string.chart_for_every_3), true);
    }

    @Override
    public void events(Bundle saveInstance) {
        final LineChart lineChart = getView().findViewById(R.id.lc_chart_chart);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        ChartViewModel viewModel = viewModel(ChartViewModel.class);
        viewModel.getCorrects().observe(this, new Observer<List<Correct>>() {
            @Override
            public void onChanged(List<Correct> corrects) {
                if (null == corrects ) {
                    return;
                }
                int primary=ContextCompat.getColor(mParent, R.color.colorPrimary);
                int accent=ContextCompat.getColor(mParent, R.color.colorAccent);
                XAxis xAxis;
                {
                    xAxis = lineChart.getXAxis();
                    xAxis.setLabelRotationAngle(45);
                    xAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            return DateUtil.formatDate((long)value);
                        }
                    });
                }

                YAxis yAxis;
                {
                    yAxis = lineChart.getAxisLeft();
                    yAxis.setAxisMinimum(0f);
                    yAxis.setAxisMaximum(100f);
                }
                int size = corrects.size();
                List<Entry> entries = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    Correct correct = corrects.get(i);
                    entries.add(new Entry(correct.date, correct.correctPercent()));
                }
                LineDataSet lineDataSet = new LineDataSet(entries, "fuck");
                lineDataSet.setColor(accent);
                lineDataSet.setLineWidth(10f);
                lineDataSet.setCircleRadius(5f);
                lineDataSet.setCircleHoleRadius(2.5f);
                lineDataSet.setCircleColor(accent);
                lineDataSet.setHighLightColor(accent);
                List<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(lineDataSet);
                lineChart.setData(new LineData(dataSets));
                lineChart.invalidate();
            }
        });
        viewModel.correct(mParent);
    }
}
