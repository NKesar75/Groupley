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

public class LoginUser extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static int RC_Sign_in = 0;
    private static final String TAG = "LoginUser";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mgoogle;
    DataSnapshot mdatasnapshot;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference storageReference;
    private String imguri;
    public  static final int Request_Code = 1234;

    private Button mlogin;
    private Button mcreate;

    boolean inthere = false;
    String Firstname;
    String Lastname;
    String DOB;
    String Gender;
    String Username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        mlogin = (Button) findViewById(R.id.LOGIN_BTN_LOgin);
        mcreate = (Button) findViewById(R.id.CREATE__ACCOUNT_BTN_LOGin);
        final EditText Email = (EditText) findViewById(R.id.Email_txt);
        final EditText Password = (EditText) findViewById(R.id.Password_txxt);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();;

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mgoogle = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        findViewById(R.id.Google_sign_in).setOnClickListener(this);

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
    }

    @Override
    public void onClick(View v) {
        Intent signInIntenet = Auth.GoogleSignInApi.getSignInIntent(mgoogle);
        startActivityForResult(signInIntenet,RC_Sign_in);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_Sign_in){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                Firstname = account.getGivenName();
                Lastname = account.getFamilyName();
                Username = account.getDisplayName();
                DOB = "10/04/1997";
                Gender = "PREFER NOT TO ANSWER";
                imguri = account.getPhotoUrl().toString();
                firebaseAuthWithGoogle(account);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                            if (!inthere) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            final String userID = user.getUid();


                            myRef.child(userID).child("UserInfo").child("Firstname").setValue(Firstname);
                            myRef.child(userID).child("UserInfo").child("Lastname").setValue(Lastname);
                            myRef.child(userID).child("UserInfo").child("DOB").setValue(DOB);
                            myRef.child(userID).child("UserInfo").child("UserName").setValue(Username);
                            myRef.child(userID).child("UserInfo").child("Sex").setValue(Gender);

                            myRef.child(userID).child("Filter").child("Sortby").setValue("DATE");
                            myRef.child(userID).child("Filter").child("Spefic").setValue("Yours");
                            myRef.child(userID).child("Filter").child("SpeficString").setValue("sarchery");

                                myRef.child(userID).child("Interests").child("cactionfigures").setValue(false);
                                myRef.child(userID).child("Interests").child("ccars").setValue(false);
                                myRef.child(userID).child("Interests").child("ccoins").setValue(false);
                                myRef.child(userID).child("Interests").child("ccomics").setValue(false);
                                myRef.child(userID).child("Interests").child("cguns").setValue(false);
                                myRef.child(userID).child("Interests").child("ctrucks").setValue(false);
                                myRef.child(userID).child("Interests").child("dacting").setValue(false);
                                myRef.child(userID).child("Interests").child("dcosplay").setValue(false);
                                myRef.child(userID).child("Interests").child("dlarping").setValue(false);
                                myRef.child(userID).child("Interests").child("gaction").setValue(false);
                                myRef.child(userID).child("Interests").child("gadventure").setValue(false);
                                myRef.child(userID).child("Interests").child("gfps").setValue(false);
                                myRef.child(userID).child("Interests").child("gindies").setValue(false);
                                myRef.child(userID).child("Interests").child("gmmo").setValue(false);
                                myRef.child(userID).child("Interests").child("gpartyfamily").setValue(false);
                                myRef.child(userID).child("Interests").child("grpg").setValue(false);
                                myRef.child(userID).child("Interests").child("gsimulation").setValue(false);
                                myRef.child(userID).child("Interests").child("gsports").setValue(false);
                                myRef.child(userID).child("Interests").child("gstragy").setValue(false);
                                myRef.child(userID).child("Interests").child("mcountry").setValue(false);
                                myRef.child(userID).child("Interests").child("mdrillrap").setValue(false);
                                myRef.child(userID).child("Interests").child("medm").setValue(false);
                                myRef.child(userID).child("Interests").child("mjazz").setValue(false);
                                myRef.child(userID).child("Interests").child("moAction").setValue(false);
                                myRef.child(userID).child("Interests").child("moAnimation").setValue(false);
                                myRef.child(userID).child("Interests").child("moComdey").setValue(false);
                                myRef.child(userID).child("Interests").child("moDocumentary").setValue(false);
                                myRef.child(userID).child("Interests").child("moFamily").setValue(false);
                                myRef.child(userID).child("Interests").child("moHorror").setValue(false);
                                myRef.child(userID).child("Interests").child("moMusical").setValue(false);
                                myRef.child(userID).child("Interests").child("moSifi").setValue(false);
                                myRef.child(userID).child("Interests").child("moSports").setValue(false);
                                myRef.child(userID).child("Interests").child("moThriller").setValue(false);
                                myRef.child(userID).child("Interests").child("moWar").setValue(false);
                                myRef.child(userID).child("Interests").child("mrap").setValue(false);
                                myRef.child(userID).child("Interests").child("mrnb").setValue(false);
                                myRef.child(userID).child("Interests").child("mrock").setValue(false);
                                myRef.child(userID).child("Interests").child("mscremo").setValue(false);
                                myRef.child(userID).child("Interests").child("pfestivles").setValue(false);
                                myRef.child(userID).child("Interests").child("phouseParites").setValue(false);
                                myRef.child(userID).child("Interests").child("pnightClubs").setValue(false);
                                myRef.child(userID).child("Interests").child("sarchery").setValue(false);
                                myRef.child(userID).child("Interests").child("sbaseball").setValue(false);
                                myRef.child(userID).child("Interests").child("sbasketball").setValue(false);
                                myRef.child(userID).child("Interests").child("scycling").setValue(false);
                                myRef.child(userID).child("Interests").child("sfishing").setValue(false);
                                myRef.child(userID).child("Interests").child("sfootball").setValue(false);
                                myRef.child(userID).child("Interests").child("sfrisbe").setValue(false);
                                myRef.child(userID).child("Interests").child("sgolf").setValue(false);
                                myRef.child(userID).child("Interests").child("shoccey").setValue(false);
                                myRef.child(userID).child("Interests").child("shunting").setValue(false);
                                myRef.child(userID).child("Interests").child("sskateboarding").setValue(false);
                                myRef.child(userID).child("Interests").child("ssnowBoarding").setValue(false);
                                myRef.child(userID).child("Interests").child("swaterSports").setValue(false);
                                myRef.child(userID).child("Interests").child("swrestling").setValue(false);
                                myRef.child(userID).child("Interests").child("taction").setValue(false);
                                myRef.child(userID).child("Interests").child("tadventure").setValue(false);
                                myRef.child(userID).child("Interests").child("tanimation").setValue(false);
                                myRef.child(userID).child("Interests").child("tbiography").setValue(false);
                                myRef.child(userID).child("Interests").child("tcomedy").setValue(false);
                                myRef.child(userID).child("Interests").child("tcrime").setValue(false);
                                myRef.child(userID).child("Interests").child("tdocoumentary").setValue(false);
                                myRef.child(userID).child("Interests").child("tdrama").setValue(false);
                                myRef.child(userID).child("Interests").child("tfamily").setValue(false);
                                myRef.child(userID).child("Interests").child("tgameShows").setValue(false);
                                myRef.child(userID).child("Interests").child("thistory").setValue(false);
                                myRef.child(userID).child("Interests").child("thorror").setValue(false);
                                myRef.child(userID).child("Interests").child("tmystery").setValue(false);
                                myRef.child(userID).child("Interests").child("treality").setValue(false);
                                myRef.child(userID).child("Interests").child("tsitcom").setValue(false);
                                myRef.child(userID).child("Interests").child("tsports").setValue(false);
                                myRef.child(userID).child("Interests").child("ttalkShows").setValue(false);
                                myRef.child(userID).child("Interests").child("twar").setValue(false);

                                myRef.child(userID).child("UserInfo").child("Image").child("url").setValue(imguri);


                            Intent changepage = new Intent(LoginUser.this, Home.class);
                            startActivity(changepage);
                        }
                        if (inthere) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            final String userID = user.getUid();

                            myRef.child(userID).child("UserInfo").child("Firstname").setValue(Firstname);
                            myRef.child(userID).child("UserInfo").child("Lastname").setValue(Lastname);
                            myRef.child(userID).child("UserInfo").child("DOB").setValue(DOB);
                            myRef.child(userID).child("UserInfo").child("UserName").setValue(Username);
                            myRef.child(userID).child("UserInfo").child("Sex").setValue(Gender);

                            myRef.child(userID).child("Filter").child("Sortby").setValue("DATE");
                            myRef.child(userID).child("Filter").child("Spefic").setValue("Yours");
                            myRef.child(userID).child("Filter").child("SpeficString").setValue("sarchery");

//                         StorageReference ref = storageReference.child(userID).child("Profile Image").child(System.currentTimeMillis() + "." + getImageExt(imguri));
//                        ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                ImageUpload imageUpload = new ImageUpload(taskSnapshot.getDownloadUrl().toString());
//                                myRef.child(userID).child("UserInfo").child("Image").setValue(imageUpload);
//
//    }
//                        });

                            Intent changepage = new Intent(LoginUser.this, Home.class);
                            startActivity(changepage);
                        }

                    }
                });



    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG,"ConnectionFAiled");
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

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
