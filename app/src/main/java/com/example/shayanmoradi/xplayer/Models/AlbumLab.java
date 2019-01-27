package com.example.shayanmoradi.xplayer.Models;

import java.util.ArrayList;
import java.util.List;

public class AlbumLab {
    private static AlbumLab instance;
    ArrayList<Album> mAllAlbums;

    private AlbumLab() {
        mAllAlbums = new ArrayList<>();
    }

    private void setAlbum() {
        //set songs here
    }

    public static AlbumLab getInstance() {
        if (instance == null)
            instance = new AlbumLab();
        return instance;
    }

    public List<Album> getAllAllbums() {
        List<Album> albumList = mAllAlbums;
        return albumList;
    }

    public Album getAlbum(String AlbumName) {
        //search for song
        List<Album> albnums = getAllAllbums();
        for (int i = 0; i < albnums.size(); i++) {
            if (albnums.get(i).getmAlbumId().equals(AlbumName))
                return albnums.get(i);
        }
        return null;
    }

    public List<Song> getSongsInAlbum(String AlbumName) {
        Album album = getAlbum(AlbumName);
        List<Song> song = SongLab.getInstance().getAllSongs();
        List<Song> resulat = new ArrayList<>();

        String albumId = album.getmAlbumId();
        for (int i = 0; i < song.size(); i++) {

            String songAlubumId = song.get(i).getmAlbumId();
            if (albumId.equals(songAlubumId))
                resulat.add(song.get(i));
        }
        return null;
    }


}
