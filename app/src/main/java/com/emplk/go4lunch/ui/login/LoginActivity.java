package com.emplk.go4lunch.ui.login;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.LoginActivityBinding;
import com.emplk.go4lunch.ui.dispatcher.DispatcherActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;

import java.util.Collections;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private LoginActivityBinding binding;

    private LoginActivityViewModel viewModel;

    private GoogleSignInClient googleSignInClient;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LoginActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();

        // GOOGLE SIGN IN
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);

        binding.loginGoogleLogBtn.setOnClickListener(v -> {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
            }
        );

        // GITHUB SIGN IN
        binding.loginGithubLogBtn.setOnClickListener(v -> {
                OAuthProvider.Builder provider = OAuthProvider
                    .newBuilder("github.com")
                    .setScopes(Collections.singletonList(("user:email")
                        )
                    );

                Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
                if (pendingResultTask != null) {
                    // There's something already here! Finish the sign-in for your user.
                    pendingResultTask
                        .addOnSuccessListener(
                            authResult -> {
                                viewModel.onLoginComplete();
                                startActivity(DispatcherActivity.navigate(this));
                            }
                        )
                        .addOnFailureListener(
                            e -> {
                                Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onFailure of pendingResultTask: " + e.getMessage());
                            }
                        );
                } else {
                    firebaseAuth
                        .startActivityForSignInWithProvider(this, provider.build())
                        .addOnSuccessListener(
                            authResult -> {
                                {
                                    if (authResult.getAdditionalUserInfo() != null && authResult.getAdditionalUserInfo().getProfile() != null && authResult.getAdditionalUserInfo().getProfile().get("email") != null) {
                                        Log.d(TAG, "EMILIE " + authResult.getAdditionalUserInfo().getProfile().get("email"));
                                    }

                                    viewModel.onLoginComplete();
                                    startActivity(DispatcherActivity.navigate(this));
                                }
                            }
                        )
                        .addOnFailureListener(
                            e -> {
                                Toast.makeText(LoginActivity.this, "Something went wrong!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onFailure of startActivityForSignInWithProvider: " + e.getMessage());
                            }
                        );
                }
            }
        );

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            startActivity(DispatcherActivity.navigate(this));
        }
    }

    @Override
    protected void onActivityResult(
        int requestCode,
        int resultCode,
        @Nullable Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (signInAccountTask.isSuccessful()) {
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    if (googleSignInAccount != null) {

                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential
                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    viewModel.onLoginComplete();
                                    startActivity(DispatcherActivity.navigate(LoginActivity.this));
                                    Log.i(TAG, "Firebase auth successful");
                                } else {
                                    Log.e("Firebase auth error: ", task.getException().getMessage());
                                }
                            }
                        );
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}