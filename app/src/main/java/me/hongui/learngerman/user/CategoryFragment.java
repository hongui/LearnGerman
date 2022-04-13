package me.hongui.learngerman.user;

import android.os.Bundle;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.hongui.learngerman.R;
import me.hongui.learngerman.adapter.CategoryAdapter;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Correct;
import me.hongui.learngerman.viewmodel.CategoryViewModel;

public class CategoryFragment extends AppFragment {

    @Override
    public int layout() {
        return R.layout.fragment_category;
    }

    @Override
    public void setting(Bundle saveInstance) {
        setTitle(getString(R.string.category_pie),true);
    }

    @Override
    public void events(Bundle saveInstance) {
        final CategoryAdapter adapter = new CategoryAdapter();
        RecyclerView content = getView().findViewById(R.id.category_content);
        content.setLayoutManager(new LinearLayoutManager(mParent));
        content.setAdapter(adapter);

        CategoryViewModel viewModel = viewModel(CategoryViewModel.class);
        viewModel.getRecords().observe(this, new Observer<List<Correct>>() {
            @Override
            public void onChanged(List<Correct> corrects) {
                adapter.addDatas(corrects);
            }
        });
        viewModel.load(mParent);
    }
}
