package com.displayfort.mvpatel.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.displayfort.mvpatel.Base.BaseActivity;
import com.displayfort.mvpatel.MVPatelPrefrence;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Services.SaveJsonDateInDbService;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    private ActivityViewHolder activityViewHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activityViewHolder = new ActivityViewHolder(findViewById(R.id.container_Ll), this);
        context = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guest_login_btn:
                callHomeActiviyt();
                break;
            case R.id.login_btn:
                callHomeActiviyt();
                break;
            case R.id.forget_pssword_tv:
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private void callHomeActiviyt() {
        startActivityWithAnim(new Intent(getBaseContext(), HomeActivity.class));
        new MVPatelPrefrence(context).setIsLogin(true);
//        startService(new Intent(context, SaveJsonDateInDbService.class));
    }

    public class ActivityViewHolder {

        private final TextView mForgetPasswordTv, mGuestLoginTv;
        public EditText mUsernameEt;
        public EditText mPasswordEt;
        public Button mLoginBtn;
        public Button mSignupBtn;

        public ActivityViewHolder(View view, View.OnClickListener listener) {
            mForgetPasswordTv = (TextView) view.findViewById(R.id.forget_pssword_tv);
            mGuestLoginTv = (TextView) view.findViewById(R.id.guest_login_btn);
            mUsernameEt = (EditText) view.findViewById(R.id.username_et);
            mPasswordEt = (EditText) view.findViewById(R.id.password_et);
            mLoginBtn = (Button) view.findViewById(R.id.login_btn);


            mLoginBtn.setOnClickListener(listener);
            mForgetPasswordTv.setOnClickListener(listener);
            mGuestLoginTv.setOnClickListener(listener);
        }
    }
}

