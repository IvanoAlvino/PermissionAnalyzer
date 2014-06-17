package com.tabexample.permissionanalyzer;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tabexample.app.R;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SafeAppFragment extends Fragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView t2 = (TextView) getActivity().findViewById(R.id.infoTxtCredits);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_safe_app, container, false);
    }


}
