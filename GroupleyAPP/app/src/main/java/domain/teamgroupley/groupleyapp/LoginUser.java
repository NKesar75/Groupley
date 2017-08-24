package domain.teamgroupley.groupleyapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class LoginUser extends AppCompatActivity
        //implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener
        {

    //private static int RC_Sign_in = 0;
    private static final String TAG = "LoginUser";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //private GoogleApiClient mgoogle;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference storageReference;
    private String imguri;

    private Button mlogin;
    private TextView mcreate;
    private TextView mReset;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        mlogin = (Button) findViewById(R.id.LOGIN_BTN_LOgin);
        mcreate = (TextView) findViewById(R.id.createacc_txt_btn);
        mReset = (TextView) findViewById(R.id.Resetpassword);
        final EditText Email = (EditText) findViewById(R.id.Email_txt);
        final EditText Password = (EditText) findViewById(R.id.Password_txxt);
       mlogin.setFocusable(true);
       mlogin.setFocusableInTouchMode(true);///add this line
       mlogin.requestFocus();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(LoginUser.this,Home.class));
                    Toast.makeText(LoginUser.this, "Successfully logged in",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Logged in");
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        mgoogle = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
//                .build();
//
//        findViewById(R.id.Google_sign_in).setOnClickListener(this);

        mcreate.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(LoginUser.this, createLoginatstart.class);
                startActivity(changepage2);
            }
        }));

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Mail = Email.getText().toString();
                String Pass = Password.getText().toString();

                if (!Mail.equals("") && !Pass.equals("")) {
                    if (!Mail.contains(" ")) {
                            mAuth.signInWithEmailAndPassword(Mail, Pass).addOnCompleteListener(LoginUser.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                        Email.setError("Email or Password is invalid");
                                        Email.requestFocus();

                                    }
                                }


                            });
                        }
                    else {
                        Email.setError("Email can not contain spaces");
                        Email.requestFocus();
                    }
                }
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(LoginUser.this, ResetPassword.class);
                startActivity(changepage2);
            }
        });
    }


//    @Override
//    public void onClick(View v) {
//        Intent signInIntenet = Auth.GoogleSignInApi.getSignInIntent(mgoogle);
//        startActivityForResult(signInIntenet,RC_Sign_in);
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_Sign_in){
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//
//            if (result.isSuccess()){
//                GoogleSignInAccount account = result.getSignInAccount();
//                Username = account.getDisplayName();
//                imguri = account.getPhotoUrl().toString();
//                firebaseAuthWithGoogle(account);
//            }
//        }
//    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        String userID = user.getUid();
//
//                                   myRef.child(userID).child("UserInfo").child("UserName").setValue(Username);
//                                   myRef.child(userID).child("UserInfo").child("Image").child("url").setValue(imguri);
//
//                                   myRef.child(userID).child("Filter").child("Sortby").setValue("DATE");
//                                   myRef.child(userID).child("Filter").child("Spefic").setValue("Yours");
//                                   myRef.child(userID).child("Filter").child("SpeficString").setValue("sarchery");
//
//                                   Intent changepage = new Intent(LoginUser.this, Create_Interest.class);
//                                   startActivity(changepage);
//
//                       }
//
//                });
//
//
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.d(TAG,"ConnectionFAiled");
//    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
