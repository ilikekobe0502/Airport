package com.whatsmedia.ttia.page.main.language;


public interface LanguageSettingContract {
    interface View {
        void editUserLanguageSuccess();

        void editUserLanguageFailed(String error, int status);
    }

    interface Presenter {

        void editUserLanguage(int index);
    }
}
