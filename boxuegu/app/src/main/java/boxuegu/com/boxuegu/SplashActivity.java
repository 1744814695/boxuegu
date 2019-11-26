package boxuegu.com.boxuegu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import boxuegu.com.boxuegu.activity.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView textView=findViewById(R.id.tv_version);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置屏幕是否横屏

        PackageManager packageManager=getPackageManager();
        try {//获取程序包信息
            PackageInfo packageInfo=packageManager.getPackageInfo(getPackageName(), 0);
            textView.setText("V"+packageInfo.versionName);
        }
        catch (Exception e){
            e.printStackTrace();
            textView.setText("V");
        }

        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {//过三秒就执行这里面的代码
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(task,3000);
    }
}
