package domain.teamgroupley.groupleyapp;

/**
 * Created by Raj Chandan on 7/20/2017.
 */

public class Product
{
    private  int imageid;
    private String title;
    private String Date;


    public Product()
    {

    }

    public Product(String title, String Date)
    {
        this.title = title;
        this.Date = Date;
    }

    public Product(String title, String Date,int imageid)
    {
        this.imageid = imageid;
        this.title = title;
        this.Date = Date;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
