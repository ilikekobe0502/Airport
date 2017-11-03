package com.whatmedia.ttia.page.main.Achievement.Detail;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.CornorTransform;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AchievementDetailFragment extends BaseFragment implements AchievementDetailContract.View {
    private static final String TAG = AchievementDetailFragment.class.getSimpleName();

    @BindView(R.id.image_icon)
    ImageView mImageIcon;
    @BindView(R.id.text_date)
    TextView mTextDate;
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.text_content)
    TextView mTextContent;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private AchievementDetailContract.Presenter mPresenter;

    private String imagePath;
    private String textDate;
    private String textTitle;
    private String textContent;
    private int mRadius;

    public AchievementDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AchievementDetailFragment newInstance() {
        AchievementDetailFragment fragment = new AchievementDetailFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imagePath = getArguments().containsKey(AchievementDetailContract.IMAGE_PATH) ?
                    getArguments().getString(AchievementDetailContract.IMAGE_PATH) : "";
            textDate = getArguments().containsKey(AchievementDetailContract.TEXT_DATE) ?
                    getArguments().getString(AchievementDetailContract.TEXT_DATE) : "";
            textTitle = getArguments().containsKey(AchievementDetailContract.TEXT_TITLE) ?
                    getArguments().getString(AchievementDetailContract.TEXT_TITLE) : "";
            textContent = getArguments().containsKey(AchievementDetailContract.TEXT_CONTENT) ?
                    getArguments().getString(AchievementDetailContract.TEXT_CONTENT) : "";
        }
        mRadius = getResources().getDimensionPixelSize(R.dimen.dp_pixel_8);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_achievement_detail, container, false);
        ButterKnife.bind(this, view);

        mPresenter = AchievementDetailPresenter.getInstance(getContext(), this);
        mLoadingView.showLoadingView();
        Log.e(TAG, imagePath);
        Util.getHttpsPicasso(getContext()).load(imagePath).transform(new CornorTransform(mRadius, 0)).into(mImageIcon);
        mTextDate.setText(textDate);
        mTextTitle.setText(textTitle);
        mTextContent.setText(textContent);

        mLoadingView.goneLoadingView();

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

//    public void init(){
//        mMainActivity.getMyToolbar().clearState()
//                .setTitleText(textName)
//                .setBackground(ContextCompat.getColor(getContext(), R.color.colorSubTitle))
//                .setBackVisibility(View.VISIBLE)
//                .setOnBackClickListener(new MyToolbar.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        switch (v.getId()) {
//                            case R.id.imageView_back:
//                                mMainActivity.backPress();
//                                break;
//                        }
//                    }
//                });
//    }
}
