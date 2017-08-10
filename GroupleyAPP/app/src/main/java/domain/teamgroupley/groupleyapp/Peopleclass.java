package domain.teamgroupley.groupleyapp;

/**
 * Created by Hector on 8/10/2017.
 */

public class Peopleclass {

    String mUser;
    String mPhoto;

    Peopleclass(String user, String photo){
        mUser = user;
        mPhoto = photo;
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }
}
