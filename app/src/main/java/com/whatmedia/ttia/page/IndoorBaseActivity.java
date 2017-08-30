package com.whatmedia.ttia.page;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.whatmedia.ttia.utility.MyContextWrapper;
import com.whatmedia.ttia.utility.Preferences;

public class IndoorBaseActivity extends AppCompatActivity implements IActivityTools {
    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = MyContextWrapper.wrap(newBase, Preferences.getLocaleSetting(newBase));
        super.attachBaseContext(context);
    }
}
