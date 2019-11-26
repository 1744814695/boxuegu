package boxuegu.com.boxuegu.utils;

import android.util.Xml;

import com.alibaba.fastjson.JSONObject;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import boxuegu.com.boxuegu.bean.ExercisesDetailBean;
import boxuegu.com.boxuegu.bean.VideoBean;

public class AnalysisUtils {

    public static List<VideoBean> getVideoListByJsonFile(InputStream inputStream) throws Exception{
        List<VideoBean> beans=null;
        StringBuffer jsonStr=new StringBuffer();
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        String line;
        line=reader.readLine();
        while (line!=null){
            jsonStr.append(line);
            jsonStr.append("\n");
            line=reader.readLine();
        }
        beans= JSONObject.parseArray(jsonStr.toString(),VideoBean.class);
        reader.close();
        inputStream.close();
        return beans;
    }
    //下面方法解析xml文件,返回值是一个集合，集合中元素是一个选择题对象
    public static ArrayList<ExercisesDetailBean> getExercisesInfos(InputStream inputStream) throws Exception{
        ArrayList<ExercisesDetailBean> beans=null;
        ExercisesDetailBean bean=null;
        //1、生成解析器对象
        XmlPullParser parser= Xml.newPullParser();
        //2、设置解析器对象的解析对象
        parser.setInput(inputStream,"utf-8");
        int type=parser.getEventType();//读取当前部分的类型
        while (type!=XmlPullParser.END_DOCUMENT){
            //接下来解析当前的语义部分
            if(type==XmlPullParser.START_TAG){
                if("infos".equals(parser.getName())){//文档解析开始
                    beans=new ArrayList<ExercisesDetailBean>();
                }else if("exercises".equals(parser.getName())){//当解析部分为exercises
                    //生成一个选择题对象,开始解析一个选择题
                    bean=new ExercisesDetailBean();
                    String id=parser.getAttributeValue(0);
                    bean.id=Integer.valueOf(id);
                }else if("subject".equals(parser.getName())){
                    bean.subject=parser.nextText();//两个作用：1、跳到下一个语义部分；2、读取当前内容
                }else if("a".equals(parser.getName())){
                    bean.a=parser.nextText();//两个作用：1、跳到下一个语义部分；2、读取当前内容
                }else if("b".equals(parser.getName())){
                    bean.b=parser.nextText();//两个作用：1、跳到下一个语义部分；2、读取当前内容
                }else if("c".equals(parser.getName())){
                    bean.c=parser.nextText();//两个作用：1、跳到下一个语义部分；2、读取当前内容
                }else if("d".equals(parser.getName())){
                    bean.d=parser.nextText();//两个作用：1、跳到下一个语义部分；2、读取当前内容
                }else if("answer".equals(parser.getName())){
                    bean.answer=Integer.valueOf(parser.nextText());//两个作用：1、跳到下一个语义部分；2、读取当前内容
                }
            }else if(type==XmlPullParser.END_TAG) {
                if("exercises".equals(parser.getName())){//表示一个选择题解析完毕
                    bean.selected=0;
                    beans.add(bean);
                    bean=null;
                }
            }
            type=parser.next();//1、先转向下一个语义部分,2、读取新的语义部分的类型
        }
        return beans;
    }
}