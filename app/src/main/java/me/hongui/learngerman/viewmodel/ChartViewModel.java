package me.hongui.learngerman.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import me.hongui.learngerman.app.AppViewModel;
import me.hongui.learngerman.bean.Correct;
import me.hongui.learngerman.bean.Record;
import me.hongui.learngerman.bean.Wrong;
import me.hongui.learngerman.repo.RecordRepo;

public class ChartViewModel extends AppViewModel {
    private MutableLiveData<List<Record>> records;
    private MutableLiveData<List<Wrong>> wrongs;
    private MutableLiveData<List<Correct>> corrects;
    private RecordRepo repo;

    public ChartViewModel() {
        records = new MutableLiveData<>();
        wrongs = new MutableLiveData<>();
        corrects = new MutableLiveData<>();
    }

    public void init(Context context) {
        if (null == repo) {
            repo = new RecordRepo(context);
        }
    }

    public void load(Context context, int year, int month, int day, long duration) {
        init(context);
        subscript(repo.records(year, month, day, duration), records);
    }

    public void wrong(Context context) {
        init(context);
        subscript(repo.wrong(),wrongs);
    }

    public void correct(Context context) {
        init(context);
        Observable<List<Correct>> observable = repo.days().flatMap(new Function<Long, ObservableSource<List<Correct>>>() {
            @Override
            public ObservableSource<List<Correct>> apply(Long aLong) throws Exception {
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(aLong);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                long start = calendar.getTimeInMillis();
                long duration =(long) (0.1 * 24 * 60 * 60 * 1000);
                long end = start + duration;
                Observable<Correct> single=null;
                Observable<List<Correct>> multiple=null;
                while (start < System.currentTimeMillis()) {
                    if(null==single){
                        single=repo.correct(start, end);
                    }else if(null==multiple){
                        multiple=single.zipWith(repo.correct(start, end), new BiFunction<Correct, Correct, List<Correct>>() {
                            @Override
                            public List<Correct> apply(Correct correct, Correct correct2) throws Exception {
                                List<Correct> temp = new ArrayList<>();
                                temp.add(correct);
                                temp.add(correct2);
                                return temp;
                            }
                        });
                    }else {
                        multiple=multiple.zipWith(repo.correct(start, end), new BiFunction<List<Correct>, Correct, List<Correct>>() {
                            @Override
                            public List<Correct> apply(List<Correct> corrects, Correct correct) throws Exception {
                                corrects.add(correct);
                                return corrects;
                            }
                        });
                    }
                    start = end;
                    end += duration;
                }
                if(null==multiple&&null!=single){
                    multiple=single.flatMap(new Function<Correct, ObservableSource<List<Correct>>>() {
                        @Override
                        public ObservableSource<List<Correct>> apply(Correct correct) throws Exception {
                            List<Correct> list = new ArrayList<>();
                            list.add(correct);
                            return Observable.just(list);
                        }
                    });
                }
                return multiple;
            }
        });

        subscript(observable,corrects);
    }

    public MutableLiveData<List<Record>> getRecords() {
        return records;
    }
    public MutableLiveData<List<Wrong>> getWrongs() {
        return wrongs;
    }

    public MutableLiveData<List<Correct>> getCorrects() {
        return corrects;
    }
}
