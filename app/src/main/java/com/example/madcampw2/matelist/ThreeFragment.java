package com.example.madcampw2.matelist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.madcampw2.MyGlobals;
import com.example.madcampw2.R;

public class ThreeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        TextView name3 = view.findViewById(R.id.name3);
        TextView nickname3 = view.findViewById(R.id.nickname3);
        TextView score3 = view.findViewById(R.id.score3);
        TextView school3 = view.findViewById(R.id.school3);
        TextView major3 = view.findViewById(R.id.major3);
        TextView stdid3 = view.findViewById(R.id.stdid3);
        TextView phonenum3 = view.findViewById(R.id.phonenum3);
        TextView email3 = view.findViewById(R.id.email3);
        TextView state3 = view.findViewById(R.id.state3);
        TextView sk13 = view.findViewById(R.id.sk13);
        TextView sk23 = view.findViewById(R.id.sk23);
        TextView sk33 = view.findViewById(R.id.sk33);
        TextView sk43 = view.findViewById(R.id.sk43);
        TextView sk53 = view.findViewById(R.id.sk53);
        TextView sk63 = view.findViewById(R.id.sk63);
        TextView sk73 = view.findViewById(R.id.sk73);
        TextView sk83 = view.findViewById(R.id.sk83);
        TextView sk93 = view.findViewById(R.id.sk93);
        TextView sk103 = view.findViewById(R.id.sk103);
        TextView sk113 = view.findViewById(R.id.sk113);
        ImageView imageView3 = view.findViewById(R.id.imageView3);

        Log.v("twofrag", "twofrag: " + MyGlobals.getInstance().getNamen());
        name3.setText(MyGlobals.getInstance().getNamen());
        nickname3.setText(MyGlobals.getInstance().getNicknamen());
        //score3.setText(MyGlobals.getInstance().getEvaln());
        school3.setText(MyGlobals.getInstance().getSchooln());
        major3.setText(MyGlobals.getInstance().getMajorn());
        stdid3.setText(MyGlobals.getInstance().getNumbern());
        phonenum3.setText(MyGlobals.getInstance().getPhonen());
        email3.setText(MyGlobals.getInstance().getMailn());
        state3.setText(MyGlobals.getInstance().getMaten());

        if(MyGlobals.getInstance().getImagen() != null) {
            String combinedBase64 = MyGlobals.getInstance().getImagen();
            byte[] decodedString = android.util.Base64.decode(combinedBase64, android.util.Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView3.setImageBitmap(decodedByte);
        }

        String evaluation = MyGlobals.getInstance().getEvaln();
        if(evaluation != null) {
            if (evaluation.charAt(0) == '1') {
                sk13.setPressed(true);
            }
            if (evaluation.charAt(1) == '1') {
                sk23.setPressed(true);
            }
            if (evaluation.charAt(2) == '1') {
                sk33.setPressed(true);
            }
            if (evaluation.charAt(3) == '1') {
                sk43.setPressed(true);
            }
            if (evaluation.charAt(4) == '1') {
                sk53.setPressed(true);
            }
            if (evaluation.charAt(5) == '1') {
                sk63.setPressed(true);
            }
            if (evaluation.charAt(6) == '1') {
                sk73.setPressed(true);
            }
            if (evaluation.charAt(7) == '1') {
                sk83.setPressed(true);
            }
            if (evaluation.charAt(8) == '1') {
                sk93.setPressed(true);
            }
            if (evaluation.charAt(9) == '1') {
                sk103.setPressed(true);
            }
            if (evaluation.charAt(10) == '1') {
                sk113.setPressed(true);
            }
        }


        return view;
    }


}
