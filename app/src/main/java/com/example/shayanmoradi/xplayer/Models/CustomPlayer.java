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
    MediaPlayer mediaPlayer = new MediaPlayer();
    public static List<Song> playingList = SongLab.getInstance().getAllSongs();
    public Song currentSong;

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

    public void changeDuration(int duration) {
        mediaPlayer.seekTo(duration);
    }

    public int getCurrentPos() {
        int pos = mediaPlayer.getCurrentPosition();
        return pos;
    }

    public int getSongDuration(Song song) {
        MediaPlayer mediaPlayers = new MediaPlayer();

        String songUri = song.getmSongPath();
        mediaPlayers = MediaPlayer.create(mContext, Uri.parse(songUri));
        int time = mediaPlayers.getDuration();
        return time;
    }    public int getSongDuration() {

        int time = mediaPlayer.getDuration();
        return time;
    }

    public void start(Song song) {

        if (!mediaPlayer.isPlaying()) {
            currentSong = song;

            currentSongPointer = song.getmNumbpointer();
            String songUri = playingList.get(currentSongPointer).getmSongPath();
            mediaPlayer = MediaPlayer.create(mContext, Uri.parse(songUri));
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {


                }
            });

            mediaPlayer.start();
        } else {

        }

    }

    public void play() {


        currentSongPointer = currentSong.getmNumbpointer();
        String songUri = playingList.get(currentSongPointer).getmSongPath();
        mediaPlayer = MediaPlayer.create(mContext, Uri.parse(songUri));
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {


            }
        });

        mediaPlayer.start();


    }

    public void resmue() {


        mediaPlayer.start();


    }

    public void pause() {

        mediaPlayer.pause();

    }

    public void shuffle() {
        Collections.shuffle(playingList);

    }

    public void unShuffle() {

        playingList = SongLab.getInstance().getAllSongs();

    }

    public Song nextSong() {
//        currentSongPointer=song.getmNumbpointer();currentSongPointer++;
//        play();
        currentSong.setmNumbpointer(currentSong.getmNumbpointer() + 1);
        play();
        currentSongPointer = currentSong.getmNumbpointer();
        return playingList.get(currentSongPointer);
    }

    public Song periviousSong() {
//        currentSongPointer=song.getmNumbpointer();currentSongPointer++;
//        play();
        currentSong.setmNumbpointer(currentSong.getmNumbpointer() - 1);
        play();
        currentSongPointer = currentSong.getmNumbpointer();
        return playingList.get(currentSongPointer);
    }

    public void repeatSong() {
        Song song = playingList.get(currentSongPointer);
        playingList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            playingList.set(i, song);
        }
    }

    public Song getCurrentSong() {
        return playingList.get(getCurrentSongPointer());
    }

    public static int getCurrentSongPointer() {
        return currentSongPointer;
    }

    public void unRepeat() {
        playingList = SongLab.getInstance().getAllSongs();
    }

}
