package DatabseHandlingEntity;

import FunctionalEntity.UserOperations;
import StructuralEntity.Users;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UsersDao
{
    Connection connection= Connector.connection;
    Statement statement=null;
    PreparedStatement preparedStatement=null;

    public String insertIntoUsers() throws SQLException {
        Scanner ab= new Scanner(System.in);
        int choice;
        do{
            System.out.print("Enter 1 for login(New User) \nEnter 2 for sign-in(Existing User)\nYour choice: ");
            choice= ab.nextInt();
            System.out.println("\n\n");
        }while(choice>2 || choice<1);
        UserOperations userOperations= new UserOperations();
        if (choice==1)
        {
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tLogin\n\n");
            Users user= userOperations.createUser();
            System.out.println("\n\nYay! Account Created.\nAccount Details...");
            System.out.println("UserId: "+user.getUserid());
            System.out.println("UserName: "+user.getUsername());
            System.out.println("Password: "+user.getPassword());

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String query1= "insert into Users values(?,?,?)";
            preparedStatement= connection.prepareStatement(query1);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getUserid());
            preparedStatement.setString(3,user.getPassword());
            int count= preparedStatement.executeUpdate();
            System.out.println("\n\nAccount saved.\n\n");
        }
//        preparedStatement.close();

        System.out.println("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tSignin\n\n");
        String query2="select * from Users";
        statement= connection.createStatement();
        ResultSet resultSet= statement.executeQuery(query2);
        List<Users> usersList= new ArrayList<>();
        while (resultSet.next())
        {
            String un= resultSet.getString(1);
            String ui= resultSet.getString(2);
            String p= resultSet.getString(3);
            Users u= new Users(un,ui,p);
            usersList.add(u);
//            System.out.println(u);
        }


        int count=5;
        String userName="";
        do{
            System.out.print("userId: ");
            String userid= ab.next();
            System.out.print("password: ");
            String password= ab.next();

            if(usersList.stream().anyMatch(u->u.getUserid().equalsIgnoreCase(userid)))
            {
                Optional<Users> o;
                o= usersList.stream().filter(u->u.getUserid().equalsIgnoreCase(userid) && u.getPassword().equals(password)).findFirst();
                if (o.isEmpty())
                    System.out.println("\nWrong Password\n");
                else
                {
                    userName= o.get().getUsername();
                    break;
                }

            }
            else
                System.out.println("\nWrong userId\n");
//            userName= userOperations.validateUser(userid,password,usersList);
//            if(userName.equals("wp"))
//                System.out.println("Wrong Password!");
//            else if (userName.equals("wu"))
//                System.out.println("Wrong userId!");
//            else
//                break;
            System.out.println("\nRemaining chances "+(--count)+"\n\n");
        }while (count!=0);

        resultSet.close();
        statement.close();

        if(count!=0)
        {
            String message;
            LocalTime time= LocalTime.now();
            int hour= time.getHour();
            if(hour<12 && hour>3)
                message= "Good Morning ";
            else if (hour<17)
                message= "Good Afternoon ";
            else
                message= "Good Evening ";
            System.out.println("\n\n\n\n\n\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+message+userName+"\n\n\n\n\n\n\n\n");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return userName;
        }
        return userName;


    }
}
