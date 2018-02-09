package com.whatsmedia.ttia.page.main.flights.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetFlightsListResponse;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.page.Page;
import com.whatsmedia.ttia.page.main.flights.result.FlightsSearchResultContract;
import com.whatsmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlightsSearchFragment extends BaseFragment implements FlightsSearchContract.View {
    private static final String TAG = FlightsSearchFragment.class.getSimpleName();
    @BindView(R.id.editText_search)
    EditText mEditTextSearch;
    @BindView(R.id.layout_search)
    RelativeLayout mLayoutSearch;
    @BindView(R.id.textView_explain)
    TextView mTextViewExplain;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private FlightsSearchContract.Presenter mPresenter;

    private Bundle mBundle = new Bundle();

    public FlightsSearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FlightsSearchFragment newInstance() {
        FlightsSearchFragment fragment = new FlightsSearchFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flights_search, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new FlightsSearchPresenter(getContext(), this);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        Util.hideSoftKeyboard(mEditTextSearch);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMainActivity.getMyToolbar().setOnBackClickListener(null);
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void getFlightsDepartureSucceed(String response) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (!TextUtils.isEmpty(response) && GetFlightsListResponse.getGson(response) != null) {
                if (GetFlightsListResponse.getGson(response).getFlightList().size() != 0) {
                    mBundle.putString(FlightsSearchResultContract.TAG_ALL_FLIGHTS, response);
                }
            } else {
                Log.e(TAG, "departure response is null");
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
        checkToNextPage();
    }

    @Override
    public void getFlightsDepartureFailed(String message, final int status) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    switch (status) {
                        case NewApiConnect.TAG_DEFAULT:
                            showMessage(getString(R.string.server_error));
                            break;
                        case NewApiConnect.TAG_TIMEOUT:
                            if (getContext() != null && isAdded() && !isDetached())
                                Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            if (getContext() != null && isAdded() && !isDetached())
                                Util.showNetworkErrorDialog(getContext());
                            break;
                    }
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @OnClick(R.id.layout_search)
    public void onViewClicked() {
        String keyword = mEditTextSearch.getText().toString();
        if (!TextUtils.isEmpty(keyword)) {
            mBundle.clear();
            mBundle.putString(FlightsSearchResultContract.TAG_KEY_WORLD, keyword);
            Util.hideSoftKeyboard(mEditTextSearch);
            mLoadingView.showLoadingView();
            mPresenter.getFlightsInfoAPI(keyword);
        }
    }

    /**
     * 檢查資料進下一頁
     */
    private void checkToNextPage() {
        if (isAdded() && !isDetached()) {
            if (TextUtils.isEmpty(mBundle.getString(FlightsSearchResultContract.TAG_ALL_FLIGHTS))) {

                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.note)
                                .setMessage(R.string.flights_search_not_found_flights_message)
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                });
            } else {
                mMainActivity.addFragment(Page.TAG_FIGHTS_SEARCH_RESULT, mBundle, true);
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }
}
