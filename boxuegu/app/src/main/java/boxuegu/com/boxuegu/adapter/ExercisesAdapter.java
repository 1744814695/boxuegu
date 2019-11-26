package boxuegu.com.boxuegu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import boxuegu.com.boxuegu.activity.ExercisesDetailActivity;
import boxuegu.com.boxuegu.R;
import boxuegu.com.boxuegu.bean.ExercisesBean;

public class ExercisesAdapter extends BaseAdapter {
    private Context context;
    private List<ExercisesBean> lists;

    public ExercisesAdapter(List<ExercisesBean> lists,Context context) {
        this.context=context;
        this.lists=lists;
    }

    @Override
    public int getCount() {
        if(lists==null) return 0;
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        if(lists==null) return null;
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        if(lists==null) return 0;
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //当listview要在屏幕上生成某一行position时，就执行一次该方法，返回值就是这一行的行view控件
        //第二个参数，就是滚走的那一行的行控件view
        ViewHolder viewHolder;
        if(convertView==null){//没有滚走的行
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.exercises_list_item,null);
            TextView tv_order=convertView.findViewById(R.id.tv_order);
            TextView tv_title=convertView.findViewById(R.id.tv_title);
            TextView tv_content=convertView.findViewById(R.id.tv_content);
            viewHolder=new ViewHolder();
            viewHolder.tv_order=tv_order;
            viewHolder.tv_title=tv_title;
            viewHolder.tv_content=tv_content;
            convertView.setTag(viewHolder);//将新生成的行控件view和对应的viewHolder保存到缓冲器区中，以备当某次滚走该行时，再次利用
        }else{//有滚走的行
            viewHolder=(ViewHolder) convertView.getTag();//取出滚走的行控件view对应viewHolder
        }        ;
        viewHolder.tv_order.setText((position+1)+"");
        viewHolder.tv_title.setText(lists.get(position).getTitle());
        viewHolder.tv_content.setText(lists.get(position).getContent());
        viewHolder.tv_order.setBackgroundResource(lists.get(position).getBackground());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //即将打开新的页面，是习题内容页面
                //1、压制章节标题参数；
                // 2、用意图跳转到习题详情页面，带过去压制的参数；
                // 3、习题详情页面接收这个参数，显示到标题栏
                //Toast.makeText(context,"即将打开"+lists.get(position).getTitle()+"的习题内容页面",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context, ExercisesDetailActivity.class);
                intent.putExtra("id",lists.get(position).getId());
                intent.putExtra("title",lists.get(position).getTitle());
                context.startActivity(intent);//如果对方的页面不存在，则生成新的页面，会执行oncreate方法

            }
        });

        return convertView;
    }

    class ViewHolder{
        public TextView tv_order,tv_title,tv_content;
    }
}