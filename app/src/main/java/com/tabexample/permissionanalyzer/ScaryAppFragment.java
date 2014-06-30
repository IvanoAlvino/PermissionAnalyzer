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


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ScaryAppFragment extends Fragment {

    // array to store al the dangerous applications
    ArrayList<String> appNameList = new ArrayList<String>();
    ArrayList<ApplicationInfo> dangerousApps = new ArrayList<ApplicationInfo>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scary_app, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Definitions
        ListView listview = (ListView) getActivity().findViewById(R.id.listview);

        // Retrieve bundles
        appNameList = (ArrayList<String>) getArguments().getSerializable("appNameList");
        dangerousApps = (ArrayList<ApplicationInfo>) getArguments().getSerializable("dangerousApps");

        // Create the display of the list throught a standard adapter
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, appNameList);
        listview.setAdapter(adapter);

        // create the onClick listener
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PackageManager pm = getActivity().getPackageManager();

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
    } /* onActivityCreated end */

}
