package com.whatmedia.ttia.page.main.useful.language;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.whatmedia.ttia.page.Page.TAG_USERFUL_LANGUAGE_RESULT;

public class TravelLanguageFragment extends BaseFragment implements TravelLanguageContract.View{

    @BindView(R.id.item_1)
    TextView mItem1;
    @BindView(R.id.item_2)
    TextView mItem2;
    @BindView(R.id.item_3)
    TextView mItem3;
    @BindView(R.id.item_4)
    TextView mItem4;
    @BindView(R.id.item_5)
    TextView mItem5;
    @BindView(R.id.item_6)
    TextView mItem6;
    @BindView(R.id.item_7)
    TextView mItem7;
    @BindView(R.id.item_8)
    TextView mItem8;


    private static final String TAG = TravelLanguageFragment.class.getSimpleName();

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private TravelLanguageContract.Presenter mPresenter;

    public TravelLanguageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TravelLanguageFragment newInstance() {
        TravelLanguageFragment fragment = new TravelLanguageFragment();
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
        View view = inflater.inflate(R.layout.fragment_language_list, container, false);
        ButterKnife.bind(this, view);

        mPresenter = TravelLanguagePresenter.getInstance(getContext(), this);

        mItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("key",1);
                mMainActivity.addFragment(TAG_USERFUL_LANGUAGE_RESULT,bundle,true);
            }
        });
        mItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("key",2);
                mMainActivity.addFragment(TAG_USERFUL_LANGUAGE_RESULT,bundle,true);
            }
        });
        mItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("key",3);
                mMainActivity.addFragment(TAG_USERFUL_LANGUAGE_RESULT,bundle,true);
            }
        });
        mItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("key",4);
                mMainActivity.addFragment(TAG_USERFUL_LANGUAGE_RESULT,bundle,true);
            }
        });
        mItem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("key",5);
                mMainActivity.addFragment(TAG_USERFUL_LANGUAGE_RESULT,bundle,true);
            }
        });
        mItem6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("key",6);
                mMainActivity.addFragment(TAG_USERFUL_LANGUAGE_RESULT,bundle,true);
            }
        });
        mItem7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("key",7);
                mMainActivity.addFragment(TAG_USERFUL_LANGUAGE_RESULT,bundle,true);
            }
        });
        mItem8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("key",8);
                mMainActivity.addFragment(TAG_USERFUL_LANGUAGE_RESULT,bundle,true);
            }
        });

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
}
