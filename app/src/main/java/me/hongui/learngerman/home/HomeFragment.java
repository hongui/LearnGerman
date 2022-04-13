package me.hongui.learngerman.home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import me.hongui.learngerman.R;
import me.hongui.learngerman.adapter.Adapter;
import me.hongui.learngerman.adapter.HomeAdapter;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Category;
import me.hongui.learngerman.bean.Course;
import me.hongui.learngerman.quiz.QuestionFragment;
import me.hongui.learngerman.viewmodel.HomeViewModel;

public class HomeFragment extends AppFragment {
    private HomeAdapter mAdapter;

    @Override
    public int layout() {
        return R.layout.fragment_home;
    }

    @Override
    public void setting(Bundle saveInstance) {
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        mAdapter = new HomeAdapter();
        RecyclerView content = getView().findViewById(R.id.rv_home_content);
        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(content);
        content.setLayoutManager(new LinearLayoutManager(mParent, LinearLayout.HORIZONTAL, false));
        content.setAdapter(mAdapter);
    }

    @Override
    public void events(Bundle saveInstance) {
        HomeViewModel viewModel = viewModel(HomeViewModel.class);
        viewModel.getCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                mAdapter.addDatas(courses);
            }
        });
        viewModel.load(mParent);

        mAdapter.setChildListener(R.id.btn_item_home_study,new Adapter.OnItemClickListener<Course>() {
            @Override
            public void onClick(View view, Course t, int position) {
                switch (position) {
                    case 0: {
                        QuestionFragment.nav(getContext(),0,false, Category.NUMBER.category);
                        break;
                    }

                    case 1: {
                        QuestionFragment.nav(getContext(), 0,false,Category.NUMBER_EXPRESS.category);
                        break;
                    }

                    case 2: {
                        QuestionFragment.nav(getContext(), 0,false,Category.ORDER.category);
                        break;
                    }

                    case 3: {
                        QuestionFragment.nav(getContext(), 0,false,Category.DATE_EXPRESS.category,Category.TIME_EXPRESS.category,Category.PRICE_EXPRESS.category);
                        break;
                    }
                }
            }
        });
    }
}
