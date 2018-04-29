package com.sinhaparul.autolibbooks.fragment;


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
import com.sinhaparul.autolibbooks.Constants;
import com.sinhaparul.autolibbooks.activity.LoginActivity;
import com.sinhaparul.autolibbooks.R;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private CircleImageView imgProfile;
    private TextView name, email, mobile;
    private Button btnLogout;
    private SharedPreferences sp, app_sp;

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
        email = (TextView) mView.findViewById(R.id.email);
        mobile = (TextView) mView.findViewById(R.id.mobile);
        btnLogout = (Button) mView.findViewById(R.id.logout);

        sp = getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
        app_sp = getContext().getSharedPreferences(Constants.SP_APP_DATA, Context.MODE_PRIVATE);

        name.setText(sp.getString("name","Parul Sinha"));
        email.setText(sp.getString("email", "NA"));
        mobile.setText(sp.getString("mobile","NA"));

        String profileImgURL = sp.getString("image","NA");

        imgProfile.setImageResource(R.drawable.placeholder_profile_img);

        if(!profileImgURL.equals("NA")) {
            Glide.with(getActivity()).load(profileImgURL).into(imgProfile);
        }
        else {
            imgProfile.setImageResource(R.drawable.placeholder_profile_img);
        }


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences.Editor editor = sp.edit();
                final SharedPreferences.Editor appDataEditor = app_sp.edit();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Message");
                builder.setMessage("Are you sure you want to sign out of your account?");
                builder.setPositiveButton("SIGN OUT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString("name", null);
                        editor.putString("email", null);
                        editor.putString("mobile", null);
                        editor.putString("status", "false");
                        editor.apply();

                        appDataEditor.clear();
                        appDataEditor.apply();

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
