package com.example.shayanmoradi.xplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shayanmoradi.xplayer.Models.CustomPlayer;
import com.example.shayanmoradi.xplayer.Models.Song;
import com.example.shayanmoradi.xplayer.ViewPagerFragments.AlbumsFragment;
import com.example.shayanmoradi.xplayer.ViewPagerFragments.ArtistsFragment;
import com.example.shayanmoradi.xplayer.ViewPagerFragments.SongsFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements SongsFragment.CallBacks {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ViewPagerAdapter adapter;
    private ImageButton goToNextSong;
    private ImageButton pauseUnPauseSong;
    private ImageButton gotToPerivousSong;
    private MaterialButton playBtn;
    private boolean trueStart = false;
    private Song currentSong;
    private ImageView songArtWork;
    public TextView songTitile;
    public TextView artistName;
    public ImageView bigArtWrk;
    private SeekBar duration;
    private TextView espledTime;
    private TextView remaingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        goToNextSong = findViewById(R.id.go_to_next_song);
        songArtWork = findViewById(R.id.small_art_work_sheet);
        bigArtWrk = findViewById(R.id.big_art_work_sheet);
        gotToPerivousSong = findViewById(R.id.go_to_perivous_song);
        songTitile = findViewById(R.id.song_title_sheet);
        artistName = findViewById(R.id.artist_name_sheet);
        duration = findViewById(R.id.time_shower_seek_bar);
        espledTime = findViewById(R.id.espled_time);
        remaingTime = findViewById(R.id.remaing_time);

        duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    CustomPlayer.getInstance(MainActivity.this).changeDuration(progress);
                duration.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        pauseUnPauseSong = findViewById(R.id.pause_unpause);

        tabLayout.setupWithViewPager(viewPager);


        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        pauseUnPauseSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trueStart == true) {
                    // CustomPlayer.getInstance(MainActivity.this).start(currentSong);
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
                    CustomPlayer.getInstance(MainActivity.this).resmue();
                    pauseUnPauseSong.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.icons8_play_52));

                } else {
                    CustomPlayer.getInstance(MainActivity.this).pause();
                }
                trueStart = !trueStart;
            }
        });
        goToNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPlayer.getInstance(MainActivity.this).pause();
                currentSong = CustomPlayer.getInstance(MainActivity.this).nextSong();

                setViewUp(currentSong);
            }
        });
        gotToPerivousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPlayer.getInstance(MainActivity.this).pause();
                currentSong = CustomPlayer.getInstance(MainActivity.this).periviousSong();

                setViewUp(currentSong);
            }
        });

        SongsFragment topAppBarFragment = SongsFragment.newInstance();
        adapter.addFrag(topAppBarFragment, "         Songs          ");

        AlbumsFragment tabLayoutFragment = AlbumsFragment.newInstance();
        adapter.addFrag(tabLayoutFragment, "      Albums     ");

        ArtistsFragment bottomNavigationFragment = ArtistsFragment.newInstance();
        adapter.addFrag(bottomNavigationFragment, "       Artist       ");
        viewPager.setOffscreenPageLimit(1);
//        TextFieldsFragment textFieldsFragment = TextFieldsFragment.newInstance();
//        adapter.addFrag(textFieldsFragment,"Text Fields");

        viewPager.setAdapter(adapter);
        UUID uuid = UUID.randomUUID();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Message message = new Message();
                        message.what = CustomPlayer.getInstance(MainActivity.this).getCurrentPos();
                        handler.sendMessage(message);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    public void setSong(Song song) {
        currentSong = song;
        setViewUp(song);
    }

    private void setViewUp(Song song) {
        songArtWork.setImageBitmap(getsongArtWork(song.getmSongPath()));
        songTitile.setText(song.getmSongName());
        artistName.setText(song.getmArtistName());
        bigArtWrk.setImageBitmap(getsongArtWork(song.getmSongPath()));
        duration.setMax(CustomPlayer.getInstance(MainActivity.this).getSongDuration(song));
        Toast.makeText(MainActivity.this, CustomPlayer.getInstance(MainActivity.this).getSongDuration(song) + "", Toast.LENGTH_SHORT).show();
        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);

    }

    @Override
    public void trueStart(boolean state) {
        trueStart = state;
    }

    private Bitmap getsongArtWork(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        byte[] data = mmr.getEmbeddedPicture();
        if (data != null) return BitmapFactory.decodeByteArray(data, 0, data.length);
        return null;
    }

    @Override
    public void setImage(Song song) {
        String path = song.getmSongPath();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        byte[] data = mmr.getEmbeddedPicture();
        Bitmap show;
        if (data != null) {
            show = BitmapFactory.decodeByteArray(data, 0, data.length);
        } else {
            ////nulll
            show = null;
        }
        songArtWork.setImageBitmap(show);
    }

    @Override
    public void setTitle(String title) {

        songTitile.setText(title);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    private Handler mSeekbarUpdateHandler = new Handler();
    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            duration.setProgress(CustomPlayer.getInstance(MainActivity.this).getCurrentPos());
            mSeekbarUpdateHandler.postDelayed(this, 50);
            String elapsedTime = createTime(CustomPlayer.getInstance(MainActivity.this).getCurrentPos());
            espledTime.setText(elapsedTime);
            int remaing = CustomPlayer.getInstance(MainActivity.this).getSongDuration()-CustomPlayer.getInstance(MainActivity.this).getCurrentPos();
            remaingTime.setText(createTime(remaing));
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            String onlyTemp;
            int currentPosition = message.what;
            duration.setProgress(currentPosition);
            String elapsedTime = createTime(currentPosition);
            espledTime.setText(elapsedTime+"wtf");
            // String remaingTime=createTime(totalTime-currentPosition);

        }
    };

    private String createTime(int time) {
        String timeLabe = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;
        timeLabe = min + ":";
        if (sec < 10)
            timeLabe += "0";
        timeLabe += sec;
        return timeLabe;
    }
}
