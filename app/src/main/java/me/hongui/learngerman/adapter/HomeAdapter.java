package me.hongui.learngerman.adapter;

import me.hongui.learngerman.R;
import me.hongui.learngerman.bean.Course;

public class HomeAdapter extends Adapter<Course> {
    @Override
    int layout() {
        return R.layout.item_home;
    }

    @Override
    void convert(ViewHolder holder, Course data, int position) {
        holder.setImage(R.id.iv_item_home_icon,data.getImg());
        holder.setText(R.id.tv_item_home_name, data.getName());
        holder.setText(R.id.tv_item_home_summary, data.getSummary());
    }
}
