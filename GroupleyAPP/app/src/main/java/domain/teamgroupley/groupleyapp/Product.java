package domain.teamgroupley.groupleyapp;

/**
 * Created by Raj Chandan on 7/20/2017.
 */

public class Product
{
    private  String imageid;
    private String title;
    private String Date;
    private String Category;
    private int mEventnumber;


    public Product()
    {

    }

    public Product(String title, String date, String category,String imageid)
    {
        this.imageid = imageid;
        this.title = title;
        this.Date = date;
        this.Category = category;
    }
    public Product(String title, String date, String category,String imageid, int Eventnumber)
    {
        this.imageid = imageid;
        this.title = title;
        this.Date = date;
        this.Category = category;
        mEventnumber = Eventnumber;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getmEventnumber() {
        return mEventnumber;
    }

    public void setmEventnumber(int mEventnumber) {
        this.mEventnumber = mEventnumber;
    }
}
