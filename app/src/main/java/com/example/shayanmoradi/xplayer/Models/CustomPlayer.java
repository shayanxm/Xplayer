package com.example.shayanmoradi.xplayer.Models;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomPlayer {

    public static List<Song> shufflelist;
    public static int currentSongPointer = 0;
    private static CustomPlayer instance;
    ArrayList<Song> mAllSongs;
    private Context mContext;
    MediaPlayer mpintro=new MediaPlayer();
    public static List<Song> playingList = SongLab.getInstance().getAllSongs();

    public static void setCurrentSongPointer(int currentSongPointer) {
        CustomPlayer.currentSongPointer = currentSongPointer;
    }

    private CustomPlayer(Context context) {
        mAllSongs = new ArrayList<>();
        this.mContext = context;
    }

    private void setSong() {
        //set songs here
    }

    public static CustomPlayer getInstance(Context context) {
        if (instance == null)
            instance = new CustomPlayer(context);
        return instance;
    }

    public void play( Song song ) {
currentSongPointer=song.getmNumbpointer();
       String songUri = playingList.get(currentSongPointer).getmSongPath();
        mpintro = MediaPlayer.create(mContext, Uri.parse(songUri));
        mpintro.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {


            }
        });

        mpintro.start();

    }
    public void pause(){

        mpintro.stop();

    }
    public void shuffle() {
        Collections.shuffle(playingList);

    }

    public void unShuffle() {

        playingList = SongLab.getInstance().getAllSongs();
    }

    public void nextSong() {
//        currentSongPointer=song.getmNumbpointer();currentSongPointer++;
//        play();
    }

    public void pervioudSong() {
        currentSongPointer--;
    }

    public void repeatSong() {
        Song song = playingList.get(currentSongPointer);
        playingList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            playingList.set(i, song);
        }
    }

    public static int getCurrentSongPointer() {
        return currentSongPointer;
    }

    public void unRepeat() {
        playingList = SongLab.getInstance().getAllSongs();
    }

}
