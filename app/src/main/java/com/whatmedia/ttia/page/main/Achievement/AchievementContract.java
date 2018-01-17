package com.whatmedia.ttia.page.main.Achievement;



import com.whatmedia.ttia.newresponse.data.AchievementsData;

import java.util.List;

public interface AchievementContract {
    interface View {
        void queryAchievementListSuccess(List<AchievementsData> list);
        void queryAchievementListFail(String message, int status);
    }

    interface Presenter {
        void queryAchievementList();
    }
}
