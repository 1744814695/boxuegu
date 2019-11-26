package boxuegu.com.boxuegu.view;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import boxuegu.com.boxuegu.R;

public class BottomBar {

    private  View view;
    private LinearLayout title_bar;
    private FrameLayout main_body;
    private AppCompatActivity context;
    private RelativeLayout bottom_bar_course_btn,bottom_bar_exerises_btn,bottom_bar_myinfo_btn;
    private ImageView bottom_bar_image_course,bottom_bar_image_exerise,bottom_bar_image_myinfo;
    private TextView bottom_bar_text_exerises,bottom_bar_text_course,bottom_bar_text_myinfo;

    private ImageView imageViews[]=new ImageView[3];
    private TextView textViews[]=new TextView[3];
    //底部按钮的控件数组
    private View bodyViews[]=new View[3];//中间三个页面view控件数组

    private int[] unSelecteds={R.drawable.main_course_icon,R.drawable.main_exercises_icon,R.drawable.main_my_icon};
    private int selecteds[]={R.drawable.main_course_icon_selected,R.drawable.main_exercises_icon_selected,R.drawable.main_my_icon_selected};
    //底部按钮的图片资源数组

    private CourseView courseView;
    private ExercisesView exercisesView;
    private MyInfoView myInfoView;


    private int currentSelected;//保存当前用户的选择，0:选择的课程，1：习题，2：我

    private  TitleBar titleBar;

    private void createBodyView(int newPage){
        //1如果对应中间页面不存在，则生成
        //2添加到主界面的对应位置
        //3将生成中间页面view对象添加的数组中
        switch (newPage){
            case 0:{
                if(courseView==null){
                    courseView=new CourseView(context);
                    main_body.addView(courseView.getView());
                    bodyViews[0]=courseView.getView();
                }
                break;
            }
            case 1:{
                if(exercisesView==null){
                    exercisesView=new ExercisesView(context);
                    main_body.addView(exercisesView.getView());
                    bodyViews[1]=exercisesView.getView();
                }
                break;
            }
            case 2:{
                if(myInfoView==null){
                    myInfoView=new MyInfoView(context);
                    main_body.addView(myInfoView.getView());
                    bodyViews[2]=myInfoView.getView();
                }
                break;
            }
        }
    }

    private void switchPage(int newPage){
        //选中课程按钮时，完成下面工作
        //1、修改标题栏的标题:如果需要标题栏并且没有生存则new出来，并添加主界面对应位置；
        // 如果需要标题栏同时已经有标题栏对象，则修改标题并显示，如果不需要标题栏并标题栏对象存在，则隐藏
        //2、将前面选中的中间页面设置隐藏
        //3、将当前选中的页面设置显示，如果不存在，则new出来
        //4、将前面导航栏的选中按钮设置为未选中
        //5、将当前的导航栏按钮设置为选中
        //6、将当期选中的选项保存到currentSelected变量中
        switch (newPage){
            case 0:{//新选中的是课程
                if(titleBar==null)
                {
                    titleBar=new TitleBar(context,"博学谷课程");
                    title_bar.addView(titleBar.getView());
                }else{//表示这个标题栏对象已经存在，但是隐藏，这里显示即可
                    titleBar.getView().setVisibility(View.VISIBLE);
                    titleBar.setTitleName("博学谷课程");
                }
                break;
            }
            case 1:{
                if(titleBar==null)
                {
                    titleBar=new TitleBar(context,"博学谷习题");
                    title_bar.addView(titleBar.getView());
                }else{//表示这个标题栏对象已经存在，但是隐藏，这里显示即可
                    titleBar.getView().setVisibility(View.VISIBLE);
                    titleBar.setTitleName("博学谷习题");
                }
                break;
            }
            case 2:{
                if(titleBar!=null)
                {
                    titleBar.getView().setVisibility(View.GONE);
                }
                break;
            }
        }
        //前面选中的中间页面要设置为隐藏
        if(bodyViews[currentSelected]!=null){
            bodyViews[currentSelected].setVisibility(View.GONE);
        }

        //当前选中中间页面设置为显示，如果不存在则生成，并添加到主界面位置中
        createBodyView(newPage);//如果不存在则生成
        bodyViews[newPage].setVisibility(View.VISIBLE);

        //前面选中的底部导航栏按钮设置为未选中
        imageViews[currentSelected].setImageResource(unSelecteds[currentSelected]);
        textViews[currentSelected].setTextColor(Color.parseColor("#666666"));

        //设置底部导航栏的选中状态
        imageViews[newPage].setImageResource(selecteds[newPage]);
        textViews[newPage].setTextColor(Color.parseColor("#0097f7"));

        currentSelected=newPage;
    }

    public void setUserName(String userName){
        myInfoView.setUserName(userName);
    }

    public BottomBar(AppCompatActivity context){
        this.context=context;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.main_view_bottom_bar,null);

        findView();

        setDefaultStatus();        ;

        bottom_bar_course_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPage(0);
            }
        });

        bottom_bar_exerises_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPage(1);
            }
        });

        bottom_bar_myinfo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPage(2);
            }
        });
    }

    private void findView(){
        main_body=context.findViewById(R.id.main_body);
        title_bar=context.findViewById(R.id.title_bar);

        bottom_bar_course_btn=view.findViewById(R.id.bottom_bar_course_btn);
        bottom_bar_exerises_btn=view.findViewById(R.id.bottom_bar_exerises_btn);
        bottom_bar_myinfo_btn=view.findViewById(R.id.bottom_bar_myinfo_btn);
        bottom_bar_image_course=view.findViewById(R.id.bottom_bar_image_course);

        imageViews[0]=bottom_bar_image_course;
        bottom_bar_image_exerise=view.findViewById(R.id.bottom_bar_image_exerise);
        imageViews[1]=bottom_bar_image_exerise;
        bottom_bar_image_myinfo=view.findViewById(R.id.bottom_bar_image_myinfo);
        imageViews[2]=bottom_bar_image_myinfo;

        bottom_bar_text_course=view.findViewById(R.id.bottom_bar_text_course);
        textViews[0]=bottom_bar_text_course;
        bottom_bar_text_exerises=view.findViewById(R.id.bottom_bar_text_exerises);
        textViews[1]=bottom_bar_text_exerises;
        bottom_bar_text_myinfo=view.findViewById(R.id.bottom_bar_text_myinfo);
        textViews[2]=bottom_bar_text_myinfo;
    }

    public View getView() {
        return view;
    }

    private void setDefaultStatus(){
        //app启动进入主界面默认标题栏，默认中间页面，默认底部导航栏
        currentSelected=0;
        switchPage(0);

    }
}