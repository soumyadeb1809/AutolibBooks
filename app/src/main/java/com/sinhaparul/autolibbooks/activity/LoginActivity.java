package com.sinhaparul.autolibbooks.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sinhaparul.autolibbooks.Constants;
import com.sinhaparul.autolibbooks.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    // Declare the instances:
    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegister;
    private SharedPreferences sp;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize instances:
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);

        sp = getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
        progress = new ProgressDialog(LoginActivity.this);

        // OnClick Listeners:
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;

                email = etEmail.getText().toString();
                pass = etPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this, "Please fill all the details and try again.", Toast.LENGTH_LONG).show();
                }
                else {
                    if (!email.contains("@")) {
                        Toast.makeText(LoginActivity.this, "Invalid email. Please try again", Toast.LENGTH_LONG).show();
                        etEmail.setError("Invalid email.");
                    }
                    else if (pass.length() < 6) {
                        Toast.makeText(LoginActivity.this, "Invalid password. Please try again", Toast.LENGTH_LONG).show();
                        etPassword.setError("Invalid password.");
                    }
                    else {
                        loginUser(email, pass);
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                //finish();
            }
        });

    }

    // Method to verify user data and login the user:
    private void loginUser(final String email, final String pass) {

        progress.setMessage("Hang on...");
        progress.setCancelable(false);
        progress.show();

        Log.i(Constants.LOG_TAG,"Roll: "+email);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = Constants.IP + Constants.DIR + Constants.LOGIN_USER;

        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(progress.isShowing()){
                    progress.dismiss();
                }

                try {

                    Log.i(Constants.LOG_TAG, response);
                    JSONObject mainObj = new JSONObject(response);
                    String result = mainObj.getString("result");
                    Log.i(Constants.LOG_TAG, result);
                    if(result.equals("SUCCESS")){

                        JSONObject dataObj = mainObj.getJSONObject("data");
                        String name = dataObj.getString("name");
                        String mobile = dataObj.getString("mobile");
                        String image = dataObj.getString("image");
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("email", email);
                        editor.putString("name", name);
                        editor.putString("mobile", mobile);
                        editor.putString("image", image);
                        editor.putString("status", "true");
                        editor.apply();

                        Log.i(Constants.LOG_TAG,"Name: " + name);
                        Log.i(Constants.LOG_TAG, "Roll: " + email);
                        Log.i(Constants.LOG_TAG, "Mobile: " + mobile);

                        startActivity(new Intent(LoginActivity.this, SplashScreenActivity.class));
                        //finish();

                    }
                    else{

                        builder.setTitle("Error");
                        builder.setMessage("Login failed. Please check your credentials and try again.");
                        builder.setPositiveButton("OKAY", null);
                        builder.show();
                        etPassword.setText(null);
                        //etEmail.setText(null);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map getParams(){
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("password", pass);

                return map;
            }
        };

        queue.add(stringRequest);
    }


}
