package com.example.shayanmoradi.xplayer.Models;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;

public class SongLab {
    private static SongLab instance;
    List<Song> mAllSongs;
    private Context mContext;

    private SongLab(Context context) {
        mAllSongs = new ArrayList<>();
        this.mContext = context;
    }


    public static SongLab getInstance(Context context) {
        if (instance == null)
            instance = new SongLab(context);
        return instance;
    }

    //when get some thing dont want context
    private SongLab() {
        mAllSongs = new ArrayList<>();


    }

    public static SongLab getInstance() {
        if (instance == null)
            instance = new SongLab();
        return instance;
    }

    //when get some thing dont want context
    public List<Song> getAllSongs() {
        setAllSongsList();
        List<Song> songList = mAllSongs;
        return songList;
    }

    private void setAllSongsList() {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        ContentResolver cr = mContext.getContentResolver();
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count = 0;
        List<String> names = new ArrayList<>();
        List<String> namesz = new ArrayList<>();
        List<String> albumId = new ArrayList<>();
        List<Song> allSongs = new ArrayList<>();
        String thisArtist = "";
        String thisTitle = "";
        String album = "";
        String albumTestId;
        int i = 0;
        if (cur != null) {
            count = cur.getCount();

            if (count > 0) {
                while (cur.moveToNext()) {

                    int titleColumn = cur.getColumnIndex(MediaStore.MediaColumns.TITLE);
                    int idColumn = cur.getColumnIndex(BaseColumns._ID);
                    int albumIdH = cur.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID);
                    //   int albumIdH = cur.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID);
                    //   int albumIdH = cur.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST_ID);
                    int artistColumn = cur.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
                    int artistId = cur.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST_ID);
                    int artistColumns = cur.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
                    int column_index = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    thisTitle = cur.getString(titleColumn);
                    String albumIdSeted = cur.getString(albumIdH);
                    thisArtist = cur.getString(artistColumn);
                    album = cur.getString(artistColumns);
                    String artistIdSeted = cur.getString(artistId);
                    namesz.add(album);
                    //     albumId.add(test);
                    // Add code to get more column here
                    names.add(data);
                    // Save to your list here


                    /////////////
                    Song song = new Song(data);
                    song.setmAlbumName(album);
                    song.setmAlbumId(albumIdSeted);

                    song.setmArtistId(artistIdSeted);
                    song.setmSongName(thisTitle);
                    song.setmArtistName(thisArtist);
                    song.setmAlbumArtWorkPath(albumIdSeted);
                    song.setmSongArtWorkPath(data);
                    song.setmNumbpointer(i);
//                    song.setmAlbumArtWorkPath(getAlbumart(mContext, Long.valueOf(albumIdSeted)));
//                    song.setmSongArtWorkPath(getsongArtWork(data));
//
//

                    allSongs.add(song);
                    i++;
                }

            }
        }
        mAllSongs = allSongs;
    }

    private Bitmap getsongArtWork(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        byte[] data = mmr.getEmbeddedPicture();
        if (data != null) return BitmapFactory.decodeByteArray(data, 0, data.length);
        return null;
    }

    public Bitmap getAlbumart(Context context, Long album_id) {
        Bitmap albumArtBitMap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {

            final Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");

            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);

            ParcelFileDescriptor pfd = context.getContentResolver()
                    .openFileDescriptor(uri, "r");

            if (pfd != null) {
                FileDescriptor fd = pfd.getFileDescriptor();
                albumArtBitMap = BitmapFactory.decodeFileDescriptor(fd, null,
                        options);
                pfd = null;
                fd = null;
            }
        } catch (Error ee) {
        } catch (Exception e) {
        }

        if (null != albumArtBitMap) {
            return albumArtBitMap;
        }
        return null;
    }

    public Song getSong(String mSongPath) {
        //search for song
        List<Song> songs = getAllSongs();
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getmSongPath().equals(mSongPath))
                return songs.get(i);
        }
        return null;
    }

    public int getNurmberPointer(Song song) {
        List<Song> songs = getAllSongs();
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getmSongPath().equals(song.getmSongPath()))
                return i;
        }
        return -1;
    }
}
