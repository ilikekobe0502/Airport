package com.whatsmedia.ttia.page;

import android.os.Bundle;

/**
 * Created by neo_mac on 2017/6/20.
 */

public interface IPageTool {
    void replaceFragment(int page, Bundle bundle, boolean backStack);

    void addFragment(int page, Bundle bundle, boolean backStack);
}
