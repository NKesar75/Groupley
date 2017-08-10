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
   public Spinner Gender;

    private DatePickerDialog.OnDateSetListener mDateSetListner;

    private StorageReference storageReference;
    private ImageView imageView;
    private Uri imguri;

    public  static final int Request_Code = 1234;

    @SuppressWarnings("VisibleForTests")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_stats);
      final EditText FirstName = (EditText)findViewById(R.id.First_NAME_Txt);
      final EditText LastName = (EditText) findViewById(R.id.LAST_NAME_txt);
      final EditText Dateofbirth = (EditText)findViewById(R.id.DOB_txt);
      final EditText Username = (EditText)findViewById(R.id.USERNAME_TXT);
        imageView = (ImageView)findViewById(R.id.image_load_profile);
        Gender = (Spinner) findViewById(R.id.GENDER_SPINNER);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UserInfoStats.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Genders));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(myAdapter);

        storageReference = FirebaseStorage.getInstance().getReference();;

        Dateofbirth.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UserInfoStats.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListner,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
                dialog.show();
            }
        });

        mDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;

                String mMonth = String.valueOf(month);
                String mDay = String.valueOf(dayOfMonth);
                String mYear = String.valueOf(year);

                Calendar cal = Calendar.getInstance();
                int curYear = cal.get(Calendar.YEAR);
                int curMonth = cal.get(Calendar.MONTH);
                int curDay = cal.get(Calendar.DAY_OF_MONTH);

                String mCurMonth = String.valueOf(curMonth);
                String mCurDay = String.valueOf(curDay);
                String mCurYear = String.valueOf(curYear);



                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    Date enteredDate = sdf.parse(mMonth + "/" + mDay + "/" + mYear);
                    Date curDate = sdf.parse(mCurMonth + "/" + mCurDay + "/" + mCurYear);

                    if(curDate.after(enteredDate))
                    {
                        Dateofbirth.setText(date);
                    }
                    else
                    {
                        Toast.makeText(UserInfoStats.this, "Invaild Date", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }

            }
        };

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

                String First = FirstName.getText().toString();

                String Last = LastName.getText().toString();

                String DOB = Dateofbirth.getText().toString();

                String User = Username.getText().toString();

                String Sex = Gender.getSelectedItem().toString();


                if (!First.equals("") && !Last.equals("") && !DOB.equals("") && !User.equals("") && !Sex.equals("")) {
                    if (!First.contains(" ")){
                        if (!Last.contains(" ")){
                            if (!User.contains(" ")){


                                // Userinformaiton userinformaiton = new Userinformaiton(First, Last, DOB, User, Sex);

                                myRef.child(userID).child("UserInfo").child("Firstname").setValue(First);
                                myRef.child(userID).child("UserInfo").child("Lastname").setValue(Last);
                                myRef.child(userID).child("UserInfo").child("DOB").setValue(DOB);
                                myRef.child(userID).child("UserInfo").child("UserName").setValue(User);
                                myRef.child(userID).child("UserInfo").child("Sex").setValue(Sex);

                                myRef.child(userID).child("Filter").child("Sortby").setValue("DATE");
                                myRef.child(userID).child("Filter").child("Spefic").setValue("Yours");
                                myRef.child(userID).child("Filter").child("SpeficString").setValue("sarchery");

                                if(imageView.getDrawable() != null)
                                {
                                    final StorageReference ref = storageReference.child(userID).child("Profile Image").child(System.currentTimeMillis() + "." + getImageExt(imguri));
                                    ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                        {
                                            ImageUpload imageUpload = new ImageUpload(taskSnapshot.getDownloadUrl().toString());
                                            myRef.child(userID).child("UserInfo").child("Image").setValue(imageUpload);

                                        }
                                    });
                                }
                                else
                                {
                                    String defaulturl = "https://firebasestorage.googleapis.com/v0/b/groupleyproject.appspot.com/o/default%20profile.png?alt=media&token=3a4db2ed-5ceb-449c-b859-f788d22110af";
                                    myRef.child(userID).child("UserInfo").child("Image").setValue(defaulturl);
                                }

                                //   myRef.child(userID).child("UserInfo").setValue(userinformaiton);
                                Intent changepage = new Intent(UserInfoStats.this, Create_Interest.class);
                                startActivity(changepage);
                            }
                            else{
                                Username.setError("Username can not contain spaces");
                                Username.requestFocus();
                            }
                        }
                        else{
                            LastName.setError("Last name can not contain spaces");
                            LastName.requestFocus();
                        }
                    }

                    else{
                        FirstName.setError("First name can not contain spaces");
                        FirstName.requestFocus();
                    }
                }
                else{
                    FirstName.setError("Missing Some Information");
                    FirstName.requestFocus();
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
