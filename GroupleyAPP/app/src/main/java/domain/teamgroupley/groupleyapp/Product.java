package domain.teamgroupley.groupleyapp;

/**
 * Created by Raj Chandan on 7/20/2017.
 */

public class Product
{
    private  int imageid;
    private String title;
    private String Date;
    private String Category;


    public Product()
    {

    }

    public Product(String title, String date, String category)
    {
        this.title = title;
        this.Date = date;
        this.Category = category;
    }

    public Product(String title, String date, String category,int imageid)
    {
        this.imageid = imageid;
        this.title = title;
        this.Date = date;
        this.Category = category;
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
