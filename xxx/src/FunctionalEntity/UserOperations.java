package FunctionalEntity;

import StructuralEntity.Users;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class UserOperations
{
    public Users createUser()
    {
        Scanner ab=new Scanner(System.in);
        System.out.print("Enter username: ");
        String name= ab.nextLine();
        System.out.println("Password must be minimum of 8 characters and maximum of 20 characters.");
        String password;
        do{
            System.out.print("Enter password: ");
            password= ab.next();
        }while (password.length()<8 || password.length()>20);
        Date doo= new Date();
        int day= doo.getDay();
        int mon= doo.getMonth();
        int year=doo.getYear();
        int hours= doo.getHours();
        int minutes= doo.getMinutes();
        int seconds= doo.getSeconds();
        String userid= password.substring(0,3)+day+mon+year+hours+minutes+seconds;
        Users u= new Users(name,userid,password);
        return u;
    }
    public String validateUser(String userId, String password, List<Users> usersList)
    {
        ListIterator<Users> itr= usersList.listIterator();
        Users u;
        while (itr.hasNext())
        {
            u= itr.next();
            if (u.getUserid().equalsIgnoreCase(userId));
            {
                if (u.getPassword().equals(password))
                    return u.getUsername();
                else
                return "wp";
            }
        }
        return "wu";
    }
}
