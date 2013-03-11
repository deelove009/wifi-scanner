package com.cm.wifiscanner.wifi;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.cm.wifiscanner.hub.LoginUtils;
import com.cm.wifiscanner.util.Constants;
import com.cm.wifiscanner.util.Logger;
import com.cm.wifiscanner.util.Utils;

public class LoginTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = "LoginTask";
    private Context mContext;

    LoginTask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        Logger.debug(TAG, "Start to login");
        String name = Utils.getUserName(mContext);
        String password = Utils.getPassword(mContext);
        return LoginUtils.getInstance(mContext).loginHub(name, password,
                Constants.TEST_URL);
    }

    @Override
    public void onPostExecute(String result) {
        Logger.debug(TAG, "=========Connect Status: " + result + " ==========");
        if (result == null) {
            Utils.setLoginStatus(mContext, Constants.HAVE_LOGIN);
            long time = System.currentTimeMillis();
            Utils.setLastLoginTime(mContext, time);
        } else if (!TextUtils.isEmpty(result)) {
            Utils.setLoginStatus(mContext, Constants.LOGIN_FALLURE);
            Utils.setErrorMessage(mContext, result);
            Toast.makeText(mContext, Utils.getShowErrorMessage(mContext),
                    Toast.LENGTH_SHORT).show();
        }
    }
}