package StructuralEntity;

import java.util.ArrayList;
import java.util.List;

public class ListOfPlaylist
{
    private String name;
    private List<Playlist> lop;

    public ListOfPlaylist(String name) {
        this.name = name;
        lop= new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Playlist> getLop() {
        return lop;
    }

    public void setLop(List<Playlist> lop) {
        this.lop = lop;
    }
}
