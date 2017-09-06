package com.whatmedia.ttia.page.main.Achievement;


import com.whatmedia.ttia.response.data.AchievementsData;

import java.util.List;

public interface AchievementContract {
    interface View {
        void queryAchievementListSuccess(List<AchievementsData> list);
        void queryAchievementListFail(String message, boolean timeout);
    }

    interface Presenter {
        void queryAchievementList();
    }
}
