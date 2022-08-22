package AudioPlayer;

import DatabseHandlingEntity.*;
import FunctionalEntity.AudioOperations;
import StructuralEntity.Audio;
import StructuralEntity.Playlist;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Audioverse
{
    public static void main(String[] args)
    {
        try {
            Connector.setConnection();

            System.out.println("\n\n\n\n\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWelcome to Audioverse\n\n\n\n");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            UsersDao ud= new UsersDao();
            String username= ud.insertIntoUsers();
            if(username.equals(""))
            {
                System.exit(0);
                System.out.println("\n\n\n\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tGoodBye, See you soon...\n\n\n\n");
            }

            AudioPlayer ap= new AudioPlayer();

            BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
            Scanner ab= new Scanner(System.in);
            int choice;
            String audioName, genre,artistName,albumName, playlistName, audioId;
            Date releaseDate;
            boolean isPresent;
            int c;
            Playlist playlist;
            List<Audio> audios;

            ListOfPlaylistsDao listOfPlaylistsDao= new ListOfPlaylistsDao();
            listOfPlaylistsDao.createListOfPlaylists(username);

            PlaylistDao playlistDao= new PlaylistDao();

            AudioOperations audioOperations= new AudioOperations();

            do{
                System.out.println("\n\n\nEnter 1 to view songs \nEnter 2 to view podcasts\nEnter 3 to view your playlists\nEnter 4 to create playlist\nEnter 5 to add songs or podcasts to playlist\nEnter 6 to view playlist\nEnter 7 to exit Audioverse ");
                System.out.print("Your choice: ");
                choice= ab.nextInt();
                System.out.println("\n\n");

                if(choice==1)
                {
                    System.out.println(String.format("%-20s","SongId")+" "+String.format("%-40s","Song Name")+" "+
                            String.format("%-30s","Artist Name")+" "+String.format("%-10s","Genre")+" "+
                            String.format("%-30s","Album Name")+" "+String.format("%-12s","Release Date")+" "+
                            String.format("%-10s","Duration")+"\n");
                    SongsDao sd= new SongsDao();
                    List<Audio> songs= sd.getSongs();
                    songs.stream().forEach(o-> System.out.println(o));

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }



                    do{
                        System.out.print("\n\nEnter 1 to search by name\nEnter 2 to filter by genre\nEnter 3 to filter by artist name\nEnter 4 to filter by album name\nEnter 5 to know release date\nEnter 6 to return to main menu\nYour choice:");
                        c= ab.nextInt();
                        System.out.println("\n");
                        switch (c)
                        {
                            case 1:
//                                System.out.println("Case 1");
                                System.out.print("Enter song name: ");
                                audioName= br.readLine();
                                System.out.println("\n");
                                audios= audioOperations.searchByName(audioName,songs);
                                if(audios.size()!=0)
                                    audioOperations.displayAudios(audios);
                                else
                                    System.out.println("No such song present.");
                                break;
                            case 2:
                                System.out.print("Enter genre: ");
                                genre= br.readLine();
                                System.out.println("\n");
                                audios= audioOperations.searchByGenre(genre,songs);
                                if(audios.size()!=0)
                                    audioOperations.displayAudios(audios);
                                else
                                    System.out.println("No such genre present.");
                                break;
                            case 3:
                                System.out.print("Enter artist name: ");
                                artistName= br.readLine();
                                System.out.println("\n");
                                audios= audioOperations.searchByArtistName(artistName,songs);
                                if(audios.size()!=0)
                                    audioOperations.displayAudios(audios);
                                else
                                    System.out.println("No such artist present.");
                                break;
                            case 4:
                                System.out.print("Enter album name: ");
                                albumName= br.readLine();
                                System.out.println("\n");
                                audios= audioOperations.searchByAlbumName(albumName,songs);
                                if(audios.size()!=0)
                                    audioOperations.displayAudios(audios);
                                else
                                    System.out.println("No such album present.");
                                break;
                            case 5:
                                System.out.print("Enter song name: ");
                                audioName= br.readLine();
                                System.out.print("Enter artist name: ");
                                artistName= br.readLine();
                                System.out.println("\n");
                                isPresent= audioOperations.audioIsPresent(audioName,artistName,songs);
                                if (!isPresent)
                                {
                                    System.out.println("No such song present.");
                                    break;
                                }
                                releaseDate= audioOperations.releaseDate(audioName,artistName,songs);
                                System.out.println("Release Date of "+audioName+ " is "+releaseDate);

                                break;
                            case 6:
                                break;
                        }
                    }while (c!=6);

                String wantToPlay;
                System.out.print("\n\nDo you want to play songs? ");
                wantToPlay= ab.next();
                System.out.println("\n");
                if(wantToPlay.equalsIgnoreCase("yes"))
                    ap.APlayer(songs);

                }
                if(choice==2)
                {
                    System.out.println(String.format("%-20s","PodcastId")+" "+String.format("%-30s","Podcast Name")+" "+
                            String.format("%-30s","Artist Name")+" "+String.format("%-10s","Genre")+" "+
                            String.format("%-30s","Album Name")+" "+String.format("%-12s","Release Date")+" "+
                            String.format("%-10s","Duration")+"\n");
                    PodcastsDao pd= new PodcastsDao();
                    List<Audio> podcasts= pd.getPodcasts();
                    podcasts.stream().forEach(o-> System.out.println(o));

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    do{
                        System.out.print("\n\nEnter 1 to search by name\nEnter 2 to filter by genre\nEnter 3 to filter by artist name\nEnter 4 to filter by album name\nEnter 5 to know release date\nEnter 6 to return to main menu\nYour choice:");
                        c= ab.nextInt();
                        System.out.println("\n");
                        switch (c)
                        {
                            case 1:
//                                System.out.println("Case 1");
                                System.out.print("Enter podcast name: ");
                                audioName= br.readLine();
                                System.out.println("\n");
                                audios= audioOperations.searchByName(audioName,podcasts);
                                if(audios.size()!=0)
                                    audioOperations.displayAudios(audios);
                                else
                                    System.out.println("No such podcast present.");
                                break;
                            case 2:
                                System.out.print("Enter genre: ");
                                genre= br.readLine();
                                System.out.println("\n");
                                audios= audioOperations.searchByGenre(genre,podcasts);
                                if(audios.size()!=0)
                                    audioOperations.displayAudios(audios);
                                else
                                    System.out.println("No such genre present.");
                                break;
                            case 3:
                                System.out.print("Enter artist name: ");
                                artistName= br.readLine();
                                System.out.println("\n");
                                audios= audioOperations.searchByArtistName(artistName,podcasts);
                                if(audios.size()!=0)
                                    audioOperations.displayAudios(audios);
                                else
                                    System.out.println("No such artist present.");
                                break;
                            case 4:
                                System.out.print("Enter album name: ");
                                albumName= br.readLine();
                                System.out.println("\n");
                                audios= audioOperations.searchByAlbumName(albumName,podcasts);
                                if(audios.size()!=0)
                                    audioOperations.displayAudios(audios);
                                else
                                    System.out.println("No such album present.");
                                break;
                            case 5:
                                System.out.print("Enter podcast name: ");
                                audioName= br.readLine();
                                System.out.print("Enter artist name: ");
                                artistName= br.readLine();
                                System.out.println("\n");
                                isPresent= audioOperations.audioIsPresent(audioName,artistName,podcasts);
                                if (!isPresent)
                                {
                                    System.out.println("No such podcast present.");
                                    break;
                                }
                                releaseDate= audioOperations.releaseDate(audioName,artistName,podcasts);
                                System.out.println("Release Date of "+audioName+ " is "+releaseDate);

                                break;
                            case 6:
                                break;
                        }
                    }while (c!=6);

                    String wantToPlay;
                    System.out.print("\n\nDo you want to play podcasts? ");
                    wantToPlay= ab.next();
                    System.out.println("\n");
                    if(wantToPlay.equalsIgnoreCase("yes"))
                       ap.APlayer(podcasts);

                }
                if(choice==4)
                {
                    System.out.print("Enter playlist name: " );
                    playlistName= br.readLine();
                    System.out.println("\n");
                    playlist= playlistDao.createPlaylist(playlistName,username);
                    List<Playlist> playlists= new ArrayList<>();
                    playlists.add(playlist);
                    listOfPlaylistsDao.insertIntoListOfPlaylists(username,playlists);
                }
                if(choice==5)
                {
                    System.out.print("Enter audioId: ");
                    audioId= br.readLine();

                    SongsDao sd= new SongsDao();
                    List<Audio> songs= sd.getSongs();

                    PodcastsDao pd= new PodcastsDao();
                    List<Audio> podcasts= pd.getPodcasts();

                    if (audioOperations.audioIsPresent(audioId,songs) || audioOperations.audioIsPresent(audioId,podcasts))
                    {
                        System.out.print("Enter playlist Name: ");
                        playlistName= br.readLine();
                        System.out.println("\n");
                        List<String> al= new ArrayList<>();
                        al.add(audioId);
                        if (playlistDao.playlistIsPresent(username+playlistName,username))
                           playlistDao.insertIntoPlaylist(playlistName,username,al);
                    }
                    else
                        System.out.println("Invalid Audio Id");

                }
                if(choice ==6)
                {
                    System.out.print("Enter playlist Name: ");
                    playlistName= br.readLine();
                    System.out.println("\n");
                    playlist= playlistDao.getPlaylist(playlistName,username);

                    SongsDao sd= new SongsDao();
                    List<Audio> songs= sd.getSongs();

                    PodcastsDao pd= new PodcastsDao();
                    List<Audio> podcasts= pd.getPodcasts();

//                    playlist.getAudioList().stream().forEach(o-> System.out.println(o));
                    if (playlist!=null)
                    {
                        List<String> al= playlist.getAudioList();
                        audios= new ArrayList<>();
                        for (String a:al)
                        {
                            if(a.charAt(0)=='S')
                                audios.add(audioOperations.getAudio(a,songs));
                            else
                                audios.add(audioOperations.getAudio(a,podcasts));
                        }
                        audioOperations.displayAudios(audios);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        String wantToPlay;
                        System.out.println("\n");
                        System.out.print("Do you want to play playlist? ");
                        wantToPlay= ab.next();
                        System.out.println("\n");
                        if(wantToPlay.equalsIgnoreCase("yes"))
                            ap.APlayer(audios);
                    }

                }
                if(choice== 3)
                {
                    List<String> allplaylists= listOfPlaylistsDao.getPlaylists(username);
                    if (allplaylists.size()!=0)
                    {
                        System.out.println("\nYour Playlists \n");
                        allplaylists.stream().forEach(o-> System.out.println(o.substring(username.length())));

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                        System.out.println("\n\nYou haven't created any playlist yet. ");
                }
            }while (choice!=7);

            System.out.println("\n\n\n\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tGoodBye, See you soon...\n\n\n\n");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Connector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
