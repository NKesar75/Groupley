package domain.teamgroupley.groupleyapp;

import android.widget.EditText;

/**
 * Created by Raj Chandan on 6/14/2017.
 */

public class ProfileDataReading
{
    private String FirstName;
    private String LastName;
    private String Dateofbirth;
    private String Username;
    private String Gender;

    public ProfileDataReading()
    {

        
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getDateofbirth() {
        return Dateofbirth;
    }

    public String getUsername() {
        return Username;
    }

    public String getGender() {
        return Gender;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setDateofbirth(String dateofbirth) {
        Dateofbirth = dateofbirth;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}

