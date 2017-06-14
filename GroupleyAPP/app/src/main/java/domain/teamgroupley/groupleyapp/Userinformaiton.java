package domain.teamgroupley.groupleyapp;

/**
 * Created by Hector on 6/13/2017.
 */

public class Userinformaiton {
    String Firstname;
    String Lastname;
    String DOB;
    String UserName;
    String Sex;
    Userinformaiton(){

    }
    Userinformaiton(String First, String Last, String Dob, String User, String Gender){

        Firstname = First;
        Lastname = Last;
        DOB = Dob;
        UserName = User;
        Sex = Gender;
    }
    String GetFirstName(){
        return Firstname;
    }
    String GetLastName(){
        return Lastname;
    }
    String GetDOB(){
        return DOB;
    }
    String GetUsername(){
        return UserName;
    }
    String GetGender(){
        return Sex;
    }

    void SetFirst(String Temp){
        Firstname = Temp;
    }
    void SetLast(String Temp){
        Lastname = Temp;
    }
    void SetDob(String Temp){
        DOB = Temp;
    }
    void SetUSername(String Temp){
        UserName = Temp;
    }
    void SetSex(String Temp) {
        Sex = Temp;
    }
}
