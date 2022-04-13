package me.hongui.learngerman.adapter;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import me.hongui.learngerman.R;
import me.hongui.learngerman.bean.Category;
import me.hongui.learngerman.bean.Correct;

public class CategoryAdapter extends Adapter<Correct> {
    @Override
    int layout() {
        return R.layout.item_category_pie;
    }

    @Override
    void convert(ViewHolder holder, Correct data, int position) {
        PieChart pieChart = holder.view(R.id.pc_item_category);

        pieChart.setCenterText(Category.text(data.category));
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(8f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);

        PieEntry right = new PieEntry(data.correctRate(), holder.string(R.string.pie_right));
        PieEntry wrong = new PieEntry(data.errorRate(),holder.string(R.string.pie_wrong));
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(right);
        pieEntries.add(wrong);
        PieDataSet pieSet = new PieDataSet(pieEntries, holder.string(R.string.category_pie));
        pieSet.setColors(holder.color(R.color.colorPrimary), holder.color(R.color.colorAccent));
        pieSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData pieData = new PieData(pieSet);
        pieData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.02f",value*100)+"%";
            }
        });
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
