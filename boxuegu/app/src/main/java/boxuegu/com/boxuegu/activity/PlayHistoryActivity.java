package boxuegu.com.boxuegu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import boxuegu.com.boxuegu.R;
import boxuegu.com.boxuegu.adapter.PlayHistoryAdapter;
import boxuegu.com.boxuegu.adapter.VideoListAdapter;
import boxuegu.com.boxuegu.bean.VideoBean;
import boxuegu.com.boxuegu.utils.DBUtils;
import boxuegu.com.boxuegu.utils.SPreadWrite;
import boxuegu.com.boxuegu.view.TitleBar;

public class PlayHistoryActivity extends AppCompatActivity {
    private FrameLayout fl_header;
    private ListView lv_list;
    private TextView tv_none;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_history);
        initView();
    }

    private void initView(){
        fl_header=findViewById(R.id.fl_header);
        lv_list=findViewById(R.id.lv_list);
        tv_none=findViewById(R.id.tv_none);
        TitleBar titleBar=new TitleBar(this,"播放记录");
        fl_header.addView(titleBar.getView());

        DBUtils db=DBUtils.getInstance(this);
        List<VideoBean> videoBeans=db.getPlayHistory(SPreadWrite.readLoginName(this));

        if(videoBeans.size()==0){
            lv_list.setVisibility(View.GONE);
            tv_none.setVisibility(View.VISIBLE);
        }else {
            lv_list.setVisibility(View.VISIBLE);
            tv_none.setVisibility(View.GONE);
            //VideoListAdapter adapter = new VideoListAdapter(this, videoBeans);
            PlayHistoryAdapter adapter=new PlayHistoryAdapter(this,videoBeans);
            lv_list.setAdapter(adapter);
        }

    }
}