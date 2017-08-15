package com.whatmedia.ttia.page.main;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyMarquee;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.enums.FlightInfo;
import com.whatmedia.ttia.enums.HomeFeature;
import com.whatmedia.ttia.enums.LanguageSetting;
import com.whatmedia.ttia.page.BaseActivity;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.communication.CommunicationFragment;
import com.whatmedia.ttia.page.main.communication.emergency.EmergencyCallFragment;
import com.whatmedia.ttia.page.main.communication.international.InternationalCallFragment;
import com.whatmedia.ttia.page.main.communication.roaming.RoamingServiceFragment;
import com.whatmedia.ttia.page.main.flights.info.FlightsInfoFragment;
import com.whatmedia.ttia.page.main.flights.my.MyFlightsInfoFragment;
import com.whatmedia.ttia.page.main.flights.notify.MyFlightsNotifyFragment;
import com.whatmedia.ttia.page.main.flights.result.FlightsSearchResultFragment;
import com.whatmedia.ttia.page.main.flights.search.FlightsSearchFragment;
import com.whatmedia.ttia.page.main.home.HomeFragment;
import com.whatmedia.ttia.page.main.home.moreflights.MoreFlightsContract;
import com.whatmedia.ttia.page.main.home.moreflights.MoreFlightsFragment;
import com.whatmedia.ttia.page.main.home.weather.more.MoreWeatherFragment;
import com.whatmedia.ttia.page.main.language.LanguageSettingFragment;
import com.whatmedia.ttia.page.main.secretary.AirportSecretaryFragment;
import com.whatmedia.ttia.page.main.secretary.detail.emergency.EmergencyDetailFragment;
import com.whatmedia.ttia.page.main.secretary.detail.news.NewsDetailFragment;
import com.whatmedia.ttia.page.main.secretary.detail.sweet.SweetNotifyDetailFragment;
import com.whatmedia.ttia.page.main.secretary.emergency.AirportEmergencyFragment;
import com.whatmedia.ttia.page.main.secretary.news.AirportUserNewsFragment;
import com.whatmedia.ttia.page.main.secretary.sweet.AirportSweetNotifyFragment;
import com.whatmedia.ttia.page.main.store.StoreOffersFragment;
import com.whatmedia.ttia.page.main.terminals.facility.AirportFacilityFragment;
import com.whatmedia.ttia.page.main.terminals.info.TerminalInfoFragment;
import com.whatmedia.ttia.page.main.terminals.store.result.StoreSearchResultFragment;
import com.whatmedia.ttia.page.main.terminals.store.search.StoreSearchContract;
import com.whatmedia.ttia.page.main.terminals.store.search.StoreSearchFragment;
import com.whatmedia.ttia.page.main.terminals.toilet.PublicToiletFragment;
import com.whatmedia.ttia.page.main.traffic.AirportTrafficFragment;
import com.whatmedia.ttia.page.main.traffic.bus.AirportBusFragment;
import com.whatmedia.ttia.page.main.traffic.mrt.AirportMrtFragment;
import com.whatmedia.ttia.page.main.traffic.parking.ParkingInfoFragment;
import com.whatmedia.ttia.page.main.traffic.roadside.RoadsideAssistanceFragment;
import com.whatmedia.ttia.page.main.traffic.skytrain.SkyTrainFragment;
import com.whatmedia.ttia.page.main.traffic.taxi.TaxiFragment;
import com.whatmedia.ttia.page.main.traffic.tourbus.TourBusFragment;
import com.whatmedia.ttia.page.main.useful.currency.CurrencyConversionFragment;
import com.whatmedia.ttia.page.main.useful.info.UsefulInfoFragment;
import com.whatmedia.ttia.page.main.useful.language.TravelLanguageFragment;
import com.whatmedia.ttia.page.main.useful.lost.LostAndFoundFragment;
import com.whatmedia.ttia.page.main.useful.questionnaire.QuestionnaireFragment;
import com.whatmedia.ttia.page.main.useful.timezone.TimeZoneQueryFragment;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.utility.Util;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IActivityTools.ILoadingView, IActivityTools.IMainActivity, FragmentManager.OnBackStackChangedListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    public final static String TAG_DATA = "data";

    @BindView(R.id.myToolbar)
    MyToolbar mMyToolbar;
    @BindView(R.id.loadingView)
    FrameLayout mLoadingView;
    @BindView(R.id.myMarquee)
    MyMarquee mMyMarquee;
    @BindView(R.id.imageView_home)
    ImageView mImageViewHome;

    private String mMarqueeMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setMarqueeHomeState();

        Page.clearBackStack(MainActivity.this);
        Page.setBackStackChangedListener(this, this);
        addFragment(Page.TAG_HOME, null, false);

        mImageViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Page.clearBackStack(MainActivity.this);
                addFragment(Page.TAG_HOME, null, false);
            }
        });
    }

    @Override
    public void showLoadingView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void goneLoadingView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void replaceFragment(int page, Bundle bundle, boolean backStack) {
        Page.switchFragment(this, page, bundle, backStack);
    }

    @Override
    public void addFragment(int page, Bundle bundle, boolean backStack) {
        Page.addFragment(this, page, bundle, backStack);
    }

    @Override
    public MyToolbar getMyToolbar() {
        return mMyToolbar;
    }

    @Override
    public MyMarquee getMyMarquee() {
        return mMyMarquee;
    }

    @Override
    public void setMarqueeMessage(String subMessage) {
        mMarqueeMessage = getString(R.string.marquee_parking_info, subMessage);
    }

    @Override
    public void backPress() {
        onBackPressed();
    }

    @Override
    public boolean getUserVisibility() {
        // TODO: 2017/8/10
        return false;
    }

    @Override
    public void runOnUI(Runnable runnable) {
        runOnUiThread(runnable);
    }


    @Override
    public void onBackStackChanged() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.layout_container);
        if (fragment != null) {
            Log.i(TAG, "Current fragment = " + fragment);
            Log.i(TAG, "getBackStackEntryCount = " + getSupportFragmentManager().getBackStackEntryCount());

            //除了Home 以外頁面的跑馬燈
            setMarqueeSubState();
            if (fragment instanceof HomeFragment) {//Home
                setMarqueeHomeState();
                mMyToolbar.clearState()
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorBackgroundHomeDeparture))
                        .setBackIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.refresh))
                        .setLeftText(getString(R.string.tableview_header_takeoff, Util.getNowDate(Util.TAG_FORMAT_MD)))
                        .setLeftIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.up))
                        .setRightText(getString(R.string.home_more_flights))
                        .setMoreIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.home_more))
                        .setOnMoreClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString(MoreFlightsContract.TAG_KIND, FlightsInfoData.TAG_KIND_DEPARTURE);
                                addFragment(Page.TAG_HOME_MORE_FLIGHTS, bundle, true);
                            }
                        })
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Page.clearBackStack(MainActivity.this);
                                addFragment(Page.TAG_HOME, null, false);
                            }
                        });
            } else if (fragment instanceof FlightsInfoFragment) {//航班資訊
                mMyToolbar.clearState()
                        .setTitleText(getString(HomeFeature.TAG_FLIGHTS_INFO.getTitle()))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof FlightsSearchFragment) {//航班搜尋
                mMyToolbar.clearState()
                        .setTitleText(getString(FlightInfo.TAG_SEARCH_FLIGHTS.getTitle()))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof FlightsSearchResultFragment) {//航班搜尋結果
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.navi_title_flight_search_result))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof TerminalInfoFragment) {//航廈資訊
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.home_terminal_info_title))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof PublicToiletFragment) {//公共廁所
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_restroom))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof AirportFacilityFragment) {//機場設施
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_airport_facility))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof StoreSearchFragment) {//餐廳與商店搜尋. StoreSearchContract.TAG_FROM_PAGE =  Page.TAG_STORE_OFFERS => 商店資訊
                mMyToolbar.clearState()
                        .setTitleText((fragment.getArguments().getInt(StoreSearchContract.TAG_FROM_PAGE) != 0 &&
                                fragment.getArguments().getInt(StoreSearchContract.TAG_FROM_PAGE) == Page.TAG_STORE_OFFERS) ?
                                getString(R.string.store_offers_info) : getString(R.string.title_restaurant))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });

            } else if (fragment instanceof StoreSearchResultFragment) {//餐廳與商店搜尋結果
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.restaurant_store_search_result_subtitle))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });

            } else if (fragment instanceof AirportTrafficFragment) {//機場交通
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_traffic))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });

            } else if (fragment instanceof AirportBusFragment) {//機場巴士
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_airport_bus))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });

            } else if (fragment instanceof RoadsideAssistanceFragment) {//道路救援服務
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_road_rescue))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });

            } else if (fragment instanceof TaxiFragment) {//計程車
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_taxi))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });

            } else if (fragment instanceof TourBusFragment) {//巡迴巴士
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_tourist_bus))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });

            } else if (fragment instanceof AirportMrtFragment) {//機場高鐵/捷運
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_mrt_hsr))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof SkyTrainFragment) {//電車
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_tram_car))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof ParkingInfoFragment) {//停車資訊
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_parking_infomation))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof AirportSecretaryFragment) {//機場秘書
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.home_airport_secretary_title))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof MoreWeatherFragment) {//一週天氣
//                mMyToolbar.clearState()
//                        .setTitleText(getString(R.string.home_weather_title))
//                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
//                        .setMoreLayoutVisibility(View.GONE)
//                        .setRightText(getString(R.string.currency_conversion_other_area))
//                        .setAreaLayoutVisibility(View.VISIBLE)
//                        .setBackVisibility(View.VISIBLE)
//                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                switch (v.getId()) {
//                                    case R.id.imageView_back:
//                                        backPress();
//                                        break;
//                                }
//                            }
//                        });
            } else if (fragment instanceof AirportUserNewsFragment) {//使用者最新消息
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.airport_secretary_news))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof AirportEmergencyFragment) {//使用者緊急通知
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.airport_secretary_emergency))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof AirportSweetNotifyFragment) {//使用者貼心提醒
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.airport_secretary_notify))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof MyFlightsNotifyFragment) {//航班提醒
                /*****************************************
                 *
                 *
                 * Setting in MyFlightsNotifyFragment
                 *
                 *
                 ****************************************/
            } else if (fragment instanceof MyFlightsInfoFragment) {//我的航班
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_my_flight))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof MoreFlightsFragment) {//更多航班
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.home_more_flights))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof NewsDetailFragment) {//最新訊息(詳細資料)
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.airport_secretary_news))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof EmergencyDetailFragment) {//緊急通知(詳細資料)
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.airport_secretary_emergency))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof SweetNotifyDetailFragment) {//貼心提醒(詳細資料)
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.airport_secretary_notify))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof UsefulInfoFragment) {//實用資訊(目錄)
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_utility))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof StoreOffersFragment) {//商店優惠
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.home_store_offers_title))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof LostAndFoundFragment) {//失物協尋
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_lost_found))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof CommunicationFragment) {//通訊服務
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_communication))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof CurrencyConversionFragment) {//匯率換算
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_currency))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof QuestionnaireFragment) {//問卷調查
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_feedback))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof TravelLanguageFragment) {//旅行外文
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_conversation))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof TimeZoneQueryFragment) {//時區查詢
//                mMyToolbar.clearState()
//                        .setTitleText(getString(R.string.useful_info_timezone))
//                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
//                        .setBackVisibility(View.VISIBLE)
//                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                switch (v.getId()) {
//                                    case R.id.imageView_back:
//                                        backPress();
//                                        break;
//                                }
//                            }
//                        });
            } else if (fragment instanceof InternationalCallFragment) {//國際電話
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_international_call))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof EmergencyCallFragment) {//緊急電話
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_emergency_phone))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof RoamingServiceFragment) {//漫遊服務
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_roaming_service))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            } else if (fragment instanceof LanguageSettingFragment) {//語言
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.home_language_setting_title))
                        .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorSubTitle))
                        .setBackVisibility(View.VISIBLE)
                        .setOnBackClickListener(new MyToolbar.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (v.getId()) {
                                    case R.id.imageView_back:
                                        backPress();
                                        break;
                                }
                            }
                        });
            }

        } else
            Log.e(TAG, "Fragment is null : ");
    }

    /**
     * 設置除了Home以外的跑馬燈
     */
    private void setMarqueeSubState() {
        getMarqueeString();
        mMyMarquee.clearState()
                .setMessage(mMarqueeMessage)
                .setIconVisibility(View.GONE);
        mImageViewHome.setVisibility(View.VISIBLE);
    }

    /**
     * 設置除了Home page跑馬燈
     */
    private void setMarqueeHomeState() {
        getMarqueeString();
        mMyMarquee.clearState()
                .setMessage(mMarqueeMessage)
                .setIcon(R.drawable.marquee_new);
        mImageViewHome.setVisibility(View.GONE);
    }

    /**
     * Get Marquee String
     */
    private void getMarqueeString() {
        mMarqueeMessage = getString(R.string.marquee_parking_info, Util.getMarqueeSubMessage(getApplicationContext()));
    }
}
