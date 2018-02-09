package com.whatsmedia.ttia.page;

import android.webkit.WebView;

import com.whatsmedia.ttia.component.MyFlightsDetailInfo;
import com.whatsmedia.ttia.component.MyMarquee;
import com.whatsmedia.ttia.component.MyToolbar;

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

        void setWebView(WebView webView);

        boolean getCallLanguage();

        void setCallLanguage(boolean called);
    }

    interface IIndoorMapActivity {
        MyToolbar getMyToolbar();

        MyMarquee getMyMarquee();

        void setMarqueeMessage(String subMessage);

        void backPress();

        void runOnUI(Runnable runnable);
    }
}
