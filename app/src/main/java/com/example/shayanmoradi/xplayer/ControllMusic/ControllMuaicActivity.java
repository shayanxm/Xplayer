package com.example.shayanmoradi.xplayer.ControllMusic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shayanmoradi.xplayer.R;

import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ControllMuaicActivity extends AppCompatActivity {
    public static Intent newIntent(Context context, UUID songId) {
        Intent intent = new Intent(context, ControllMuaicActivity.class);
        intent.putExtra("id",songId);

        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controll_muaic);
       UUID baseId = (UUID) getIntent().getSerializableExtra("id");

//showDialog();

        Fragment fragment =  ControlMusicFragment.newInstance(baseId);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.control_continer, fragment)
                .commit();

    }
}