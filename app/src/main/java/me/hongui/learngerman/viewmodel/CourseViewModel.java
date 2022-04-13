package me.hongui.learngerman.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import me.hongui.learngerman.app.AppViewModel;
import me.hongui.learngerman.bean.Category;
import me.hongui.learngerman.bean.CourseProgress;
import me.hongui.learngerman.bean.Round;
import me.hongui.learngerman.bean.SimpleQuestion;
import me.hongui.learngerman.repo.GermanRepo;
import me.hongui.learngerman.repo.RecordRepo;

public class CourseViewModel extends AppViewModel {
    private RecordRepo repo;
    private GermanRepo origin;
    private MutableLiveData<List<CourseProgress>> courses;

    public CourseViewModel(){
        courses =new MutableLiveData<>();
    }

    public void load(Context context) {
        repo = new RecordRepo(context);
        origin = new GermanRepo(context);

        Observable<List<SimpleQuestion>> simpleQuestions = origin.simpleQuestions();
        final Observable<List<Round>> progress = repo.progress();
        Observable<List<CourseProgress>> observable = simpleQuestions.zipWith(progress, new BiFunction<List<SimpleQuestion>, List<Round>, List<CourseProgress>>() {
            @Override
            public List<CourseProgress> apply(List<SimpleQuestion> simpleQuestions, List<Round> rounds) throws Exception {
                List<CourseProgress> progresses = new ArrayList<>();
                int total=0;
                for (SimpleQuestion s:simpleQuestions){
                    if(s.category>11){
                        total+=s.total;
                    }else {
                        CourseProgress p=new CourseProgress();
                        p.total=s.total;
                        p.category=s.category;
                        progresses.add(p);
                    }
                }
                CourseProgress p=new CourseProgress();
                p.total=total;
                p.category= Category.NUMBER_DIFFERENT.category;
                progresses.add(p);

                for (Round round : rounds) {
                    if(round.category>11){
                        round.category=Category.NUMBER_DIFFERENT.category;
                    }
                    for(CourseProgress c:progresses){
                        if(c.category==round.category){
                            c.rounds.add(round);
                            if(c.finished<round.finished){
                                c.finished=round.finished;
                            }
                            break;
                        }
                    }
                }
                p = progresses.get(1);
                progresses.set(1, progresses.get(2));
                progresses.set(2, p);
                return progresses;
            }
        });
        subscript(observable, courses);
    }

    public MutableLiveData<List<CourseProgress>> getCourses() {
        return courses;
    }
}
