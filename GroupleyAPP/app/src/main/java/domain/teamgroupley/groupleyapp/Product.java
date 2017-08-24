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

    public String getMcity() {
        return mcity;
    }

    public void setMcity(String mcity) {
        this.mcity = mcity;
    }

    public String getMstate() {
        return mstate;
    }

    public void setMstate(String mstate) {
        this.mstate = mstate;
    }

    public String getMcountry() {
        return mcountry;
    }

    public void setMcountry(String mcountry) {
        this.mcountry = mcountry;
    }

    public String getMzipcode() {
        return mzipcode;
    }

    public void setMzipcode(String mzipcode) {
        this.mzipcode = mzipcode;
    }

    private  String mcity;
    private String mstate;
    private String mcountry;
    private String mzipcode;
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

    public Product(String title, String date, String category,String imageid, int Eventnumber, String city, String State, String Country, String Zipcode)
    {
        this.imageid = imageid;
        this.title = title;
        this.Date = date;
        this.Category = category;
        mcity = city;
        mstate = State;
        mcountry = Country;
        mzipcode = Zipcode;
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
