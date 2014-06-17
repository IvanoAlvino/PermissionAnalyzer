package com.permisisonanalyzer.permissionanalyzer;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;


public class Permission_view extends Activity {

    String[] badPermissions = new String[] {"android.permission.INTERNET","android.permission.AUTHENTICATE_ACCOUNTS",
                                "android.permission.READ_CALL_LOG", "android.permission.READ_LOGS", "android.permission.READ_CONTACTS",
                                "android.permission.WRITE_SECURE_SETTINGS", "android.permission.PROCESS_OUTGOING_CALLS",
                                "android.permission.SEND_SMS", "android.permission.READ_SOCIAL_STREAM"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_view);

        // Definitions
        PackageManager pm = getPackageManager();
        ArrayList<String> valori = new ArrayList<String>();
        ListView listview = (ListView) findViewById(R.id.listview);

        // Retrieve all applications installed by user
        int flags = PackageManager.GET_META_DATA |
                    PackageManager.GET_SHARED_LIBRARY_FILES |
                    PackageManager.GET_UNINSTALLED_PACKAGES;
        List<ApplicationInfo> installed_packages = pm.getInstalledApplications(flags);

        for ( ApplicationInfo appInfo : installed_packages ) {
            // Select only the application installed by user
            if ( (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 ) {
                // check if possess a dangerous permission
                try {
                    PackageInfo pkgInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS);
                    String[] requested_permission = pkgInfo.requestedPermissions;
                    if ( dangerous_permission(requested_permission)) {
                        // found an application with dangerous permission
                        valori.add( appInfo.loadLabel(pm).toString() );     // add application name in valori
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        // Create the display of the list throught a standard adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, valori);
        listview.setAdapter(adapter);
    }

    private boolean dangerous_permission(String[] req_permissions) {
        boolean found = false;

        if ( req_permissions != null ) {
            for (int i = 0; i < req_permissions.length && !found; i++) {
                for (int j = 0; j < badPermissions.length && !found; j++) {
                    if (req_permissions[i].equals(badPermissions[j])) {
                        found = true;
                    }
                }
            }
        }

        return found;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.permission_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
