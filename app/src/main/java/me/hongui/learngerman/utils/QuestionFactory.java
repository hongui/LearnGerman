package me.hongui.learngerman.utils;

import java.util.HashMap;
import java.util.Map;

import me.hongui.learngerman.app.AppFragment;
import me.hongui.learngerman.bean.Category;
import me.hongui.learngerman.bean.Question;
import me.hongui.learngerman.home.BaseNumberFragment;
import me.hongui.learngerman.home.ExpressNumberFragment;
import me.hongui.learngerman.quiz.BaseImageFragment;
import me.hongui.learngerman.quiz.BaseInputFragment;
import me.hongui.learngerman.quiz.BaseOptionFragment;
import me.hongui.learngerman.quiz.BaseVolumeFragment;
import me.hongui.learngerman.quiz.VolumeOptionFragment;

public class QuestionFactory {

    private static final Map<Integer,Class> maps=new HashMap<>();

    static {
        maps.put(Category.NUMBER.category, BaseNumberFragment.class);
        maps.put(Category.NUMBER_EXPRESS.category, ExpressNumberFragment.class);
        maps.put(Category.ORDER.category, ExpressNumberFragment.class);
        maps.put(Category.DATE_EXPRESS.category, ExpressNumberFragment.class);
        maps.put(Category.TIME_EXPRESS.category, ExpressNumberFragment.class);
        maps.put(Category.PRICE_EXPRESS.category, ExpressNumberFragment.class);

        maps.put(Category.NUMBER_INPUT.category, BaseOptionFragment.class);
        maps.put(Category.IMAGE_OPTION.category, BaseImageFragment.class);
        maps.put(Category.WATCH_INPUT.category, BaseInputFragment.class);
        maps.put(Category.VOICE_INPUT.category, BaseVolumeFragment.class);
        maps.put(Category.VOICE_OPTION.category, VolumeOptionFragment.class);
        maps.put(Category.VOICE_OPTION_ADVANCE.category, VolumeOptionFragment.class);
        maps.put(Category.WATCH_INPUT_ADVANCE.category, BaseInputFragment.class);
        maps.put(Category.VOICE_INPUT_ADVANCE.category, BaseVolumeFragment.class);
    }

    public static Class<? extends AppFragment> create(Question question){
        return maps.get(question.getCategory());
    }
}
