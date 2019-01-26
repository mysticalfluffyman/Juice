package major.com.juice_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import major.com.juice_android.model.DefaultResponse;
import major.com.juice_android.api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{

    private EditText usernameTxt, nameTxt, phoneTxt, emailTxt, passwordTxt;
    private Button registerBtn;
    private TextView goToLoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nowplaying);

//        usernameTxt = (EditText)findViewById(R.id.usernameText);
//        nameTxt = (EditText)findViewById(R.id.nameText);
//        phoneTxt = (EditText)findViewById(R.id.phoneText);
//        emailTxt = (EditText)findViewById(R.id.emailText);
//        passwordTxt = (EditText)findViewById(R.id.passwordText);
//        registerBtn = (Button)findViewById(R.id.registerButton);
//        goToLoginText = (TextView)findViewById(R.id.goToLogin);
//
//        registerBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//               registerUser();
//            }
//        });
//        goToLoginText.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                goToLoginPage();
//            }
//        });

    }

//    public void registerUser()
//    {
//        String usernameValue = usernameTxt.getText().toString();
//        String nameValue = nameTxt.getText().toString();
//        String phoneValue = phoneTxt.getText().toString();
//        String emailValue = emailTxt.getText().toString();
//        String passwordValue = passwordTxt.getText().toString();
//
//        if (usernameValue.isEmpty())
//        {
//            usernameTxt.setError("Username is required");
//            usernameTxt.requestFocus();
//            return;
//        }
//        if (emailValue.isEmpty())
//        {
//            emailTxt.setError("Email is required");
//            emailTxt.requestFocus();
//            return;
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches())
//        {
//            emailTxt.setError("Enter a valid E-mail");
//            emailTxt.requestFocus();
//            return;
//        }
//        if (passwordValue.isEmpty())
//        {
//            passwordTxt.setError("Password is required");
//            passwordTxt.requestFocus();
//            return;
//        }
//        if (passwordValue.length() < 6)
//        {
//            passwordTxt.setError("Password should be more than 6 characters long");
//            passwordTxt.requestFocus();
//            return;
//        }
//        if (nameValue.isEmpty())
//        {
//            nameTxt.setError("Name is required");
//            nameTxt.requestFocus();
//            return;
//        }
//        if (phoneValue.isEmpty())
//        {
//            phoneTxt.setError("Phone is required");
//            phoneTxt.requestFocus();
//            return;
//        }
//
//        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().createUser(usernameValue, nameValue, emailValue, passwordValue, phoneValue);
//        call.enqueue(new Callback<DefaultResponse>()
//        {
//            @Override
//            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response)
//            {
//                DefaultResponse defaultResponse = response.body();
//                if (response.code() == 201)
//                {
//                    Toast.makeText(MainActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//                else if (response.code() == 422)
//                {
//                    Toast.makeText(MainActivity.this, "User Already Exists" , Toast.LENGTH_SHORT).show();
//                }
//                else if (response.code() == 421)
//                {
//                    Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DefaultResponse> call, Throwable t)
//            {
//
//            }
//        });
//    }
//
//    public void goToLoginPage()
//    {
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }

}
