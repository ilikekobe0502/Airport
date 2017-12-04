package com.whatmedia.ttia.page.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.splunk.mint.Mint;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyMarquee;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.enums.FlightInfo;
import com.whatmedia.ttia.enums.HomeFeature;
import com.whatmedia.ttia.page.BaseActivity;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.Achievement.AchievementFragment;
import com.whatmedia.ttia.page.main.Achievement.Detail.AchievementDetailFragment;
import com.whatmedia.ttia.page.main.communication.CommunicationFragment;
import com.whatmedia.ttia.page.main.communication.emergency.EmergencyCallFragment;
import com.whatmedia.ttia.page.main.communication.international.InternationalCallFragment;
import com.whatmedia.ttia.page.main.communication.roaming.RoamingServiceFragment;
import com.whatmedia.ttia.page.main.flights.info.FlightsInfoFragment;
import com.whatmedia.ttia.page.main.flights.my.MyFlightsInfoFragment;
import com.whatmedia.ttia.page.main.flights.notify.MyFlightsNotifyFragment;
import com.whatmedia.ttia.page.main.flights.result.FlightsSearchResultFragment;
import com.whatmedia.ttia.page.main.flights.search.FlightsSearchFragment;
import com.whatmedia.ttia.page.main.home.HomeContract;
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
import com.whatmedia.ttia.page.main.store.souvenir.SouvenirAreaFragment;
import com.whatmedia.ttia.page.main.terminals.facility.AirportFacilityFragment;
import com.whatmedia.ttia.page.main.terminals.info.TerminalInfoFragment;
import com.whatmedia.ttia.page.main.terminals.store.result.StoreSearchResultContract;
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
import com.whatmedia.ttia.services.IBeacon;
import com.whatmedia.ttia.services.MyLocationService;
import com.whatmedia.ttia.utility.Preferences;
import com.whatmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IActivityTools.ILoadingView, IActivityTools.IMainActivity, FragmentManager.OnBackStackChangedListener {
    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String TAG_DEVICE_NAME = android.os.Build.MODEL;

    public static final String TAG_NOTIFICATION = "type";
    private static final String TAG_DEFAULT = "-1";


    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    public final static String TAG_DATA = "data";

    @BindView(R.id.myToolbar)
    MyToolbar mMyToolbar;
    @BindView(R.id.loadingView)
    FrameLayout mLoadingView;
    @BindView(R.id.myMarquee)
    MyMarquee mMyMarquee;
    @BindView(R.id.imageView_home)
    ImageView mImageViewHome;
    @BindView(R.id.view_top)
    View mViewTop;

    private String mMarqueeMessage;
    private boolean mPositionListening;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.initAndStartSession(this.getApplication(), "95cdb302");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        checkLayoutMode();

        String versionName = "Error";
        int versionCode = -1;
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Mint.logEvent(TAG_DEVICE_NAME + "(" + versionName + " ," + versionCode + ")");

        setMarqueeHomeState();


        Page.setBackStackChangedListener(this, this);
        if (getIntent() != null && getIntent().getExtras() != null && !TextUtils.isEmpty(getIntent().getExtras().getString(MainActivity.TAG_NOTIFICATION))) {
            gotoFistPage(getIntent().getExtras().getString(MainActivity.TAG_NOTIFICATION), false);
        } else
            gotoFistPage(TAG_DEFAULT, true);

        mImageViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoadingView != null && mImageViewHome.isShown()) {
                    mLoadingView.setVisibility(View.GONE);
                }
                gotoFistPage(TAG_DEFAULT, true);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check?
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.permission_title));
                builder.setMessage(getString(R.string.permission_message));
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                        }
                    }
                });
                builder.show();
            }
        }

        Intent beacons = new Intent(this, IBeacon.class);
        startService(beacons);

        Intent loactionService = new Intent(this, MyLocationService.class);
        startService(loactionService);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(getString(R.string.permission_reject_title));
                    builder.setMessage(getString(R.string.permission_reject_message));
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (!TextUtils.isEmpty(extras.getString(TAG_NOTIFICATION))) {
                String type = extras.getString(TAG_NOTIFICATION);
                gotoFistPage(type, false);
            }
        }
    }

    @Override
    protected void onPause() {
        mPositionListening = false;
        super.onPause();
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
                        .setRightText(getString(R.string.home_more))
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
                        .setTitleText(getString(R.string.tableview_header_takeoff, Util.getNowDate(Util.TAG_FORMAT_MD)))
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
                        .setTitleText(!TextUtils.isEmpty(fragment.getArguments().getString(StoreSearchResultContract.TAG_RESTAURANT_RESULT)) ? getString(R.string.restaurant_store_search_restaurant_result_subtitle) : getString(R.string.restaurant_store_search_result_subtitle))
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
                String date = Util.getNowDate(Util.TAG_FORMAT_MD);
                mMyToolbar.clearState()
                        .setTitleText(TextUtils.equals(fragment.getArguments().getString(MoreFlightsContract.TAG_KIND), FlightsInfoData.TAG_KIND_DEPARTURE) ?
                                getString(R.string.tableview_header_takeoff, date) :
                                getString(R.string.tableview_header_arrival, date))
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
                                        Util.hideSoftKeyboard(v);
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
            } else if (fragment instanceof SouvenirAreaFragment) {//紀念品專區
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.title_souvenirs))
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
            } else if (fragment instanceof AchievementFragment || fragment instanceof AchievementDetailFragment) {//紀念品專區
                mMyToolbar.clearState()
                        .setTitleText(getString(R.string.home_airport_achievement_title))
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

    @Override
    public void onBackPressed() {
        if (mLoadingView != null) {
            if (!mLoadingView.isShown())
                super.onBackPressed();
            else {
                mLoadingView.setVisibility(View.GONE);
                super.onBackPressed();
            }
        } else
            super.onBackPressed();
    }

    public void checkLayoutMode() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        float screenWidth = dm.widthPixels;
        float screenHeight = dm.heightPixels;

        if (screenHeight > screenWidth) {
            if (screenWidth / screenHeight >= (float) 2 / (float) 3) {
                Preferences.saveScreenMode(getBaseContext(), true);
            } else {
                Preferences.saveScreenMode(getBaseContext(), false);
            }
        } else {
            if (screenHeight / screenWidth >= (float) 2 / (float) 3) {
                Preferences.saveScreenMode(getBaseContext(), true);
            } else {
                Preferences.saveScreenMode(getBaseContext(), false);
            }
        }
    }

    /**
     * 切換第一頁
     *
     * @param type
     * @param clear
     */
    private void gotoFistPage(String type, boolean clear) {
        if (clear)
            Page.clearBackStack(MainActivity.this);
        Bundle bundle = null;
        if (!TextUtils.equals(type, "-1")) {
            bundle = new Bundle();
            bundle.putString(HomeContract.TAG_TYPE, type);
        }
        addFragment(Page.TAG_HOME, bundle, false);
    }

    /**
     * 設置最上面的View顏色
     * @param color
     */
    private void setTopViewColor(int color) {
        mViewTop.setBackgroundColor(color);
    }
}
