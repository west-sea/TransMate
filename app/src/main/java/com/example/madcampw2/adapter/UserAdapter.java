package com.example.madcampw2.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcampw2.MyGlobals;
import com.example.madcampw2.R;
import com.example.madcampw2.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {


    private List<User> userList;

    public interface OnItemClickListener {
        void onItemClick(String username);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public UserAdapter(List<User> userList) {
        this.userList = userList;
        Log.v("useradapter", "go into adapter");
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // list size만큼 반복될 것임
        Log.v("useradapter", "go into onCreateViewHolder");
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);

        return new UserHolder(view);
    }


    //기본값 홍성문 생각(본인)
    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        String myself = "서해린";
        myself = MyGlobals.getInstance().getMe();

        String combinedBase64 = null;

        Log.v("useradapter", "go into onBindViewHolder");
        User user=userList.get(position); //


        String image = user.getProfile();
        String image2 = user.getProfile2();
        if (image != null && image2 != null) {
            combinedBase64 = image + image2;
            byte[] decodedString = android.util.Base64.decode(combinedBase64, android.util.Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.profile.setImageBitmap(decodedByte);

        }


        String usermate = user.getMate();
        if(usermate != null) {
            String[] mates = usermate.split(", ");
            String premate = mates[0];
            if(mates[1] != null) {
                String nowmate = mates[1];

                if(premate !=null && nowmate !=null) {
                    Log.v("premate", "premate: " + premate);
                    String username = user.getName();
                    Log.v("username", "username+" + username);
                    if (nowmate.equals(myself)) {
                        Log.v("rawuseradapter", "Name set to: " + user.getName());
                        MyGlobals.getInstance().setNamen(user.getName());
                        Log.v("rawuseradapter2", "Name set to: " + user.getName());
                        MyGlobals.getInstance().setNicknamen(user.getNickname());
                        MyGlobals.getInstance().setSchooln(user.getSchool());
                        MyGlobals.getInstance().setMajorn(user.getMajor());
                        MyGlobals.getInstance().setNumbern(user.getNumber());
                        MyGlobals.getInstance().setPhonen(user.getPhone());
                        MyGlobals.getInstance().setMailn(user.getMail());
                        MyGlobals.getInstance().setMaten(user.getMate());
                        MyGlobals.getInstance().setEvaln(user.getEval());
                        MyGlobals.getInstance().setImagen(combinedBase64);
                    }


                    if (premate.equals(myself)) {
                        Log.v("rawuseradapter", "Name set to: " + user.getName());
                        MyGlobals.getInstance().setName(user.getName());
                        Log.v("rawuseradapter2", "Name set to: " + user.getName());
                        MyGlobals.getInstance().setNickname(user.getNickname());
                        MyGlobals.getInstance().setSchool(user.getSchool());
                        MyGlobals.getInstance().setMajor(user.getMajor());
                        MyGlobals.getInstance().setNumber(user.getNumber());
                        MyGlobals.getInstance().setPhone(user.getPhone());
                        MyGlobals.getInstance().setMail(user.getMail());
                        MyGlobals.getInstance().setMate(user.getMate());
                        MyGlobals.getInstance().setEval(user.getEval());
                        MyGlobals.getInstance().setImage(combinedBase64);
                    }
                }
            }
        }
            //holder.id.setText(user.getId());
        if(user.getName() != null && user.getNickname() != null && user.getSchool() != null && user.getMajor() != null && user.getNumber() != null && user.getPhone() != null && user.getMail() != null && user.getMate() != null) {
            holder.name.setText(user.getName());
            holder.nickname.setText(user.getNickname());
            holder.school.setText(user.getSchool());
            holder.major.setText(user.getMajor());
            holder.number.setText(user.getNumber());
            holder.phone.setText(user.getPhone());
            holder.mail.setText(user.getMail());
            holder.mate.setText(user.getMate());
            //holder.eval.setText(user.getEval());



            String evaluation = user.getEval();
            if (evaluation.charAt(0) == '1') {
                holder.sk1.setPressed(true);
            }
            if (evaluation.charAt(1) == '1') {
                holder.sk2.setPressed(true);
            }
            if (evaluation.charAt(2) == '1') {
                holder.sk3.setPressed(true);
            }
            if (evaluation.charAt(3) == '1') {
                holder.sk4.setPressed(true);
            }
            if (evaluation.charAt(4) == '1') {
                holder.sk5.setPressed(true);
            }
            if (evaluation.charAt(5) == '1') {
                holder.sk6.setPressed(true);
            }
            if (evaluation.charAt(6) == '1') {
                holder.sk7.setPressed(true);
            }
            if (evaluation.charAt(7) == '1') {
                holder.sk8.setPressed(true);
            }
            if (evaluation.charAt(8) == '1') {
                holder.sk9.setPressed(true);
            }
            if (evaluation.charAt(9) == '1') {
                holder.sk10.setPressed(true);
            }
            if (evaluation.charAt(10) == '1') {
                holder.sk11.setPressed(true);
            }
        }



        //holder.img.setImageBitmap(user.getImg());
       // holder.imgM.setImageBitmap(user.getImgM());
        //holder.logid.setText(user.getLogId());
        //holder.logpw.setText(user.getLogPw());
        //holder.period.setText(user.getPeriod());
        //holder.teclist.setText(user.getTecList());
        //holder.gender.setText(user.getGender());

    }

    @Override
    public int getItemCount() {
        return userList.size(); // userList 크기 반환
    }
}
