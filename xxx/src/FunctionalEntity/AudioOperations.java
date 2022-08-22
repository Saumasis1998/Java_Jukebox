package FunctionalEntity;

import StructuralEntity.Audio;
import StructuralEntity.AudioType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AudioOperations
{
    public String generateAudioId(String name, String artistName, Date releaseDate, AudioType audioType)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(releaseDate);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        String audioId= name.substring(0,3)+artistName.substring(0,3)+day+month+year;
        if(audioType== AudioType.SONG)
            audioId= "S"+audioId;
        else
            audioId= "P"+audioId;
        return audioId;

    }
    public List<Audio> searchByName(String name, List<Audio> audioList)
    {
        List<Audio> result;
        result= audioList.stream().filter((o)-> o.getName().equalsIgnoreCase(name)).sorted(((o1, o2) -> o1.getName().compareTo(o2.getName()))).collect(Collectors.toList());
        return result;
    }
    public List<Audio> searchByGenre(String genre, List<Audio> audioList)
    {
        List<Audio> result;
        result= audioList.stream().filter((o)-> o.getGenre().equalsIgnoreCase(genre)).sorted(((o1, o2) -> o1.getName().compareTo(o2.getName()))).collect(Collectors.toList());
        return result;
    }
    public List<Audio> searchByArtistName(String artistName, List<Audio> audioList)
    {
        List<Audio> result;
        result= audioList.stream().filter((o)-> o.getArtist().getName().equalsIgnoreCase(artistName)).sorted(((o1, o2) -> o1.getName().compareTo(o2.getName()))).collect(Collectors.toList());
        return result;
    }
    public List<Audio> searchByAlbumName(String album, List<Audio> audioList)
    {
        List<Audio> result;
        result= audioList.stream().filter((o)-> o.getAlbumName().equalsIgnoreCase(album)).sorted(((o1, o2) -> o1.getName().compareTo(o2.getName()))).collect(Collectors.toList());
        return result;
    }
    public Date releaseDate(String name, String artistName, List<Audio> audioList)
    {
        Optional<Audio> result;
        result= audioList.stream().filter((o)-> o.getName().equalsIgnoreCase(name) && o.getArtist().getName().equalsIgnoreCase(artistName)).findFirst();
        return result.get().getReleaseDate();
    }
    public boolean audioIsPresent(String audioId, List<Audio> audioList)
    {
        return audioList.stream().anyMatch(o->o.getAudioId().equals(audioId));
    }
    public boolean audioIsPresent(String audioName,String artistName, List<Audio> audioList)
    {
        return audioList.stream().anyMatch(o->o.getName().equalsIgnoreCase(audioName) && o.getArtist().getName().equalsIgnoreCase(artistName));
    }
    public Audio getAudio(String audioId,List<Audio> audioList)
    {
        Optional<Audio> op;
        op= audioList.stream().filter(o->o.getAudioId().equals(audioId)).findFirst();
        return op.get();
    }
    public void displayAudios(List<Audio> audioList)
    {
        System.out.println(String.format("%-20s","AudioId")+" "+String.format("%-40s","Audio Name")+" "+
                String.format("%-30s","Artist Name")+" "+String.format("%-10s","Genre")+" "+
                String.format("%-30s","Album Name")+" "+String.format("%-12s","Release Date")+" "+
                String.format("%-10s","Duration")+"\n");
        audioList.stream().forEach(o-> System.out.println(o));
    }
}
