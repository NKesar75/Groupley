package domain.teamgroupley.groupleyapp;

/**
 * Created by Hector on 6/14/2017.
 */

public class CreateEventStatsinfo
{
    String Title;
    String Description;
    String Category;
    String Date;
    String Time;
    String Address;
    String Max_People;

    CreateEventStatsinfo()
    {

    }

    CreateEventStatsinfo(String _Title, String _Description, String _Category, String _Date, String _Time, String _Address, String _Max_People)
    {

        Title = _Title;
        Description = _Description;
        Category = _Category;
        Date = _Date;
        Time = _Time;
        Address = _Address;
        Max_People = _Max_People;
    }
    String GetTitle(){
        return Title;
    }
    String GetDescription(){
        return Description;
    }
    String GetCategory(){
        return Category;
    }
    String GetDate(){
        return Date;
    }
    String GetTime(){
        return Time;
    }
    String GetAddess(){
        return Address;
    }
    String GetMax_People(){
        return Max_People;
    }

    void SetTitle(String Temp){
        Title = Temp;
    }
    void SetDescription(String Temp){
        Description = Temp;
    }
    void SetCategory(String Temp){
        Category = Temp;
    }
    void SetDate(String Temp){
        Date = Temp;
    }
    void SetTime(String Temp) {
        Time = Temp;
    }
    void SetAddess(String Temp) {
        Address = Temp;
    }
    void SetMax_Peopl(String Temp) {
        Max_People = Temp;
    }
}
