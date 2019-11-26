package boxuegu.com.boxuegu.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import boxuegu.com.boxuegu.R;
import boxuegu.com.boxuegu.adapter.ExercisesAdapter;
import boxuegu.com.boxuegu.bean.ExercisesBean;

public class ExercisesView {
    private View view;//构造方法中加载解析子布局文件生成view对象
    private int bid[]={R.drawable.exercises_bg_1,R.drawable.exercises_bg_2,R.drawable.exercises_bg_3,R.drawable.exercises_bg_4};
    public ExercisesView(Activity context){
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.main_view_exercises,null);
        ListView listView=view.findViewById(R.id.lv_list);
        List<ExercisesBean> lists=new ArrayList<ExercisesBean>();
        for(int i=0;i<20;i++){
            ExercisesBean bean=new ExercisesBean();
            bean.setId(i+1);
            bean.setTitle("第"+(i+1)+"章");
            bean.setContent("共计5题");
            bean.setBackground(bid[i%4]);
            lists.add(bean);
        }

        ExercisesAdapter exercisesAdapter=new ExercisesAdapter(lists,context);

        listView.setAdapter(exercisesAdapter);

    }
    public View getView(){
        return this.view;
    }

    public void showView() {
    }
}