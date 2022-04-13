package me.hongui.learngerman.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setText(@IdRes int id, @StringRes int str){
        setText(id,itemView.getResources().getString(str));
    }

    public void setText(@IdRes int id,CharSequence str){
        TextView textView = itemView.findViewById(id);
        if(null!=textView) {
            textView.setText(str);
        }
    }

    public void setImage(@IdRes int id, @DrawableRes int drawable) {
        Glide.with(itemView.getContext())
                .load(drawable)
                .into((ImageView)view(id));
    }

    public void setBackgroundResource(@IdRes int id,@DrawableRes int drawable){
        View view = view(id);
        view.setBackgroundResource(drawable);
    }

    public void setEnable(@IdRes int id,boolean enable){
        View view = view(id);
        view.setEnabled(enable);
    }

    public <V extends View> V view(@IdRes int id){
        return itemView.findViewById(id);
    }

    public String string(@StringRes int str,Object... args){
        return itemView.getContext().getString(str,args);
    }

    public int color(@ColorRes int color){
        return ContextCompat.getColor(itemView.getContext(), color);
    }
}
