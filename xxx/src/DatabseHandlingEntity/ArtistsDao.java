package DatabseHandlingEntity;

import FunctionalEntity.ArtistOperations;
import StructuralEntity.Artists;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class  ArtistsDao
{
    Connection connection= Connector.connection;
    Statement statement=null;
    PreparedStatement preparedStatement=null;

    public void insertIntoArtists(String filename) throws SQLException
    {
        List<Artists> artistsList= new ArrayList<>();
        try {
            FileReader fr= new FileReader(filename);
            BufferedReader br= new BufferedReader(fr);

            String s;
            String[] arr;

            while ((s= br.readLine())!=null)
            {
                arr= s.split(",");
                String name= arr[0].trim();
                Date dob= Date.valueOf(arr[1].trim());
                String origin= arr[2].trim();

                ArtistOperations artistOperations= new ArtistOperations();
                Artists artist;
                if(!artistOperations.artistIsPresent(name,origin,dob,artistsList))
                {
                    artist= artistOperations.createArtist(name,origin,dob);
                    artistsList.add(artist);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListIterator<Artists> itr= artistsList.listIterator();
        Artists a;
        while (itr.hasNext())
        {
            a= itr.next();
            String query1= "insert into Artists values(?,?,?,?)";
            preparedStatement= connection.prepareStatement(query1);
            preparedStatement.setString(1,a.getArtistId());
            preparedStatement.setString(2,a.getName());
            preparedStatement.setString(3,a.getDob()+"");
            preparedStatement.setString(4,a.getOrigin());
            int c= preparedStatement.executeUpdate();
            System.out.println(c+ "Row updated");
        }
        preparedStatement.close();


    }
    public List<Artists> getArtists() throws SQLException
    {
        String query1="select * from Artists";
        statement= connection.createStatement();
        ResultSet resultSet= statement.executeQuery(query1);
        List<Artists> artistsList= new ArrayList<>();
        while(resultSet.next())
        {
            String ai= resultSet.getString(1);
            String an= resultSet.getString(2);
            Date dob= resultSet.getDate(3);
            String o= resultSet.getString(4);
            Artists a= new Artists(an,ai,dob,o);
            artistsList.add(a);
        }
        resultSet.close();
        statement.close();
        return artistsList;
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

        ArtistsDao artistsDao= new ArtistsDao();
        String filename= "Resources/Artist.csv";
        try {
            artistsDao.insertIntoArtists(filename);
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
