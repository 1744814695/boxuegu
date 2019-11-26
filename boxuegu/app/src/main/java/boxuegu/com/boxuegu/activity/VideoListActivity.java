package boxuegu.com.boxuegu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import boxuegu.com.boxuegu.R;
import boxuegu.com.boxuegu.adapter.VideoListAdapter;
import boxuegu.com.boxuegu.bean.VideoBean;
import boxuegu.com.boxuegu.utils.AnalysisUtils;

public class VideoListActivity extends AppCompatActivity {

    private TextView tv_left,tv_right,tv_intro;
    private ScrollView sc_intro;
    private ListView lv_video_list;
    private int id,imgId;
    private String intro,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        Intent intent=getIntent();
        id=intent.getIntExtra("id",-1);
        intro=intent.getStringExtra("intro");
        title=intent.getStringExtra("title" );
        imgId=intent.getIntExtra("imgId",-1);
        initView();
    }

    private void initView(){
        tv_left=findViewById(R.id.tv_left);
        tv_right=findViewById(R.id.tv_right);
        tv_intro=findViewById(R.id.tv_intro);
        tv_intro.setText(intro);
        sc_intro=findViewById(R.id.sc_intro);
        lv_video_list=findViewById(R.id.lv_video_list);

        List<VideoBean> beans=new ArrayList<>();
        try {
            beans=AnalysisUtils.getVideoListByJsonFile(getResources().getAssets().open("data.json"));
        }catch (Exception e){
            e.printStackTrace();
        }
//过滤beans中不属于本章的对象
        for(int i=0;i<beans.size();){
            if(beans.get(i).id!=id)
                beans.remove(i);
            else
                i++;
        }

        lv_video_list.setAdapter(new VideoListAdapter(this,beans));

        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_left.setBackgroundColor(Color.parseColor("#30b4ff"));
                tv_left.setTextColor(Color.parseColor("#ffffff"));
                tv_right.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_right.setTextColor(Color.parseColor("#000000"));
                sc_intro.setVisibility(View.VISIBLE);
                lv_video_list.setVisibility(View.GONE);
            }
        });

        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_left.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_left.setTextColor(Color.parseColor("#000000"));
                tv_right.setBackgroundColor(Color.parseColor("#30b4ff"));
                tv_right.setTextColor(Color.parseColor("#ffffff"));
                sc_intro.setVisibility(View.GONE);
                lv_video_list.setVisibility(View.VISIBLE);
            }
        });
    }
}