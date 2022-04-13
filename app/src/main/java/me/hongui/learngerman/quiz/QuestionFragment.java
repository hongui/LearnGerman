package me.hongui.learngerman.quiz;

import android.content.Context;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import me.hongui.learngerman.CompleteDialog;
import me.hongui.learngerman.R;
import me.hongui.learngerman.UniversalActivity;
import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.BookMark;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.utils.QuestionFactory;
import me.hongui.learngerman.viewmodel.QuestionViewModel;

public class QuestionFragment extends AppFragment {
    public static final String CATEGORY = "category";
    public static final String RANDOM = "random";
    public static final String LIMIT = "limit";
    public static final String BOOKMARK = "bookmark";

    private TextView mPrev;
    private TextView mNext;
    private MenuItem mBookMark;
    private CompleteDialog dialog;

    @Override
    public int layout() {
        return R.layout.fragment_question;
    }

    @Override
    public void setting(Bundle saveInstance) {
        mPrev = getView().findViewById(R.id.btn_question_prev);
        mNext = getView().findViewById(R.id.btn_question_next);
    }

    @Override
    public void events(Bundle saveInstance) {
        final QuestionViewModel viewModel = viewModel(QuestionViewModel.class);
        viewModel.getData().observe(this, new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                if (null == question) {
                    return;
                }
                Class<? extends AppFragment> aClass = QuestionFactory.create(question);
                fragment(aClass.getName(), null, R.id.fl_question_containt, true, false);
                setTitle(viewModel.category().name + getString(R.string.counter, viewModel.getCurrent(), viewModel.limit()), true);
            }
        });

        viewModel.getBookMark().observe(this, new Observer<BookMark>() {
            @Override
            public void onChanged(BookMark bookMark) {
                if (null == mBookMark) {
                    return;
                }
                int resource = 0;
                if (null == bookMark) {
                    resource = R.drawable.ic_bookmark_border_black_24dp;
                } else {
                    resource = R.drawable.ic_bookmark_black_24dp;
                }
                mBookMark.setIcon(resource);
            }
        });
        viewModel.getCanPrev().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mPrev.setEnabled(aBoolean);
            }
        });

        viewModel.getCanNext().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mNext.setEnabled(aBoolean);
                if (!aBoolean) {
                    mNext.setEnabled(!aBoolean);
                    mNext.setText(R.string.complete);
                } else {
                    mNext.setText(R.string.next_question);
                }
            }
        });

        viewModel.getRound().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                String string = getString(R.string.tip_round, aLong.toString());
                snackbar(string);
            }
        });
        mPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.prev();
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionViewModel.OnNextActionListener actionListener = viewModel.getActionListener();
                if (null != actionListener) {
                    boolean result = actionListener.onAction();
                    if (!viewModel.getCanNext().getValue() && result) {
                        if (null == dialog) {
                            dialog = new CompleteDialog();
                            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFragment);
                        }
                        dialog.show(getFragmentManager(), "dialog");
                    } else if (viewModel.getCanNext().getValue()&&result) {
                        viewModel.next();
                        TransitionManager.beginDelayedTransition((ViewGroup) getView(), new Explode());
                    }
                }
            }
        });

        boolean bookmark = getArguments().getBoolean(BOOKMARK, false);
        if (!bookmark) {
            int[] category = getArguments().getIntArray(CATEGORY);
            int limit = getArguments().getInt(LIMIT, -1);
            boolean random = getArguments().getBoolean(RANDOM, false);
            viewModel.setGenerator(limit, random, category);
        }
        viewModel.init(getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.bookmark, menu);
        mBookMark = menu.findItem(R.id.menu_bookmark);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.menu_bookmark == item.getItemId()) {
            final QuestionViewModel viewModel = viewModel(QuestionViewModel.class);
            viewModel.bookMark();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void nav(Context context, int limit, boolean random, int... category) {
        Bundle data = new Bundle();
        data.putBoolean(RANDOM, random);
        data.putInt(LIMIT, limit);
        data.putIntArray(CATEGORY, category);
        UniversalActivity.newActivity((AppCompatActivity)context, QuestionFragment.class, data);
    }

    public static void bookmark(Context context) {
        Bundle data = new Bundle();
        data.putBoolean(BOOKMARK, true);
        UniversalActivity.newActivity((AppCompatActivity)context, QuestionFragment.class, data);
    }
}
