package com.whatsmedia.ttia.page;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.page.main.Achievement.AchievementFragment;
import com.whatsmedia.ttia.page.main.Achievement.Detail.AchievementDetailFragment;
import com.whatsmedia.ttia.page.main.communication.CommunicationFragment;
import com.whatsmedia.ttia.page.main.communication.emergency.EmergencyCallFragment;
import com.whatsmedia.ttia.page.main.communication.international.InternationalCallFragment;
import com.whatsmedia.ttia.page.main.communication.roaming.RoamingServiceFragment;
import com.whatsmedia.ttia.page.main.communication.roaming.detail.RoamingDetailFragment;
import com.whatsmedia.ttia.page.main.communication.roaming.detail.webview.RoamingWebViewFragment;
import com.whatsmedia.ttia.page.main.flights.info.FlightsInfoFragment;
import com.whatsmedia.ttia.page.main.flights.my.MyFlightsInfoFragment;
import com.whatsmedia.ttia.page.main.flights.notify.MyFlightsNotifyFragment;
import com.whatsmedia.ttia.page.main.flights.result.FlightsSearchResultFragment;
import com.whatsmedia.ttia.page.main.flights.search.FlightsSearchFragment;
import com.whatsmedia.ttia.page.main.home.HomeFragment;
import com.whatsmedia.ttia.page.main.home.arrive.ArriveFlightsFragment;
import com.whatsmedia.ttia.page.main.home.departure.DepartureFlightsFragment;
import com.whatsmedia.ttia.page.main.home.moreflights.MoreFlightsFragment;
import com.whatsmedia.ttia.page.main.home.parking.HomeParkingInfoFragment;
import com.whatsmedia.ttia.page.main.home.weather.HomeWeatherInfoFragment;
import com.whatsmedia.ttia.page.main.home.weather.more.MoreWeatherFragment;
import com.whatsmedia.ttia.page.main.language.LanguageSettingFragment;
import com.whatsmedia.ttia.page.main.secretary.AirportSecretaryFragment;
import com.whatsmedia.ttia.page.main.secretary.detail.emergency.EmergencyDetailFragment;
import com.whatsmedia.ttia.page.main.secretary.detail.news.NewsDetailFragment;
import com.whatsmedia.ttia.page.main.secretary.detail.sweet.SweetNotifyDetailFragment;
import com.whatsmedia.ttia.page.main.secretary.emergency.AirportEmergencyFragment;
import com.whatsmedia.ttia.page.main.secretary.news.AirportUserNewsFragment;
import com.whatsmedia.ttia.page.main.secretary.sweet.AirportSweetNotifyFragment;
import com.whatsmedia.ttia.page.main.store.StoreOffersFragment;
import com.whatsmedia.ttia.page.main.store.souvenir.SouvenirAreaFragment;
import com.whatsmedia.ttia.page.main.store.souvenir.detail.SouvenirDetailFragment;
import com.whatsmedia.ttia.page.main.terminals.facility.AirportFacilityFragment;
import com.whatsmedia.ttia.page.main.terminals.facility.detail.FacilityDetailFragment;
import com.whatsmedia.ttia.page.main.terminals.info.TerminalInfoFragment;
import com.whatsmedia.ttia.page.main.terminals.store.info.StoreSearchInfoFragment;
import com.whatsmedia.ttia.page.main.terminals.store.result.StoreSearchResultFragment;
import com.whatsmedia.ttia.page.main.terminals.store.search.StoreSearchFragment;
import com.whatsmedia.ttia.page.main.terminals.toilet.PublicToiletFragment;
import com.whatsmedia.ttia.page.main.traffic.AirportTrafficFragment;
import com.whatsmedia.ttia.page.main.traffic.bus.AirportBusFragment;
import com.whatsmedia.ttia.page.main.traffic.mrt.AirportMrtFragment;
import com.whatsmedia.ttia.page.main.traffic.parking.ParkingInfoFragment;
import com.whatsmedia.ttia.page.main.traffic.roadside.RoadsideAssistanceFragment;
import com.whatsmedia.ttia.page.main.traffic.skytrain.SkyTrainFragment;
import com.whatsmedia.ttia.page.main.traffic.taxi.TaxiFragment;
import com.whatsmedia.ttia.page.main.traffic.tourbus.TourBusFragment;
import com.whatsmedia.ttia.page.main.useful.currency.CurrencyConversionFragment;
import com.whatsmedia.ttia.page.main.useful.info.UsefulInfoFragment;
import com.whatsmedia.ttia.page.main.useful.language.TravelLanguageFragment;
import com.whatsmedia.ttia.page.main.useful.language.result.TravelLanguageResultFragment;
import com.whatsmedia.ttia.page.main.useful.lost.LostAndFoundFragment;
import com.whatsmedia.ttia.page.main.useful.questionnaire.QuestionnaireFragment;
import com.whatsmedia.ttia.page.main.useful.timezone.TimeZoneQueryFragment;

