package com.tabexample.permissionanalyzer;



import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tabexample.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ScaryAppFragment extends Fragment {

    String[] badPermissions = new String[] {"android.permission.INTERNET","android.permission.AUTHENTICATE_ACCOUNTS",
            "android.permission.READ_CALL_LOG", "android.permission.READ_LOGS", "android.permission.READ_CONTACTS",
            "android.permission.WRITE_SECURE_SETTINGS", "android.permission.PROCESS_OUTGOING_CALLS",
            "android.permission.SEND_SMS", "android.permission.READ_SOCIAL_STREAM"};

    // array to store al the dangerous applications
    ArrayList<ApplicationInfo> dangerousApps = new ArrayList<ApplicationInfo>();
    PackageManager pm = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scary_app, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Definitions
        pm = getActivity().getPackageManager();
        ArrayList<String> valori = new ArrayList<String>();
        ListView listview = (ListView) getActivity().findViewById(R.id.listview);

        // Retrieve all applications installed
        int flags = PackageManager.GET_META_DATA |
                PackageManager.GET_SHARED_LIBRARY_FILES |
                PackageManager.GET_UNINSTALLED_PACKAGES;
        final List<ApplicationInfo> installed_packages = pm.getInstalledApplications(flags);

        for ( ApplicationInfo appInfo : installed_packages ) {
            // Select only the application installed by user
            if ( (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 ) {
                // check if possess a dangerous permission
                try {
                    PackageInfo pkgInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS);
                    String[] requested_permission = pkgInfo.requestedPermissions;
                    if ( dangerous_permission(requested_permission)) {
                        // found an application with dangerous permission
                        dangerousApps.add(appInfo);
                        valori.add( appInfo.loadLabel(pm).toString() );     // add application name in valori
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        // Create the display of the list throught a standard adapter
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, valori);
        listview.setAdapter(adapter);

        // create the onClick listener
        listview.setOnItemClickListener( new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // get the app clicked
                ApplicationInfo app = dangerousApps.get(position);

                // retrieve informations about his permissions
                try {
                    // creating array with all permission
                    PackageInfo pkgInfo = pm.getPackageInfo(app.packageName, PackageManager.GET_PERMISSIONS);
                    String[] requested_permission = pkgInfo.requestedPermissions;
                    Arrays.sort(requested_permission);

                    /* Now in requested_permission I got all the permissions this app needs
                       Calling a new activity which will display the results into a ListView */

                    // passing the requested_permission array to new activity
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("permissions", requested_permission);

                    // calling new activity
                    Intent i = new Intent(getActivity(), PermissionView.class);
                    i.putExtras(bundle);
                    startActivity(i);

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
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
}
