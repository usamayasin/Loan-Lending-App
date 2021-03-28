package org.jethro.mobile.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;

import butterknife.BindView;

public class ForgotPasswordActivity extends BaseActivity {

    @BindView(R.id.ll_forgotPasswordLinkSendMessage)
    LinearLayout ll_forgotPasswordLinkSendMessage;

    @BindView(R.id.tv_forgotPasswordLinkSendMessage)
    TextView tv_forgotPasswordLinkSendMessage;

    @BindView(R.id.til_forgotPasswordEmail)
    TextInputLayout til_forgotPasswordEmail;

    @BindView(R.id.et_forgotPasswordEmail)
    EditText et_forgotPasswordEmail;

    @BindView(R.id.btn_forgotPasswordSendLink)
    Button btn_forgotPasswordSendLink;

    @BindView(R.id.tv_forgotPasswordSignUp)
    TextView tv_forgotPasswordSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btn_forgotPasswordSendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void validation() {
        til_forgotPasswordEmail.setError(null);
        if (et_forgotPasswordEmail.getText().toString().trim().length() == 0) {
            til_forgotPasswordEmail.setError("Email required");
        }
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!et_forgotPasswordEmail.getText().toString().trim().matches(emailPattern)){
            til_forgotPasswordEmail.setError("Invalid Email");
        }
    }
}