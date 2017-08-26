package com.whatmedia.ttia.utility;


import android.os.Parcel;
import android.os.Parcelable;

public final class MyAppUtils {
    public final static String s_searchField = "CATEGORY";

    public final static String s_extra_featuresSet = "s_extra_featuresSet";
    public final static String s_extra_location = "s_extra_location";
    public final static String s_extra_propsMap = "s_extra_propsMap";
    public final static String s_extra_color = "s_extra_color";
    public final static String s_extra_logo = "s_extra_logo";
    public final static String s_extra_start = "s_extra_start";
    public final static String s_extra_end = "s_extra_end";

    public final static String s_selDestResult = "s_selDestResult";

    public final static String SUBTITLE_FIELD = "CATEGORY";

    public final static class PropDesc implements Parcelable
    {
        public int m_id;
        public String m_string;

        public PropDesc(int id, String string)
        {
            m_id = id;
            m_string = string;
        }

        PropDesc(Parcel p)
        {
            m_id = p.readInt();
            m_string = p.readString();
        }

        @Override
        public int describeContents()
        {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeInt(m_id);
            dest.writeString(m_string);
        }

        public static final Parcelable.Creator<PropDesc> CREATOR = new Parcelable.Creator<PropDesc>() {
            @Override
            public PropDesc createFromParcel(Parcel source) {
                return new PropDesc(source);
            }

            @Override
            public PropDesc[] newArray(int size) {
                return new PropDesc[size];
            }
        };
    }
}