/**
 * Created by neo_mac on 2017/6/20.
 */

public class Page {
    public static final int TAG_HOME = 1000;
    public static final int TAG_HOME_DEPARTURE_FLIGHTS = 1001;
    public static final int TAG_HOME_ARRIVE_FLIGHTS = 1002;
    public static final int TAG_HOME_PARKING_INFO = 1003;
    public static final int TAG_HOME_WEATHER_INFO = 1004;
    public static final int TAG_HOME_MORE_FLIGHTS = 1005;
    public static final int TAG_HOME_MORE_WEATHER = 1006;

    public static final int TAG_FIGHTS_INFO = 1101;
    public static final int TAG_FIGHTS_SEARCH = 1102;
    public static final int TAG_FIGHTS_SEARCH_RESULT = 1103;
    public static final int TAG_MY_FIGHTS_INFO = 1104;
    public static final int TAG_MY_FIGHTS_NOTIFY = 1105;

    public static final int TAG_TERMINAL_INFO = 1201;
    public static final int TAG_PUBLIC_TOILET = 1202;
    public static final int TAG_AIRPORT_FACILITY = 1203;
    public static final int TAG_STORE_SEARCH = 1204;
    public static final int TAG_STORE_SEARCH_RESULT = 1205;
    public static final int TAG_STORE_SEARCH_INFO = 1206;
    public static final int TAG_AIRPORT_FACILITY_DETAIL = 1207;

    public static final int TAG_AIRPORT_TRAFFIC = 1301;
    public static final int TAG_AIRPORT_BUS = 1302;
    public static final int TAG_ROADSIDE_ASSISTANCE = 1303;
    public static final int TAG_TAXI = 1304;
    public static final int TAG_TOUR_BUS = 1305;
    public static final int TAG_AIRPORT_MRT = 1306;
    public static final int TAG_SKY_TRAIN = 1307;
    public static final int TAG_PARK_INFO = 1308;

    public static final int TAG_AIRPORT_SECRETARY = 1401;
    public static final int TAG_AIRPORT_USER_NEWS = 1402;
    public static final int TAG_AIRPORT_EMERGENCY = 1403;
    public static final int TAG_AIRPORT_SWEET_NOTIFY = 1404;
    public static final int TAG_AIRPORT_NEWS_DETAIL = 1405;
    public static final int TAG_AIRPORT_EMERGENCY_DETAIL = 1406;
    public static final int TAG_AIRPORT_SWEET_DETAIL = 1407;

    public static final int TAG_STORE_OFFERS = 1501;
    public static final int TAG_SOUVENIR_AREA = 1502;
    public static final int TAG_SOUVENIR_DETAIL = 1503;

    public static final int TAG_COMMUNICATION_SERVICE = 1601;

    public static final int TAG_LANGUAGE_SETTING = 1701;

