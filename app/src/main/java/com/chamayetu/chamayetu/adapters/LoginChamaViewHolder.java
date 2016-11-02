package com.chamayetu.chamayetu.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.chamayetu.chamayetu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.adapters
 * Created by lusinabrian on 28/10/16.
 * Description: ViewHolder for the RecyclerView for {@link com.chamayetu.chamayetu.auth.loginchama.LoginChamaActivity}
 */

public class LoginChamaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView chamaName;
    public ImageView chamaImg;
    public View mView;

    public LoginChamaViewHolder(View itemView) {
        super(itemView);
        chamaName = (TextView) itemView.findViewById(R.id.chama_login_item_name);
        chamaImg = (ImageView)itemView.findViewById(R.id.chama_login_item_img);
        mView = itemView;
    }

    public void bind(String loginChamaModel){
        chamaName.setText(loginChamaModel+ " account");
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color1 = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                    .textColor(Color.WHITE)
                    .bold()
                    .toUpperCase()
                .endConfig()
                .buildRound(
                        String.valueOf(loginChamaModel.charAt(0)),
                        color1
                );

        chamaImg.setImageDrawable(drawable);
    }


    @Override
    public void onClick(View v) {
        //accessing the username data from LoginActivity
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
