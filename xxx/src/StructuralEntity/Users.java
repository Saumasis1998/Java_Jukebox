package StructuralEntity;

public class Users
{
    private String username;
    private String userid;
    private String password;

    public Users(String username, String userid, String password) {
        this.username = username;
        this.userid = userid;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

//    public void setUserid(String userid) {
//        this.userid = userid;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
