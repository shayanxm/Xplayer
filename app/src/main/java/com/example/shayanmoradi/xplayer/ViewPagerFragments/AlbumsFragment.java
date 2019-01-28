package com.example.shayanmoradi.xplayer.ViewPagerFragments;


import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shayanmoradi.xplayer.ControllMusic.ControlMusicFragment;
import com.example.shayanmoradi.xplayer.Models.Album;
import com.example.shayanmoradi.xplayer.Models.AlbumLab;
import com.example.shayanmoradi.xplayer.Models.CustomPlayer;
import com.example.shayanmoradi.xplayer.Models.Song;
import com.example.shayanmoradi.xplayer.R;

import java.io.FileDescriptor;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment {
    private RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;

    public static AlbumsFragment newInstance() {

        Bundle args = new Bundle();

        AlbumsFragment fragment = new AlbumsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AlbumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        recyclerView = view.findViewById(R.id.rec_id);
        //    List<Song>song= SongLab.getInstance(getActivity()).getAllSongs();
        List<Album> albums = AlbumLab.getInstance(getActivity()).getListOfAlbums();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        List<Song> songs = AlbumLab.getInstance(getActivity()).getSongsInAlbum(albums.get(3).getmAlbumName());

            Toast.makeText(getActivity(), songs.get(0).getmSongName() + "", Toast.LENGTH_SHORT).show();
//Toast.makeText(getActivity(),albums.size()+"",Toast.LENGTH_SHORT).show();

        // adapter = new AlbumsFragment.MyRecyclerViewAdapter(getActivity(), song);
        adapter = new AlbumsFragment.MyRecyclerViewAdapter(getActivity(), albums);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private List<Album> mData;
        private LayoutInflater mInflater;


        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, List<Album> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.material_card_view_albums, parent, false);

            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
            // Song song = mData.get(position);
            Album album = mData.get(position);
            holder.myTextView.setText(album.getmAlbumName());
            holder.artisName.setText(album.getmAlbumArtist());
            // String songPath = album.getmSongPath();

//            holder.myTextView.setText(song.getmSongName());
//            holder.artisName.setText(song.getmArtistName());
//            String songPath = song.getmSongPath();
//            Bitmap songArtWork= getsongArtWork(songPath);
//            holder.songArtWork.setImageBitmap(songArtWork);
//            holder.song= song;
            holder.album = album;
            CustomPlayer.getInstance(getActivity()).setCurrentSongPointer(position);
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView myTextView;
            TextView artisName;
            ImageView songArtWork;

            //  Song song;
            Album album;

            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.song_titile);
                artisName = itemView.findViewById(R.id.song_artist);
                songArtWork = itemView.findViewById(R.id.song_artWork);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        // CustomPlayer.getInstance(getActivity()).play(song.getmSongPath());
//
//                        Intent intent = ControllMuaicActivity.newIntent(getActivity(),album.getmAlbumIdGenarated(),true);
//
//                        startActivity(intent);
                        //Toast.makeText(getActivity(),album.getmAlbumName()+album.getmAlbumIdGenarated(),Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getFragmentManager();
                        ControlMusicFragment detailFragment = ControlMusicFragment.newInstance(album.getmAlbumIdGenarated(),true);
                        detailFragment.show(fragmentManager, "dialog");
//                        Fragment fragment = ControlMusicFragment.newInstance(album.getmAlbumIdGenarated(),true);
//                        getActivity().getSupportFragmentManager()
//                                .beginTransaction()
//
//                                .replace(R.id.continerss, fragment)
//                                .commit();

                    }
                });

            }

            @Override
            public void onClick(View v) {

            }


            // convenience method for getting data at click position

            // allows clicks events to be caught


            // parent activity will implement this method to respond to click events

        }
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
}





