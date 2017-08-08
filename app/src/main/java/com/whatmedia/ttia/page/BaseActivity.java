package com.whatmedia.ttia.page;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;

import com.whatmedia.ttia.R;

/**
 * Created by neo_mac on 2017/6/18.
 */

public class BaseActivity extends AppCompatActivity implements IActivityTools {
    private boolean mDoubleBackToExitPressedOnce;

    @Override
    public void showLoadingView(final ViewStub viewStub) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (viewStub == null)
                    viewStub.inflate();
                viewStub.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void goneLoadingView(final ViewStub viewStub) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewStub.setVisibility(View.GONE);
            }
        });
    }


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
}
