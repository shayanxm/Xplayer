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
    public static int currentSongAlbumPointer = 0;
    private static CustomPlayer instance;
    ArrayList<Song> mAllSongs;
    private Context mContext;
    MediaPlayer mediaPlayer = new MediaPlayer();
    public static List<Song> playingList = SongLab.getInstance().getAllSongs();
    public static List<Song> backUpLis;
    public Song currentSong;
//only when you should reset

    public void setAlbumList(List<Song> albumSongs) {
        playingList = albumSongs;

        backUpLis = playingList;
    }

    public void resetListBackToSongs() {
        playingList = SongLab.getInstance().getAllSongs();

    }

    public static void setCurrentSongPointer(int currentSongPointer) {
        CustomPlayer.currentSongPointer = currentSongPointer;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
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
    }

    public int getSongDuration() {

        int time = mediaPlayer.getDuration();
        return time;
    }

    public void start(Song song) {
        mediaPlayer.seekTo(0);

        if (!mediaPlayer.isPlaying()) {
            currentSong = song;
            if (song.getZeroSongOneAlbum() == 0) {
                currentSongPointer = song.getmNumbpointer();
            } else if (song.getZeroSongOneAlbum() == 1) {
                currentSongPointer = song.getInAlbumNumbPointer();
            }
            String songUri = playingList.get(currentSongPointer).getmSongPath();
            mediaPlayer = MediaPlayer.create(mContext, Uri.parse(songUri));
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

//                   Song song = nextSong();
//                    MainActivity mainActivity= new MainActivity();
//                    mainActivity.setViewUp(song,false);
                }
            });

            mediaPlayer.start();
        } else {

        }

    }

    public void play() {
        mediaPlayer.seekTo(0);
        if (currentSong.getZeroSongOneAlbum() == 0) {
            currentSongPointer = currentSong.getmNumbpointer();
        } else if (currentSong.getZeroSongOneAlbum() == 1) {
            currentSongPointer = currentSong.getInAlbumNumbPointer();
        }

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
        if (currentSong.getZeroSongOneAlbum() == 0) {
            playingList = SongLab.getInstance().getAllSongs();
        } else if (currentSong.getZeroSongOneAlbum() == 1) {
            playingList = backUpLis;
        }

    }

    public Song nextSong() {
        if (currentSong.getZeroSongOneAlbum() == 0) {
//        currentSongPointer=song.getmNumbpointer();currentSongPointer++;
//        play();
            if (currentSong.getmNumbpointer() + 1 <= playingList.size()) {
                currentSong.setmNumbpointer(currentSong.getmNumbpointer() + 1);
            } else {
                currentSong.setmNumbpointer(playingList.size());
            }
        } else if (currentSong.getZeroSongOneAlbum() == 1) {
            if (currentSong.getInAlbumNumbPointer() + 1 <= playingList.size()) {
                currentSong.setInAlbumNumbPointer(currentSong.getInAlbumNumbPointer() + 1);
            } else {
                currentSong.setInAlbumNumbPointer(playingList.size());
            }
        }
        play();
        if (currentSong.getZeroSongOneAlbum() == 0) {
            currentSongPointer = currentSong.getmNumbpointer();
        } else if (currentSong.getZeroSongOneAlbum() == 1) {
            currentSongPointer = currentSong.getInAlbumNumbPointer();
        }
        return playingList.get(currentSongPointer);
    }


    public Song periviousSong() {
//        currentSongPointer=song.getmNumbpointer();currentSongPointer++;
//        play();
        if (currentSong.getZeroSongOneAlbum() == 0) {
//        currentSongPointer=song.getmNumbpointer();currentSongPointer++;
//        play();
            if (currentSong.getmNumbpointer() - 1 <= 0) {
                currentSong.setmNumbpointer(currentSong.getmNumbpointer() - 1);
            } else {
                currentSong.setmNumbpointer(0);
            }
        } else if (currentSong.getZeroSongOneAlbum() == 1) {
            if (currentSong.getInAlbumNumbPointer() - 1 <= playingList.size()) {
                currentSong.setInAlbumNumbPointer(currentSong.getInAlbumNumbPointer() - 1);
            } else {
                currentSong.setInAlbumNumbPointer(0);
            }
        }
        play();
        if (currentSong.getZeroSongOneAlbum() == 0) {
            currentSongPointer = currentSong.getmNumbpointer();
        } else if (currentSong.getZeroSongOneAlbum() == 1) {
            currentSongPointer = currentSong.getInAlbumNumbPointer();
        }
        return playingList.get(currentSongPointer);
    }


    public void repeatSong() {
        Song song = playingList.get(currentSongPointer);
        playingList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            playingList.set(i, song);
        }
    }

    public void repeatSongb(boolean state) {
        mediaPlayer.setLooping(state);
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
