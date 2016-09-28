package com.chamayetu.chamayetu.useraccount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chamayetu.chamayetu.R;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.useraccount
 * Created by lusinabrian on 28/09/16.
 * Description: Fragment displaying the user's account
 */
public class MyAccountView extends Fragment{

    public MyAccountView(){}

    public static Fragment newInstance() {
        return new MyAccountView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myaccount_view, container, false);

        return rootView;
    }
}
