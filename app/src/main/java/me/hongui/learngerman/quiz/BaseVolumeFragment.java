package me.hongui.learngerman.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import me.hongui.learngerman.R;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.utils.VolumeManager;
import me.hongui.learngerman.viewmodel.QuestionViewModel;

public class BaseVolumeFragment extends AppFragment {
    private ImageView mVolume;
    private EditText mInput;
    private VolumeManager volumeManager;
    private int mCount = 5;

    @Override
    public int layout() {
        return R.layout.fragment_base_volume;
    }

    @Override
    public void setting(Bundle saveInstance) {
        mVolume = getView().findViewById(R.id.iv_base_volume_volume);
        mInput = getView().findViewById(R.id.et_base_volume_input);

        volumeManager = new VolumeManager(3);
    }


    @Override
    public void events(Bundle saveInstance) {
        final QuestionViewModel viewModel = viewModel(QuestionViewModel.class);
        viewModel.getData().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                volumeManager.prepare(getContext(), question.getResourceName());
            }
        });

        mVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volumeManager.play(mVolume);
            }
        });

        viewModel.setActionListener(new QuestionViewModel.OnNextActionListener() {
            @Override
            public boolean onAction() {
                String input = mInput.getText().toString();
                if (viewModel.checkAnswer(input)) {
                    mInput.setText("");
                    mCount = 5;
                    return true;
                } else {
                    String tip;
                    if (0 == mCount) {
                        String answer = viewModel.answer();
                        tip = getString(R.string.input_result_error, answer);
                    } else {
                        mCount--;
                        tip = getString(R.string.volume_result_error);
                    }
                    snackbar(tip);
                }
                return false;
            }
        });
    }
}
