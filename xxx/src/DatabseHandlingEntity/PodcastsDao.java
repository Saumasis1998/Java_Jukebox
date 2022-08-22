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

public class PodcastsDao
{
    Connection connection= Connector.connection;
    Statement statement=null;
    PreparedStatement preparedStatement=null;
    List<Artists> artistsList= new ArrayList<>();
    public PodcastsDao()
    {
        ArtistsDao artistsDao= new ArtistsDao();

        try {
            artistsList= artistsDao.getArtists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoPodcasts(String filename) throws SQLException {
        List<Audio> podcasts= new ArrayList<>();
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
                if(audioOperations.audioIsPresent(audioName,artistName,podcasts))
                    continue;

                String audioId=  audioOperations.generateAudioId(audioName,artistName,releaseDate,AudioType.PODCAST);


                String artistId= artistOperations.getArtistId(artistName,artistOrigin,artistDOB,artistsList);

                Artists artist= new Artists(artistName,artistId,artistDOB,artistOrigin);
                Audio podcast=  new Audio(audioId,audioName,artist,genre,albumName,releaseDate,url,duration,AudioType.PODCAST);

                podcasts.add(podcast);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListIterator<Audio> itr= podcasts.listIterator();
        while (itr.hasNext())
        {
            Audio podcast= itr.next();
            String query1= "insert into podcasts values(?,?,?,?,?,?,?,?)";
            preparedStatement= connection.prepareStatement(query1);
            preparedStatement.setString(1,podcast.getAudioId());
            preparedStatement.setString(2,podcast.getName());
            preparedStatement.setString(3,podcast.getArtist().getArtistId());
            preparedStatement.setString(4,podcast.getGenre());
            preparedStatement.setString(5,podcast.getAlbumName());
            preparedStatement.setString(6,podcast.getReleaseDate()+"");
            preparedStatement.setString(7,podcast.getUrl());
            preparedStatement.setString(8,podcast.getDuration().toString());
            int count= preparedStatement.executeUpdate();
            System.out.println(count+ " Row added");
        }
    }

    public List<Audio> getPodcasts() throws SQLException
    {
        String query1= "select * from Podcasts";
        statement= connection.createStatement();
        ResultSet resultSet= statement.executeQuery(query1);
        List<Audio> podcasts= new ArrayList<>();
        while (resultSet.next())
        {
            String audioId= resultSet.getString(1);
            String audioName= resultSet.getString(2);
            String artistId= resultSet.getString(3);
            ArtistOperations artistOperations= new ArtistOperations();
            Artists artist= artistOperations.getArtist(artistId,artistsList);

            String genre= resultSet.getString(4);
            String albumName= resultSet.getString(5);
            Date releaseDate= resultSet.getDate(6);
            String url= resultSet.getString(7);
            Time duration= resultSet.getTime(8);

            Audio podcast= new Audio(audioId,audioName,artist,genre,albumName,releaseDate,url,duration,AudioType.PODCAST);
            podcasts.add(podcast);
        }
        resultSet.close();
        statement.close();
        return podcasts;
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

        PodcastsDao podcastsDao= new PodcastsDao();
        String filename= "Resources/Podcasts.csv";
        try {
            podcastsDao.insertIntoPodcasts(filename);
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
