package com.whatmedia.ttia.page.main.secretary.sweet;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.interfaces.IOnItemLongClickListener;
import com.whatmedia.ttia.newresponse.data.UserNewsData;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.secretary.detail.news.NewsDetailContract;
import com.whatmedia.ttia.utility.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AirportSweetNotifyFragment extends BaseFragment implements AirportSweetNotifyContract.View, IOnItemClickListener, IOnItemLongClickListener {
    private static final String TAG = AirportSweetNotifyFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.textView_delete)
    TextView mTextViewDelete;
    @BindView(R.id.textView_cancel)
    TextView mTextViewCancel;
    @BindView(R.id.textView_edit)
    TextView mTextViewEdit;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private AirportSweetNotifyContract.Presenter mPresenter;

    private AirportSweetNotifyRecyclerViewAdapter mAdapter;
    private List<UserNewsData> mList;

    public AirportSweetNotifyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AirportSweetNotifyFragment newInstance() {
        AirportSweetNotifyFragment fragment = new AirportSweetNotifyFragment();
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
        View view = inflater.inflate(R.layout.fragment_sweet_notify, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new AirportSweetNotifyPresenter(getContext(), this);

        mLoadingView.showLoadingView();
        mPresenter.getSweetNotifyAPI();

        setEditState();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setOnclick(this);
        mAdapter.setOnLongClick(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
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
    public void getSweetNotifySucceed(final List<UserNewsData> list) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mList = list;
                    mAdapter.setData(list);
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getSweetNotifyFailed(final String message, final int status) {
        Log.e(TAG, "get sweet error : " + message);
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    switch (status) {
                        case NewApiConnect.TAG_DEFAULT:
                            showMessage(getString(R.string.server_error));
                            break;
                        case NewApiConnect.TAG_TIMEOUT:
                            Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            Util.showNetworkErrorDialog(getContext());
                            break;
                    }
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void deleteSweetNotifySucceed() {
        if (isAdded() && !isDetached()) {
            mLoadingView.goneLoadingView();
            mPresenter.getSweetNotifyAPI();

            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    setEditState();
                }
            });
        } else {
            Log.e(TAG, "Fragment is not isAdded or isDetached");
        }
    }

    @Override
    public void deleteSweetNotifyFailed(String message, final int status) {
        Log.e(TAG, "delete sweet error : " + message);
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mLoadingView.goneLoadingView();
                    switch (status) {
                        case NewApiConnect.TAG_DEFAULT:
                            showMessage(getString(R.string.server_error));
                            break;
                        case NewApiConnect.TAG_TIMEOUT:
                            Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            Util.showNetworkErrorDialog(getContext());
                            break;
                    }
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_message:
                if (v.getTag() != null && v.getTag() instanceof UserNewsData) {
                    UserNewsData userNewsData = (UserNewsData) v.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(NewsDetailContract.TAG_DATA, userNewsData);
                    mMainActivity.addFragment(Page.TAG_AIRPORT_SWEET_DETAIL, bundle, true);
                } else {
                    Log.e(TAG, "v.getTag() is error");
                    showMessage(getString(R.string.data_error));
                }
                break;
        }
    }

    private void setEditState() {
        mTextViewCancel.setVisibility(View.GONE);
        mTextViewDelete.setVisibility(View.GONE);
        mTextViewEdit.setVisibility(View.VISIBLE);

        mAdapter = null;
        mAdapter = new AirportSweetNotifyRecyclerViewAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setCheckShow(false);
        if (mList != null)
            mAdapter.setData(mList);
    }

    private void setDeleteState() {
        mTextViewCancel.setVisibility(View.VISIBLE);
        mTextViewDelete.setVisibility(View.VISIBLE);
        mTextViewEdit.setVisibility(View.GONE);
        mAdapter.setCheckShow(true);
    }

    @OnClick({R.id.textView_delete, R.id.textView_cancel, R.id.textView_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView_delete:
                mAdapter.getDeleteList();
                if (mAdapter.getDeleteList().size() > 0) {
                    mPresenter.deleteSweetAPI(mAdapter.getDeleteList());
                    mLoadingView.showLoadingView();
                }
                break;
            case R.id.textView_cancel:
                setEditState();
                break;
            case R.id.textView_edit:
                setDeleteState();
                break;
        }
    }


    @Override
    public void onLongClick(final View view) {
        if (view.getTag() instanceof UserNewsData) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.note)
                    .setMessage(getContext().getString(R.string.sweet_delete_content))
                    .setPositiveButton(R.string.alert_btn_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            List<String> list = new ArrayList<>();
                            list.add(((UserNewsData) view.getTag()).getId());
                            mPresenter.deleteSweetAPI(list);
                            mLoadingView.showLoadingView();
                        }
                    })
                    .setNegativeButton(R.string.alert_btn_cancel, null)
                    .show();
        } else {
            showMessage(getString(R.string.data_error));
        }
    }
}
