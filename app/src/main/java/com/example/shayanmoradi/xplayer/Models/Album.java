package com.example.shayanmoradi.xplayer.Models;

import java.util.List;
import java.util.UUID;

public class Album {
    private String mAlbumId;
    private String mAlbumName;
    private String mAlbumArtist;
    private String mAlbumArtWorkPath;
    private List<Song> mSongList;
    private UUID mAlbumIdGenarated= UUID.randomUUID();
    private int mNumbpointerAlbum;



    public int getmNumbpointerAlbum() {
        return mNumbpointerAlbum;
    }

    public void setmNumbpointerAlbum(int mNumbpointer) {
        this.mNumbpointerAlbum = mNumbpointer;
    }


    public UUID getmAlbumIdGenarated() {
        return mAlbumIdGenarated;
    }

    public void setmAlbumIdGenarated(UUID mAlbumIdGenarated) {
        this.mAlbumIdGenarated = mAlbumIdGenarated;
    }

    public void setmAlbumId(String mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public String getmAlbumArtWorkPath() {
        return mAlbumArtWorkPath;
    }

    public void setmAlbumArtWorkPath(String mAlbumArtWorkPath) {
        this.mAlbumArtWorkPath = mAlbumArtWorkPath;
    }

    public String getmAlbumName() {
        return mAlbumName;
    }

    public String getmAlbumArtist() {
        return mAlbumArtist;
    }

    public void setmAlbumName(String mAlbumName) {
        this.mAlbumName = mAlbumName;
    }

    public void setmAlbumArtist(String mAlbumArtist) {
        this.mAlbumArtist = mAlbumArtist;
    }

    public Album(String mAlbumId) {
        this.mAlbumId = mAlbumName;
    }

    public String getmAlbumId() {
        return mAlbumId;
    }

    public List<Song> getmSongList() {
        return mSongList;
    }

    public void setmSongList(List<Song> mSongList) {
        this.mSongList = mSongList;
    }
}
