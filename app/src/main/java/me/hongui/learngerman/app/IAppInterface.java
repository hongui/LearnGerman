package me.hongui.learngerman.app;

import android.os.Bundle;

public interface IAppInterface {
    /**
     * 设置界面布局
     * @return 布局ID
     */
    int layout();

    /**
     * 初始化数据
     * @param saveInstance 恢复数据
     */
    void setting(Bundle saveInstance);

    /**
     * 事件绑定
     * @param saveInstance 恢复数据
     */
    void events(Bundle saveInstance);

    void setTitle(CharSequence title,boolean showBackArrow);

    void fragment(String clz, Bundle args, int content, boolean replace,boolean addTo);
}
