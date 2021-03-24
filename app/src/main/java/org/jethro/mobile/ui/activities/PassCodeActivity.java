package org.jethro.mobile.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.mifos.mobile.passcode.utils.PasscodePreferencesHelper;

import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.utils.AESEncryption;
import org.jethro.mobile.utils.CheckSelfPermissionAndRequest;
import org.jethro.mobile.utils.Constants;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PassCodeActivity extends BaseActivity {

    private int count = 0;
    private String passCodeString = "";
    private TextView passCodeFirstDigit, passCodeSecondDigit, passCodeThirdDigit, passCodeFourthDigit, biometricLabel;
    private PasscodePreferencesHelper passcodePreferencesHelper;
    private boolean isInitialScreen;
    private boolean showPassCodeFlag = false;
    private AppCompatButton submitPassocdeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        passcodePreferencesHelper = new PasscodePreferencesHelper(this);
        passCodeFirstDigit = findViewById(R.id.tv_passcode_first_digit);
        passCodeSecondDigit = findViewById(R.id.tv_passcode_second_digit);
        passCodeThirdDigit = findViewById(R.id.tv_passcode_third_digit);
        passCodeFourthDigit = findViewById(R.id.tv_passcode_fourth_digit);
        submitPassocdeButton = findViewById(R.id.btn_set_passcode);
        biometricLabel = findViewById(R.id.tv_biometric_label);

        isInitialScreen = getIntent().getBooleanExtra(Constants.INTIAL_LOGIN, false);
        setupPassCodeButton();
        if (!CheckSelfPermissionAndRequest.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)) {
            requestPermission();
        }

    }

    private void setupPassCodeButton() {
        if (isInitialScreen) {
            submitPassocdeButton.setText(getResources().getString(R.string.passcode_setup));
            biometricLabel.setText(getResources().getString(R.string.setup_finger_print));
        } else {
            submitPassocdeButton.setText(getResources().getString(R.string.submit));
            biometricLabel.setText(getResources().getString(R.string.biometric));
        }
    }

    /**
     * Uses {@link CheckSelfPermissionAndRequest} to check for runtime permissions
     */
    private void requestPermission() {
        CheckSelfPermissionAndRequest.requestPermission(
                this,
                Manifest.permission.READ_PHONE_STATE,
                Constants.PERMISSIONS_REQUEST_READ_PHONE_STATE,
                getResources().getString(
                        R.string.dialog_message_phone_state_permission_denied_prompt),
                getResources().getString(R.string.
                        dialog_message_phone_state_permission_never_ask_again),
                Constants.PERMISSIONS_READ_PHONE_STATE_STATUS);
    }


    private boolean isPassCodeLengthCorrect() {
        if (passCodeString.length() == 4) {
            return true;
        }
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Alert")
                .setContentText("Invalid PassCode!")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
        return false;
    }

    public void savePassCode(View view) throws Exception {
        if (isPassCodeLengthCorrect()) {
            if (isInitialScreen) {
                try {
                    passcodePreferencesHelper.savePassCode(AESEncryption.encrypt(passCodeString));
                    startHomeActivity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                String savedPasscode = passcodePreferencesHelper.getPassCode();
                if (AESEncryption.decrypt(savedPasscode).equals(passCodeString)) {
                    startHomeActivity();
                } else {
                    showAlertDialogForError(this, getResources().getString(R.string.incorrect_passcode));
                }
            }
        }
    }

    public void changePassCodeVisibility(View view) {
        if (!showPassCodeFlag) {
            passCodeFirstDigit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passCodeSecondDigit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passCodeThirdDigit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passCodeFourthDigit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPassCodeFlag = true;
        } else {
            passCodeFirstDigit.setInputType(InputType.TYPE_CLASS_TEXT);
            passCodeSecondDigit.setInputType(InputType.TYPE_CLASS_TEXT);
            passCodeThirdDigit.setInputType(InputType.TYPE_CLASS_TEXT);
            passCodeFourthDigit.setInputType(InputType.TYPE_CLASS_TEXT);
            showPassCodeFlag = false;
        }
    }

    public void startHomeActivity() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Alert")
                .setContentText("You are Successfully Logged in!")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        startActivity(new Intent(PassCodeActivity.this, HomeActivity.class));
                        finish();
                    }
                }).show();
    }

//    @Override
//    public void startLoginActivity() {
//        new MaterialDialog.Builder().init(PassCodeActivity.this)
//                .setCancelable(false)
//                .setMessage(R.string.login_using_password_confirmation)
//                .setPositiveButton(getString(R.string.logout),
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent i = new Intent(PassCodeActivity.this,
//                                        LoginActivity.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.
//                                        FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(i);
//                                finish();
//                            }
//                        })
//                .setNegativeButton(getString(R.string.cancel),
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                .createMaterialDialog()
//                .show();
//    }
//


    @Override
    public void onBackPressed() {
        if (isInitialScreen) {
            super.onBackPressed();
        }
    }

    public void onKeypadBtnClick(View view) {
        TextView textView = findViewById(view.getId());
        String inputPassCodeDigit = textView.getText().toString();
        count++;
        setPassCode(inputPassCodeDigit);
    }

    public void setPassCode(String passCode) {
        if (count < 5) {
            passCodeString = passCodeString + passCode;
            switch (count) {
                case 1: {
                    passCodeFirstDigit.setText(passCode);
                    break;
                }
                case 2: {
                    passCodeSecondDigit.setText(passCode);
                    break;
                }
                case 3: {
                    passCodeThirdDigit.setText(passCode);
                    break;
                }
                case 4: {
                    passCodeFourthDigit.setText(passCode);
                    break;
                }
                default: {
                    clearPassCode(new View(this));
                    break;
                }
            }
        }
    }

    public void clearPassCode(View view) {
        count = 0;
        passCodeFirstDigit.setText("");
        passCodeSecondDigit.setText("");
        passCodeThirdDigit.setText("");
        passCodeFourthDigit.setText("");
        passCodeString = "";
    }

    public void startBiometricActivity(View view){
        startActivity(new Intent(PassCodeActivity.this, BiometricActivity.class));
    }
}
