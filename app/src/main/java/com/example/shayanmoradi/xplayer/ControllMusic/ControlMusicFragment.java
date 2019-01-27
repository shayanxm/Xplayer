package com.example.shayanmoradi.xplayer.ControllMusic;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shayanmoradi.xplayer.R;

import java.util.UUID;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ControlMusicFragment extends Fragment {
    private static final String ARG_CURRENT_MUSIC_POINTER = " com.example.shayanmoradi.xplayer.ControllMusic.current.music.pointer";

    private TextView textView;

    public static ControlMusicFragment newInstance(UUID songId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENT_MUSIC_POINTER, songId);

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
        return view;
    }

}
