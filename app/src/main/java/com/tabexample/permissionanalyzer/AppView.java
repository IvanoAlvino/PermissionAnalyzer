package com.tabexample.permissionanalyzer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tabexample.app.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ivano on 30/06/14.
 */
public class AppView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_view);

        ListView listview = (ListView) findViewById(R.id.list_apps);

        // retrieve the app list
        ArrayList<String> appsList = (ArrayList<String>) getIntent().getExtras().getSerializable("apps");

        // if list is empty, add a standard message
        if ( appsList.size() == 0 ) {
            appsList.add(new String("This permission is not used by any application"));
        }

        // display the list through an adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, appsList);
        listview.setAdapter(adapter);
    }
}
