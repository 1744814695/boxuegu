package boxuegu.com.boxuegu.view;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import boxuegu.com.boxuegu.R;
import boxuegu.com.boxuegu.adapter.CourseAdapter;
import boxuegu.com.boxuegu.bean.CourseBean;
import boxuegu.com.boxuegu.fragment.AdBannerFragment;

public class CourseView {
    private View view;//构造方法中加载解析子布局文件生成view对象
    private AppCompatActivity context;
    public CourseView(AppCompatActivity context){
        this.context=context;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.main_view_course,null);
        initView();
    }

    private void initView(){
        FrameLayout fl_adbanner=view.findViewById(R.id.fl_adbanner);
        List<AdBannerFragment> fragments=new ArrayList<AdBannerFragment>();
        int bannerId[]={R.drawable.banner_1,R.drawable.banner_2,R.drawable.banner_3};
        for(int i=0;i<3;i++){
            AdBannerFragment fragment=AdBannerFragment.newInstance(bannerId[i]);
            fragments.add(fragment);
        }
        MyAdBanner myAdBanner=new MyAdBanner(context,fragments);
        fl_adbanner.addView(myAdBanner.getView());

        ListView lv_list=view.findViewById(R.id.lv_list);
        List<CourseBean> beans=new ArrayList<>();
        CourseBean bean=new CourseBean();
        bean.imgId=R.drawable.banner_1;
        bean.title="第1章";
        bean.imgTitle="123";
        bean.intro="安卓入门与AS介绍";
        beans.add(bean);

        bean=new CourseBean();
        bean.imgId=R.drawable.banner_2;
        bean.title="第2章";
        bean.imgTitle="456";
        bean.intro="常用布局";
        beans.add(bean);

        bean=new CourseBean();
        bean.imgId=R.drawable.banner_3;
        bean.title="第3章";
        bean.imgTitle="789";
        bean.intro="activity与intent";
        beans.add(bean);

        lv_list.setAdapter(new CourseAdapter(context,beans));

    }
    public View getView(){
        return this.view;
    }

    public void addView(View view) {
    }

    public void showView() {

    }
}