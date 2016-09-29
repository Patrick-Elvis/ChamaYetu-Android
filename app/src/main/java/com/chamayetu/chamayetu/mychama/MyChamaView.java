package com.chamayetu.chamayetu.mychama;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chamayetu.chamayetu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.mychama
 * Created by lusinabrian on 28/09/16.
 * Description: View that will handle Chama Details and data for the user's chama
 */

public class MyChamaView extends Fragment {
    @BindView(R.id.statement_from_field) TextView dateFrom;
    @BindView(R.id.statement_to_field) TextView dateTo;
    public MyChamaView() {}

    public static Fragment newInstance(){
        MyChamaView myChamaView = new MyChamaView();
        myChamaView.setRetainInstance(true);
        return myChamaView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mychama_view, container, false);
        ButterKnife.bind(rootView);
        return rootView;
    }

}
