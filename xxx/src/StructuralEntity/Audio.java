package StructuralEntity;

import java.sql.Date;
import java.sql.Time;

public class Audio
{
    private String audioId;
    private String name;
    private Artists artist;
    private String genre;
    private String albumName;
    private Date releaseDate;
    private String url;
    private Time duration;
    private AudioType audioType;

    public Audio(String audioId, String name, Artists artist, String genre, String albumName, Date releaseDate, String url, Time duration, AudioType audioType) {
        this.audioId = audioId;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.albumName = albumName;
        this.releaseDate = releaseDate;
        this.url = url;
        this.duration = duration;
        this.audioType= audioType;
    }

    public String getAudioId() {
        return audioId;
    }

//    public void setAudioId(String audioId) {
//        this.audioId = audioId;
//    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public Artists getArtist() {
        return artist;
    }

//    public void setArtist(String artist) {
//        this.artist = artist;
//    }

    public String getGenre() {
        return genre;
    }

//    public void setGenre(String genre) {
//        this.genre = genre;
//    }

    public String getAlbumName() {
        return albumName;
    }

//    public void setAlbumName(String albumName) {
//        this.albumName = albumName;
//    }

    public Date getReleaseDate() {
        return releaseDate;
    }

//    public void setReleaseDate(Date releaseDate) {
//        this.releaseDate = releaseDate;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Time getDuration() {
        return duration;
    }

//    public void setDuration(Time duration) {
//        this.duration = duration;
//    }

    public AudioType getAudioType() {
        return audioType;
    }

    @Override
    public String toString() {
        return String.format("%-20s",audioId)+" "+String.format("%-40s",name)+" "+
                String.format("%-30s",artist.getName())+" "+String.format("%-10s",genre)+" "+
                String.format("%-30s",albumName)+" "+String.format("%-12s",releaseDate.toString())+" "+
                String.format("%-10s",duration.toString());
    }
}
