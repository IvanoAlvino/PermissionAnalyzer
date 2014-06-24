package com.tabexample.permissionanalyzer;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tabexample.app.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PermissionFragment extends Fragment {


    String[] badPermissions = new String[] {"android.permission.INTERNET","android.permission.AUTHENTICATE_ACCOUNTS",
            "android.permission.READ_CALL_LOG", "android.permission.READ_LOGS", "android.permission.READ_CONTACTS",
            "android.permission.WRITE_SECURE_SETTINGS", "android.permission.PROCESS_OUTGOING_CALLS",
            "android.permission.SEND_SMS", "android.permission.READ_SOCIAL_STREAM"};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /* HYPERLINK
        TextView t2 = (TextView) getActivity().findViewById(R.id.infoTxtCredits);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        */

        // Definitions
        ArrayList<String> permissions = new ArrayList<String>();
        ListView listview = (ListView) getActivity().findViewById(R.id.list_permissions);

        // Filling the list of permissions with the permissions of badPermissions vector
        for (int i=0; i<badPermissions.length; i++) {
            permissions.add(badPermissions[i].substring(19));
        }

        // setting the adapter to the listview
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, permissions);
        listview.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permisos, container, false);
    }


}
