package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class createLoginatstart extends AppCompatActivity {

    private static final String TAG = "createLoginatstart";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public Button Create;
    public EditText EmailAccount;
    public EditText Password;
    public EditText Repassword;


    public void CreateAccount()
    {
        Create = (Button) findViewById(R.id.Create_account_btn);
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Email = EmailAccount.getText().toString();

                String Pass = Password.getText().toString();

                String Repass = Repassword.getText().toString();

                    if (!Email.equals("") && !Pass.equals("") && !Repass.equals("")) {
                        if (Pass.equals(Repass)) {
                            mAuth.createUserWithEmailAndPassword(Email, Pass)
                                    .addOnCompleteListener(createLoginatstart.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                            Toast.makeText(createLoginatstart.this, "Account created.",
                                                    Toast.LENGTH_SHORT).show();

                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(createLoginatstart.this, "Account not created.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                            // ...
                                        }

                                    });
                            mAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(createLoginatstart.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    }
                                }

                            });
                            Intent changepage = new Intent(createLoginatstart.this, UserInfoStats.class);
                            startActivity(changepage);
                        }
                        else{
                            Toast.makeText(createLoginatstart.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(createLoginatstart.this, "Missing some information", Toast.LENGTH_SHORT).show();
                    }
                    }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_loginatstart);

        EmailAccount = (EditText) findViewById(R.id.EMAIL_TXT);
        Password = (EditText) findViewById(R.id.PASS_TXT);
        Repassword = (EditText) findViewById(R.id.REPASS_TXT);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        CreateAccount();
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
