package com.whatsmedia.ttia.page;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.whatsmedia.ttia.utility.MyContextWrapper;
import com.whatsmedia.ttia.utility.Preferences;

public class IndoorBaseActivity extends AppCompatActivity implements IActivityTools {
    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = MyContextWrapper.wrap(newBase, Preferences.getLocaleSetting(newBase));
        super.attachBaseContext(context);
    }
}
