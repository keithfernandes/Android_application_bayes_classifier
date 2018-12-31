package com.iwander.iwander;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class RegisterActivity extends ActionBarActivity {

    RadioGroup rgUserType;
    RadioButton radioPatient;
    RadioButton radioCaretaker;

    RadioButton radioLow;
    RadioButton radioMedium;
    RadioButton radioHigh;

    EditText etUsername;
    EditText etPassword;
    EditText etAge;
    EditText etPhone;
    RadioGroup rgDementiaLevel;

    Button btnRegister;

    String userType = "";
    String username = "";
    String password = "";
    String age = "0";
    String dementiaLevel = "No";
    String phone = "0";
    boolean isTypeSelcted = false;


    final Context context = this;
    SharedPreferences.Editor editor;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("iwander", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        rgUserType = (RadioGroup) findViewById(R.id.userType);
        radioPatient = (RadioButton) findViewById(R.id.radioPatient);
        radioCaretaker = (RadioButton) findViewById(R.id.radioCaretaker);
        rgDementiaLevel = (RadioGroup) findViewById(R.id.dementiaLevel);
        etPhone = (EditText) findViewById(R.id.etPhone);

        etAge = (EditText) findViewById(R.id.etAge);
        btnRegister = (Button) findViewById(R.id.btnSubmit);

        radioLow = (RadioButton) findViewById(R.id.radioLow);
        radioMedium = (RadioButton) findViewById(R.id.radioMedium);
        radioHigh = (RadioButton) findViewById(R.id.radioHigh);


        rgUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isTypeSelcted = true;
                if(radioCaretaker.isChecked())
                {
                    userType = "Caretaker";
                    etAge.setVisibility(View.GONE);
                    rgDementiaLevel.setVisibility(View.GONE);
                }
                if(radioPatient.isChecked())
                {
                    userType = "Patient";
                    etAge.setVisibility(View.VISIBLE);
                    rgDementiaLevel.setVisibility(View.VISIBLE);
                }
            }
        });

        rgDementiaLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioLow.isChecked())
                    dementiaLevel = "Low";
                if(radioMedium.isChecked())
                    dementiaLevel = "Medium";
                if(radioHigh.isChecked())
                    dementiaLevel = "High";
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                age = etAge.getText().toString();
                phone = etPhone.getText().toString();

                editor.putString("age", age);
                int tempDementiaLevel = 0;
                if(dementiaLevel.equals("Low"))
                    tempDementiaLevel = 1;
                else if(dementiaLevel.equals("Medium"))
                    tempDementiaLevel = 3;
                else
                    tempDementiaLevel = 4;
                editor.putString("dementiaLevel", String.valueOf(tempDementiaLevel));
                editor.commit();

                new RegisterAsync().execute(new ApiConnector());
            }
        });
    }


    private class RegisterAsync extends AsyncTask<ApiConnector, Void, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd= new ProgressDialog(context);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
            pd.show();
        }

        @Override
        protected String doInBackground(ApiConnector... params) {
            return params[0].registerUser(username, password, userType, age, dementiaLevel, phone);

        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            checkResult(result);
        }
    }

    private void checkResult(String result)
    {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        if(result.equals("Success"))
        {
            editor.putString("username", username);
            editor.putString("isLogged", "true");
            editor.putString("userType",userType);
            editor.commit();
            Intent intent = new Intent(context, TempActivity.class);
            startActivity(intent);

            finish();
        }
        else
        {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
