package me.hongui.learngerman.adapter;

import me.hongui.learngerman.R;
import me.hongui.learngerman.bean.Category;
import me.hongui.learngerman.bean.CourseProgress;
import me.hongui.learngerman.view.TextProgressBar;

public class CourseAdapter extends Adapter<CourseProgress> {
    @Override
    int layout() {
        return R.layout.item_course;
    }

    @Override
    void convert(final ViewHolder holder, CourseProgress data, int position) {
        holder.setText(R.id.tv_item_course_title,Category.text(data.category));
        TextProgressBar bar=holder.view(R.id.sb_item_course_progress);
        bar.setProgress(data.finishRate());
    }
}
