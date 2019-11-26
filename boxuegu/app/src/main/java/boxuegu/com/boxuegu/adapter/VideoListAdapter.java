package boxuegu.com.boxuegu.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import boxuegu.com.boxuegu.R;
import boxuegu.com.boxuegu.activity.VideoPlayActivity;
import boxuegu.com.boxuegu.bean.VideoBean;

public class VideoListAdapter extends BaseAdapter {

    private AppCompatActivity context;
    private List<VideoBean> beans;
    private ViewHolder oldLine;

    public VideoListAdapter(AppCompatActivity context, List<VideoBean> beans) {
        this.beans=beans;
        this.context=context;
        this.oldLine=null;
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
        ViewHolder viewHolder=new ViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.video_list_item,null);
            viewHolder.iv_left_icon=convertView.findViewById(R.id.iv_left_icon);
            viewHolder.tv_video_title=convertView.findViewById(R.id.tv_video_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        viewHolder.tv_video_title.setText(beans.get(position).videoTitle);

        final ViewHolder vh=viewHolder;

        viewHolder.iv_left_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenClick(vh,position);
            }
        });

        viewHolder.tv_video_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenClick(vh,position);
            }
        });


        return convertView;
    }

    private void listenClick(ViewHolder viewHolder,int position){
//        listview中单击其中一行，本行左边图片更换，右边文字变色其他行复原
        //复原前面行
        if(oldLine!=null){
            oldLine.iv_left_icon.setImageResource(R.drawable.course_bar_icon);
            oldLine.tv_video_title.setTextColor(Color.parseColor("#000000"));
        }
        //设置当前行
        viewHolder.iv_left_icon.setImageResource(R.drawable.course_intro_icon);
        viewHolder.tv_video_title.setTextColor(Color.parseColor("#30b4ff"));
        oldLine=viewHolder;

        Intent intent=new Intent(context, VideoPlayActivity.class);
        intent.putExtra("videoPath",beans.get(position).videoPath);
        intent.putExtra("videoTitle",beans.get(position).videoTitle);
        context.startActivity(intent);
    }

    class ViewHolder{
        ImageView iv_left_icon;
        TextView tv_video_title;
    }
}
