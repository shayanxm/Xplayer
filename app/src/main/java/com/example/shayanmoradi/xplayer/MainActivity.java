package com.example.shayanmoradi.xplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shayanmoradi.xplayer.ControllMusic.ControlArtistFragment;
import com.example.shayanmoradi.xplayer.ControllMusic.ControlMusicFragment;
import com.example.shayanmoradi.xplayer.Models.CustomPlayer;
import com.example.shayanmoradi.xplayer.Models.Song;
import com.example.shayanmoradi.xplayer.Models.SongLab;
import com.example.shayanmoradi.xplayer.ViewPagerFragments.AlbumsFragment;
import com.example.shayanmoradi.xplayer.ViewPagerFragments.ArtistsFragment;
import com.example.shayanmoradi.xplayer.ViewPagerFragments.LovesFragment;
import com.example.shayanmoradi.xplayer.ViewPagerFragments.SongsFragment;
import com.example.shayanmoradi.xplayer.database.LoveList;
import com.example.shayanmoradi.xplayer.database.LoveListLab;
import com.example.shayanmoradi.xplayer.database.SongDetailLab;
import com.example.shayanmoradi.xplayer.database.SongDetails;
import com.example.shayanmoradi.xplayer.lyricsStuffs.EditLyricTextFragment;
import com.example.shayanmoradi.xplayer.lyricsStuffs.LyricsActivity;
import com.example.shayanmoradi.xplayer.search.Search2Activity;
import com.example.shayanmoradi.xplayer.search.SearchFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements SongsFragment.CallBacks, ControlMusicFragment.CallBacks, ControlArtistFragment.CallBacks, LovesFragment.CallBacks, SearchFragment.CallBacks {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ViewPagerAdapter adapter;
    private ImageButton goToNextSong;
    private ImageButton pauseUnPauseSong;
    private ImageButton gotToPerivousSong;
    private MaterialButton playBtn;
    private boolean trueStart = false;
    private boolean repeat = true;
    private boolean shuffle = true;
    private boolean lyrics = false;
    private boolean love = false;
    private Song currentSong;
    private ImageView songArtWork;
    public TextView songTitile;
    public TextView artistName;
    public ImageView bigArtWrk;
    private SeekBar duration;
    private TextView espledTime;
    private TextView remaingTime;
    private ImageButton plaeUnPlayOnTop;
    private ImageButton repeatSong;
    private ImageButton shuffleSongs;
    private ImageButton showLyrics;
    private TextView lyricsShower;
    private View bottomSheet;
    private View ViewPagerContiner;
    private View bigArtContiner;
    private ImageButton addToLoveList;
    private Toolbar xPlayerToolBar;
    private TabLayout topPageTabs;
    private ImageButton searchBtn;
    SongDetails songDetailsGet;
    int xCharPointerToFirst = 0;
    int xCharPointerToEnd;

    String XFirstPos = "";
    String XEndPos = "";
    int pointerChandoimJomle = -1;
    boolean xOnlyOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /////
        castView();
        ///

        View bottomSheet = findViewById(R.id.tes);
        bottomSheet.setEnabled(false);

        lyricsShower.setVisibility(View.GONE);
        BottomSheetBehavior.from(bottomSheet).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:

                        //   Toast.makeText(MainActivity.this, "open", Toast.LENGTH_SHORT).show();
                        plaeUnPlayOnTop.setVisibility(View.GONE);
                        showLyrics.setVisibility(View.VISIBLE);
                        viewPager.setVisibility(View.GONE);
                        lyricsShower.setVisibility(View.GONE);
                        xPlayerToolBar.setVisibility(View.GONE);
                        topPageTabs.setVisibility(View.VISIBLE);
                        if (lyrics) {
                            // showLyrics.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.lyrics_3));
                            lyricsShower.setVisibility(View.VISIBLE);
                          //  bigArtWrk.setVisibility(View.GONE);
                            bigArtContiner.setVisibility(View.GONE);
                        }
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        // Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
                        plaeUnPlayOnTop.setVisibility(View.VISIBLE);
                        xPlayerToolBar.setVisibility(View.VISIBLE);
                      // bigArtWrk.setVisibility(View.VISIBLE);
                        bigArtContiner.setVisibility(View.VISIBLE);
                        viewPager.setVisibility(View.VISIBLE);

                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //   Toast.makeText(MainActivity.this, "drqgg", Toast.LENGTH_SHORT).show();

                        viewPager.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // no op
            }
        });


