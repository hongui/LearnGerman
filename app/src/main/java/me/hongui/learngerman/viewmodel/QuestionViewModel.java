package me.hongui.learngerman.viewmodel;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import me.hongui.learngerman.bean.Answer;
import me.hongui.learngerman.bean.BookMark;
import me.hongui.learngerman.bean.Category;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.bean.Record;

public class QuestionViewModel extends UniversalViewModel<Question> {
    private MutableLiveData<BookMark> bookMark;
    private MutableLiveData<List<Answer>> answers;
    private MutableLiveData<Answer> rightAnswer;
    private MutableLiveData<Long> round;
    private OnNextActionListener actionListener;
    private List<Category> categories;
    private Category category;
    private int limit;
    private boolean random;
    private boolean bookmark=true;

    public QuestionViewModel() {
        bookMark = new MutableLiveData<>();
        answers = new MutableLiveData<>();
        rightAnswer = new MutableLiveData<>();
        round = new MutableLiveData<>();
        categories = new ArrayList<>();
    }

    public MutableLiveData<BookMark> getBookMark() {
        return bookMark;
    }

    public MutableLiveData<List<Answer>> getAnswers() {
        return answers;
    }

    public MutableLiveData<Answer> getRightAnswer() {
        return rightAnswer;
    }

    public MutableLiveData<Long> getRound() {
        return round;
    }

    public OnNextActionListener getActionListener() {
        return actionListener;
    }

    public void setActionListener(OnNextActionListener listener) {
        this.actionListener = listener;
    }

    public void bookMark() {
        BookMark value = bookMark.getValue();
        if (null == value) {
            BookMark bookMark = recordRepo().addMark(this.getData().getValue());
            this.bookMark.setValue(bookMark);
        } else {
            recordRepo().removeMark(value);
            bookMark.setValue(null);
        }
    }

    public void setGenerator(int limit, boolean random, int... categories) {
        Category[] values = Category.values();
        for (Category c : values) {
            for (int i : categories) {
                if (c.category == i) {
                    this.categories.add(c);
                }
            }
        }
        this.random = random;
        this.limit = limit;
        this.bookmark=false;
    }

    @Override
    public Category category() {
        return category;
    }

    @Override
    protected Observable<List<Question>> observable() {
        if(bookmark){
            category=Category.BOOKMARK;
            return recordRepo().bookmarks();
        }
        Observable<List<Question>> observable=null;
        for (final Category c : categories) {
            Observable<List<Question>> question =germanRepo().questionByCategory(c.category);
            Observable<List<Record>> records = recordRepo().rounds(c.category);
            Observable<List<Question>> temp =records.zipWith(question, new BiFunction<List<Record>, List<Question>, List<Question>>() {
                @Override
                public List<Question> apply(List<Record> records, List<Question> questions) throws Exception {
                    //没有记录，则视为是新的一轮，用全部数据
                    if (null == records || records.isEmpty()) {
                        round.postValue(1L);
                        return questions;
                    } else if (records.size() >= questions.size()) {//一样多，完成了一轮
                        Record record = records.get(0);
                        round.postValue(record.getRound() + 1);
                    } else {//有数据，说明上一轮没有完成，去除已经完成的
                        for (Record record : records) {
                            for (Question question : questions) {
                                if (question.getId() == record.getQustion()) {
                                    stack().push(question);
                                    setCurrent(getCurrent() + 1);
                                    if(random) {
                                        questions.remove(question);
                                    }
                                    break;
                                }
                            }

                        }
                        Record record = records.get(0);
                        round.postValue(record.getRound());
                    }
                    return questions;
                }
            });
            if (null == observable) {
                observable = temp;
            } else {
                observable = observable.zipWith(temp, new BiFunction<List<Question>, List<Question>, List<Question>>() {
                    @Override
                    public List<Question> apply(List<Question> questions, List<Question> questions2) throws Exception {
                        questions.addAll(questions2);
                        return questions;
                    }
                });
            }
        }
        return observable;
    }

    @Override
    public void setDatas(List<Question> datas) {
        super.setDatas(datas);
    }

    @Override
    protected void update(Question question) {
        super.update(question);
        this.bookMark.setValue(null);
        this.answers.setValue(null);
        subscript(recordRepo().isMarked(question.getId()), bookMark);
        subscript(germanRepo().answerForQuestion(question.getId()), answers);
    }

    @Override
    protected Question getNext() {
        Question question;
        if (random) {
            List<Question> datas = getDatas();
            Random random = new Random(System.currentTimeMillis());
            int index = random.nextInt(datas.size());
            question = datas.remove(index);
        } else {
            question = super.getNext();
        }
        setCategory(question);
        return question;
    }

    private void setCategory(Question question) {
        for (Category c : categories) {
            if (c.category == question.getCategory()) {
                this.category = c;
                break;
            }
        }
    }

    public boolean checkAnswer(String input) {
        List<Answer> answers = this.answers.getValue();
        boolean right = false;
        if(null!=answers) {
            for (Answer a : answers) {
                if (a.isSelected() && input.equals(a.getContent())) {
                    right = true;
                    break;
                }
            }
        }
        if (!right) {
            if (TextUtils.isEmpty(input)) {
                right = false;
            } else {
                Question question = this.getData().getValue();
                right = input.equals(question.getSep());
            }
        }
        //答案在问题里，检查问题里是否正确
        if (!right) {
            if (TextUtils.isEmpty(input)) {
                right = false;
            } else {
                Question question = this.getData().getValue();
                right = input.equals(question.getTranslate());
            }
        }
        report(input, right);
        return right;
    }

    public boolean checkAnswer(Answer answer) {
        boolean right = false;
        List<Answer> answers = this.answers.getValue();
        if ((null == answer && (null == answers || answers.isEmpty())) || null != answer && answer.isSelected()) {
            rightAnswer.setValue(null);
            right = true;
        } else {
            for (Answer a : answers) {
                if (a.isSelected()) {
                    rightAnswer.setValue(a);
                }
            }
        }
        String input = null;
        if (null == answer) {
            input = "";
        } else {
            input = answer.getContent();
        }
        report(input, right);
        return right;
    }

    @Override
    protected boolean canNext() {
        boolean can = super.canNext();
        if (0 < limit) {
            return getCurrent() < limit;
        }
        return can;
    }

    public int limit() {
        if (limit > 0) {
            return limit;
        } else {
            return getDatas().size();
        }
    }

    public String answer() {
        String answer = null;
        if (null != answers.getValue()) {
            for (Answer ans : answers.getValue()) {
                if (ans.isSelected()) {
                    answer = ans.getContent();
                    break;
                }
            }
        } else {
            Question question = getData().getValue();
            answer = question.getTranslate();
        }
        return answer;
    }

    public void report(String answer, boolean right) {
        Question question=getData().getValue();
        long round=-9527;
        if(null!=this.round.getValue()){
             round=this.round.getValue();
        }
        Record record = new Record();
        record.setDate(System.currentTimeMillis());
        record.setAnswer(answer);
        record.setQustion(question.getId());
        record.setCategory(question.getCategory());
        record.setRound(round);
        record.setCorrect(right);
        recordRepo().months(record);
    }

    public interface OnNextActionListener {
        boolean onAction();
    }
}
