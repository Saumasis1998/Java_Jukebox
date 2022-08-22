package DatabseHandlingEntity;

import StructuralEntity.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListOfPlaylistsDao
{
    Connection connection= Connector.connection;
    Statement statement=null;
    PreparedStatement preparedStatement=null;
    public void createListOfPlaylists(String username) throws SQLException
    {
        String lopName= "PlaylistsOf"+username;
        String query1= "create table if not exists "+lopName+ " (playlistName varchar(30) primary key)";
        statement= connection.createStatement();
        int count= statement.executeUpdate(query1);
//        System.out.println(count+" Table created");
        statement.close();
    }
    public void insertIntoListOfPlaylists(String username, List<Playlist> playlists) throws SQLException
    {
        String lopName= "PlaylistsOf"+username;
        ListIterator<Playlist> itr= playlists.listIterator();
        Playlist s;
        while (itr.hasNext())
        {
            s= itr.next();
            String query1="insert into "+lopName+" values(?)";
            preparedStatement= connection.prepareStatement(query1);
            preparedStatement.setString(1,s.getName());
            int count= preparedStatement.executeUpdate();
            System.out.println("Playlist added to your account");
        }
        preparedStatement.close();
    }
    public List<String> getPlaylists(String username) throws SQLException
    {
        String lopName= "PlaylistsOf"+username;
        List<String> playlists= new ArrayList<>();
        String query= "select * from "+lopName;
        statement= connection.createStatement();
        ResultSet resultSet= statement.executeQuery(query);
        while (resultSet.next())
        {
            playlists.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return playlists;

    }
}
