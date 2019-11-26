package boxuegu.com.boxuegu.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import boxuegu.com.boxuegu.R;

public class VideoPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_video_play);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent=getIntent();
        int id=intent.getIntExtra("id",-1);
        String videoPath=intent.getStringExtra("videoPath");

        VideoView videoView=findViewById(R.id.vv_play);
//        videoView.setVideoURI(Uri.parse("android.resource://com.telecom.activities/"+R.raw.video));
        if(videoPath.equals("video11")) {
            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video11));
            videoView.start();
        }else {
            Toast.makeText(this,"视频不存在",Toast.LENGTH_LONG).show();
            this.finish();
        }
    }
}