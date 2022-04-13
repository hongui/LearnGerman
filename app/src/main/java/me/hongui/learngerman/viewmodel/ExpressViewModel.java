package me.hongui.learngerman.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import me.hongui.learngerman.bean.Lecture;

public class ExpressViewModel extends UniversalViewModel<Lecture> {

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    protected Observable<List<Lecture>> observable() {
        return Observable.create(new ObservableOnSubscribe<List<Lecture>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Lecture>> emitter) throws Exception {
                List<Lecture> lectures = lectures();
                emitter.onNext(lectures);
                emitter.onComplete();
            }
        });
    }

    private List<Lecture> lectures(){
        List<Lecture> lectures = new ArrayList<>();
        return lectures;
    }
}
