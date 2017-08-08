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
    public static String Email;
    public static String Pass;
    public static String Repass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_loginatstart);

        final EditText EmailAccount = (EditText) findViewById(R.id.EMAIL_TXT);
        final EditText Password = (EditText) findViewById(R.id.PASS_TXT);
        final EditText Repassword = (EditText) findViewById(R.id.REPASS_TXT);

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

        Create = (Button) findViewById(R.id.Create_account_btn);
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Email = EmailAccount.getText().toString();
                Pass = Password.getText().toString();
                Repass = Repassword.getText().toString();
                if (Pass.length() >= 8) {
                    if (!Email.equals("") && !Pass.equals("") && !Repass.equals("")) {
                        if (Pass.equals(Repass)) {
                            if (!Email.contains(" ")) {
                                mAuth.createUserWithEmailAndPassword(Email, Pass)
                                        .addOnCompleteListener(createLoginatstart.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    SendemailVerication();
                                                    Intent changepage = new Intent(createLoginatstart.this, VerfiyEmail.class);
                                                    startActivity(changepage);
                                                } else {
                                                    Toast.makeText(createLoginatstart.this, "Account not created.",
                                                            Toast.LENGTH_SHORT).show();
                                                }

                                                // ...
                                            }

                                        });

                            } else {
                                EmailAccount.setError("Email can not contain a space");
                                EmailAccount.requestFocus();
                            }
                        } else {
                            Password.setError("Passwords do not match");
                            Password.requestFocus();

                        }
                    } else {
                        EmailAccount.setError("Missing some information");
                        EmailAccount.requestFocus();
                    }
                }
                else{
                    Password.setError("Passwords must be atleast 8 Characters");
                    Password.requestFocus();
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
    private void SendemailVerication() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(createLoginatstart.this, "Verifcation email sent to your email", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                            }
                        }
                    });
        }
    }
}
