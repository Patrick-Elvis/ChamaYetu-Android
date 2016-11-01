package com.chamayetu.chamayetu.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.chamayetu.chamayetu.R;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.adapters
 * Created by lusinabrian on 28/10/16.
 * Description: ViewHolder for the RecyclerView for {@link com.chamayetu.chamayetu.auth.loginchama.LoginChamaActivity}
 */

public class LoginChamaViewHolder extends RecyclerView.ViewHolder{

    public TextView chamaName;
    public ImageView chamaImg;

    public LoginChamaViewHolder(View itemView) {
        super(itemView);
        chamaName = (TextView) itemView.findViewById(R.id.chama_login_item_name);
        chamaImg = (ImageView)itemView.findViewById(R.id.chama_login_item_img);

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
}
