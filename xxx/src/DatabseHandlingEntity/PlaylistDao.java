package DatabseHandlingEntity;

import FunctionalEntity.PlaylistOperations;
import StructuralEntity.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PlaylistDao
{
    Connection connection= Connector.connection;
    Statement statement=null;
    PreparedStatement preparedStatement=null;

    public Playlist createPlaylist(String playlistName, String username) throws SQLException
    {
        playlistName= username+playlistName;
        String query1= "create table if not exists "+playlistName+ " (audioId varchar(20) primary key)";
        statement= connection.createStatement();
        int count= statement.executeUpdate(query1);
        System.out.println("Playlist created\n");
        statement.close();
        PlaylistOperations obj= new PlaylistOperations();
        return obj.createPlayList(playlistName);
    }
    public void insertIntoPlaylist(String playlistName,String username,List<String> audioList) throws SQLException
    {
        playlistName= username+playlistName;
        boolean isPresent= playlistIsPresent(playlistName,username);
        if(!isPresent)
            return;
        ListIterator<String> itr= audioList.listIterator();
        String s;
        while (itr.hasNext())
        {
            s= itr.next();
            String query1="insert into "+playlistName+" values(?)";
            preparedStatement= connection.prepareStatement(query1);
            preparedStatement.setString(1,s);
            int count= preparedStatement.executeUpdate();
            System.out.println("Audio added to your playlist\n");
        }
        preparedStatement.close();
    }
    public boolean playlistIsPresent(String playlistName, String username) throws SQLException
    {
        String lopName= "PlaylistsOf"+username;
//        playlistName= username+ playlistName;
        String query1= "select * from "+lopName+" where playlistName= ?";
        preparedStatement = connection.prepareStatement(query1);
        preparedStatement.setString(1,playlistName);
        ResultSet resultSet= preparedStatement.executeQuery();
        String result=null;
        if (resultSet.next())
            result= resultSet.getString(1);
        if (result== null)
        {
            System.out.println("Playlist doesn't exist");
            return false;
        }
        return true;
    }
    public Playlist getPlaylist(String playlistName, String username) throws SQLException
    {
        playlistName= username+ playlistName;
        boolean isPresent= playlistIsPresent(playlistName,username);
        if(!isPresent)
            return null;
        String query2= "select * from "+playlistName;
        statement= connection.createStatement();
        ResultSet resultSet= preparedStatement.executeQuery(query2);
        List<String> audioId= new ArrayList<>();
        while(resultSet.next())
        {
            audioId.add(resultSet.getString(1));
        }
        PlaylistOperations po= new PlaylistOperations();
        Playlist p= po.createPlayList(playlistName);
        p.setAudioList(audioId);
        return p;
    }
}
