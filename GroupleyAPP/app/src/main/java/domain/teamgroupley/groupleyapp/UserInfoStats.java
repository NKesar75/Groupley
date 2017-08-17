package domain.teamgroupley.groupleyapp;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class UserInfoStats extends AppCompatActivity {

    private static final String TAG = "UserInfoStats";
    private FirebaseAuth mAuth =  FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    FirebaseUser user = mAuth.getCurrentUser();
    String userID = user.getUid();

    public Button Create;

    private StorageReference storageReference;
    private ImageView imageView;
    private Uri imguri;

    public  static final int Request_Code = 1234;


    @SuppressWarnings("VisibleForTests")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_stats);


      final EditText Username = (EditText)findViewById(R.id.USERNAME_TXT);
        imageView = (ImageView)findViewById(R.id.image_load_profile);

        storageReference = FirebaseStorage.getInstance().getReference();;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
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

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Create = (Button) findViewById(R.id.Create_User_stats_btn);
        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String User = Username.getText().toString();
                if ( !User.equals("")) {

                    myRef.child(userID).child("UserInfo").child("UserName").setValue(User);

                    myRef.child(userID).child("Filter").child("Sortby").setValue("DATE");
                    myRef.child(userID).child("Filter").child("Spefic").setValue("Yours");
                    myRef.child(userID).child("Filter").child("SpeficString").setValue("sarchery");

                    if (imageView.getDrawable() != null) {
                        final StorageReference ref = storageReference.child(userID).child("Profile Image").child(System.currentTimeMillis() + "." + getImageExt(imguri));
                        ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                ImageUpload imageUpload = new ImageUpload(taskSnapshot.getDownloadUrl().toString());
                                myRef.child(userID).child("UserInfo").child("Image").setValue(imageUpload);

                            }
                        });
                    } else {
                        String defaulturl = "https://firebasestorage.googleapis.com/v0/b/groupleyproject.appspot.com/o/default%20profile.png?alt=media&token=3a4db2ed-5ceb-449c-b859-f788d22110af";
                        myRef.child(userID).child("UserInfo").child("Image").child("url").setValue(defaulturl);
                    }

                    Intent changepage = new Intent(UserInfoStats.this, Create_Interest.class);
                    startActivity(changepage);

                }

            }
        });
    }

    public void image_upload(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SelectImage"),Request_Code);
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
                imageView.setImageBitmap(bm);
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
