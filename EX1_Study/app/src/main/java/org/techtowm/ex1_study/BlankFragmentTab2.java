package org.techtowm.ex1_study;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BlankFragmentTab2 extends Fragment {

    public BlankFragmentTab2() {
        // Required empty public constructor
    }

    public static BlankFragmentTab1 newInstance() {
        BlankFragmentTab1 fragment = new BlankFragmentTab1();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_fragment_tab1, container, false);
    }

}