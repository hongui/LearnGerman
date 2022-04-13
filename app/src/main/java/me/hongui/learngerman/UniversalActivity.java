package me.hongui.learngerman;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import me.hongui.learngerman.app.AppActivity;
import me.hongui.learngerman.app.AppFragment;

public class UniversalActivity extends AppActivity {

    @Override
    public void setting(Bundle saveInstance) {
        Intent intent = getIntent();
        if (intent.hasExtra(FRAGMENT_FLAG)) {
            String cls = intent.getStringExtra(FRAGMENT_FLAG);
            fragment(cls, intent.getExtras(), android.R.id.content, true,true);
        }
    }

    public static <T extends AppFragment> void newActivity(AppCompatActivity context, Class<T> cls) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context);
        Intent intent = new Intent(context, UniversalActivity.class);
        intent.putExtra(FRAGMENT_FLAG, cls.getName());
        context.startActivity(intent,options.toBundle());
    }

    public static <T extends AppFragment> void newActivity(AppCompatActivity context, Class<T> cls, Bundle datas) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context);
        Intent intent = new Intent(context, UniversalActivity.class);
        intent.putExtras(datas);
        intent.putExtra(FRAGMENT_FLAG, cls.getName());
        context.startActivity(intent,options.toBundle());
    }

    private static String FRAGMENT_FLAG = "fragmentCls";
}
