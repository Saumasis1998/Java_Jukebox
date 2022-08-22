package StructuralEntity;

import java.sql.Date;

public class Artists {
    private String name;
    private String artistId;
    private Date dob;
    private String origin;

    public Artists(String name, String artistId, Date dob, String origin) {
        this.name = name;
        this.artistId = artistId;
        this.dob = dob;
        this.origin = origin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistId() {
        return artistId;
    }

//    public void setArtistId(String artistId) {
//        this.artistId = artistId;
//    }

    public Date getDob() {
        return dob;
    }

//    public void setDob(Date dob) {
//        this.dob = dob;
//    }

    public String getOrigin() {
        return origin;
    }

//    public void setOrigin(String origin) {
//        this.origin = origin;
//    }
}
