package me.hongui.learngerman.viewmodel;

import android.content.Context;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import me.hongui.learngerman.app.AppViewModel;
import me.hongui.learngerman.bean.Correct;
import me.hongui.learngerman.repo.RecordRepo;

public class CategoryViewModel extends AppViewModel {
    private RecordRepo repo;
    private MutableLiveData<List<Correct>> records;

    public CategoryViewModel(){
        records = new MutableLiveData<>();
    }

    public void load(Context context) {
        repo = new RecordRepo(context);
        subscript(repo.categoryRate(),records);
    }

    public MutableLiveData<List<Correct>> getRecords() {
        return records;
    }
}
