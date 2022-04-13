package me.hongui.learngerman.app;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AppViewModel extends ViewModel {

    public <T> void subscript(final Observable<T> ob, final MutableLiveData<T> data){
        ob.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        if(null!=data) {
                            data.postValue(t);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        failed(ob,throwable.getMessage());
                    }
                });
    }

    public <T> void subscript(final Observable<T> ob,Consumer<T> consumer){
        if(null==consumer){
            consumer=new Consumer<T>() {
                @Override
                public void accept(T t) throws Exception {
                    //丢弃
                }
            };
        }
        ob.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        failed(ob,throwable.getMessage());
                    }
                });
    }

    public <T> void failed(Observable<T> ob,String msg){

    }
}
