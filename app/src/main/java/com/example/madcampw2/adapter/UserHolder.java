package com.example.madcampw2.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampw2.R;

public class UserHolder extends RecyclerView.ViewHolder {

    TextView id, name, nickname, school, major, number, phone, mail, mate, eval, img, imgM, logid, logpw, period, teclist, gender;
    ImageView profile;
    TextView sk1,sk2,sk3,sk4,sk5,sk6,sk7,sk8,sk9,sk10,sk11;

    public UserHolder(@NonNull View itemView){
        super(itemView);
        //id = itemView.findViewById(R.id.stdid);

        name = itemView.findViewById(R.id.name);
        nickname = itemView.findViewById(R.id.nickname);
        school = itemView.findViewById(R.id.school);
        major = itemView.findViewById(R.id.major);
        number = itemView.findViewById(R.id.stdid);
        phone = itemView.findViewById(R.id.phonenum);
        mail = itemView.findViewById(R.id.email);
        mate = itemView.findViewById(R.id.state);
        eval = itemView.findViewById(R.id.score);

        profile = itemView.findViewById(R.id.imageView);

        sk1 = itemView.findViewById(R.id.sk1);
        sk2 = itemView.findViewById(R.id.sk2);
        sk3 = itemView.findViewById(R.id.sk3);
        sk4 = itemView.findViewById(R.id.sk4);
        sk5 = itemView.findViewById(R.id.sk5);
        sk6 = itemView.findViewById(R.id.sk6);
        sk7 = itemView.findViewById(R.id.sk7);
        sk8 = itemView.findViewById(R.id.sk8);
        sk9 = itemView.findViewById(R.id.sk9);
        sk10 = itemView.findViewById(R.id.sk10);
        sk11 = itemView.findViewById(R.id.sk11);



        //img = itemView.findViewById(R.id.userListItem_Img);
        //imgM = itemView.findViewById(R.id.userListItem_ImgM);
        //logid = itemView.findViewById(R.id.userListItem_LogID);
        //logpw = itemView.findViewById(R.id.userListItem_LogPW);
        //period = itemView.findViewById(R.id.userListItem_Period);
        //teclist = itemView.findViewById(R.id.userListItem_Teclist);
        //gender = itemView.findViewById(R.id.userListItem_Gender);
    }
}
