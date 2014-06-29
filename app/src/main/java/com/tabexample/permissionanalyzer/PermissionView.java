package com.tabexample.permissionanalyzer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tabexample.app.R;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by ivano on 29/06/14.
 */
public class PermissionView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_view);

        ListView listview = (ListView) findViewById(R.id.list_permissions);

        // retrieve the permission list
        String[] pl = getIntent().getExtras().getStringArray("permissions");
        ArrayList permissionList = new ArrayList( Arrays.asList(pl));

        // cutting first part of string, for increased readibility
        for ( int i=0; i<permissionList.size(); i++) {
            String a = (String) permissionList.get(i);
         }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, permissionList);
        listview.setAdapter(adapter);
        // set the context menu for longclick
        registerForContextMenu(listview);
    }


    /*
         ** MENU
          * Menu for longClick on Items, it shows the
          * Blog page with all the permissions

     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        // when clicked a permission, send to the browser the hyperlink related
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://androidmobileprogrammingugr.blogspot.com.es/2014/06/permisos-de-android.html"));
        startActivity(browserIntent);
        return super.onContextItemSelected(item);
    }
}
