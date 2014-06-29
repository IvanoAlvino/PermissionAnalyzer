package com.tabexample.permissionanalyzer;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        // Definitions
        final ArrayList<String> permissions = new ArrayList<String>();
        ListView listview = (ListView) getActivity().findViewById(R.id.list_permissions);

        // setting the adapter to the listview
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, permissions);
        listview.setAdapter(adapter);

        // Filling the list of permissions with the permissions of badPermissions vector
        for (int i=0; i<badPermissions.length; i++) {
            permissions.add(badPermissions[i].substring(19));
        }

        // set the context menu for longclick
        registerForContextMenu(listview);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        // when clicked a permission, send to the browser the hyperlink related
        Intent browserIntent = null;
        switch(info.position) {
            case 0: browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                break;
            case 1: browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yahoo.com"));
                break;
            case 2: browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                break;
            case 3: browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                break;
            case 4: browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                break;
            case 5: browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                break;
            case 6: browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                break;
            case 7: browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                break;
            case 8: browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                break;
        }
        startActivity(browserIntent);
        return super.onContextItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permisos, container, false);
    }


}