///get song when origtion changes
        currentSong = SongLab.getInstance(MainActivity.this).getAllSongs().get(0);
        if (savedInstanceState != null) {

            String test = savedInstanceState.getString("AStringKey");
            Song song = SongLab.getInstance(MainActivity.this).getSong(test);
            currentSong = song;
        }

        setViewUp(currentSong, true);
        setViewOnrotate(currentSong);
        // setCurrentText();
        showLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lyrics == true) {
                    lyricsShower.setVisibility(View.GONE);
                  //  bigArtWrk.setVisibility(View.VISIBLE);
                    bigArtContiner.setVisibility(View.VISIBLE);
                    showLyrics.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.lyrics_4));
                } else {
                    showLyrics.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.lyrics_3));
                    lyricsShower.setVisibility(View.VISIBLE);
                    //bigArtWrk.setVisibility(View.GONE);
                    bigArtContiner.setVisibility(View.GONE);
                }


                //  showLyrics.setVisibility(View.GONE);
                SongDetails songDetails = new SongDetails();
                // songDetails.setFullLyrics("miran adama az ona faghat khatere hashon b ja mimone :)");
                //  songDetails.setDetailOfsongName(currentSong.getmSongName());
                // SongDetailLab.getInstance(MainActivity.this).addDetail(songDetails);
                //  SongDetailLab.getInstance(MainActivity.this).update(songDetails);
                songDetails = new SongDetails();
                // songDetailsGet = SongDetailLab.getInstance(MainActivity.this).getDetail(currentSong.getmSongName());
//         lyricsShower.setText(songDetailsGet.getFullLyrics());
                //  lyricsShower.setText(songDetailsGet.getTimesLyrics());

                lyrics = !lyrics;
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Search2Activity.class);
                startActivity(intent);
            }
        });
        addToLoveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (love == true) {

                    addToLoveList.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.like_3));
                } else {
                    LoveList loveList = new LoveList();
                    loveList.setLovedSongName(currentSong.getmSongName());

                    //   songDetailsGet = LoveListLab.getInstance(MainActivity.this).getAllLovedSongsName();

                    LoveListLab.getInstance(MainActivity.this).addToLove(loveList);
                    addToLoveList.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.like));
                }
                love = !love;
            }
        });
        shuffleSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shuffle) {
                    shuffleSongs.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.shuffle));
                    CustomPlayer.getInstance(MainActivity.this).unShuffle();
                } else {
                    shuffleSongs.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.shuffle_mode_arrows_3));
                    CustomPlayer.getInstance(MainActivity.this).shuffle();
                }

                shuffle = !shuffle;


            }
        });
        bigArtWrk.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EditLyricTextFragment datePickerFragment = EditLyricTextFragment.newInstance(currentSong.getmSongName());
//        datePickerFragment.setTargetFragment(GetInfoFragment.this,
//                REQ_DATE_PICKER);
                //   datePickerFragment.show(FragmentManager, "he");
                Intent intent = LyricsActivity.newIntent(MainActivity.this, currentSong.getmSongName());
                startActivity(intent);
                return false;
            }
        });
