package FunctionalEntity;

import StructuralEntity.Artists;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class ArtistOperations
{
    public Artists createArtist(String name, String origin, Date dob)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dob);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        String artistId= name.substring(0,3)+origin.substring(0,3)+day+month+year;
        Artists artists= new Artists(name,artistId,dob,origin);
        return artists;
    }
    public boolean artistIsPresent(String artistId, List<Artists> artists)
    {
        return artists.stream().anyMatch(a-> a.getArtistId().equals(artistId));
    }
    public boolean artistIsPresent(String name, String origin, Date dob, List<Artists> artists)
    {
        return artists.stream().anyMatch(a-> a.getName().equalsIgnoreCase(name) && a.getOrigin().equalsIgnoreCase(origin) && a.getDob().equals(dob));
    }
    public String getArtistId(String name, String origin, Date dob, List<Artists> artists)
    {
        Optional<Artists> o;
        o= artists.stream().filter(a-> a.getName().equalsIgnoreCase(name) && a.getOrigin().equalsIgnoreCase(origin) && a.getDob().equals(dob)).findFirst();
        if(o.get()!=null)
            return o.get().getArtistId();
        else
            return null;
    }
    public Artists getArtist(String artistId, List<Artists> artists)
    {
        Optional<Artists> o;
        o= artists.stream().filter(a-> a.getArtistId().equals(artistId)).findFirst();
        return o.get();
    }
}
