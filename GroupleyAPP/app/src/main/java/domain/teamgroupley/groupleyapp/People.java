package domain.teamgroupley.groupleyapp;

/**
 * Created by Raj Chandan on 8/10/2017.
 */

public class People
{
    private  String imageid;
    private String username;

    public People()
    {

    }

    public People(String username, String imageid)
    {
        this.imageid = imageid;
        this.username = username;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
