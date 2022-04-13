package me.hongui.learngerman.viewmodel;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;
import me.hongui.learngerman.app.AppViewModel;
import me.hongui.learngerman.bean.Record;
import me.hongui.learngerman.repo.RecordRepo;

public class MeViewModel extends AppViewModel {
    Calendar calendar;
    MutableLiveData<List<Record>> displayList;
    MutableLiveData<String> month;
    MutableLiveData<Long> days;
    RecordRepo repo;
    SimpleDateFormat dateFormat;

    public MeViewModel() {
        calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.SIMPLIFIED_CHINESE);
        displayList = new MutableLiveData<>();
        month = new MutableLiveData<>();
        days = new MutableLiveData<>();
        dateFormat = new SimpleDateFormat("yyyy年MM月", Locale.getDefault());
    }

    public void load(Context context) {
        repo = new RecordRepo(context);
        days();
        updateMonth();
    }

    public void days() {
        subscript(repo.days(), new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (null == aLong) {
                    days.setValue(0L);
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(aLong);
                    calendar.set(Calendar.HOUR,0);
                    calendar.set(Calendar.MINUTE,0);
                    calendar.set(Calendar.SECOND,0);
                    aLong=calendar.getTimeInMillis();
                    long seconds = System.currentTimeMillis() - aLong;
                    long day = seconds / oneDay();
                    day=0==seconds%oneDay()?day:day+1;
                    days.setValue(day);
                }
            }
        });
    }

    public void records(int year, int month) {
        Calendar current = current();
        int curentYear = current.get(Calendar.YEAR);
        int curentMonth = current.get(Calendar.MONTH);

        if (curentYear < year) {
            year = curentYear;
        }
        if (curentMonth < month) {
            month = curentMonth;
        }
        subscript(repo.months(year, month), displayList);
    }

    public Calendar update(int value) {
        calendar.add(Calendar.MONTH, value);
        Object copy = calendar.clone();
        if (null == copy) {
            return null;
        }
        return (Calendar) copy;
    }

    public boolean prevMonth() {
        update(-1);
        updateMonth();
        return true;
    }

    public boolean nextMonth() {
        update(1);
        updateMonth();
        int targetYear = calendar.get(Calendar.YEAR);
        int targetMonth = calendar.get(Calendar.MONTH);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        return targetYear < currentYear || targetMonth < currentMonth;
    }

    public void updateMonth() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        records(year, month);

        String m = dateFormat.format(calendar.getTime());
        this.month.setValue(m);
    }

    public Calendar current() {
        return calendar;
    }

    public long oneDay() {
        return 1000L * 60 * 60 * 24;
    }

    public MutableLiveData<List<Record>> getDisplayList() {
        return displayList;
    }

    public MutableLiveData<String> getMonth() {
        return month;
    }

    public MutableLiveData<Long> getDays() {
        return days;
    }
}
