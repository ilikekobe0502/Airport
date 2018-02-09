package com.whatsmedia.ttia.page;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.utility.MyContextWrapper;
import com.whatsmedia.ttia.utility.Preferences;

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
