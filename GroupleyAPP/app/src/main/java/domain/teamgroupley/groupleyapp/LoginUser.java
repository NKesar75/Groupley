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
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mail = Email.getText().toString();
                String Pass = Password.getText().toString();
                if (!Mail.equals("") && !Pass.equals("")){
                    mAuth.signInWithEmailAndPassword(Mail,Pass);
                    startActivity(new Intent(LoginUser.this,MenuPage.class));
                }
                else{
                    Toast.makeText(LoginUser.this, "Username or Password is invalid.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        mcreate.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepage2 = new Intent(LoginUser.this,createLoginatstart.class);
                startActivity(changepage2);
            }
        }));
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
