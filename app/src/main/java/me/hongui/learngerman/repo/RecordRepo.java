package me.hongui.learngerman.repo;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import me.hongui.learngerman.app.AppRepo;
import me.hongui.learngerman.bean.BookMark;
import me.hongui.learngerman.bean.Correct;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.bean.Record;
import me.hongui.learngerman.bean.Round;
import me.hongui.learngerman.bean.Wrong;
import me.hongui.learngerman.db.UserDatabase;

public class RecordRepo extends AppRepo {
    private UserDatabase database;

    public RecordRepo(Context context){
        database = UserDatabase.getInstance(context.getApplicationContext());
    }

    public Observable<List<Record>> months(final int year, final int month) {
        final Calendar c = Calendar.getInstance();
        c.set(year,month,1);
        long start=c.getTimeInMillis();
        c.set(Calendar.MONTH, month + 1);
        c.add(Calendar.DATE,-1);
        long end=c.getTimeInMillis();
        return database.userDao().records(start,end).flatMap(new Function<List<Record>, ObservableSource<List<Record>>>() {
            @Override
            public ObservableSource<List<Record>> apply(List<Record> records) throws Exception {
                List<LimitDate> dates = dates(year, month);
                List<Record> result = new ArrayList<>();
                c.set(year, month,0,0,0,0);
                for (LimitDate d : dates) {
                    Record r = new Record();
                    r.setEnable(d.isEnable);
                    r.setDay(d.date);
                    result.add(r);
                    if(d.isEnable && null!=records && !records.isEmpty()){
                        c.set(Calendar.DATE,d.date);
                        long s=c.getTimeInMillis();
                        long e = s+1000*60*60*24;
                        for(Record record:records){
                            if(r.isJoin()){
                                break;
                            }
                            long date = record.getDate();
                            r.setJoin(date>=s && date<=e);
                        }
                    }else {
                        r.setJoin(false);
                    }
                }
                return Observable.just(result);
            }
        });
    }

    public Observable<Correct> correct(long start,long end){
        return database.userDao().correct(start, end);
    }

    public Observable<List<Wrong>> wrong(){
        return database.userDao().wrong();
    }

    public Observable<List<Round>> progress(){
        return database.userDao().progress();
    }

    public Observable<List<Correct>> categoryRate(){
        return database.userDao().categoryRate();
    }

    public  Observable<List<Record>> records(int year,int month,int day,long duration){
        Calendar calendar=Calendar.getInstance();
        calendar.set(year, month, day,0,0,0);
        long start=calendar.getTimeInMillis();
        long end=start+duration;
        return database.userDao().records(start, end);
    }

    public Observable<List<Record>> all(){
        return database.userDao().records(0, System.currentTimeMillis());
    }

    public List<LimitDate> dates(int year, int month) {
        Calendar c = Calendar.getInstance();
        /**
         * 获取当月天数
         */
        c.set(year, month + 1, 1);
        c.add(Calendar.DATE, -1);
        int amount = c.get(Calendar.DATE);
        int lastDays = c.get(Calendar.DAY_OF_WEEK);
        if(Calendar.SUNDAY==lastDays){
            lastDays=0;
        }else {
            lastDays=8-lastDays;
        }
        /**
         * 用上个月填充
         */
        c.set(Calendar.DATE, 1);
        int firstDay = c.get(Calendar.DAY_OF_WEEK);
        if(Calendar.SUNDAY==firstDay){
            firstDay=6;
        }else {
            firstDay-=2;
        }
        int length = amount + lastDays + firstDay;
        List<LimitDate> dates = new ArrayList<>();
        c.add(Calendar.DATE, -firstDay);
        for (int i = 0; i < length; i++) {
            int day = c.get(Calendar.DAY_OF_MONTH);
            int m = c.get(Calendar.MONTH);
            c.add(Calendar.DATE, 1);
            LimitDate limitMonth=new LimitDate();
            limitMonth.isEnable=month==m;
            limitMonth.date=day;
            dates.add(limitMonth);
        }
        return dates;
    }

    public Observable<Long> days(){
        return database.userDao().days();
    }

    public Observable<BookMark> isMarked(long id){
        return database.bookMarkDao().exists(id);
    }

    public Observable<List<Record>> rounds(int category){
        return database.userDao().rounds(category);
    }

    public Observable<List<Question>> bookmarks(){
        return database.bookMarkDao().all();
    }

    public BookMark addMark(Question question){
        final BookMark b = BookMark.fromQuestion(question);
        subscript(new Action() {
            @Override
            public void run() throws Exception {
                database.bookMarkDao().add(b);
            }
        });
        return b;
    }

    public void removeMark(final BookMark bookMark){
        subscript(new Action() {
            @Override
            public void run() throws Exception {
                database.bookMarkDao().delete(bookMark);
            }
        });
    }

    public void months(final Record record){
        subscript(new Action() {
            @Override
            public void run() throws Exception {
                database.userDao().addRecord(record);
            }
        });
    }

    private static class LimitDate{
        public boolean isEnable=false;
        public int date=0;
    }
}
