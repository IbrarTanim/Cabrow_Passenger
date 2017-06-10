package com.cabrow.cabrowpassenger.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cabrow.cabrowpassenger.R;
import com.cabrow.cabrowpassenger.firebase.MyFirebaseInstanceIDService;
import com.cabrow.cabrowpassenger.utils.AndyUtils;
import com.cabrow.cabrowpassenger.utils.GCMRegisterHendler;
import com.cabrow.cabrowpassenger.utils.PreferenceHelper;
import com.google.firebase.iid.FirebaseInstanceId;

import static com.cabrow.cabrowpassenger.utils.CommonUtilities.DISPLAY_REGISTER_GCM;
import static com.cabrow.cabrowpassenger.utils.CommonUtilities.RESULT;


public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "TAG";
    /**
     * Called when the activity is first created.
     */
    private ImageButton btnSignIn, btnRegister;
    private PreferenceHelper pHelper;
    private boolean isReceiverRegister;
    private int oldOptions;
    String token = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        if (!TextUtils.isEmpty(new PreferenceHelper(this).getUserId())) {
            startActivity(new Intent(this, MainDrawerActivity.class));
            this.finish();
            return;
        }
        pHelper = new PreferenceHelper(this);
        setContentView(R.layout.activity_main);

        btnSignIn = (ImageButton) findViewById(R.id.btnSignIn);
        btnRegister = (ImageButton) findViewById(R.id.btnRegister);

		 /*VideoView video = (VideoView) findViewById(R.id.video);
           // Load and start the movie
		   Uri video1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
		   video.setVideoURI(video1);
		   video.start();
		   */

        // rlLoginRegisterLayout = (RelativeLayout) view
        // .findViewById(R.id.rlLoginRegisterLayout);
        // tvMainBottomView = (MyFontTextView) view
        // .findViewById(R.id.tvMainBottomView);
        btnSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        String deviceToken = pHelper.getDeviceToken();
        if (TextUtils.isEmpty(deviceToken)) {
            Intent intent = new Intent(this, MyFirebaseInstanceIDService.class);
            startService(intent);
            return;

        }
    }

    @Override
    public void onClick(View v) {
        Intent startRegisterActivity = new Intent(MainActivity.this,
                RegisterActivity.class);
        switch (v.getId()) {
            case R.id.btnSignIn:
                startRegisterActivity.putExtra("isSignin", true);
                break;
            case R.id.btnRegister:
                startRegisterActivity.putExtra("isSignin", false);
                break;
        }
        startActivity(startRegisterActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed();
        openExitDialog();
    }

    public void openExitDialog() {
        final Dialog mDialog = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.exit_layout);
        mDialog.findViewById(R.id.tvExitOk).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                        finish();
                        overridePendingTransition(R.anim.slide_in_left,
                                R.anim.slide_out_right);

                    }
                });
        mDialog.findViewById(R.id.tvExitCancel).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                    }
                });
        mDialog.show();
    }
}