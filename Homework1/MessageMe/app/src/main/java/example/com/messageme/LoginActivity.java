package example.com.messageme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button login;
    Button newUser;
    User user;
    final static String USER_KEY = "USER";
    final static String TOKEN_KEY = "TOKEN";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        //menu.setIcon(R.drawable.ic_launcher);
        menu.setLogo(R.drawable.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);
        menu.setDisplayShowTitleEnabled(true);
        menu.setTitle("MessageMe!");
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.buttonLogin);
        newUser = (Button) findViewById(R.id.buttonNewUser);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = ((EditText) findViewById(R.id.editTextUserName)).getText().toString();
                String pwd = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
                final RequestParams params = new RequestParams("POST", "http://apidevelopment-inclass01.azurewebsites.net/Token");
                params.addParams("username", username);
                params.addParams("password", pwd);
                params.addParams("grant_type", "password");


                new GetTokenAsyncTask(new GetTokenAsyncTask.IGetTokenString() {
                    @Override
                    public void getTokenForUser(String s) {
                        token = s;

                        if (token.length() != 0 && token != null) {
                            Intent intent = new Intent(LoginActivity.this, InboxActivity.class);
                            intent.putExtra(TOKEN_KEY,token);
                            startActivity(intent);
                        }
                    }
                }).execute(params);
            }
        });




        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}


