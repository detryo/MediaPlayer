package com.cristian_sedano.mediaplayer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MediaPlayer extends AppCompatActivity {
    android.media.MediaPlayer mp;
    SeekBar seekBarVolume, seekBarStatus;
    TextView volume, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        seekBarVolume = (SeekBar) findViewById(R.id.seekBar);
        seekBarVolume.setMax(100);
        seekBarStatus = (SeekBar) findViewById(R.id.seekBar_Audio);
        mp  = android.media.MediaPlayer.create(this,R.raw.santaflow);
        volume = (TextView) findViewById(R.id.textView_seek_status);
        status = (TextView) findViewById(R.id.textView_seek_duration);
        seekBarStatus.setMax(mp.getDuration());
        Button buttonplayer = (Button) findViewById(R.id.button_play);
        buttonplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp == null){
                    mp = android.media.MediaPlayer.create(getApplicationContext(),R.raw.santaflow);
                    seekBarStatus.setMax(mp.getDuration());
                }
                mp.start();
            }
        });
        mp.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(android.media.MediaPlayer mp) {
                mp.reset();
                mp.release();
                mp = null;
            }
        });
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progres = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progres = progress;
                if (mp!=null) {
                    mp.setVolume(progres * 1.0f/100.0f, progres * 1.0f/100.0f);
                }
                volume.setText("Volume"+progres + "/"+seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volume.setText("Volume"+progres + "/"+seekBar.getMax());
            }
        });
        seekBarStatus.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mp!=null && fromUser){
                    mp.seekTo(progress);
                    status.setText("Duration"+progress+"/"+mp.getDuration());
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final Handler handler = new Handler();
        MediaPlayer.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mp!=null){
                    int mpCurrentPosition = mp.getCurrentPosition();
                    seekBarStatus.setProgress(mpCurrentPosition);
                    status.setText("Duration"+mpCurrentPosition+"/"+mp.getDuration());
                }
                handler.postDelayed(this, 100);
            }
        });
    }
}
