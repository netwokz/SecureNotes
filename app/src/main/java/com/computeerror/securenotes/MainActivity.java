package com.computeerror.securenotes;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    String mTitle = "Login";
    String mSubtitle = "Please authenticate";
    String mDescription = "Place finger on the fingerprint scanner to proceed.";
    ConstraintLayout mMainLayoutId;

    private static final String TAG = MainActivity.class.getName();

    boolean mIsAuthenticated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list_view);
        mMainLayoutId = findViewById(R.id.main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create a thread pool with a single thread//
        Executor newExecutor = Executors.newSingleThreadExecutor();
        FragmentActivity activity = this;
        mIsAuthenticated = false;

        //Start listening for authentication events//

        final androidx.biometric.BiometricPrompt myBiometricPrompt = new androidx.biometric.BiometricPrompt(activity, newExecutor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            //onAuthenticationError is called when a fatal error occurrs//
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                } else {
                    //Print a message to Logcat//
                    Log.d(TAG, "An unrecoverable error occurred");
                }
            }
            //onAuthenticationSucceeded is called when a fingerprint is matched successfully//

            @Override
            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                userAuthenticated();
                super.onAuthenticationSucceeded(result);

            }
            //onAuthenticationFailed is called when the fingerprint doesn’t match//

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //Print a message to Logcat//
                Log.d(TAG, "Fingerprint not recognised");
            }
        });

        final androidx.biometric.BiometricPrompt.PromptInfo promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                //Add some text to the dialog//
                .setTitle(mTitle)
//                .setSubtitle(mSubtitle)
                .setDescription(mDescription)
                .setNegativeButtonText("Cancel")
                //Build the dialog//
                .build();
        //Assign an onClickListener to the app’s “Authentication” button//
        findViewById(R.id.launchAuthentication).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBiometricPrompt.authenticate(promptInfo);
            }
        });
    }

    private void userAuthenticated() {
        //User is good son....
        //Print a message to Logcat//
//        mIsAuthenticated = true;
        // logNotifyUser("isAuthenticated = " + mIsAuthenticated);
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.main_frame, new ListFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
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

    private void snackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
