package me.hongui.learngerman.quiz;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxLayoutManager;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import me.hongui.learngerman.R;
import me.hongui.learngerman.adapter.SplitAdapter;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.viewmodel.QuestionViewModel;

public class BaseOptionFragment extends AppFragment {
    private TextView mContent;
    private TextView mTranslate;
    private TextView mResult;
    private ImageView mClear;
    private SplitAdapter mAdapter;

    @Override
    public int layout() {
        return R.layout.fragment_base_option;
    }

    @Override
    public void setting(Bundle saveInstance) {
        mContent = getView().findViewById(R.id.tv_base_option_title);
        mTranslate = getView().findViewById(R.id.tv_base_option_explain);
        mResult = getView().findViewById(R.id.tv_base_option_result);
        mClear = getView().findViewById(R.id.iv_base_option_clear);
        RecyclerView content = getView().findViewById(R.id.rv_base_option_content);
        FlexboxLayoutManager manager = new FlexboxLayoutManager(mParent);
        manager.setAlignItems(AlignItems.CENTER);
        content.setLayoutManager(manager);
        content.setAdapter(mAdapter = new SplitAdapter());
    }

    @Override
    public void events(Bundle saveInstance) {
        final QuestionViewModel viewModel = viewModel(QuestionViewModel.class);
        viewModel.getData().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                mContent.setText(question.getSepContent());
                mTranslate.setText(question.getTranslate());
                mAdapter.addSource(question.getSep());
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.remove();
            }
        });
        mAdapter.setListener(new SplitAdapter.OnItemPressListener() {
            @Override
            public void onItemPressed(String total, char c, int pos) {
                mResult.setText(total);
                if (TextUtils.isEmpty(total)) {
                    mClear.setVisibility(View.INVISIBLE);
                } else {
                    mClear.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.setActionListener(new QuestionViewModel.OnNextActionListener() {
            @Override
            public boolean onAction() {
                String input = mAdapter.getInput();
                boolean right = viewModel.checkAnswer(input);
                if (right) {
                    return true;
                } else {
                    String tip = getString(R.string.input_result_error, mAdapter.getOrigin());
                    snackbar(tip);
                }
                return false;
            }
        });
    }
}
