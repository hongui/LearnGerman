package me.hongui.learngerman.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.hongui.learngerman.R;
import me.hongui.learngerman.adapter.OptionAdapter;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Answer;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.utils.VolumeManager;
import me.hongui.learngerman.viewmodel.QuestionViewModel;

public class VolumeOptionFragment extends AppFragment {
    private VolumeManager mManager;
    private ImageView mPlayer;

    @Override
    public int layout() {
        return R.layout.fragment_volume_option;
    }

    @Override
    public void setting(Bundle saveInstance) {
        mManager = new VolumeManager(3);
        final OptionAdapter adapter = new OptionAdapter();

        mPlayer = getView().findViewById(R.id.iv_volume_option_image);
        RecyclerView option = getView().findViewById(R.id.rv_volume_option_options);
        option.setLayoutManager(new LinearLayoutManager(mParent));
        option.setAdapter(adapter);

        final QuestionViewModel viewModel = viewModel(QuestionViewModel.class);
        viewModel.getData().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                mManager.prepare(mParent, question.getResourceName());
            }
        });

        viewModel.getAnswers().observe(this, new Observer<List<Answer>>() {
            @Override
            public void onChanged(List<Answer> answers) {
                adapter.addNewDatas(answers);
            }
        });

        viewModel.getRightAnswer().observe(this, new Observer<Answer>() {
            @Override
            public void onChanged(Answer answer) {
                adapter.setRightAnswer(answer);
            }
        });
        viewModel.setActionListener(new QuestionViewModel.OnNextActionListener() {
            @Override
            public boolean onAction() {
                Answer answer = adapter.getSelectAnswer();
                if (null == answer) {
                    snackbar(R.string.empty_option_error);
                } else {
                    return viewModel.checkAnswer(answer);
                }
                return false;
            }
        });
    }

    @Override
    public void events(Bundle saveInstance) {
        mPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.play(mPlayer);
            }
        });
    }
}
