package com.bignerdranch.android.beerkeeper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.bignerdranch.android.beerkeeper.modules.Login;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText mEditTextEmail;
    @BindView(R.id.password)
    EditText mEditTextPassword;
    @BindView(R.id.submit_btn)
    Button mButtonSubmit;
    static String email = "", password = "";
    String sessionId = "";
    boolean resultAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        if (sessionId != null && sessionId.equals("0")) {
            email = "";
            password = "";
        }

        if (!email.equals("") && !password.equals("")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mEditTextEmail.getText().toString().trim();
                password = mEditTextPassword.getText().toString().trim();
                checkLogin();
                System.out.println("Resultanswer1" + resultAnswer);
                if (!isEmailCorrect()) {
                    mEditTextEmail.setError("Неправильні дані");
                } else if (!isPasswordCorrect()) {
                    mEditTextPassword.setError("Неправильні дані");
                }


            }
        });
        mEditTextEmail.setOnKeyListener(new View.OnKeyListener() {
                                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                                    // сохраняем текст, введенный до нажатия Enter в переменную


                                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                    imm.hideSoftInputFromWindow(mEditTextEmail.getWindowToken(), 0);

                                                    return true;
                                                }
                                                return false;
                                            }
                                        }
        );
        mEditTextPassword.setOnKeyListener(new View.OnKeyListener() {
                                               public boolean onKey(View v, int keyCode, KeyEvent event) {
                                                   if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                                           (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                                       // сохраняем текст, введенный до нажатия Enter в переменную


                                                       InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                       imm.hideSoftInputFromWindow(mEditTextPassword.getWindowToken(), 0);

                                                       return true;
                                                   }
                                                   return false;
                                               }
                                           }
        );
    }

    public void checkLogin() {
        Login login = new Login(this, Request.Method.GET, email, password);

        login.checkLogin(new Login.BeekeeperServiceCallback() {
            @Override
            public void onResult(boolean result) {
                if (!result) {
                    Toast.makeText(LoginActivity.this, "Invalid input data", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    public boolean isEmailCorrect() {
        if (email.equals("")) {
            mEditTextEmail.setError("Це поле не може бути пустим");
            return false;
        } else if (!email.matches("^(.+)@(.+)$")) {
            mEditTextEmail.setError("Не відповідає формату email");
        }

        return true;
    }

    public boolean isPasswordCorrect() {
        if (password.equals("")) {
            mEditTextPassword.setError("Це поле не може бути пустим");
            return false;
        }

        return true;
    }
}
