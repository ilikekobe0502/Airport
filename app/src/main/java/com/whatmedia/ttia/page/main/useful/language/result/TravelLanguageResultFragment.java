package com.whatmedia.ttia.page.main.useful.language.result;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.LanguageData;
import com.whatmedia.ttia.utility.Util;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TravelLanguageResultFragment extends BaseFragment implements TravelLanguageResultContract.View, IOnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static final String TAG = TravelLanguageResultFragment.class.getSimpleName();

    private static final String TAG_PRONUNCIATION = "100";

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private TravelLanguageResultContract.Presenter mPresenter;
    private TravelLanguageResultRecyclerViewAdapter mTravelLanguageResultRecyclerViewAdapter;
    private int mQueryId = 0;
    private String mTitle = "";
    private TextToSpeech mTts;
    private final TextToSpeech.OnInitListener mInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
            if (status == TextToSpeech.SUCCESS) {
                // Set preferred language to US english.
                // Note that a language may not be available, and the result will indicate this.
                int result = mTts.setLanguage(Locale.CHINESE);
                // Try this someday for some interesting results.
                // int result mTts.setLanguage(Locale.FRANCE);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Lanuage data is missing or the language is not supported.
                    Log.e(TAG, "Language is not available.");
                } else {
                    // Check the documentation for other possible result codes.
                    // For example, the language may be available for the locale,
                    // but not for the specified country and variant.

                    // The TTS engine has been successfully initialized.
                    // Allow the user to press the button for the app to speak again.
//                    mAgainButton.setEnabled(true);

                }
            } else {
                // Initialization failed.
                Log.e(TAG, "Could not initialize TextToSpeech.");
            }
        }
    };


    public TravelLanguageResultFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TravelLanguageResultFragment newInstance() {
        TravelLanguageResultFragment fragment = new TravelLanguageResultFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("key")) {
            mQueryId = getArguments().getInt("key");
        }
        if (getArguments().containsKey("title")) {
            mTitle = getArguments().getString("title");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lauguage_result, container, false);
        ButterKnife.bind(this, view);
        mLoadingView.showLoadingView();
        mPresenter = TravelLanguageResultPresenter.getInstance(getContext(), this);
        mPresenter.getLanguageAPI(mQueryId);

        mTravelLanguageResultRecyclerViewAdapter = new TravelLanguageResultRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mTravelLanguageResultRecyclerViewAdapter);
        mTravelLanguageResultRecyclerViewAdapter.setClickListener(this);
        mTts = new TextToSpeech(getContext(), mInitListener);
        mMainActivity.getMyToolbar().setTitleText(mTitle);
        return view;
    }

    @Override
    public void onDestroy() {
        mMainActivity.getMyToolbar().setOnBackClickListener(null);
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mLoadingView = (IActivityTools.ILoadingView) context;
            mMainActivity = (IActivityTools.IMainActivity) context;
        } catch (ClassCastException castException) {
            Log.e(TAG, castException.toString());
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public void getLanguageSucceed(final List<LanguageData> response) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mTravelLanguageResultRecyclerViewAdapter.setData(response);
                    mTravelLanguageResultRecyclerViewAdapter.notifyDataSetChanged();
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getLanguageFailed(String message, boolean timeout) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (timeout) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        Util.showTimeoutDialog(getContext());
                    }
                });
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_pronunciation:
                if (view.getTag() != null && view.getTag() instanceof String) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mTts.speak(String.valueOf(view.getTag()), TextToSpeech.QUEUE_FLUSH, null, TAG_PRONUNCIATION);
                    } else {
                        mTts.speak(String.valueOf(view.getTag()), TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                break;
        }
    }
}
