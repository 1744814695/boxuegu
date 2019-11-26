package boxuegu.com.boxuegu.adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import boxuegu.com.boxuegu.R;
import boxuegu.com.boxuegu.activity.VideoListActivity;
import boxuegu.com.boxuegu.bean.CourseBean;

public class CourseAdapter extends BaseAdapter{
    private AppCompatActivity context;
    private List<CourseBean> beans;

    public CourseAdapter(AppCompatActivity context, List<CourseBean> beans) {//传两个参数，1、适配器的上下文，2、传入数据源
        this.beans=beans;
        this.context=context;
    }

    @Override
    public int getCount() {
        return (int)Math.rint(this.beans.size()/2.0);
    }

    @Override
    public Object getItem(int position) {
        return this.beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.course_list_item,null);
            viewHolder.iv_left_img=convertView.findViewById(R.id.iv_left_img);
            viewHolder.tv_left_img_title=convertView.findViewById(R.id.tv_left_img_title);
            viewHolder.tv_left_titlr=convertView.findViewById(R.id.tv_left_title);
            viewHolder.iv_right_img=convertView.findViewById(R.id.iv_right_img);
            viewHolder.tv_right_img_title=convertView.findViewById(R.id.tv_right_img_title);
            viewHolder.tv_right_titlr=convertView.findViewById(R.id.tv_right_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        viewHolder.iv_left_img.setImageResource(beans.get(position*2).imgId);
        viewHolder.tv_left_img_title.setText(beans.get(position*2).imgTitle);
        viewHolder.tv_left_titlr.setText(beans.get(position*2).title);
        if((position * 2 + 1)<beans.size()) {//该行有右边
            viewHolder.iv_right_img.setVisibility(View.VISIBLE);
            viewHolder.tv_right_img_title.setVisibility(View.VISIBLE);
            viewHolder.tv_right_titlr.setVisibility(View.VISIBLE);
            viewHolder.iv_right_img.setImageResource(beans.get(position * 2 + 1).imgId);
            viewHolder.tv_right_img_title.setText(beans.get(position * 2 + 1).imgTitle);
            viewHolder.tv_right_titlr.setText(beans.get(position * 2 + 1).title);
        }else {//该行没有右边
            viewHolder.iv_right_img.setVisibility(View.GONE);
            viewHolder.tv_right_img_title.setVisibility(View.GONE);
            viewHolder.tv_right_titlr.setVisibility(View.GONE);
        }

        viewHolder.iv_left_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, VideoListActivity.class);
                intent.putExtra("id",beans.get(position*2).id);
                intent.putExtra("intro",beans.get(position*2).intro);
                intent.putExtra("title",beans.get(position*2).title);
                intent.putExtra("imgId",beans.get(position*2).imgId);
                context.startActivity(intent);
            }
        });

        viewHolder.iv_right_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, VideoListActivity.class);
                intent.putExtra("id",beans.get(position*2+1).id);
                intent.putExtra("intro",beans.get(position*2+1).intro);
                intent.putExtra("title",beans.get(position*2+1).title);
                intent.putExtra("imgId",beans.get(position*2+1).imgId);
                context.startActivity(intent);
            }
        });



        return convertView;
    }

    class ViewHolder{
        public TextView tv_left_img_title,tv_left_titlr;
        public ImageView iv_left_img;
        public TextView tv_right_img_title,tv_right_titlr;
        public ImageView iv_right_img;
    }
}