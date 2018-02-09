package com.whatsmedia.ttia.page;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class BaseFragment extends Fragment implements IFragmentTools {
    private final static String TAG = BaseFragment.class.getSimpleName();

    @Override
    public void showMessage(String message) {
        if (isAdded() && !isDetached() && getActivity() != null && getActivity().findViewById(android.R.id.content) != null)
            Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
        else {
            Log.e(TAG, "getActivity().findViewById(android.R.id.content) is null");
        }
    }
}
