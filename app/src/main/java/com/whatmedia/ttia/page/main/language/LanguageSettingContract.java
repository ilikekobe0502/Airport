package com.whatmedia.ttia.page.main.language;


public interface LanguageSettingContract {
    interface View {
        void editUserLanguageSuccess();

        void editUserLanguageFailed(String error, boolean timeout);
    }

    interface Presenter {

        void editUserLanguage(int index);
    }
}
