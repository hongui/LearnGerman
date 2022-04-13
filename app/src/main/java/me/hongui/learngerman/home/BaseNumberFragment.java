package me.hongui.learngerman.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import me.hongui.learngerman.R;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.utils.VolumeManager;
import me.hongui.learngerman.viewmodel.QuestionViewModel;

public class BaseNumberFragment extends AppFragment {
    private TextView mTitle;
    private TextView mExpain;
    private TextView mBackup;
    private ImageView mVolume;
    private VolumeManager volumeManager;

    @Override
    public int layout() {
        return R.layout.fragment_base_number;
    }

    @Override
    public void setting(Bundle saveInstance) {
        mTitle = getView().findViewById(R.id.tv_base_number_title);
        mExpain = getView().findViewById(R.id.tv_base_number_explain);
        mBackup = getView().findViewById(R.id.tv_base_number_backup);
        mVolume = getView().findViewById(R.id.iv_base_number_play);

        volumeManager = new VolumeManager(3);
    }

    @Override
    public void events(Bundle saveInstance) {
        final QuestionViewModel viewModel = viewModel(QuestionViewModel.class);
        viewModel.getData().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                mTitle.setText(question.getTranslate());
                mExpain.setText(question.getContent());
                mBackup.setText(question.getBackup());
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
