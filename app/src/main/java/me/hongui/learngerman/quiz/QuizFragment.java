package me.hongui.learngerman.quiz;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.hongui.learngerman.R;
import me.hongui.learngerman.adapter.QuizAdapter;
import me.hongui.learngerman.app.AppFragment;

public class QuizFragment extends AppFragment {
    private QuizAdapter mAdapter;

    @Override
    public int layout() {
        return R.layout.fragment_quiz;
    }

    @Override
    public void setting(Bundle saveInstance) {
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.quiz);
        RecyclerView content = getView().findViewById(R.id.rv_quiz_content);
        content.setLayoutManager(new LinearLayoutManager(mParent));
        mAdapter = new QuizAdapter();
        content.setAdapter(mAdapter);
    }
}
