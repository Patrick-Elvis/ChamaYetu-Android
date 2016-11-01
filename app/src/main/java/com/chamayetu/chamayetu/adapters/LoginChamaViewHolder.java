package com.chamayetu.chamayetu.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.chamayetu.chamayetu.R;
import com.chamayetu.chamayetu.models.LoginChamaModel;

import static com.chamayetu.chamayetu.utils.Contract.LOGINCHAMA_TAG;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.adapters
 * Created by lusinabrian on 28/10/16.
 * Description: ViewHolder for the RecyclerView for {@link com.chamayetu.chamayetu.auth.loginchama.LoginChamaActivity}
 */

public class LoginChamaViewHolder extends RecyclerView.ViewHolder{

    public TextView chamaName;

    public LoginChamaViewHolder(View itemView) {
        super(itemView);
        chamaName = (TextView) itemView.findViewById(R.id.chama_login_item_name);
    }

    public void bind(LoginChamaModel loginChamaModel){
        chamaName.setText(loginChamaModel.getChamaGroups());
    }
}
