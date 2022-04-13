package me.hongui.learngerman.quiz;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.hongui.learngerman.R;
import me.hongui.learngerman.adapter.OptionAdapter;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Answer;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.viewmodel.QuestionViewModel;

public class BaseImageFragment extends AppFragment {

    @Override
    public int layout() {
        return R.layout.fragment_base_image;
    }

    @Override
    public void setting(Bundle saveInstance) {
        final ImageView ivQuestion = getView().findViewById(R.id.iv_base_image_image);
        RecyclerView answers=getView().findViewById(R.id.rv_base_image_options);
        final OptionAdapter adapter=new OptionAdapter();

        answers.setLayoutManager(new LinearLayoutManager(mParent));
        answers.setAdapter(adapter);

        final QuestionViewModel viewModel = viewModel(QuestionViewModel.class);
        viewModel.getData().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                Glide.with(BaseImageFragment.this)
                        .load(question.getDrawable(mParent))
                        .into(ivQuestion);
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
                boolean right = viewModel.checkAnswer(answer);
                return right;
            }
        });
    }
}
