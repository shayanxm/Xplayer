package com.example.shayanmoradi.xplayer.Models;

import java.util.UUID;

public class Song {
    private String mSongPath;
    private String mSongArtWorkPath;
    private String mAlbumArtWorkPath;
    private String mSongName;
    private String mAlbumName;
    private String mArtistName;
    private String mAlbumId;
    private String mArtistId;
    private int mNumbpointer;
    private UUID songId= UUID.randomUUID();
    public String getmAlbumName() {
        return mAlbumName;
    }

    public int getmNumbpointer() {
        return mNumbpointer;
    }

    public void setmNumbpointer(int mNumbpointer) {
        this.mNumbpointer = mNumbpointer;
    }



    public UUID getSongId() {
        return songId;
    }
//                    song.setmAlbumArtWorkPath(getAlbumart(mContext, Long.valueOf(albumIdSeted)));
//                    song.setmSongArtWorkPath(getsongArtWork(data));



    public String getmSongName() {
        return mSongName;
    }

    public String getmArtistName() {
        return mArtistName;
    }

    public String getmAlbumId() {
        return mAlbumId;
    }

    public Song(String mSongPath) {
        this.mSongPath = mSongPath;

    }
    public String getmSongPath() {
        return mSongPath;
    }
    public void setmSongArtWorkPath(String  mSongArtWorkPath) {
        this.mSongArtWorkPath = mSongArtWorkPath;
    }

    public void setmAlbumArtWorkPath(String mAlbumArtWorkPath) {
        this.mAlbumArtWorkPath = mAlbumArtWorkPath;
    }

    public void setmSongName(String mSongName) {
        this.mSongName = mSongName;
    }

    public void setmAlbumName(String mAlbumName) {
        this.mAlbumName = mAlbumName;
    }

    public void setmArtistName(String mArtistName) {
        this.mArtistName = mArtistName;
    }

    public void setmAlbumId(String mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public void setmArtistId(String mArtistId) {
        this.mArtistId = mArtistId;
    }



}
