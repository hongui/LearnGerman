package me.hongui.learngerman.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarManager {
    private Snackbar snackbar;

    public void show(Context context, View view, CharSequence charSequence) {
        cancel();
        SpannableString span = new SpannableString(charSequence);
        span.setSpan(new ForegroundColorSpan(Color.WHITE), 0, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        snackbar = Snackbar.make(view, span, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void show(Context context, View view, int res) {
        show(context, view, context.getString(res));
    }

    public void cancel() {
        if (null != snackbar) {
            snackbar.dismiss();
            snackbar = null;
        }
    }

    private SnackbarManager() {
    }

    public static SnackbarManager INSTANCE = new SnackbarManager();
}
