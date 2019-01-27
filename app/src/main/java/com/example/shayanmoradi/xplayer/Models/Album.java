package com.example.shayanmoradi.xplayer.Models;

import java.util.List;

public class Album {
    private String mAlbumId;
    private String mAlbumName;
    private String mAlbumArtist;
    private String mAlbumArtWorkPath;
    private List<Song> mSongList;

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
