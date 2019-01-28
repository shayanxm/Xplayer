package com.example.shayanmoradi.xplayer.ControllMusic;


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

import com.example.shayanmoradi.xplayer.Models.Album;
import com.example.shayanmoradi.xplayer.Models.AlbumLab;
import com.example.shayanmoradi.xplayer.Models.CustomPlayer;
import com.example.shayanmoradi.xplayer.Models.Song;
import com.example.shayanmoradi.xplayer.Models.SongLab;
import com.example.shayanmoradi.xplayer.R;

import java.io.FileDescriptor;
import java.util.List;
import java.util.UUID;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ControlMusicFragment extends DialogFragment {
    private RecyclerView recyclerView;
 MyRecyclerViewAdapter adapter;
    private static final String ARG_CURRENT_MUSIC_POINTER = " com.example.shayanmoradi.xplayer.ControllMusic.current.music.pointer";

    private TextView textView;

    public static ControlMusicFragment newInstance(UUID albumId,boolean trueForAlbum) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENT_MUSIC_POINTER, albumId);
        args.putSerializable("state", true);
        ControlMusicFragment fragment = new ControlMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ControlMusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control_music, container, false);
        recyclerView = view.findViewById(R.id.rec_id);
        UUID albumCratedId = (UUID) getArguments().getSerializable(ARG_CURRENT_MUSIC_POINTER);
     Album album= AlbumLab.getInstance(getActivity()).getAlbum(albumCratedId);
        //List<Song> song = AlbumLab.getInstance(getActivity()).getSongsInAlbum(album);
      Toast.makeText(getActivity(),album.getmAlbumName(),Toast.LENGTH_SHORT).show();
        List<Song> song = SongLab.getInstance(getActivity()).getAllSongs();


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyRecyclerViewAdapter(getActivity(), song);

        recyclerView.setAdapter(adapter);
        return view;

    }

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private List<Song> mData;
        private LayoutInflater mInflater;


        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, List<Song> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.material_card_view, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
            Song song = mData.get(position);
            holder.myTextView.setText(song.getmSongName());
            holder.artisName.setText(song.getmArtistName());
            String songPath = song.getmSongPath();
            Bitmap songArtWork = getsongArtWork(songPath);
            holder.songArtWork.setImageBitmap(songArtWork);
            holder.song = song;
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

            Song song;

            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.song_titile);
                artisName = itemView.findViewById(R.id.song_artist);
                songArtWork = itemView.findViewById(R.id.song_artWork);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        mCallBacks.setSong(song);
//                        mCallBacks.setTitle(song.getmSongName());
//                        mCallBacks.trueStart(false);
//                        mCallBacks.setImage(song);
                        CustomPlayer.getInstance(getActivity()).start(song);
//
//                        //   Intent intent = ControllMuaicActivity.newIntent(getActivity(),song.getSongId());
//                        Intent intent1 = new Intent(getActivity(), MainActivity.class);
//                        intent1.putExtra("songId", song.getSongId());
//                        // startActivity(intent);

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





