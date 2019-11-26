package boxuegu.com.boxuegu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import boxuegu.com.boxuegu.R;
import boxuegu.com.boxuegu.bean.ExercisesDetailBean;

public class ExercisesDetailAdapter extends BaseAdapter {
    private ArrayList<ExercisesDetailBean> beans;
    private Context context;
    private int ivId[]={R.drawable.exercises_a,R.drawable.exercises_b,R.drawable.exercises_c,R.drawable.exercises_d};

    public ExercisesDetailAdapter(ArrayList<ExercisesDetailBean> beans, Context context) {
        this.beans = beans;
        this.context = context;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=null;
        if(convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.exercises_detail_list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_subject=convertView.findViewById(R.id.tv_subject);
            viewHolder.tv[0]=convertView.findViewById(R.id.tv_a);
            viewHolder.tv[1]=convertView.findViewById(R.id.tv_b);
            viewHolder.tv[2]=convertView.findViewById(R.id.tv_c);
            viewHolder.tv[3]=convertView.findViewById(R.id.tv_d);
            viewHolder.iv[0]=convertView.findViewById(R.id.iv_a);
            viewHolder.iv[1]=convertView.findViewById(R.id.iv_b);
            viewHolder.iv[2]=convertView.findViewById(R.id.iv_c);
            viewHolder.iv[3]=convertView.findViewById(R.id.iv_d);

            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();

            //滚入的行曾经是否有作答信息，如果没有，则只需要清除滚走行的作答信息
            //如果有，则清除滚走行的作答信息，还要恢复滚入行曾经的作答信息
            for(int i=0;i<4;i++){
                viewHolder.iv[i].setImageResource(ivId[i]);
            }
            if(beans.get(position).selected==0){//滚入行没有作答信息
                for(int i=0;i<4;i++){
                    viewHolder.iv[i].setEnabled(true);
                }
            }else {//有
                setRightError(viewHolder.iv,position,beans.get(position).selected);
            }

        }

        final ViewHolder vh=viewHolder;

        viewHolder.iv[0].setOnClickListener(new View.OnClickListener() {//为A答案设置监听代码，当用户单击A答案时执行
            @Override
            public void onClick(View v) {
                setRightError(vh.iv,position,1);
            }
        });

        viewHolder.iv[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRightError(vh.iv,position,2);
            }
        });

        viewHolder.iv[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRightError(vh.iv,position,3);
            }
        });

        viewHolder.iv[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRightError(vh.iv,position,4);
            }
        });

        viewHolder.tv_subject.setText(beans.get(position).subject);
        viewHolder.tv[0].setText(beans.get(position).a);
        viewHolder.tv[1].setText(beans.get(position).b);
        viewHolder.tv[2].setText(beans.get(position).c);
        viewHolder.tv[3].setText(beans.get(position).d);

        return convertView;
    }

    private void setRightError(ImageView[] iv,int position,int selected){
        beans.get(position).selected=selected;
        if(beans.get(position).answer==selected){//选择该答案正确
            iv[selected-1].setImageResource(R.drawable.exercises_right_icon);
        }else {
            iv[selected-1].setImageResource(R.drawable.exercises_error_icon);
            iv[beans.get(position).answer-1].setImageResource(R.drawable.exercises_right_icon);
        }

        for(int i=0;i<4;i++){
            iv[i].setEnabled(false);
        }
    }

    class ViewHolder{
        public TextView tv_subject;
        public TextView[] tv=new TextView[4];
        public ImageView[] iv=new ImageView[4];
    }
}