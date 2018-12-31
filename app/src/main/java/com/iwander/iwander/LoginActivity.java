package com.iwander.iwander;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;


public class LoginActivity extends ActionBarActivity {

    Button btnRegister;
    Button btnLogin;

    EditText etUsername;
    EditText etPassword;

    String username;
    String password;
    String userType;

    final Context context = this;
    boolean isTypeSelected = false;

    RadioButton radioPatient;
    RadioButton radioCaretaker;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("iwander", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getString("isLoggedIn", "").equals("true"))
        {
            if(sharedPreferences.getString("userType","").equals("Caretaker"))
            {
                startActivity(new Intent(getApplicationContext(), DisplayLoationActivity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), TempActivity.class));
                finish();
            }

        }

        radioPatient = (RadioButton) findViewById(R.id.radioPatient);
        radioCaretaker = (RadioButton) findViewById(R.id.radioCaretaker);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnSubmit);



        radioPatient.setChecked(true);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioPatient.isChecked())
                    userType = "Patient";
                else
                    userType = "Caretaker";

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                new LoginAsync().execute(new ApiConnector());
            }
        });


    }


    private void checkResult(String result)
    {
        if(result.equals("Success"))
        {
            editor.putString("isLoggedIn" , "true");
            editor.putString("username" , username);
            editor.putString("userType", userType);
            editor.commit();

            if(userType.equals("Caretaker"))
            {
                startActivity(new Intent(context , DisplayLoationActivity.class));
                finish();

            }
            else
            {
                startActivity(new Intent(context, TempActivity.class));
                finish();
            }
        }
        else
        {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }

    }



    private class LoginAsync extends AsyncTask<ApiConnector, Void, String>
    {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd= new ProgressDialog(context);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
            pd.show();
        }

        @Override
        protected String doInBackground(ApiConnector... params) {
            return params[0].checkLogin(username, password, userType);
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            checkResult(s);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
