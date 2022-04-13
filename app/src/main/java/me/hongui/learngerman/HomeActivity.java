package me.hongui.learngerman;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import me.hongui.learngerman.app.AppActivity;
import me.hongui.learngerman.home.HomeFragment;
import me.hongui.learngerman.quiz.QuizFragment;
import me.hongui.learngerman.user.MeFragment;
import me.hongui.learngerman.utils.ResourceUtil;

import android.view.MenuItem;

public class HomeActivity extends AppActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_study:
                    fragment(HomeFragment.class,null,R.id.fl_home_content,false);
                    return true;
                case R.id.navigation_quiz:
                    fragment(QuizFragment.class,null,R.id.fl_home_content,false);
                    return true;
                case R.id.navigation_me:
                    fragment(MeFragment.class,null,R.id.fl_home_content,false);
                    return true;
            }
            return false;
        }
    };

    @Override
    public int layout() {
        return R.layout.activity_home;
    }

    @Override
    public void setting(Bundle saveInstance) {
        fragment(HomeFragment.class,null,R.id.fl_home_content);
        ResourceUtil.moveDb(this);
    }

    @Override
    public void events(Bundle saveInstance) {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bn_home_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
