package boxuegu.com.boxuegu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import boxuegu.com.boxuegu.R;
import boxuegu.com.boxuegu.adapter.ExercisesDetailAdapter;
import boxuegu.com.boxuegu.bean.ExercisesDetailBean;
import boxuegu.com.boxuegu.utils.AnalysisUtils;
import boxuegu.com.boxuegu.view.TitleBar;

public class ExercisesDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_detail);

        String title="";
        int id=1;
        //这里接收从习题列表页面跳转过来的意图
        Intent intent=getIntent();
        if(intent!=null){
            id=intent.getIntExtra("id",-1);
            if(id!=-1){
                title=intent.getStringExtra("title");
            }else {
                finish();
            }
        }

        LinearLayout exericses_detail_title_bar;
        exericses_detail_title_bar=findViewById(R.id.exericses_detail_title_bar);
        TitleBar titleBar=new TitleBar(this,title+"习题");
        //标题栏是这一章的题目
        exericses_detail_title_bar.addView(titleBar.getView());

        ListView listView=findViewById(R.id.lv_exercisesDetail_list);

        try {
            InputStream inputStream = getResources().getAssets().open("chapter" + id + ".xml");
            ArrayList<ExercisesDetailBean> beans=AnalysisUtils.getExercisesInfos(inputStream);
            ExercisesDetailAdapter adapter = new ExercisesDetailAdapter(beans, this);
            listView.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(this,"文件打开失败或解析失败！",Toast.LENGTH_LONG).show();
            finish();
        }


    }
}