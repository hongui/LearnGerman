package me.hongui.learngerman.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.hongui.learngerman.app.AppViewModel;
import me.hongui.learngerman.bean.Category;
import me.hongui.learngerman.repo.GermanRepo;
import me.hongui.learngerman.repo.RecordRepo;

public class UniversalViewModel<T> extends AppViewModel {
    private GermanRepo germanRepo;
    private RecordRepo recordRepo;
    private int current;
    private Stack<T> records;
    private List<T> datas;
    private MutableLiveData<T> data;
    private MutableLiveData<Boolean> canNext;
    private MutableLiveData<Boolean> canPrev;

    public UniversalViewModel() {
        this.data = new MutableLiveData<>();
        canNext = new MutableLiveData<>();
        canPrev = new MutableLiveData<>();
        canPrev.setValue(false);
        canNext.setValue(false);
        records = new Stack<>();
        datas = new ArrayList<>();
    }

    public void init(Context context) {
        germanRepo = new GermanRepo(context);
        recordRepo = new RecordRepo(context);

        Observable<List<T>> observable = observable();
        load(observable);
    }

    protected void load(Observable<List<T>> observable) {
        if (null == observable) {
            return;
        }
        subscript(observable, new Consumer<List<T>>() {
            @Override
            public void accept(List<T> questions) throws Exception {
                setDatas(questions);

                next();
            }
        });
    }

    protected Observable<List<T>> observable() {
        return null;
    }

    public Category category() {
        return null;
    }

    protected boolean canNext() {
        return records.size() < datas.size();
    }

    protected boolean canPrev() {
        return !records.isEmpty();
    }

    protected T getNext() {
        return datas.get(current);
    }

    protected GermanRepo germanRepo() {
        return germanRepo;
    }

    protected RecordRepo recordRepo() {
        return recordRepo;
    }

    protected Stack<T> stack() {
        return records;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCurrent() {
        return current;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas.addAll(datas);
    }

    public MutableLiveData<T> getData() {
        return data;
    }

    public MutableLiveData<Boolean> getCanNext() {
        return canNext;
    }

    public MutableLiveData<Boolean> getCanPrev() {
        return canPrev;
    }

    public T next() {
        boolean can = canNext();
        T t = getNext();
        if (can) {
            canPrev.setValue(canPrev());
            current += 1;
            T value = getData().getValue();
            records.push(value);
            canNext.setValue(canNext());
        } else {
            return null;
        }
        update(t);
        return t;
    }

    public T prev() {
        boolean can = canPrev();
        T t = null;
        if (can) {
            current -= 1;
            t = records.pop();
            canPrev.setValue(canPrev());
            canNext.setValue(canNext());
        }
        update(t);
        return t;
    }

    protected void update(T t) {
        this.data.setValue(t);
    }
}
