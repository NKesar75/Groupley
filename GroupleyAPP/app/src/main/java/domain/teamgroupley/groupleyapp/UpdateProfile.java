package domain.teamgroupley.groupleyapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;

public class UpdateProfile extends AppCompatActivity {


    private EditText muusername;
    private ImageView updateImage;

    private StorageReference storageReference;
    private Uri imguri;

    public  static final int Request_Code = 1234;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String USerid = user.getUid();

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUsername = mRootRef.child(USerid).child("UserInfo").child("UserName");
    DatabaseReference mImage = mRootRef.child(USerid).child("UserInfo").child("Image").child("url");

    private Button Save;

    private static final String TAG = "UpdateProfile";
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Save = (Button)findViewById(R.id.EDIT_PROFILE_BTN_update);

       Save.setFocusable(true);
       Save.setFocusableInTouchMode(true);///add this line
       Save.requestFocus();

        final EditText username = (EditText)findViewById(R.id.USERNAME_Tst_update);


        muusername = (EditText)findViewById(R.id.USERNAME_Tst_update);
        updateImage = (ImageView)findViewById(R.id.profile_update_image);

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"SelectImage"),Request_Code);
            }
        });

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
                    Toast.makeText(UpdateProfile.this, "Signed Out", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String User = username.getText().toString();



                         if (!User.equals("") && !User.equals(" ")){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();

                    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                    mRootRef.child(userID).child("UserInfo").child("UserName").setValue(User);

                    startActivity(new Intent(UpdateProfile.this,Profile.class));
                    Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                }
                else{
                     username.setError("Username can not be blank or have a space");
                     username.requestFocus();
                    }

            }
        });

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imguri = data.getData();
            try
            {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),imguri);
                updateImage.setImageBitmap(bm);
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);


        mUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Temp = dataSnapshot.getValue(String.class);
                muusername.setText(Temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mImage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue(String.class);
                Glide.with(UpdateProfile.this).load(image.toString()).into(updateImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
