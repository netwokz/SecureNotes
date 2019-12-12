package com.computeerror.securenotes;

import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String mTitle = "Login";
    String mSubtitle = "Please authenticate";
    String mDescription = "Place finger on the fingerprint scanner to proceed.";
    CoordinatorLayout mMainLayoutId;

    boolean mIsAuthenticated = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_view);
        mMainLayoutId = findViewById(R.id.main_activity);

//        authenticateUser();

        if (mIsAuthenticated) {

            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // Replace the contents of the container with the new fragment
            ft.replace(R.id.main_frame, new ListFragment());
            // or ft.add(R.id.your_placeholder, new FooFragment());
            // Complete the changes added above
            ft.commit();
        }
    }

    private void logNotifyUser(String s) {
        Log.d("Main", s);
    }

    private ArrayList<MyListData> generateListData() {
        ArrayList<MyListData> myListData = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            myListData.add(new MyListData("Test " + i, "Description " + i));
        }
        return myListData;
    }

    public void authenticateUser() {
        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle(mTitle)
                .setSubtitle(mSubtitle)
                .setDescription(mDescription)
                .setNegativeButton("Cancel", this.getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notifyUser("Authentication cancelled");
                    }
                }).build();

        biometricPrompt.authenticate(BiometricUtils.getCancellationSignal(), getMainExecutor(), getAuthenticationCallback());
    }

    private void notifyUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void snackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    private BiometricPrompt.AuthenticationCallback getAuthenticationCallback() {
        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                notifyUser("Authentication error: " + errString);
                mIsAuthenticated = false;
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationFailed() {
                mIsAuthenticated = false;
                super.onAuthenticationFailed();
            }


            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
//                notifyUser("Authentication Succeeded");
                snackbar(mMainLayoutId, "Authentication Succeeded");
                mIsAuthenticated = true;
                super.onAuthenticationSucceeded(result);
            }
        };
    }


}
