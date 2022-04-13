package me.hongui.learngerman.user;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.Calendar;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import me.hongui.learngerman.R;
import me.hongui.learngerman.UniversalActivity;
import me.hongui.learngerman.adapter.Adapter;
import me.hongui.learngerman.adapter.MeAdapter;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Record;
import me.hongui.learngerman.quiz.QuestionFragment;
import me.hongui.learngerman.viewmodel.MeViewModel;

public class MeFragment extends AppFragment {
    @Override
    public int layout() {
        return R.layout.fragment_me;
    }

    @Override
    public void setting(Bundle saveInstance) {
        setTitle("", false);
        FlexboxLayoutManager manager = new FlexboxLayoutManager(getContext());
        manager.setFlexWrap(FlexWrap.WRAP);
        RecyclerView content = getView().findViewById(R.id.rv_me_records);
        content.setLayoutManager(manager);
        MeAdapter adapter = new MeAdapter();
        content.setAdapter(adapter);
    }

    @Override
    public void events(Bundle saveInstance) {
        final MeViewModel viewModel = viewModel(MeViewModel.class);
        TextView progress = getView().findViewById(R.id.tv_me_progress);
        TextView pie = getView().findViewById(R.id.tv_me_pie);
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UniversalActivity.newActivity(mParent, CourseFragment.class);
            }
        });
        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UniversalActivity.newActivity(mParent, WrongFragment.class);
            }
        });
        RecyclerView content = getView().findViewById(R.id.rv_me_records);
        final MeAdapter adapter = (MeAdapter) content.getAdapter();
        viewModel.getDisplayList().observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                adapter.update(records);
            }
        });
        viewModel.getMonth().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                adapter.setTitle(s);
            }
        });
        adapter.setStateListener(new MeAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(View v, boolean isNext) {
                if (isNext) {
                    boolean nextMonth = viewModel.nextMonth();
                    adapter.canNext(nextMonth);
                } else {
                    boolean prevMonth = viewModel.prevMonth();
                    adapter.canPrev(prevMonth);
                    if (prevMonth) {
                        adapter.canNext(true);
                    }
                }
            }
        });
        adapter.setListener(new Adapter.OnItemClickListener<Record>() {
            @Override
            public void onClick(View view, Record t, int position) {
                if (t.isJoin()) {
                    Calendar calendar = viewModel.current();
                    calendar.set(Calendar.DATE, t.getDay());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int date = calendar.get(Calendar.DATE);
                    long duration = viewModel.oneDay();
                    PieFragment.nav(mParent, year, month, date, duration);
                }
            }
        });
        final TextView days = getView().findViewById(R.id.tv_me_count);
        viewModel.getDays().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                String day = aLong.toString();
                String origin = getString(R.string.start_days, day);
                SpannableStringBuilder sp = new SpannableStringBuilder(origin);
                int start = origin.indexOf(day);
                int end = start + day.length();
                sp.setSpan(new RelativeSizeSpan(2.1F), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mParent, R.color.colorAccent)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                days.setText(sp);
            }
        });
        viewModel.load(mParent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_me,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.menu_bookmark==item.getItemId()){
            QuestionFragment.bookmark(mParent);
        }else if(R.id.menu_chart==item.getItemId()){
            UniversalActivity.newActivity(mParent,ChartFragment.class);
        }
        return super.onOptionsItemSelected(item);
    }
}
