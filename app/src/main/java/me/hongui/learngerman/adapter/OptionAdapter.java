package me.hongui.learngerman.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.TextView;

import java.util.List;

import me.hongui.learngerman.R;
import me.hongui.learngerman.bean.Answer;

public class OptionAdapter extends Adapter<Answer>{
    private int selected = -1;
    private int rightPosition = -1;
    private Animator animator;

    @Override
    int layout() {
        return R.layout.item_option;
    }

    @Override
    void convert(ViewHolder holder, Answer data, final int pos) {
        TextView content = holder.view(R.id.tv_item_option);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int old = selected;
                selected = pos;

                notifyItemChanged(old);
                notifyItemChanged(pos);

                if(-1<rightPosition) {
                    old = rightPosition;
                    rightPosition = -1;
                    notifyItemChanged(old);
                }
            }
        });
        content.setSelected(selected == pos);
        content.setText(data.getContent());

        if (pos == rightPosition) {
            content.setBackgroundResource(R.drawable.shape_right);
            content.setSelected(true);
            return;
        } else if (content.isSelected() && rightPosition>=0) {
            animator(content);
        }
        content.setBackgroundResource(R.drawable.selector_option);
    }


    @Override
    public void addNewDatas(List<Answer> datas) {
        super.addNewDatas(datas);
        rightPosition=-1;
        selected=-1;
    }

    public void setRightAnswer(Answer answer) {
        List<Answer> answers = getDatas();
        for (int i = 0; i < answers.size(); i++) {
            if (answer == answers.get(i)) {
                rightPosition = i;
                notifyItemChanged(selected);
                break;
            }
        }
    }

    public Answer getSelectAnswer() {
        if (0 > selected) {
            return null;
        }
        return getDatas().get(selected);
    }

    private void animator(final View view) {
        if (null != animator) {
            return;
        }
        final float originX = view.getTranslationX();
        ObjectAnimator animatorX = new ObjectAnimator();
        animatorX.setTarget(view);
        animatorX.setDuration(500);
        animatorX.setPropertyName("translationX");
        animatorX.setFloatValues(view.getTranslationX() - 40, view.getTranslationX() + 40);
        animatorX.setInterpolator(new CycleInterpolator(7F));
        animatorX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animation.removeAllListeners();
                view.setTranslationX(originX);
                notifyItemChanged(rightPosition);
                OptionAdapter.this.animator = null;
            }
        });
        animatorX.start();
        this.animator = animatorX;
    }
}
