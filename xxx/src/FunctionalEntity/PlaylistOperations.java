package FunctionalEntity;

import StructuralEntity.ListOfPlaylist;
import StructuralEntity.Playlist;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaylistOperations
{
    public Playlist createPlayList(String playlistName)
    {
        Playlist playlist= new Playlist(playlistName);
        return playlist;
    }
    public Playlist add(String audioId, Playlist playlist) {
       List<String> temp= playlist.getAudioList();
       temp.add(audioId);
       playlist.setAudioList(temp);
       return playlist;
    }
    public boolean audioIsPresent(String audioId, Playlist playlist)
    {
        return playlist.getAudioList().stream().anyMatch(o->o.equalsIgnoreCase(audioId));
    }
    public Playlist remove(String audioId, Playlist playlist) {
        List<String> temp= playlist.getAudioList();
        temp.remove(audioId);
        playlist.setAudioList(temp);
        return playlist;
    }
}
