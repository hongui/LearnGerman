package me.hongui.learngerman.user;

import android.os.Bundle;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.hongui.learngerman.R;
import me.hongui.learngerman.adapter.CourseAdapter;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.CourseProgress;
import me.hongui.learngerman.viewmodel.CourseViewModel;

public class CourseFragment extends AppFragment {

    @Override
    public int layout() {
        return R.layout.fragment_course;
    }

    @Override
    public void setting(Bundle saveInstance) {
        setTitle(getString(R.string.course_progress), true);
    }

    @Override
    public void events(Bundle saveInstance) {
        final CourseAdapter adapter = new CourseAdapter();
        RecyclerView recyclerView = getView().findViewById(R.id.course_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(mParent));
        recyclerView.setAdapter(adapter);
        CourseViewModel viewModel = viewModel(CourseViewModel.class);
        viewModel.getCourses().observe(this, new Observer<List<CourseProgress>>() {
            @Override
            public void onChanged(List<CourseProgress> courseProgresses) {
                adapter.addDatas(courseProgresses);
            }
        });
        viewModel.load(mParent);
    }
}
