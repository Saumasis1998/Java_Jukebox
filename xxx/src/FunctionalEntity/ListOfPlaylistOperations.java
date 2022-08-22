package FunctionalEntity;

import StructuralEntity.ListOfPlaylist;
import StructuralEntity.Playlist;

import java.util.List;

public class ListOfPlaylistOperations
{
    public ListOfPlaylist createListOfPlaylists(String username)
    {
        ListOfPlaylist playlists= new ListOfPlaylist("PlaylistsOf".concat(username));
        return playlists;
    }
    public boolean playlistIsPresent(String playlistName, ListOfPlaylist playlists)
    {
        return playlists.getLop().stream().anyMatch(p->p.getName().equalsIgnoreCase(playlistName));
    }
    public ListOfPlaylist addPlaylist(Playlist p, ListOfPlaylist playlists)
    {
        List<Playlist> t= playlists.getLop();
        t.add(p);
        playlists.setLop(t);
        return playlists;
    }
    public ListOfPlaylist removePlaylist(Playlist p, ListOfPlaylist playlists)
    {
        List<Playlist> t= playlists.getLop();
        t.remove(p);
        playlists.setLop(t);
        return playlists;
    }
}
