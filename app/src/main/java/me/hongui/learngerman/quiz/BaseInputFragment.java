package me.hongui.learngerman.quiz;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayoutManager;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import me.hongui.learngerman.R;
import me.hongui.learngerman.adapter.SplitAdapter;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.viewmodel.QuestionViewModel;

public class BaseInputFragment extends AppFragment {
    @Override
    public int layout() {
        return R.layout.fragment_base_input;
    }

    @Override
    public void setting(Bundle saveInstance) {
        final SplitAdapter adapter = new SplitAdapter();
        final TextView content = getView().findViewById(R.id.tv_base_input_content);
        final TextView result = getView().findViewById(R.id.tv_base_input_result);
        final ImageView clear = getView().findViewById(R.id.iv_base_input_clear);
        RecyclerView recyclerView = getView().findViewById(R.id.rv_base_input_items);

        recyclerView.setLayoutManager(new FlexboxLayoutManager(mParent));
        recyclerView.setAdapter(adapter);

        final QuestionViewModel viewModel = viewModel(QuestionViewModel.class);
        viewModel.getData().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                content.setText(question.getContent());
                adapter.addSource(question.getTranslate());
            }
        });

        viewModel.setActionListener(new QuestionViewModel.OnNextActionListener() {
            @Override
            public boolean onAction() {
                String input = adapter.getInput();
                boolean right=viewModel.checkAnswer(input);
                if (right) {
                    return true;
                }else {
                    String tip=getString(R.string.input_result_error, adapter.getOrigin());
                    snackbar(tip);
                }
                return false;
            }
        });

        adapter.setListener(new SplitAdapter.OnItemPressListener() {
            @Override
            public void onItemPressed(String total, char c, int pos) {
                result.setText(total);
                if (TextUtils.isEmpty(total)) {
                    clear.setVisibility(View.INVISIBLE);
                } else {
                    clear.setVisibility(View.VISIBLE);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.remove();
            }
        });
    }
}
