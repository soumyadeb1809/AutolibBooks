package com.sinhaparul.autolibbooks.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class RegisterActivity extends AppCompatActivity {
    
    private EditText etName, etEmail, etPass, etCnfPass, etMobile;
    private Button btnRegister;

    private Toolbar toolbar;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPass = (EditText) findViewById(R.id.et_password);
        etCnfPass = (EditText) findViewById(R.id.et_cnf_pass);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        btnRegister = (Button) findViewById(R.id.btn_register);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        progress = new ProgressDialog(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();
        String cnfPass = etCnfPass.getText().toString();
        String mobile = etMobile.getText().toString();


        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(cnfPass) || TextUtils.isEmpty(mobile)){
            Toast.makeText(this, "Please fill all the details and try again.", Toast.LENGTH_LONG).show();
        }
        else {
             if(!email.contains("@")) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_LONG).show();
                etPass.setError("Please enter a valid email address");
            }
             else if(password.length()<6){
                 Toast.makeText(this, "Passwords must be at least 6 characters long.", Toast.LENGTH_LONG).show();
                 etPass.setError("Passwords must be at least 6 characters long.");
             }
            else if(!password.equals(cnfPass)){
                Toast.makeText(this, "Passwords do not match. Please try again.", Toast.LENGTH_LONG).show();
                etCnfPass.setError("Passwords do not match. Please try again.");
            }

            else if(mobile.length() != 10) {
                Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_LONG).show();
                etMobile.setError("Please enter a valid mobile number");
            }

            else {

                 progress.setMessage("Hang on. Registering your account...");
                 progress.show();
                 sendData(name, email, password, mobile);
            }
        }

    }

    private void sendData(final String name, final String email, final String password, final String mobile) {

        String url = Constants.IP + Constants.DIR + Constants.REGISTER_USER;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(progress.isShowing())
                    progress.dismiss();

                Log.i(Constants.LOG_TAG, "Response: "+response);

                try {
                    JSONObject obj = new JSONObject(response);

                    String result = obj.getString("result");

                    if(result.equals(Constants.RESULT_SUCCESS)){

                        Toast.makeText(RegisterActivity.this, "Registration successful. Please login to continue.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();

                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(progress.isShowing())
                    progress.dismiss();
                Toast.makeText(RegisterActivity.this, "Error occurred. Please try again.", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("email", email);
                map.put("password", password);
                map.put("mobile", mobile);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }
}
