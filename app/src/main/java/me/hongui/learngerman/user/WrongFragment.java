package me.hongui.learngerman.user;

import android.os.Bundle;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.hongui.learngerman.R;
import me.hongui.learngerman.adapter.WrongAdapter;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Wrong;
import me.hongui.learngerman.viewmodel.ChartViewModel;

public class WrongFragment extends AppFragment {

    @Override
    public int layout() {
        return R.layout.fragment_wrong;
    }

    @Override
    public void setting(Bundle saveInstance) {
        setTitle(getString(R.string.wrong_rate), true);

        final WrongAdapter adapter = new WrongAdapter();
        RecyclerView content = getView().findViewById(R.id.wrong_content);
        content.setLayoutManager(new LinearLayoutManager(mParent));
        content.setAdapter(adapter);

        ChartViewModel viewModel = viewModel(ChartViewModel.class);
        viewModel.getWrongs().observe(this, new Observer<List<Wrong>>() {
            @Override
            public void onChanged(List<Wrong> wrongs) {
                adapter.addNewDatas(wrongs);
            }
        });

        viewModel.wrong(mParent);
    }
}
