package boxuegu.com.boxuegu.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import boxuegu.com.boxuegu.bean.CourseBean;
import boxuegu.com.boxuegu.bean.UserBean;
import boxuegu.com.boxuegu.bean.VideoBean;
import boxuegu.com.boxuegu.sqlite.SQLiteHelper;

public class DBUtils {
    private static DBUtils instance = null;
    private static SQLiteHelper helper;
    private static SQLiteDatabase db;
    public DBUtils(Context context){
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }
    public static DBUtils getInstance(Context context){
        if (instance == null){
            instance = new DBUtils(context);
        }
        return instance;
    }
    /**
     * 保存个人资料信息
     */
    public void saveUserInfo(UserBean bean){
        ContentValues cv=new ContentValues();
        cv.put("userName",bean.userName);
        cv.put("nickName",bean.nickName);
        cv.put("sex",bean.sex);
        cv.put("signature",bean.signature);
        db.insert(SQLiteHelper.U_USERINFO,null,cv);
    }
    /**
     * 获取个人资料信息
     */
    public UserBean getUserInfo(String userName){
        String sql="SELECT * FROM "+SQLiteHelper.U_USERINFO+" WHERE userName=?";
        Cursor cursor=db.rawQuery(sql,new String[]{userName});
        UserBean bean = null;
        while (cursor.moveToNext()){
            bean = new UserBean();
            bean.userName=cursor.getString(cursor.getColumnIndex("userName"));
            bean.nickName=cursor.getString(cursor.getColumnIndex("nickName"));
            bean.sex=cursor.getString(cursor.getColumnIndex("sex"));
            bean.signature=cursor.getString(cursor.getColumnIndex("signature"));
        }
        cursor.close();
        return bean;
    }
    /**
     * 修改个人资料
     */
    public void updateUserInfo(String key,String value,String userName){
        ContentValues cv=new ContentValues();
        cv.put(key,value);
        db.update(SQLiteHelper.U_USERINFO,cv,"userName=?",new String[]{userName});
    }

    public void saveVideoPlayList(VideoBean bean,String userName){
        if (hasVideoPlay(bean.chapterId,bean.videoId,userName)){
            boolean isDelete=delVideoPlay(bean.chapterId,bean.videoId,userName);
            if (!isDelete){
                return;
            }
        }
        //保存播放记录
        ContentValues cv=new ContentValues();
        cv.put("userName",userName);
        cv.put("id",bean.id);
        cv.put("videoId",bean.videoId);
        cv.put("videoPath",bean.videoPath);
        cv.put("title",bean.title);
        cv.put("videoTitle",bean.videoTitle);
        db.insert("videoplaylist",null,cv);

    }

    public boolean hasVideoPlay(int chapterId,int videoId,String userName){
        boolean hasVideo=false;
        String sql="SELECT * FROM "+SQLiteHelper.U_VIDEO_PLAY_LIST+" WHERE chapterId=? AND " +
                "videoId=? AND userName=?";
        Cursor cursor=db.rawQuery(sql,new String[]{chapterId+"",videoId+"",userName});
        if (cursor.moveToFirst()){
            hasVideo=true;
        }
        cursor.close();
        return hasVideo;
    }

    public boolean delVideoPlay(int chapterId,int videoId,String userName){
        boolean delSuccess=false;
        String sql="SELECT * FROM "+SQLiteHelper.U_VIDEO_PLAY_LIST+" WHERE chapterId=? AND " +
                "videoId=? AND userName=?";
        Cursor cursor=db.rawQuery(sql,new String[]{chapterId+"",videoId+"",userName});
        if (cursor.moveToFirst()){
            delSuccess=true;
        }
        cursor.close();
        return delSuccess;
    }


    public List<VideoBean> getPlayHistory(String userName) {
        String sql="select * " +
                "from videoplaylist " +
                "where userName=?";
        Cursor cursor=db.rawQuery(sql,new String[]{userName});
        //游标集合指针指向第一条前面
        List<VideoBean> vbl = new ArrayList<VideoBean>();
        while (cursor.moveToNext()) {
            VideoBean bean = new VideoBean();
            bean.id=cursor.getInt(cursor.getColumnIndex("id"));
            bean.videoId=cursor.getInt(cursor.getColumnIndex("videoId"));
            bean.videoPath=cursor.getString(cursor
                    .getColumnIndex("videoPath"));
            bean.title=cursor.getString(cursor.getColumnIndex("title"));
            bean.videoTitle=cursor.getString(cursor
                    .getColumnIndex("videoTitle"));
            vbl.add(bean);
        }
        cursor.close();
        return vbl;
    }
}
