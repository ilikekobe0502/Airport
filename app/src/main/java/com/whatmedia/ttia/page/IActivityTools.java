package com.whatmedia.ttia.page;

import android.view.ViewStub;

import com.whatmedia.ttia.component.MyToolbar;

/**
 * Created by neo_mac on 2017/6/18.
 */

public interface IActivityTools {

    void showLoadingView(ViewStub viewStub);

    void goneLoadingView(ViewStub viewStub);

    interface ILoadingView {
        void showLoadingView();

        void goneLoadingView();
    }

    interface IMainActivity extends IPageTool {
        //        void setMenuClickListener(MainActivity.setMenuListener listener);
        MyToolbar getMyToolbar();

        void backPress();

        boolean getUserVisibility();

        void runOnUI(Runnable runnable);
    }
}
