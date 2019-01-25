package major.com.juice_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import major.com.juice_android.api.RetrofitClient;
import major.com.juice_android.model.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity
{
    private EditText usernameTxt, passwordTxt;
    private Button loginButton;
    private Button goToRegistrationText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameTxt = (EditText)findViewById(R.id.usernameText);
        passwordTxt = (EditText)findViewById(R.id.passwordText);
        loginButton = (Button)findViewById(R.id.loginButton);
        goToRegistrationText = (Button) findViewById(R.id.goToRegistration);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                loginToApp();
            }
        });
        goToRegistrationText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goToRegistrationPage();
            }
        });
    }

    public void loginToApp()
    {
        String usernameValue = usernameTxt.getText().toString();
        String passwordValue = passwordTxt.getText().toString();

        if (usernameValue.isEmpty())
        {
            usernameTxt.setError("Username is required");
            usernameTxt.requestFocus();
            return;
        }
        if (passwordValue.isEmpty())
        {
            passwordTxt.setError("Password is required");
            passwordTxt.requestFocus();
            return;
        }
        if (passwordValue.length() < 6)
        {
            passwordTxt.setError("Password should be more than 6 characters long");
            passwordTxt.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(usernameValue, passwordValue);
        call.enqueue(new Callback<LoginResponse>()
        {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response)
            {
                LoginResponse loginResponse = response.body();

                if (!loginResponse.isError())
                {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t)
            {

            }
        });

    }

    public void goToRegistrationPage()
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
