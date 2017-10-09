package com.ocv.testproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.facebook.drawee.backends.pipeline.Fresco;


public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener, InfoFragment.OnFragmentInteractionListener, SettingFragment.OnFragmentInteractionListener{



    FragmentManager fm;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private boolean useDarkTheme;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //TODO Follow the instructions below to complete this project
        /*
            Take the parsed items in the Arraylist 'items' and display
            them in a list via a RecyclerView. Each item, when clicked,
            should go to a detail page with the ability to show all of
            said items details and images (if there aren't any images it should
            note that as well in the detail page)

            IMO: Though a bit outdated at this point, Vogella has nice
            Android Tutorials.

            http://www.vogella.com/tutorials/android.html
         */
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
        else setTheme(R.style.Theme_AppCompat_Light_NoActionBar);

        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        setContentView(R.layout.main);

        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("useDarkTheme", useDarkTheme);
        listFragment.setArguments(bundle);
        ft.add(R.id.list_container, listFragment);
        ft.commit();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.back_arrow, null));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SettingFragment settingFragment = new SettingFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("useDarkTheme", useDarkTheme);
                settingFragment.setArguments(bundle);
                ft.replace(R.id.list_container, settingFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        //MenuItem share = menu.findItem(R.id.action_share);
        //mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(share);
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
