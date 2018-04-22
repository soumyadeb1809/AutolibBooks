package com.soumyadeb.autolibbooks.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soumyadeb.autolibbooks.Constants;
import com.soumyadeb.autolibbooks.activity.LoginActivity;
import com.soumyadeb.autolibbooks.R;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private CircleImageView imgProfile;
    private TextView name, roll, uid;
    private Button btnLogout;
    private SharedPreferences sp;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_profile, container, false);

        imgProfile = (CircleImageView) mView.findViewById(R.id.img_profile);
        name = (TextView) mView.findViewById(R.id.name);
        roll = (TextView) mView.findViewById(R.id.roll);
        uid = (TextView) mView.findViewById(R.id.uid);
        btnLogout = (Button) mView.findViewById(R.id.logout);

        sp = getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);

        name.setText(sp.getString("name","Soumya Deb"));
        roll.setText("Roll Number: "+sp.getString("roll", "1504060"));
        uid.setText("Your UID: "+sp.getString("uid","F05D6083"));
        String profileImgURL = sp.getString("image","NA");
        imgProfile.setImageResource(R.drawable.placeholder_profile_img);
        if(!profileImgURL.equals("NA")) {
            Glide.with(getActivity()).load(profileImgURL).into(imgProfile);
        }
        else {

        }


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences.Editor editor = sp.edit();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Message");
                builder.setMessage("Are you sure you want to sign out of your account?");
                builder.setPositiveButton("SIGN OUT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString("name", null);
                        editor.putString("roll", null);
                        editor.putString("uid", null);
                        editor.putString("status", "false");
                        editor.apply();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                });

                builder.setNegativeButton("CANCEL", null);
                builder.show();

            }
        });

        return mView;
    }

}
