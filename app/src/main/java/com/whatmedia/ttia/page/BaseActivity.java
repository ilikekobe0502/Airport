package com.whatmedia.ttia.page;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.utility.MyContextWrapper;
import com.whatmedia.ttia.utility.Preferences;

import java.util.Locale;

/**
 * Created by neo_mac on 2017/6/18.
 */

public class BaseActivity extends AppCompatActivity implements IActivityTools {
    private boolean mDoubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.press_again_left), Snackbar.LENGTH_SHORT).show();

            if (mDoubleBackToExitPressedOnce) {
                super.onBackPressed();
            } else {
                mDoubleBackToExitPressedOnce = true;

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mDoubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = MyContextWrapper.wrap(newBase, Preferences.getLocaleSetting(newBase));
        super.attachBaseContext(context);
    }
}
