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

import com.example.shayanmoradi.xplayer.Models.CustomPlayer;
import com.example.shayanmoradi.xplayer.Models.Song;
import com.example.shayanmoradi.xplayer.Models.SongLab;
import com.example.shayanmoradi.xplayer.R;

import java.io.FileDescriptor;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongsFragment extends Fragment {
    public static Context mContext;
    private RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    private CallBacks mCallBacks;
    public static Context contextofMain;

    public interface CallBacks {
        public void setSong(Song song);

        public void setImage(Song song);

        void setTitle(String title);

        void trueStart(boolean state);
        void setViewOnrotate(Song song);
    }

    public static SongsFragment newInstance() {

        Bundle args = new Bundle();

        SongsFragment fragment = new SongsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SongsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        contextofMain=context;
        mCallBacks = (CallBacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        mContext = getActivity();
        recyclerView = view.findViewById(R.id.rec_id);
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
        public void onBindViewHolder(ViewHolder holder, int position) {
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
                        CustomPlayer.getInstance(getActivity()).resetListBackToSongs();
                        CustomPlayer.setCurrentSongPointer(0);
                        song.setZeroSongOneAlbum(0);
                        mCallBacks.setSong(song);
                        mCallBacks.setTitle(song.getmSongName());
                        mCallBacks.trueStart(false);
                        mCallBacks.setViewOnrotate(song);
//                        mCallBacks.setImage(song);

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



