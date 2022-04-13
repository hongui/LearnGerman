package me.hongui.learngerman.home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import me.hongui.learngerman.R;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.viewmodel.QuestionViewModel;

public class ExpressNumberFragment extends AppFragment {

    @Override
    public int layout() {
        return R.layout.fragment_express_number;
    }

    @Override
    public void setting(Bundle saveInstance) {
        final TextView title = getView().findViewById(R.id.tv_express_title);
        final TextView explain = getView().findViewById(R.id.tv_express_explain);
        final TextView backup = getView().findViewById(R.id.tv_express_backup);

        final QuestionViewModel viewModel = viewModel(QuestionViewModel.class);
        viewModel.getData().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                setTitle(viewModel.category().name,true);
                title.setText(question.getTranslate());
                explain.setText(question.getContent());
                backup.setText(question.getBackup());
            }
        });

        viewModel.setActionListener(new QuestionViewModel.OnNextActionListener() {
            @Override
            public boolean onAction() {
                viewModel.report("",true);
                return true;
            }
        });

        viewModel.getCanNext().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean) {
                    viewModel.report("", true);
                }
            }
        });
    }
}
