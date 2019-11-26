package boxuegu.com.boxuegu.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import boxuegu.com.boxuegu.LoginActivity;
import boxuegu.com.boxuegu.R;

public class TitleBar {
    private View view;
    private TextView tv_main_title,tv_back;

    public TitleBar(final Activity context, String titleName){
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.main_title_bar,null);
        tv_back=view.findViewById(R.id.tv_back);
        tv_main_title=view.findViewById(R.id.tv_main_title);
        tv_main_title.setText(titleName);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();//系统完成返回栈的相关工作
            }
        });

    }

    public View getView() {
        return view;
    }

    public void setTitleName(String  name){
        tv_main_title.setText(name);
    }
}