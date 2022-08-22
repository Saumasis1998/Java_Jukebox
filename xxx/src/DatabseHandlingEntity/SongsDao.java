package DatabseHandlingEntity;

import FunctionalEntity.ArtistOperations;
import FunctionalEntity.AudioOperations;
import StructuralEntity.Artists;
import StructuralEntity.Audio;
import StructuralEntity.AudioType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SongsDao
{
    Connection connection= Connector.connection;
    Statement statement=null;
    PreparedStatement preparedStatement=null;
    List<Artists> artistsList= new ArrayList<>();
    public SongsDao()
    {
        ArtistsDao artistsDao= new ArtistsDao();

        try {
            artistsList= artistsDao.getArtists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoSongs(String filename) throws SQLException {
        List<Audio> songs= new ArrayList<>();
        try {
            FileReader fr= new FileReader(filename);
            BufferedReader br= new BufferedReader(fr);
            String s;
            String[] arr;
            Audio audio;
            while ((s= br.readLine())!=null)
            {
                arr= s.split(",");
                String audioName= arr[0].trim();
                String artistName= arr[1].trim();
                Date artistDOB= Date.valueOf(arr[2].trim());
                String artistOrigin= arr[3].trim();
                String genre= arr[4].trim();
                String albumName= arr[5].trim();
                Date releaseDate= Date.valueOf(arr[6].trim());
                String url= arr[7].trim();
                Time duration= Time.valueOf(arr[8].trim());
//                AudioType audioType= AudioType.valueOf(arr[9].trim().toUpperCase());

                AudioOperations audioOperations= new AudioOperations();
                ArtistOperations artistOperations= new ArtistOperations();
                if(audioOperations.audioIsPresent(audioName,artistName,songs))
                    continue;

                String audioId=  audioOperations.generateAudioId(audioName,artistName,releaseDate,AudioType.SONG);


                String artistId= artistOperations.getArtistId(artistName,artistOrigin,artistDOB,artistsList);

                Artists artist= new Artists(artistName,artistId,artistDOB,artistOrigin);
                Audio song=  new Audio(audioId,audioName,artist,genre,albumName,releaseDate,url,duration,AudioType.SONG);

                songs.add(song);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListIterator<Audio> itr= songs.listIterator();
        while (itr.hasNext())
        {
            Audio song= itr.next();
            String query1= "insert into songs values(?,?,?,?,?,?,?,?)";
            preparedStatement= connection.prepareStatement(query1);
            preparedStatement.setString(1,song.getAudioId());
            preparedStatement.setString(2,song.getName());
            preparedStatement.setString(3,song.getArtist().getArtistId());
            preparedStatement.setString(4,song.getGenre());
            preparedStatement.setString(5,song.getAlbumName());
            preparedStatement.setString(6,song.getReleaseDate()+"");
            preparedStatement.setString(7,song.getUrl());
            preparedStatement.setString(8,song.getDuration().toString());
            int count= preparedStatement.executeUpdate();
            System.out.println(count+ " Row added");
        }
    }
    public List<Audio> getSongs() throws SQLException
    {
        String query1= "select * from Songs";
        statement= connection.createStatement();
        ResultSet resultSet= statement.executeQuery(query1);
        List<Audio> songs= new ArrayList<>();
        while (resultSet.next())
        {
            String audioId= resultSet.getString(1);
            String audioName= resultSet.getString(2);
            String artistid= resultSet.getString(3);
            ArtistOperations artistOperations= new ArtistOperations();
            Artists artist= artistOperations.getArtist(artistid,artistsList);

            String genre= resultSet.getString(4);
            String albumName= resultSet.getString(5);
            Date releaseDate= resultSet.getDate(6);
            String url= resultSet.getString(7);
            Time duration= resultSet.getTime(8);

            Audio song= new Audio(audioId,audioName,artist,genre,albumName,releaseDate,url,duration,AudioType.SONG);
            songs.add(song);
        }
        resultSet.close();
        statement.close();
        return songs;
    }

    public static void main(String[] args)
    {
        try {
            Connector.setConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        SongsDao songsDao= new SongsDao();
        String filename= "Resources/Songs.csv";
        try {
            songsDao.insertIntoSongs(filename);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
