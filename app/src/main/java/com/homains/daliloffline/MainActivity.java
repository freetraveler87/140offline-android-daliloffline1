package com.homains.daliloffline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.homains.daliloffline.R;
import com.homains.daliloffline.rss.ServiceStarter;
import com.homains.daliloffline.web.WebviewFragment;

public class MainActivity extends ActionBarActivity implements NavDrawerCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    public static String DATA = "transaction_data";
    SharedPreferences prefs;
    String mWebUrl = null;
    boolean openedByBackPress = false;
    private Toolbar mToolbar;
    private NavDrawerFragment mNavigationDrawerFragment;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 0;
    private boolean mIsResolving = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Build GoogleApiClient with access to basic profile
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();

        boolean newDrawer = getResources().getBoolean(R.bool.newdrawer);

        if (newDrawer == true) {
            setContentView(R.layout.activity_main_alternate);
        } else {
            setContentView(R.layout.activity_main);
            Helper.setStatusBarColor(MainActivity.this, getResources().getColor(R.color.myPrimaryDarkColor));
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationDrawerFragment = (NavDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);

        if (newDrawer == true) {
            mNavigationDrawerFragment.setup(R.id.scrimInsetsFrameLayout, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
            mNavigationDrawerFragment.getDrawerLayout().setStatusBarBackgroundColor(
                    getResources().getColor(R.color.myPrimaryDarkColor));
        } else {
            mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        }

        prefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        //setting push enabled
        String push = getString(R.string.rss_push_url);
        if (null != push && !push.equals("")) {
            // Create object of SharedPreferences.
            boolean firstStart = prefs.getBoolean("firstStart", true);

            if (firstStart) {

                final ServiceStarter alarm = new ServiceStarter();

                SharedPreferences.Editor editor = prefs.edit();

                alarm.setAlarm(this);
                //now, just to be sure, where going to set a value to check if notifications is really enabled
                editor.putBoolean("firstStart", false);
                //commits your edits
                editor.commit();
            }

        }

        //Checking if the user would prefer to show the menu on start
        boolean checkBox = prefs.getBoolean("menuOpenOnStart", false);
        if (checkBox == true && null == mWebUrl) {
            mNavigationDrawerFragment.openDrawer();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.rss_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, NavItem item) {
        Fragment fragment;
        try {
            fragment = item.getFragment().newInstance();
            if (fragment != null && null == mWebUrl) {
                //adding the data
                Bundle bundle = new Bundle();
                String extra = item.getData();
                bundle.putString(DATA, extra);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

                setTitle(item.getText());

                if (null != MainActivity.this.getSupportActionBar() && null != MainActivity.this.getSupportActionBar().getCustomView()) {
                    MainActivity.this.getSupportActionBar().setDisplayOptions(
                            ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
                }

            } else {
                // error in creating fragment
                Log.e("MainActivity", "Error in creating fragment");
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment webview = getSupportFragmentManager().findFragmentById(R.id.container);

        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else if (webview instanceof WebviewFragment) {
            boolean goback = ((WebviewFragment) webview).canGoBack();
            if (!goback)
                super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, connectionResult.getErrorCode());
                mIsResolving = true;
            } catch (Exception e) {
                //Log.e(TAG, "Could not resolve ConnectionResult.", e);
                mIsResolving = false;
                mGoogleApiClient.connect();
            }
        }
    }
}



