package com.example.shayanmoradi.xplayer.Models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class AlbumLab {
    private static AlbumLab instance;
    List<Album> mAllAlbums;
    List<String> uniqueAlbumIds;
    private Context mContext;


    private AlbumLab(Context context) {
        mAllAlbums = new ArrayList<>();
        this.mContext = context;

    }

    public ArrayList<Album> getListOfAlbums() {

        String where = null;

        final Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        final String _id = MediaStore.Audio.Albums._ID;
        final String album_name = MediaStore.Audio.Albums.ALBUM;
        final String artist = MediaStore.Audio.Albums.ARTIST;
        final String albumart = MediaStore.Audio.Albums.ALBUM_ART;
        final String tracks = MediaStore.Audio.Albums.NUMBER_OF_SONGS;
        int i = 0;
        final String[] columns = {_id, album_name, artist, albumart, tracks};
        Cursor cursor = mContext.getContentResolver().query(uri, columns, where,
                null, null);

        ArrayList<Album> list = new ArrayList<Album>();

        // add playlsit to list

        if (cursor.moveToFirst()) {

            do {

                Album albumData = new Album(cursor.getString(cursor.getColumnIndex(_id)));


                albumData.setmAlbumId(cursor.getString(cursor
                        .getColumnIndex(_id)));
                albumData.setmAlbumName(cursor.getString(cursor
                        .getColumnIndex(album_name)));

                albumData.setmAlbumArtist(cursor.getString(cursor
                        .getColumnIndex(artist)));

                albumData.setmAlbumArtWorkPath(cursor.getString(cursor
                        .getColumnIndex(albumart)));
                albumData.setmNumbpointerAlbum(i);
                i++;

//                albumData.setTracks(cursor.getString(cursor
//                        .getColumnIndex(tracks)));
//
                list.add(albumData);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return list;
    }

    private void setAlbum() {
        //set songs here
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        ContentResolver cr = mContext.getContentResolver();
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count = 0;
        List<String> names = new ArrayList<>();
        List<String> namesz = new ArrayList<>();
        List<String> albumId = new ArrayList<>();
        List<Album> allSongs = new ArrayList<>();
        List<Album> allAlums = new ArrayList<>();
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
                    /////
                    int albumIdsecond = cur.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID);
                    int artistName = cur.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
                    int albumNAme = cur.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
                    String albumIdf = cur.getString(albumIdsecond);
                    String artistamef = cur.getString(artistName);
                    String albbumNamef = cur.getString(albumNAme);

                    Album album1 = new Album(albumIdf);
                    album1.setmAlbumName(artistamef);
                    album1.setmAlbumArtist(albbumNamef);
                    album1.setmAlbumArtWorkPath(albumIdf);
                    mAllAlbums.add(album1);
                    i++;
                    ///
                    //   int albumIdH = cur.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID);
                    //   int albumIdH = cur.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST_ID);
//                    int artistColumn = cur.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
//                    int artistId = cur.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST_ID);
//                    int artistColumns = cur.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
//                    int column_index = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
//                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
//                    thisTitle = cur.getString(titleColumn);
//                    String albumIdSeted = cur.getString(albumIdH);
//                    thisArtist = cur.getString(artistColumn);
//                    album = cur.getString(artistColumns);
//                    String artistIdSeted = cur.getString(artistId);
//                    namesz.add(album);
//                    //     albumId.add(test);
//                    // Add code to get more column here
//                    names.add(data);
//                    // Save to your list here
//
//
//                    /////////////
//                    Song song = new Song(data);
//
//                    song.setmAlbumName(album);
//                    song.setmAlbumId(albumIdSeted);
//
//                    song.setmArtistId(artistIdSeted);
//                    song.setmSongName(thisTitle);
//                    song.setmArtistName(thisArtist);
//                    song.setmAlbumArtWorkPath(albumIdSeted);
//                    song.setmSongArtWorkPath(data);
//                    song.setmNumbpointer(i);
////                    song.setmAlbumArtWorkPath(getAlbumart(mContext, Long.valueOf(albumIdSeted)));
////                    song.setmSongArtWorkPath(getsongArtWork(data));
////
////
//
//                    allSongs.add(song);
//                    i++;
                }

            }
        }
        mAllAlbums = allAlums;
    }

    public static AlbumLab getInstance(Context context) {
        if (instance == null)
            instance = new AlbumLab(context);
        return instance;
    }

    public List<Album> getAllAllbums() {
        setAlbum();
        List<Album> albumList = mAllAlbums;
        return albumList;
    }

    //    public Album getAlbum(String AlbumName) {
//        //search for song
//        List<Album> albnums = getAllAllbums();
//        for (int i = 0; i < albnums.size(); i++) {
//            if (albnums.get(i).getmAlbumId().equals(AlbumName))
//                return albnums.get(i);
//        }
//        return null;
//    }
//    public Album getAlbum(UUID albumId) {
//        List<Album> albnums = getListOfAlbums();
//        for (int i = 0; i < albnums.size(); i++) {
//            if (albnums.get(i).getmAlbumIdGenarated().equals(albumId))
//
//                return albnums.get(i);
//        }
//return null;
//    }
    //    }
    public Album getAlbum(String albumName) {
        List<Album> albnums = getListOfAlbums();
        for (int i = 0; i < albnums.size(); i++) {
            if (albnums.get(i).getmAlbumName().equals(albumName))

                return albnums.get(i);
        }
        return null;
    }


    public List<Song> getSongsInAlbum(String AlbumName) {

        List<Song> song = SongLab.getInstance().getAllSongs();
        List<Song> resulat = new ArrayList<>();

//        String albumId = album.getmAlbumId();
        int numb = 0;
        for (int i = 0; i < song.size(); i++) {

            String songAlubumName = song.get(i).getmAlbumName();
            if (AlbumName.equals(songAlubumName)) {
                song.get(i).setInAlbumNumbPointer(numb);
                resulat.add(song.get(i));
                numb++;
            }
        }
        return resulat;
    }
//
//    public List<Song> getSongsInAlbum(Album album) {
//
//        List<Song> song = SongLab.getInstance().getAllSongs();
//        List<Song> resulat = new ArrayList<>();
//
//        String albumId = album.getmAlbumId();
//        for (int i = 0; i < song.size(); i++) {
//
//            String songAlubumId = song.get(i).getmAlbumId();
//            if (albumId.equals(songAlubumId))
//                resulat.add(song.get(i));
//        }
//        return null;
//    }

}
