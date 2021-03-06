package com.cabrow.cabrowpassenger.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.VolleyError;
import com.cabrow.cabrowpassenger.R;
import com.cabrow.cabrowpassenger.fragments.UberRegisterFragment;
import com.cabrow.cabrowpassenger.fragments.UberSignInFragment;
import com.cabrow.cabrowpassenger.utils.AndyUtils;
import com.cabrow.cabrowpassenger.utils.AppLog;
import com.cabrow.cabrowpassenger.utils.Const;
import com.cabrow.cabrowpassenger.utils.GCMRegisterHendler;
import com.cabrow.cabrowpassenger.utils.PreferenceHelper;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

/**
 * @author Hardik A Bhalodi
 */
public class RegisterActivity extends ActionBarBaseActivitiy {

	Permission[] permissions = new Permission[] { Permission.EMAIL };
	SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
			.setPermissions(permissions).build();
	public PreferenceHelper phelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// actionBar.hide();
		setContentView(R.layout.register_activity);
		SimpleFacebook.setConfiguration(configuration);
		phelper = new PreferenceHelper(this);
		// actionBar.setDisplayShowHomeEnabled(true);
		// actionBar.setDisplayHomeAsUpEnabled(true);

		setIcon(R.drawable.back);

		if (getIntent().getBooleanExtra("isSignin", false)) {
			gotSignInFragment();
		} else {
			goToRegisterFragment();
		}
		// UberMainFragment mainFrag = new UberMainFragment();
		// addFragment(mainFrag, false, Const.FRAGMENT_MAIN);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnActionNotification:
			onBackPressed();
			break;

		default:
			break;
		}
	}



	private void gotSignInFragment() {
		UberSignInFragment signInFrag = new UberSignInFragment();
		clearBackStack();
		addFragment(signInFrag, false, Const.FRAGMENT_SIGNIN);
	}

	private void goToRegisterFragment() {
		UberRegisterFragment regFrag = new UberRegisterFragment();
		clearBackStack();
		addFragment(regFrag, false, Const.FRAGMENT_REGISTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uberorg.ActionBarBaseActivitiy#isValidate()
	 */
	@Override
	protected boolean isValidate() {
		return false;
	}

	public void showKeyboard(View v) {
		InputMethodManager inputManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		// check if no view has focus:
		// View view = activity.getCurrentFocus();
		// if (view != null) {
		inputManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
		// }
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		AppLog.Log(Const.TAG, error.getMessage());
	}
}