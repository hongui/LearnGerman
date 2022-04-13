package me.hongui.learngerman.user;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import me.hongui.learngerman.R;
import me.hongui.learngerman.UniversalActivity;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Record;
import me.hongui.learngerman.viewmodel.ChartViewModel;

public class PieFragment extends AppFragment {

    @Override
    public int layout() {
        return R.layout.fragment_pie;
    }

    @Override
    public void setting(Bundle saveInstance) {
        setTitle(getString(R.string.pie_title), true);
    }

    @Override
    public void events(Bundle saveInstance) {
        final PieChart pieChart = getView().findViewById(R.id.pc_pie);
        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setRotationAngle(0);
        // enable rotation of the pieChart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);

        ChartViewModel viewModel = viewModel(ChartViewModel.class);
        viewModel.getRecords().observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                List<PieEntry> entries = new ArrayList<>();
                int rightCount=0;
                int total=records.size();
                for (Record record : records) {
                    if(record.isCorrect()){
                        rightCount++;
                    }
                }
                PieEntry right = new PieEntry(rightCount * 1.0F / total,getString(R.string.pie_right));
                PieEntry wrong = new PieEntry(1-(rightCount*1.0f/total),getString(R.string.pie_wrong));
                entries.add(right);
                entries.add(wrong);
                PieDataSet set = new PieDataSet(entries, getString(R.string.pie_title));
                set.setColors(ContextCompat.getColor(mParent,R.color.colorPrimary),ContextCompat.getColor(mParent,R.color.colorAccent));
                set.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                set.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return String.format("%.02f",value*100)+"%";
                    }
                });
                PieData data = new PieData(set);
                pieChart.setData(data);
                pieChart.invalidate();
            }
        });
        int year = getArguments().getInt("year", 0);
        int month = getArguments().getInt("month", 0);
        int day = getArguments().getInt("day", 0);
        long duration = getArguments().getLong("duration", 0);
        viewModel.load(mParent,year,month,day,duration);
    }

    public static void nav(AppCompatActivity context, int year, int month, int day, long duration){
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("day", day);
        bundle.putLong("duration",duration);
        UniversalActivity.newActivity(context, PieFragment.class,bundle);
    }
}
