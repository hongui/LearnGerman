package me.hongui.learngerman.adapter;

import me.hongui.learngerman.R;
import me.hongui.learngerman.bean.Category;
import me.hongui.learngerman.bean.Wrong;

public class WrongAdapter extends Adapter<Wrong> {
    @Override
    int layout() {
        return R.layout.item_wrong;
    }

    @Override
    void convert(ViewHolder holder, Wrong data, int position) {
        holder.setText(R.id.tv_item_wrong_title, Category.text(data.category));
        holder.setText(R.id.tv_item_wrong_count, holder.string(R.string.wrong_count, data.total, data.wrong));
        holder.setText(R.id.tv_item_wrong_rate,String.format("%.1f",100*data.rate())+"%");
    }
}
