package StructuralEntity;

import java.util.ArrayList;
import java.util.List;

public class Playlist
{
    private String name;
    private List<String> audioList;

    public Playlist(String name) {
        this.name = name;
        audioList= new ArrayList<>();
    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public List<String> getAudioList() {
        return audioList;
    }

    public void setAudioList(List<String> audioList) {
        this.audioList = audioList;
    }
}