    public static final int TAG_USERFUL_INFO = 2000;
    public static final int TAG_USERFUL_LOST = 2001;
    public static final int TAG_USERFUL_CURRENCY_CONVERSION = 2002;
    public static final int TAG_USERFUL_QUEST = 2003;
    public static final int TAG_USERFUL_TIMEZONE = 2004;
    public static final int TAG_USERFUL_LANGUAGE = 2005;
    public static final int TAG_USERFUL_LANGUAGE_RESULT = 2006;

    public static final int TAG_COMMUNICATION_INTERNATIONAL_CALL = 3001;
    public static final int TAG_COMMUNICATION_EMERGENCY_CALL = 3002;
    public static final int TAG_COMMUNICATION_ROAMING_SERVICE = 3003;
    public static final int TAG_COMMUNICATION_ROAMING_DETAIL = 3004;
    public static final int TAG_COMMUNICATION_ROAMING_WEBVIEW = 3005;

    public static final int TAG_ACHIEVEMENT = 4000;
    public static final int TAG_ACHIEVEMENT_DETAIL = 4001;

    /**
     * Switch AirportEmergencyFragment
     *
     * @param activity
     * @param page
     * @param bundle
     * @param backStack
     */
    public static void switchFragment(AppCompatActivity activity, int page, Bundle bundle, boolean backStack) {
        android.support.v4.app.Fragment fragment = getFragment(page);
        String fragmentName = fragment.getClass().getSimpleName();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (bundle != null)
            fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.layout_container, fragment, fragmentName);
        if (backStack)
            fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * Add AirportEmergencyFragment
     *
     * @param activity
     * @param page
     * @param bundle
     * @param backStack
     */
    public static void addFragment(AppCompatActivity activity, int page, Bundle bundle, boolean backStack) {
        android.support.v4.app.Fragment fragment = getFragment(page);
        String fragmentName = fragment.getClass().getSimpleName();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (bundle != null)
            fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.layout_container, fragment, fragmentName);
        if (backStack)
            fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * Set back Stack change listener
     *
     * @param activity
     * @param listener
     */
    public static void setBackStackChangedListener(AppCompatActivity activity, FragmentManager.OnBackStackChangedListener listener) {
        activity.getSupportFragmentManager().addOnBackStackChangedListener(listener);
    }