//        bigArtWrk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditLyricTextFragment datePickerFragment = EditLyricTextFragment.newInstance(currentSong.getmSongName());
////        datePickerFragment.setTargetFragment(GetInfoFragment.this,
////                REQ_DATE_PICKER);
//                //   datePickerFragment.show(FragmentManager, "he");
//                Intent intent = LyricsActivity.newIntent(MainActivity.this, currentSong.getmSongName());
//                startActivity(intent);
//
//            }
//        });
        repeatSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPlayer.getInstance(MainActivity.this).repeatSongb(repeat);

                if (!repeat) {
                    repeatSong.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.repaeting));
                } else {
                    repeatSong.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.repeat_3));
                }

                repeat = !repeat;


            }
        });
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
        plaeUnPlayOnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trueStart == true) {
                    // CustomPlayer.getInstance(MainActivity.this).start(currentSong);
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
                    CustomPlayer.getInstance(MainActivity.this).resmue();
                    pauseUnPauseSong.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.pause));
                    plaeUnPlayOnTop.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.pause_2));

                } else {
                    CustomPlayer.getInstance(MainActivity.this).pause();
                    pauseUnPauseSong.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.movie_player_play_button_2));
                    plaeUnPlayOnTop.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.play));

                }
                trueStart = !trueStart;
            }
        });
        pauseUnPauseSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (trueStart == true) {
                    // CustomPlayer.getInstance(MainActivity.this).start(currentSong);
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
                    CustomPlayer.getInstance(MainActivity.this).resmue();
                    pauseUnPauseSong.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.pause));
                    plaeUnPlayOnTop.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.pause_2));

                } else {
                    CustomPlayer.getInstance(MainActivity.this).pause();
                    pauseUnPauseSong.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.movie_player_play_button_2));
                    plaeUnPlayOnTop.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.play));

                }
                trueStart = !trueStart;
            }
        });
        goToNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPlayer.getInstance(MainActivity.this).pause();
                currentSong = CustomPlayer.getInstance(MainActivity.this).nextSong();

                setViewUp(currentSong, false);
            }
        });

        gotToPerivousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPlayer.getInstance(MainActivity.this).pause();
                currentSong = CustomPlayer.getInstance(MainActivity.this).periviousSong();

                setViewUp(currentSong, false);
            }
        });

        setViewPagerUp();


//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Message message = new Message();
//                        message.what = CustomPlayer.getInstance(MainActivity.this).getCurrentPos();
//                        handler.sendMessage(message);
//                        Thread.sleep(100000);
//                    } catch (InterruptedException e) {
//                        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//        });
    }

    private void setViewPagerUp() {
        SongsFragment topAppBarFragment = SongsFragment.newInstance();
        adapter.addFrag(topAppBarFragment, "         Songs          ");

        AlbumsFragment tabLayoutFragment = AlbumsFragment.newInstance();
        adapter.addFrag(tabLayoutFragment, "      Albums     ");

        ArtistsFragment bottomNavigationFragment = ArtistsFragment.newInstance();
        adapter.addFrag(bottomNavigationFragment, "       Artist       ");
        LovesFragment lovesFragment = LovesFragment.newInstance();
        adapter.addFrag(lovesFragment, "       loves       ");
        viewPager.setOffscreenPageLimit(1);
//        TextFieldsFragment textFieldsFragment = TextFieldsFragment.newInstance();
//        adapter.addFrag(textFieldsFragment,"Text Fields");

        viewPager.setAdapter(adapter);
    }

    private void castView() {
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
        plaeUnPlayOnTop = findViewById(R.id.paly_pause_on_top);
        repeatSong = findViewById(R.id.repeat_btn);
        shuffleSongs = findViewById(R.id.shuffle_btn);
        showLyrics = findViewById(R.id.show_lyrics_btn);
        lyricsShower = findViewById(R.id.lyrics_shower);
        bottomSheet = findViewById(R.id.bottom_sheet);
        xPlayerToolBar = findViewById(R.id.x_palyer);
        addToLoveList = findViewById(R.id.add_to_love);
        topPageTabs = findViewById(R.id.tabs);
        searchBtn = findViewById(R.id.search_btn);
        bigArtContiner = findViewById(R.id.big_art_continer);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("AStringKey", currentSong.getmSongPath());
    }

    @Override
    public void setSong(Song song) {
        currentSong = song;
        CustomPlayer.getInstance(MainActivity.this).pause();
        CustomPlayer.getInstance(MainActivity.this).start(song);
        setViewUp(song, false);
    }

    public void setViewUp(Song song, boolean start) {
        songArtWork.setImageBitmap(getsongArtWork(song.getmSongPath()));
        songTitile.setText(song.getmSongName());
        artistName.setText(song.getmArtistName());
        if (song.getmSongPath() != null)
            bigArtWrk.setImageBitmap(getsongArtWork(song.getmSongPath()));

        if (song.getmSongPath() == null) {
            bigArtWrk.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.noimage));
        }

        duration.setMax(CustomPlayer.getInstance(MainActivity.this).getSongDuration(song));
        Toast.makeText(MainActivity.this, CustomPlayer.getInstance(MainActivity.this).getSongDuration(song) + "", Toast.LENGTH_SHORT).show();
        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
        if (start == false) {
            pauseUnPauseSong.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.pause));
            plaeUnPlayOnTop.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.pause_2));
        }
        songDetailsGet = SongDetailLab.getInstance(MainActivity.this).getDetail(currentSong.getmSongName());
        if (xOnlyOnce == false) {
            setCurrentText();
            xOnlyOnce = true;
        }
    }

    @Override
    public void setViewOnrotate(Song song) {
        songArtWork.setImageBitmap(getsongArtWork(song.getmSongPath()));
        songTitile.setText(song.getmSongName());
        artistName.setText(song.getmArtistName());
        if (song.getmSongPath() != null)
            bigArtWrk.setImageBitmap(getsongArtWork(song.getmSongPath()));

        if (song.getmSongPath() == null) {
            bigArtWrk.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.noimage));
        }

        duration.setMax(CustomPlayer.getInstance(MainActivity.this).getSongDuration(song));
        Toast.makeText(MainActivity.this, CustomPlayer.getInstance(MainActivity.this).getSongDuration(song) + "", Toast.LENGTH_SHORT).show();
        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 50);

