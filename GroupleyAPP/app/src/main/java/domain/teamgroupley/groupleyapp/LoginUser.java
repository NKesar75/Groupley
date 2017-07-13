package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginUser extends AppCompatActivity {

    private static final String TAG = "LoginUser";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button mlogin;
    private Button mcreate;
    private EditText Email;
    private EditText Password;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        mlogin = (Button) findViewById(R.id.LOGIN_BTN_LOgin);
        mcreate = (Button) findViewById(R.id.CREATE__ACCOUNT_BTN_LOGin);
        Email = (EditText) findViewById(R.id.Email_txt);
        Password = (EditText) findViewById(R.id.Password_txxt);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(LoginUser.this,MenuPage.class));
                    Toast.makeText(LoginUser.this, "Successfully logged in",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
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
                                    Toast.makeText(LoginUser.this, "Username or Password is invalid.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }


                        });
                    }
                    else {
                        Toast.makeText(LoginUser.this, "Email can not contain spaces.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
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
