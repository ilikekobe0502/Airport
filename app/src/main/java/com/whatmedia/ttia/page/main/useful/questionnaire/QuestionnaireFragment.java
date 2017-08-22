package com.whatmedia.ttia.page.main.useful.questionnaire;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.QuestionnaireData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionnaireFragment extends BaseFragment implements QuestionnaireContract.View, IOnItemClickListener {

    @BindView(R.id.layout_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_delete)
    RelativeLayout mButtonSend;

    private static final String TAG = QuestionnaireFragment.class.getSimpleName();

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private QuestionnaireContract.Presenter mPresenter;
    private QuestionnaireRecyclerViewAdapter mQuestionnaireRecyclerViewAdapter;

    public QuestionnaireFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static QuestionnaireFragment newInstance() {
        QuestionnaireFragment fragment = new QuestionnaireFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_useful_quests, container, false);
        ButterKnife.bind(this, view);

        mPresenter = QuestionnairePresenter.getInstance(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getQuestionnaireAPI();

        mQuestionnaireRecyclerViewAdapter = new QuestionnaireRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mQuestionnaireRecyclerViewAdapter);
        mQuestionnaireRecyclerViewAdapter.setClickListener(this);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingView.showLoadingView();
                mPresenter.sendQuestionnaireAPI(mQuestionnaireRecyclerViewAdapter.getAnswer());
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        mMainActivity.getMyToolbar().setOnBackClickListener(null);
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
    public void getQuestionnaireSucceed(final List<QuestionnaireData> response) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (response != null && response.size() > 0) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        mQuestionnaireRecyclerViewAdapter.setData(response);
                        mQuestionnaireRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                Log.e(TAG, "Response is error");
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getQuestionnaireFailed(String message) {

    }

    @Override
    public void sendQuestionnaireSucceed(final String response) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    showNoDataDialog();
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void sendQuestionnaireFailed(String message) {

    }

    @Override
    public void onClick(View view) {
        mLoadingView.showLoadingView();
        mPresenter.sendQuestionnaireAPI(mQuestionnaireRecyclerViewAdapter.getAnswer());
    }

    private void showNoDataDialog() {
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.note)
                            .setMessage(R.string.useful_quest_send_message)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mMainActivity.backPress();
                                }
                            })
                            .show();
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }
}