//            pauseUnPauseSong.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.pause));
//            plaeUnPlayOnTop.setBackground(MainActivity.this.getResources().getDrawable(R.drawable.pause_2));
//
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
            mSeekbarUpdateHandler.postDelayed(this, 1000);
            String elapsedTime = createTime(CustomPlayer.getInstance(MainActivity.this).getCurrentPos());
            espledTime.setText(elapsedTime);
            int remaing = CustomPlayer.getInstance(MainActivity.this).getSongDuration() - CustomPlayer.getInstance(MainActivity.this).getCurrentPos();
            remaingTime.setText(createTime(remaing));


            //  lyricsShower.setText("not began yet");
            if (songDetailsGet != null)
                checkPositions(CustomPlayer.getInstance(MainActivity.this).getCurrentPos());

        }
    };

    private void checkPositions(int mediaPlayerPos) {

        if (XFirstPos != "") {
          //  lyricsShower.setText("NO thing to show");
            int first = Integer.parseInt(XFirstPos);
            int end = Integer.parseInt(XEndPos);


            if (mediaPlayerPos > first && mediaPlayerPos <= end) {

                lyricsShower.setText(showString());
            }
            if (end < mediaPlayerPos) {
                setCurrentText();
            }
        }
    }

    private String showString() {
        String temp = songDetailsGet.getFullLyrics();
        String shower = temp.substring(pointerChandoimJomle * 20, (pointerChandoimJomle + 1) * 20);
        return shower;
    }

    private void setCurrentText() {
        if (songDetailsGet != null) {
            XFirstPos = "";
            XEndPos = "";
            songDetailsGet = SongDetailLab.getInstance(MainActivity.this).getDetail(currentSong.getmSongName());
            String timesLyrics = songDetailsGet.getTimesLyrics();

            if (timesLyrics.charAt(xCharPointerToFirst) != 'E' && timesLyrics.charAt(xCharPointerToEnd) != 'E') {
                while (timesLyrics.charAt(xCharPointerToFirst) != ',') {
                    XFirstPos += timesLyrics.charAt(xCharPointerToFirst);


                    xCharPointerToFirst++;
                }
                xCharPointerToEnd = xCharPointerToFirst + 1;
                while (timesLyrics.charAt(xCharPointerToEnd) != ';') {
                    XEndPos += timesLyrics.charAt(xCharPointerToEnd);


                    xCharPointerToEnd++;
                }
                xCharPointerToFirst = xCharPointerToEnd + 1;
                pointerChandoimJomle++;
            }
        }
    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message message) {
//            String onlyTemp;
//            int currentPosition = message.what;
//            duration.setProgress(currentPosition);
//            String elapsedTime = createTime(currentPosition);
//            espledTime.setText(elapsedTime + "wtf");
//            // String remaingTime=createTime(totalTime-currentPosition);
//
//        }
//    };

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

    public int deCodeTime(String time) {
        return 0;
    }
}
