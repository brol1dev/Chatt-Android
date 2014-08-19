package qcodemx.com.chatt;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import qcodemx.com.chatt.data.PreferencesManager;
import qcodemx.com.chatt.data.api.UserToken;
import qcodemx.com.chatt.model.DrawerItem;
import qcodemx.com.chatt.ui.widget.LeftNavAdapter;

/**
 * Created by Eric Vargas on 8/8/14.
 *
 * Activity shown when a user is authenticated.
 */
public class MainActivity extends CTActivity {
    private static final String LOG_TAG = "MainActivity";

    private static final int EVENTS_IDX = 1;
    private static final int LOGOUT_IDX = 2;

    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @InjectView(R.id.left_drawer) ListView drawerLeft;

    private ActionBarDrawerToggle drawerToggle;

    @Inject PreferencesManager preferencesManager;
    UserToken userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        userToken = preferencesManager.retrieveCurrentUser();
        Log.d(LOG_TAG, "current user: " + userToken);

        setupActionBar();
        setupContainer();
        setupDrawer();
    }

    protected void setupActionBar()
    {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setLogo(R.drawable.icon);
//        actionBar.setBackgroundDrawable(getResources().getDrawable(
//                R.drawable.actionbar_bg));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    /**
     * Setup the drawer layout. This method also includes the method calls for
     * setting up the Left side drawer.
     */
    private void setupDrawer()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View view)
            {
                setActionBarTitle();
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                getSupportActionBar().setTitle("My Out Life");
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.closeDrawers();

        setupLeftNavDrawer();
    }

    /**
     * Setup the left navigation drawer/slider. You can add your logic to load
     * the contents to be displayed on the left side drawer. You can also setup
     * the Header and Footer contents of left drawer if you need them.
     */
    private void setupLeftNavDrawer()
    {
        drawerLeft = (ListView) findViewById(R.id.left_drawer);

        View header = getLayoutInflater().inflate(R.layout.left_nav_header,
                null);
        TextView emailTextView = (TextView) header.findViewById(R.id.text_mail);
        Log.d(LOG_TAG, "email: " + userToken.getUser().getEmail());
        emailTextView.setText(userToken.getUser().getEmail());
        drawerLeft.addHeaderView(header);

        drawerLeft.setAdapter(new LeftNavAdapter(this, getDummyLeftNavItems()));
        drawerLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3)
            {
                drawerLayout.closeDrawers();
                launchFragment(pos);
            }
        });
        drawerLayout.openDrawer(drawerLeft);
    }

    /**
     * This method returns a list of dummy items for left navigation slider. You
     * can write or replace this method with the actual implementation for list
     * items.
     *
     * @return the dummy items
     */
    private ArrayList<DrawerItem> getDummyLeftNavItems()
    {
        ArrayList<DrawerItem> al = new ArrayList<>();
        al.add(new DrawerItem("Events", R.drawable.ic_projects));
        al.add(new DrawerItem("Logout", R.drawable.ic_logout));
        return al;
    }

    /**
     * This method can be used to attach Fragment on activity view for a
     * particular tab position. You can customize this method as per your need.
     *
     * @param pos
     *            the position of tab selected.
     */
    private void launchFragment(int pos)
    {
        Fragment f = null;
        String title = null;
        if (pos == EVENTS_IDX) {
            title = "Events";
            f = new EventListFragment();
        } else if (pos == LOGOUT_IDX)
        {
            preferencesManager.clearUser();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        if (f != null)
        {
            while (getSupportFragmentManager().getBackStackEntryCount() > 0)
            {
                getSupportFragmentManager().popBackStackImmediate();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, f).addToBackStack(title)
                    .commit();
        }
    }

    /**
     * Setup the container fragment for drawer layout. The current
     * implementation of this method simply calls launchFragment method for tab
     * position 1 as the position 0 is for List header view. You can customize
     * this method as per your need to display specific content.
     */
    private void setupContainer()
    {
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {

                    @Override
                    public void onBackStackChanged()
                    {
                        setActionBarTitle();
                    }
                });
        launchFragment(EVENTS_IDX);
    }

    /**
     * Set the action bar title text.
     */
    private void setActionBarTitle()
    {
        if (drawerLayout.isDrawerOpen(drawerLeft))
        {
//            getSupportActionBar().setTitle(R.string.app_name);
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            return;
        String title = getSupportFragmentManager().getBackStackEntryAt(
                getSupportFragmentManager().getBackStackEntryCount() - 1)
                .getName();
        getSupportActionBar().setTitle(title);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPostCreate(android.os.Bundle)
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* (non-Javadoc)
     * @see com.newsfeeder.custom.CustomActivity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            {
                getSupportFragmentManager().popBackStackImmediate();
            }
            else
                finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