    /**
     * clear back stack
     *
     * @param activity
     */
    public static void clearBackStack(AppCompatActivity activity) {
        if (activity != null && activity.getSupportFragmentManager() != null)
            activity.getSupportFragmentManager().popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private static android.support.v4.app.Fragment getFragment(int page) {
        switch (page) {
            case TAG_HOME:
                return  HomeFragment.newInstance();
            case TAG_FIGHTS_INFO:
                return  FlightsInfoFragment.newInstance();
            case TAG_FIGHTS_SEARCH:
                return  FlightsSearchFragment.newInstance();
            case TAG_FIGHTS_SEARCH_RESULT:
                return  FlightsSearchResultFragment.newInstance();
            case TAG_MY_FIGHTS_INFO:
                return  MyFlightsInfoFragment.newInstance();
            case TAG_TERMINAL_INFO:
                return  TerminalInfoFragment.newInstance();
            case TAG_PUBLIC_TOILET:
                return  PublicToiletFragment.newInstance();
            case TAG_AIRPORT_FACILITY:
                return  AirportFacilityFragment.newInstance();
            case TAG_STORE_SEARCH:
                return  StoreSearchFragment.newInstance();
            case TAG_STORE_SEARCH_RESULT:
                return  StoreSearchResultFragment.newInstance();
            case TAG_STORE_SEARCH_INFO:
                return  StoreSearchInfoFragment.newInstance();
            case TAG_AIRPORT_TRAFFIC:
                return  AirportTrafficFragment.newInstance();
            case TAG_AIRPORT_BUS:
                return  AirportBusFragment.newInstance();
            case TAG_ROADSIDE_ASSISTANCE:
                return  RoadsideAssistanceFragment.newInstance();
            case TAG_TAXI:
                return  TaxiFragment.newInstance();
            case TAG_TOUR_BUS:
                return  TourBusFragment.newInstance();
            case TAG_AIRPORT_MRT:
                return  AirportMrtFragment.newInstance();
            case TAG_SKY_TRAIN:
                return  SkyTrainFragment.newInstance();
            case TAG_PARK_INFO:
                return  ParkingInfoFragment.newInstance();
            case TAG_AIRPORT_SECRETARY:
                return  AirportSecretaryFragment.newInstance();
            case TAG_HOME_DEPARTURE_FLIGHTS:
                return  DepartureFlightsFragment.newInstance();
            case TAG_HOME_ARRIVE_FLIGHTS:
                return  ArriveFlightsFragment.newInstance();
            case TAG_HOME_PARKING_INFO:
                return  HomeParkingInfoFragment.newInstance();
            case TAG_HOME_WEATHER_INFO:
                return  HomeWeatherInfoFragment.newInstance();
            case TAG_HOME_MORE_FLIGHTS:
                return  MoreFlightsFragment.newInstance();
            case TAG_HOME_MORE_WEATHER:
                return  MoreWeatherFragment.newInstance();
            case TAG_AIRPORT_USER_NEWS:
                return  AirportUserNewsFragment.newInstance();
            case TAG_AIRPORT_EMERGENCY:
                return  AirportEmergencyFragment.newInstance();
            case TAG_AIRPORT_SWEET_NOTIFY:
                return  AirportSweetNotifyFragment.newInstance();
            case TAG_MY_FIGHTS_NOTIFY:
                return  MyFlightsNotifyFragment.newInstance();
            case TAG_AIRPORT_NEWS_DETAIL:
                return  NewsDetailFragment.newInstance();
            case TAG_AIRPORT_EMERGENCY_DETAIL:
                return  EmergencyDetailFragment.newInstance();
            case TAG_AIRPORT_SWEET_DETAIL:
                return  SweetNotifyDetailFragment.newInstance();
            case TAG_AIRPORT_FACILITY_DETAIL:
                return  FacilityDetailFragment.newInstance();
            case TAG_USERFUL_INFO:
                return  UsefulInfoFragment.newInstance();
            case TAG_USERFUL_LOST:
                return  LostAndFoundFragment.newInstance();
            case TAG_STORE_OFFERS:
                return  StoreOffersFragment.newInstance();
            case TAG_COMMUNICATION_SERVICE:
                return  CommunicationFragment.newInstance();
            case TAG_USERFUL_CURRENCY_CONVERSION:
                return  CurrencyConversionFragment.newInstance();
            case TAG_USERFUL_QUEST:
                return  QuestionnaireFragment.newInstance();
            case TAG_USERFUL_TIMEZONE:
                return  TimeZoneQueryFragment.newInstance();
            case TAG_USERFUL_LANGUAGE:
                return  TravelLanguageFragment.newInstance();
            case TAG_USERFUL_LANGUAGE_RESULT:
                return  TravelLanguageResultFragment.newInstance();
            case TAG_COMMUNICATION_INTERNATIONAL_CALL:
                return  InternationalCallFragment.newInstance();
            case TAG_COMMUNICATION_EMERGENCY_CALL:
                return  EmergencyCallFragment.newInstance();
            case TAG_COMMUNICATION_ROAMING_SERVICE:
                return  RoamingServiceFragment.newInstance();
            case TAG_COMMUNICATION_ROAMING_DETAIL:
                return  RoamingDetailFragment.newInstance();
            case TAG_COMMUNICATION_ROAMING_WEBVIEW:
                return  RoamingWebViewFragment.newInstance();
            case TAG_LANGUAGE_SETTING:
                return  LanguageSettingFragment.newInstance();
            case TAG_SOUVENIR_AREA:
                return  SouvenirAreaFragment.newInstance();
            case TAG_SOUVENIR_DETAIL:
                return  SouvenirDetailFragment.newInstance();
            case TAG_ACHIEVEMENT:
                return  AchievementFragment.newInstance();
            case TAG_ACHIEVEMENT_DETAIL:
                return  AchievementDetailFragment.newInstance();

        }

        return null;
    }
}
