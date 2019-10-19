package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView time;
    Boolean active =false;
    CountDownTimer count;
    int secs;
    public void timer(View view) {

        final Button start = (Button) findViewById(R.id.go);

        if(active) {
            start.setText("START");
            seekBar.setEnabled(true);
            seekBar.setProgress(secs);
            update(secs);
            active=false;
            count.cancel();
        }

        else if(!active) {

            seekBar.setEnabled(false);
            active=true;
            start.setText("STOP!");

            count = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    //time.setText(Integer.toString(minute)+":"+Integer.toString(second));
                    secs=(int)millisUntilFinished/1000;
                    update((int) millisUntilFinished / 1000);
                    seekBar.setProgress(secs);
                }

                @Override
                public void onFinish() {
                    start.setText("Timer Off");
                    MediaPlayer gun = MediaPlayer.create(getApplicationContext(), R.raw.gun);
                    gun.start();
                }
            }.start();
        }
    }

    public void update(int progress){

        int minute=progress/60;
        int second=(progress-minute*60);

        String secStr=Integer.toString(second);
        String minStr=Integer.toString(minute);

        if(minute<=9) {
            minStr="0"+minute;
        }

        if(second<=9) {
            secStr="0"+second;
        }

        time.setText(minStr+":"+secStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar =(SeekBar)findViewById(R.id.seekBar);
        time = (TextView) findViewById(R.id.time);
        seekBar.setProgress(30);
        seekBar.setMax(100);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
