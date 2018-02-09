package com.whatsmedia.ttia.page.main.language;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetLanguageListResponse;
import com.whatsmedia.ttia.newresponse.GetLanguageResponse;
import com.whatsmedia.ttia.newresponse.data.LanguageData;
import com.whatsmedia.ttia.utility.Preferences;

import java.io.IOException;

import okhttp3.Call;

public class LanguageSettingPresenter implements LanguageSettingContract.Presenter {
    private final static String TAG = LanguageSettingPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private LanguageSettingContract.View mView;
    private Context mContext;


    LanguageSettingPresenter(Context context, LanguageSettingContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void editUserLanguage(int index) {
        LanguageData data = new LanguageData();
        GetLanguageResponse response = new GetLanguageResponse();
        GetLanguageListResponse languageListResponse;

        //取得cache的語言列表
        if (!TextUtils.isEmpty(Preferences.getLanguageList(mContext))) {
            languageListResponse = GetLanguageListResponse.getInstance(mContext, Preferences.getLanguageList(mContext));
        } else {
            mView.editUserLanguageFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            return;
        }

        //根據index取得cache的語言ID
        if (languageListResponse.getData() == null || languageListResponse.getData().get(index) == null) {
            mView.editUserLanguageFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            Log.e(TAG, "[editUserLanguage languageList Data is null]");
            return;
        }

        //包裝成要上傳的檔案
        data.setLanguageId(languageListResponse.getData().get(index).getId());
        response.setData(data);
        mNewApiConnect.editUserLanguage(response.getJson(), new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.editUserLanguageFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.editUserLanguageSuccess();
            }
        });
    }
}
