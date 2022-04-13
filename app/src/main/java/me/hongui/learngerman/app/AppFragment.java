package me.hongui.learngerman.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

import me.hongui.learngerman.R;
import me.hongui.learngerman.utils.SnackbarManager;

abstract public class AppFragment extends Fragment implements IAppInterface {
    protected AppActivity mParent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppActivity) {
            mParent = (AppActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mParent = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = layout();
        if (0 != layout) {
            return inflater.inflate(layout, container, false);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        setting(savedInstanceState);
        events(savedInstanceState);
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
    public void fragment(String clz, Bundle args, int content, boolean replace, boolean addTo) {
        FragmentManager fragmentManager = getChildFragmentManager();
        mParent.realFragment(fragmentManager,clz,args,content,replace,addTo);
    }

    public void setTitle(CharSequence title, boolean showBackArrow) {
        ActionBar actionBar = mParent.getSupportActionBar();
        if(null!=actionBar){
            actionBar.setTitle(title);
            return;
        }
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        if (null == toolbar) {
            return;
        }
        toolbar.setTitle(title);
        mParent.setSupportActionBar(toolbar);
        if (showBackArrow) {
            actionBar=mParent.getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mParent.onBackPressed();
                }
            });
        }
    }

    public void snackbar(CharSequence charSequence){
        SnackbarManager.INSTANCE.show(getContext(),getView(),charSequence);
    }

    public void snackbar(int res){
        snackbar(getString(res));
    }

    public <T extends ViewModel> T viewModel(Class<T> clz) {
        return mParent.viewModel(clz);
    }
}
