package major.com.juice_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    EditText usernameTxt, nameText, phoneTxt, emailTxt, passwordTxt;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameTxt = (EditText)findViewById(R.id.usernameText);
        nameText = (EditText)findViewById(R.id.nameText);
        phoneTxt = (EditText)findViewById(R.id.phoneText);
        emailTxt = (EditText)findViewById(R.id.emailText);
        passwordTxt = (EditText)findViewById(R.id.passwordText);
        registerBtn = (Button)findViewById(R.id.registerButton);

    }

}
