package org.jethro.mobile.ui.fragments;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import org.jethro.mobile.R;
import org.jethro.mobile.models.register.RegisterPayload;
import org.jethro.mobile.presenters.RegistrationPresenter;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.ui.fragments.base.BaseFragment;
import org.jethro.mobile.ui.views.RegistrationView;
import org.jethro.mobile.utils.Network;
import org.jethro.mobile.utils.PasswordStrength;
import org.jethro.mobile.utils.Toaster;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by dilpreet on 31/7/17.
 */

public class RegistrationFragment extends BaseFragment implements RegistrationView {

    @BindView(R.id.et_account_number)
    EditText etAccountNumber;

    @BindView((R.id.rb_email))
    RadioButton emailRadioButton;

    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.et_first_name)
    EditText etFirstName;

    @BindView(R.id.et_last_name)
    EditText etLastName;

    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;

    @BindView(R.id.rg_verification_mode)
    RadioGroup rgVerificationMode;

    @Inject
    RegistrationPresenter presenter;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.password_strength)
    TextView strengthView;

    @BindView(R.id.til_account_number)
    TextInputLayout til_account_number;

    @BindView(R.id.til_username)
    TextInputLayout til_username;

    @BindView(R.id.til_firstName)
    TextInputLayout til_firstName;

    @BindView(R.id.til_lastName)
    TextInputLayout til_lastName;

    @BindView(R.id.til_phoneNumber)
    TextInputLayout til_phoneNumber;

    @BindView(R.id.til_email)
    TextInputLayout til_email;

    @BindView(R.id.til_password)
    TextInputLayout til_password;

    @BindView(R.id.til_confirmPassword)
    TextInputLayout til_confirmPassword;


    private View rootView;

    public static RegistrationFragment newInstance() {
        RegistrationFragment fragment = new RegistrationFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_registration, container, false);

        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        ButterKnife.bind(this, rootView);
        presenter.attachView(this);

        progressBar.setVisibility(View.GONE);
        strengthView.setVisibility(View.GONE);

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    progressBar.setVisibility(View.GONE);
                    strengthView.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    strengthView.setVisibility(View.VISIBLE);
                    updatePasswordStrengthView(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return rootView;
    }

    private void updatePasswordStrengthView(String password) {
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            progressBar.setProgress(0);
            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(getContext()));
        strengthView.setTextColor(str.getColor());

        PorterDuff.Mode mode = android.graphics.PorterDuff.Mode.SRC_IN;
        progressBar.getProgressDrawable().setColorFilter(str.getColor(), mode);
        if (str.getText(getContext()).equals(getString(R.string.password_strength_weak))) {
            progressBar.setProgress(25);
        } else if (str.getText(getContext()).equals(getString(R.string.password_strength_medium))) {
            progressBar.setProgress(50);
        } else if (str.getText(getContext()).equals(getString(R.string.password_strength_strong))) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }

    @OnClick(R.id.btn_register)
    public void registerClicked() {
        boolean setSendPasswordToEmail = false;
        if (areFieldsValidated()) {

            RadioButton radioButton = rootView.findViewById(rgVerificationMode.
                    getCheckedRadioButtonId());
            if (rgVerificationMode.getCheckedRadioButtonId() != -1) {
                if(emailRadioButton.isChecked()){
                    setSendPasswordToEmail = true;
                } else {
                    setSendPasswordToEmail = false;
                }
            }
            RegisterPayload payload = new RegisterPayload();

            Integer[] roles = {2};
            String accNumber = etAccountNumber.getText().toString();
            int accountNumber = Integer.parseInt(accNumber);
            Integer[] clients = {accountNumber};
            payload.setFirstname(etFirstName.getText().toString());
            payload.setLastname(etLastName.getText().toString());
            payload.setRoles(roles);
            payload.setClients(clients);
            payload.setSelfServiceUser(true);
            payload.setOfficeId(1);
            payload.setSendPasswordToEmail(setSendPasswordToEmail);
            if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                Toaster.show(rootView, getString(R.string.error_password_not_match));
                return;
            } else {
                payload.setPassword(etPassword.getText().toString());
            }
            payload.setPassword(etPassword.getText().toString());
            payload.setRepeatPassword(etConfirmPassword.getText().toString());
            payload.setUsername(etUsername.getText().toString().replace(" ", ""));

            if (Network.isConnected(getContext())) {
                presenter.registerUser(payload);
            } else {
                Toaster.show(rootView, getString(R.string.no_internet_connection));
            }
        }

    }

    @SuppressLint({"StringFormatInvalid", "StringFormatMatches"})
    private boolean areFieldsValidated() {
        clearTextInputLayoutErrorMessage();

        if (etAccountNumber.getText().toString().trim().length() == 0) {
            // Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.account_number)));
            setTextInputLayoutErrorMessage(til_account_number, getString(R.string.error_validation_blank, getString(R.string.
                    account_number)));
            return false;
        } else if (etUsername.getText().toString().trim().length() == 0) {
            // Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.username)));
            setTextInputLayoutErrorMessage(til_username, getString(R.string.error_validation_blank, getString(R.string.
                    username)));
            return false;
        } else if (etUsername.getText().toString().trim().length() < 6) {
            // Toaster.show(rootView, getString(R.string.error_username_greater_than_six));
            setTextInputLayoutErrorMessage(til_username, getString(R.string.error_username_greater_than_six));

            return false;
        } else if (etUsername.getText().toString().trim().contains(" ")) {
            /*Toaster.show(rootView, getString(R.string.error_validation_cannot_contain_spaces,
                    getString(R.string.username), getString(R.string.not_contain_username)));*/
            setTextInputLayoutErrorMessage(til_username, getString(R.string.error_validation_cannot_contain_spaces,
                    getString(R.string.username), getString(R.string.not_contain_username)));
            return false;
        } else if (etFirstName.getText().length() == 0) {
            // Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.first_name)));
            setTextInputLayoutErrorMessage(til_firstName, getString(R.string.error_validation_blank, getString(R.string.
                    first_name)));
            return false;
        } else if (etLastName.getText().toString().trim().length() == 0) {
            //Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.last_name)));
            setTextInputLayoutErrorMessage(til_lastName, getString(R.string.error_validation_blank, getString(R.string.
                    last_name)));
            return false;
        } else if (etEmail.getText().toString().trim().length() == 0) {
            //Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.email)));
            setTextInputLayoutErrorMessage(til_email, getString(R.string.error_validation_blank, getString(R.string.
                    email)));
            return false;
        } else if (etPassword.getText().toString().trim().length() == 0) {
            // Toaster.show(rootView, getString(R.string.error_validation_blank, getString(R.string.password)));
            setTextInputLayoutErrorMessage(til_password, getString(R.string.error_validation_blank, getString(R.string.
                    password)));
            return false;
        } else if (etPassword.getText().toString().trim().length()
                < etPassword.getText().toString().length()) {
          /*  Toaster.show(rootView,
                    getString(R.string.error_validation_cannot_contain_leading_or_trailing_spaces,
                            getString(R.string.password)));*/
            setTextInputLayoutErrorMessage(til_password, getString(R.string.error_validation_cannot_contain_leading_or_trailing_spaces,
                    getString(R.string.password)));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim())
                .matches()) {
            //Toaster.show(rootView, getString(R.string.error_invalid_email));
            setTextInputLayoutErrorMessage(til_email, getString(R.string.error_invalid_email));

            return false;
        } else if (etPassword.getText().toString().trim().length() < 6) {
            /*Toaster.show(rootView, getString(R.string.error_validation_minimum_chars,
                    getString(R.string.password), getResources().
                            getInteger(R.integer.password_minimum_length)));*/
            setTextInputLayoutErrorMessage(til_password, getString(R.string.error_validation_minimum_chars,
                    getString(R.string.password), getResources().
                            getInteger(R.integer.password_minimum_length)));
            return false;
        }

        return true;
    }

    @Override
    public void showRegisteredSuccessfully() {
        ((BaseActivity) getActivity()).replaceFragment(RegistrationVerificationFragment.
                newInstance(), true, R.id.container);
    }

    @Override
    public void showError(String msg) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Alert")
                .setContentText(msg)
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    @Override
    public void showProgress() {
        showMifosProgressDialog(getString(R.string.sign_up));
    }

    @Override
    public void hideProgress() {
        hideMifosProgressDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    private void setTextInputLayoutErrorMessage(TextInputLayout inputLayout, String message) {
        inputLayout.setError(message);
        inputLayout.requestFocus();

    }

    private void clearTextInputLayoutErrorMessage() {

        til_account_number.setErrorEnabled(false);
        til_account_number.clearFocus();

        til_username.setErrorEnabled(false);
        til_username.clearFocus();

        til_firstName.setErrorEnabled(false);
        til_firstName.clearFocus();

        til_lastName.setErrorEnabled(false);
        til_lastName.clearFocus();

        til_email.setErrorEnabled(false);
        til_email.clearFocus();

        til_password.setErrorEnabled(false);
        til_password.clearFocus();

        til_confirmPassword.setErrorEnabled(false);
        til_confirmPassword.clearFocus();
    }
}
