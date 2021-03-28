package org.jethro.mobile.ui.activities;

/*
 * Created by saksham on 01/June/2018
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mifos.mobile.passcode.utils.PasscodePreferencesHelper;

import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.utils.Constants;

public class SplashActivity extends BaseActivity {

    PasscodePreferencesHelper passcodePreferencesHelper;
    Intent intent;
    ImageView splashImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_splash);

        splashImage = (ImageView)findViewById(R.id.iv_splash_logo);

        passcodePreferencesHelper = new PasscodePreferencesHelper(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!passcodePreferencesHelper.getPassCode().isEmpty()) {
                    intent = new Intent(SplashActivity.this, PassCodeActivity.class);
                    intent.putExtra(Constants.INTIAL_LOGIN, false);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },2000);

        Animation myAnimation = AnimationUtils.loadAnimation(this,R.anim.animation);
        if(splashImage!=null) splashImage.startAnimation(myAnimation);

    }
}
