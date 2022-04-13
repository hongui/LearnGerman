package me.hongui.learngerman.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import me.hongui.learngerman.R;
import me.hongui.learngerman.app.AppViewModel;
import me.hongui.learngerman.bean.Course;

public class HomeViewModel extends AppViewModel {
    private MutableLiveData<List<Course>> courses;

    public HomeViewModel() {
        this.courses = new MutableLiveData<>();
    }

    public void load(Context context){
        List<Course> courses = new ArrayList<>();
        Course first=new Course();
        first.setColor(R.color.ligh_blue);
        first.setImg(R.drawable.ic_step_first);
        first.setName(context.getString(R.string.course_step_first));
        first.setSummary(context.getString(R.string.course_step_first_summary));
        courses.add(first);

        Course second=new Course();
        second.setColor(R.color.blue);
        second.setImg(R.drawable.ic_step_second);
        second.setName(context.getString(R.string.course_step_second));
        second.setSummary(context.getString(R.string.course_step_second_summary));
        courses.add(second);

        Course third=new Course();
        third.setColor(R.color.cyan);
        third.setImg(R.drawable.ic_step_third);
        third.setName(context.getString(R.string.course_step_third));
        third.setSummary(context.getString(R.string.course_step_third_summary));
        courses.add(third);

        Course fourth=new Course();
        fourth.setColor(R.color.teal);
        fourth.setImg(R.drawable.ic_step_fourth);
        fourth.setName(context.getString(R.string.course_step_fourth));
        fourth.setSummary(context.getString(R.string.course_step_fourth_summary));
        courses.add(fourth);

        getCourses().setValue(courses);
    }
    public MutableLiveData<List<Course>> getCourses() {
        return courses;
    }
}
