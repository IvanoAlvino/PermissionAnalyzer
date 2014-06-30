package com.tabexample.permissionanalyzer;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;

import com.tabexample.app.R;

import java.util.ArrayList;
import java.util.List;


public class TabExample extends FragmentActivity implements ActionBar.TabListener {

    ViewPager viewPager;
    ActionBar actionBar;
    String[] badPermissions = new String[]{"android.permission.INTERNET", "android.permission.AUTHENTICATE_ACCOUNTS",
            "android.permission.READ_CALL_LOG", "android.permission.READ_LOGS", "android.permission.READ_CONTACTS",
            "android.permission.WRITE_SECURE_SETTINGS", "android.permission.PROCESS_OUTGOING_CALLS",
            "android.permission.SEND_SMS", "android.permission.READ_SOCIAL_STREAM"};
    ArrayList<ApplicationInfo> dangerousApps = new ArrayList<ApplicationInfo>();
    ArrayList<String> appNameList = new ArrayList<String>();
    FragmentManager fm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_example);

        /*
            Collect informations about application installed
        */

        // Definitions
        PackageManager pm = getPackageManager();

        // Retrieve all the application installed
        int flags = PackageManager.GET_META_DATA |
                PackageManager.GET_SHARED_LIBRARY_FILES |
                PackageManager.GET_UNINSTALLED_PACKAGES;
        final List<ApplicationInfo> installed_packages = pm.getInstalledApplications(flags);

        // Filter all application selecting only application installed by user
        for (ApplicationInfo appInfo : installed_packages) {
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                // Check if possess a dangerous permission
                try {
                    PackageInfo pkgInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS);
                    String[] requested_permission = pkgInfo.requestedPermissions;
                    if (dangerous_permission(requested_permission)) {
                        // Found an application with dangerous permission
                        dangerousApps.add(appInfo);
                        appNameList.add(appInfo.loadLabel(pm).toString());     // add application name in valori
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        /*
            Here we got:
               * dangerousApps = an array with all the user installed application whit dangerous permissions
               * appNameList = a String Array with all the names of the dangerous App
         */

        // get the ViewPager handle
        viewPager = (ViewPager) findViewById(R.id.pager);
        fm = getSupportFragmentManager();
        viewPager.setAdapter(new MyPageAdapter(fm, dangerousApps, appNameList));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // create the actionBar and sets navigation mode to TABS
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // create TAB 1
        ActionBar.Tab list_tab = actionBar.newTab();
        list_tab.setText(R.string.scary_app);
        list_tab.setTabListener(this);

        // create TAB 2
        ActionBar.Tab map_tab = actionBar.newTab();
        map_tab.setText(R.string.permission);
        map_tab.setTabListener(this);

        // add TABS to actionBar
        actionBar.addTab(list_tab);
        actionBar.addTab(map_tab);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    private boolean dangerous_permission(String[] req_permissions) {
        boolean found = false;
        if (req_permissions != null)
            for (int i = 0; i < req_permissions.length && !found; i++)
                for (int j = 0; j < badPermissions.length && !found; j++)
                    if (req_permissions[i].equals(badPermissions[j]))
                        found = true;
        return found;
    }
}


    /*
        MY PAGE ADAPTER CLASS
        A class which works as a Adapter to
        retrieve a fragment when user clicks on the actionbar tab
     */
class MyPageAdapter extends FragmentPagerAdapter {

    ArrayList<ApplicationInfo> dangerousApps;
    ArrayList<String> appNameList;

    MyPageAdapter(FragmentManager fm, ArrayList<ApplicationInfo> dApp, ArrayList<String> appNames) {
        super(fm);
        dangerousApps = dApp;
        appNameList = appNames;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (position) {
            case 0: fragment = new ScaryAppFragment();
                    bundle.putSerializable("appNameList", appNameList);
                    bundle.putSerializable("dangerousApps", dangerousApps);
                    fragment.setArguments(bundle);
                break;
            case 1: fragment = new PermissionFragment();
                    bundle.putSerializable("dangerousApps", dangerousApps);
                    fragment.setArguments(bundle);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}


