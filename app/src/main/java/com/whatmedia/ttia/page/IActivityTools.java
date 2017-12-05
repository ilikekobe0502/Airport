package com.whatmedia.ttia.page;

import android.view.ViewStub;

import com.whatmedia.ttia.component.MyFlightsDetailInfo;
import com.whatmedia.ttia.component.MyMarquee;
import com.whatmedia.ttia.component.MyToolbar;

/**
 * Created by neo_mac on 2017/6/18.
 */

public interface IActivityTools {
    interface ILoadingView {
        void showLoadingView();

        void goneLoadingView();
    }

    interface IMainActivity extends IPageTool {
        //        void setMenuClickListener(MainActivity.setMenuListener listener);
        MyToolbar getMyToolbar();

        MyMarquee getMyMarquee();

        void setMarqueeMessage(String subMessage);

        void backPress();

        void runOnUI(Runnable runnable);

        /**
         * 設置最上面view的顏色
         *
         * @param color
         */
        void setTopViewColor(int color);

        MyFlightsDetailInfo getFlightsDetailInfo();
    }

    interface IIndoorMapActivity {
        MyToolbar getMyToolbar();

        MyMarquee getMyMarquee();

        void setMarqueeMessage(String subMessage);

        void backPress();

        void runOnUI(Runnable runnable);
    }
}
