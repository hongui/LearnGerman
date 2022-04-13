package me.hongui.learngerman.app;

import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import me.hongui.learngerman.R;

public class AppActivity extends AppCompatActivity implements IAppInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        int layout = layout();
        if (0 != layout) {
            setContentView(layout);
        }
        setting(savedInstanceState);
        events(savedInstanceState);

        Explode explode = new Explode();
        explode.setDuration(150);
        getWindow().setEnterTransition(explode);
        getWindow().setExitTransition(explode);
        getWindow().setReenterTransition(explode);
        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setAllowReturnTransitionOverlap(false);
    }

    @Override
    public int layout() {
        return 0;
    }

    @Override
    public void setting(Bundle saveInstance) {

    }

    @Override
    public void events(Bundle saveInstance) {

    }

    @Override
    public void setTitle(CharSequence title, boolean showBackArrow) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (null == toolbar) {
            return;
        }
        setSupportActionBar(toolbar);
        if (showBackArrow) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    supportFinishAfterTransition();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager manager=getSupportFragmentManager();
        int count=manager.getBackStackEntryCount();
        if(0==count){
            supportFinishAfterTransition();
        }
    }

    public void fragment(Class clz, Bundle args) {
        fragment(clz, args, android.R.id.content, true);
    }

    public void fragment(Class clz, Bundle args, int content) {
        fragment(clz, args, content, true);
    }

    public void fragment(Class clz, Bundle args, int content, boolean replace) {
        fragment(clz.getName(), args, content, replace,true);
    }

    public void fragment(String clz, Bundle args, int content, boolean replace,boolean addTo) {
        realFragment(getSupportFragmentManager(),clz,args,content,replace,addTo);
    }

    public void realFragment(FragmentManager manager,String clz, Bundle args, int content, boolean replace,boolean addTo){
        Fragment fragment = manager.findFragmentByTag(clz);
        boolean hasAdded = false;
        if (null == fragment) {
            fragment = Fragment.instantiate(this, clz, args);
        } else {
            hasAdded = true;
            Bundle arguments = fragment.getArguments();
            if (null == arguments) {
                fragment.setArguments(args);
            } else {
                arguments.putAll(args);
            }
        }
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (null != fragments) {
            for (Fragment f : fragments) {
                if (null == f || f == fragment) {
                    continue;
                }
                transaction.hide(f);
            }
        }
        if (!hasAdded) {
            if (replace) {
                transaction.replace(content, fragment, clz);
                if(addTo) {
                    transaction.addToBackStack(null);
                }
            } else {
                transaction.add(content, fragment, clz);
            }
        }
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
    }

    public <T extends ViewModel> T viewModel(Class<T> clz) {
        ViewModelProvider.NewInstanceFactory factory = new ViewModelProvider.NewInstanceFactory();
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        T t = provider.get(clz);
        return t;
    }
}
