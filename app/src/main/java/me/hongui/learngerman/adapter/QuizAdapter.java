package me.hongui.learngerman.adapter;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.hongui.learngerman.R;
import me.hongui.learngerman.bean.Quiz;
import me.hongui.learngerman.quiz.QuestionFragment;
import me.hongui.learngerman.utils.CategoryHelper;

public class QuizAdapter extends Adapter<Quiz> {
    private boolean collapse=true;
    private List<Quiz> sublist;
    public QuizAdapter(){
        super();
        addData(new Quiz(R.string.quiz_primary, R.string.quiz_primary_summary, R.drawable.ic_library_books_black_24dp));
        addData(new Quiz(R.string.quiz_advance, R.string.quiz_advance_summary, R.drawable.ic_rate_review_black_24dp, CategoryHelper.ADVANCE));

        sublist = new ArrayList<>();
        sublist.add(new Quiz(R.string.quiz_number_input, R.string.quiz_primary_summary, R.drawable.ic_library_books_black_24dp,CategoryHelper.NUMBER_INPUT));
        sublist.add(new Quiz(R.string.quiz_watch_input, R.string.quiz_primary_summary, R.drawable.ic_library_books_black_24dp,CategoryHelper.WATCH_INPUT));
        sublist.add(new Quiz(R.string.quiz_image_option, R.string.quiz_primary_summary, R.drawable.ic_library_books_black_24dp,CategoryHelper.IMAGE_OPTION));
        sublist.add(new Quiz(R.string.quiz_volume_input, R.string.quiz_primary_summary, R.drawable.ic_library_books_black_24dp,CategoryHelper.VOICE_INPUT));
        sublist.add(new Quiz(R.string.quiz_volume_option, R.string.quiz_primary_summary, R.drawable.ic_library_books_black_24dp,CategoryHelper.VOICE_OPTION));
        sublist.add(new Quiz(R.string.quiz_random, R.string.quiz_primary_summary, R.drawable.ic_library_books_black_24dp,CategoryHelper.PRIMARY_RANDOM));
        for(Quiz q:sublist){
            q.setCollapse(true);
        }
        setListener(new OnItemClickListener<Quiz>() {
            @Override
            public void onClick(View view, Quiz t, int position) {
                if(0==position) {
                    if (collapse) {
                        addDatas(1, sublist);
                    } else {
                        remove(1, sublist.size());
                    }
                    collapse = !collapse;
                }else {
                    boolean random=1<t.getCategoirs().length;
                    int limit=random?20:0;
                    QuestionFragment.nav(view.getContext(),limit,random, t.getCategoirs());
                }
            }
        });
    }

    @Override
    int layout() {
        return R.layout.item_quiz;
    }

    @Override
    void convert(ViewHolder holder, Quiz data, int position) {
        holder.setText(R.id.tv_item_quiz_title, data.getTitle());
        holder.setText(R.id.tv_item_quiz_sumary, data.getSummary());
        holder.setImage(R.id.iv_item_quiz_icon, data.getResource());
        View view = holder.itemView;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        float dimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, holder.itemView.getResources().getDisplayMetrics());
        if(data.isCollapse()){
            layoutParams.leftMargin=(int)(dimension*2);
        }else {
            layoutParams.leftMargin=(int)dimension;
        }
        view.setLayoutParams(layoutParams);
    }
}
