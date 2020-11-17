package com.example.proressbartest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ProgressBar pb;
    private SeekBar seekBar;
    private Button btnDownload;
    private TextView tvProgress;
    private Handler handler;//主线程以及子线程之间传递信息
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvProgress = findViewById(R.id.tv_progress);
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1:
                if(progress<100){
                        pb.setProgress(progress);
                        progress++;
                        tvProgress.setText(progress + "%");
                        handler.sendEmptyMessageDelayed(1, 200);
                    }else {
                    Toast.makeText(MainActivity.this,"下载完成！",Toast.LENGTH_SHORT).show();
                }
                        break;
                }
            }
        };
        btnDownload = findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个子线程
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                    }
                });
                // 启动线程
                thread.start();
            }
        });
        pb = findViewById(R.id.pb_progress);
        seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 手动拖动滑杆进度条随之改变
                pb.setProgress(progress);
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