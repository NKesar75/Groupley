package domain.teamgroupley.groupleyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Date;

public class createLoginatstart extends AppCompatActivity {

    private static final String TAG = "createLoginatstart";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public Button Create;
    public EditText FirstName;
    public EditText LastName;
    public EditText EmailAccount;
    public EditText Dateofbirth;
    public EditText Username;
    public EditText Password;
    public EditText Repassword;
    public Spinner  Gender;


    public void CreateAccount()
    {
        FirstName = (EditText) findViewById(R.id.First_NAME_Txt);
        LastName = (EditText) findViewById(R.id.LAST_NAME_txt);
        EmailAccount = (EditText) findViewById(R.id.EMAIL_TXT);
        Dateofbirth = (EditText) findViewById(R.id.DOB_txt);
        Username = (EditText) findViewById(R.id.USERNAME_TXT);
        Gender = (Spinner) findViewById(R.id.GENDER_SPINNER);
        Password = (EditText) findViewById(R.id.PASS_TXT);
        Repassword = (EditText) findViewById(R.id.REPASS_TXT);

        Create = (Button) findViewById(R.id.Create_account_btn);
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String First = FirstName.getText().toString();


                String Last = LastName.getText().toString();


                String Email = EmailAccount.getText().toString();


                String DOB = Dateofbirth.getText().toString();


                String User = Username.getText().toString();


                String Sex = Gender.getSelectedItem().toString();


                String Pass = Password.getText().toString();


                String Repass = Repassword.getText().toString();
                if (!Email.equals("") && !Pass.equals("")){
                    if (Pass.equals(Repass)) {
                        mAuth.createUserWithEmailAndPassword(Email, Pass)
                     .addOnCompleteListener(createLoginatstart.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(createLoginatstart.this, "Could not create Account.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
                    }
                }

                Intent changepage = new Intent(createLoginatstart.this, MenuPage.class);
                startActivity(changepage);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_loginatstart);

        Spinner mySpinner = (Spinner) findViewById(R.id.GENDER_SPINNER);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(createLoginatstart.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Genders));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        CreateAccount();
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
