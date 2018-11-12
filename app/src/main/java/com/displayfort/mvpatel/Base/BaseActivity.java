package com.displayfort.mvpatel.Base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by pc on 12/11/2018 16:57.
 * MVPatel
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * @param intent Start Activity with Custom animation
     */
    public void startActivityWithAnim(Intent intent) {
        startActivityWithAnim(intent, BaseAnimation.EFFECT_TYPE.TAB_DEAFULT);
    }

    public void startActivityWithAnim(Intent intent, BaseAnimation.EFFECT_TYPE type) {
        startActivity(intent);
        setAnimationTransition(type);

    }

    public void startActivityWithResultAnim(Intent intent, int requestCode) {
        startActivityWithResultAnim(intent, requestCode,
                BaseAnimation.EFFECT_TYPE.TAB_DEAFULT);
    }

    public void startActivityWithResultAnim(Intent intent, int requestCode,
                                            BaseAnimation.EFFECT_TYPE type) {
        startActivityForResult(intent, requestCode);
        setAnimationTransition(type);
    }

    public void finishActivityWithAnim() {
        finishActivityWithAnim(BaseAnimation.EFFECT_TYPE.TAB_DEAFULT);

    }

    public void finishActivityWithAnim(BaseAnimation.EFFECT_TYPE type) {
        finish();
        setAnimationTransition(type);


    }

    private void setAnimationTransition(BaseAnimation.EFFECT_TYPE type) {
        BaseAnimation.setAnimationTransition(this, type);

    }

}
