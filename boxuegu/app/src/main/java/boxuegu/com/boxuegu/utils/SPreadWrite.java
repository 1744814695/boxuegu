package boxuegu.com.boxuegu.utils;

import android.app.Activity;
import android.content.SharedPreferences;

public class SPreadWrite {
    public static void clearLoginStatus(Activity context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("loginUserName","");
        editor.commit();
    }

    public static boolean readLoginStatus(Activity context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo", context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLogin",false);
    }

    public static String readLoginName(Activity context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo", context.MODE_PRIVATE);
        return sharedPreferences.getString("loginUserName","");
    }

    public static void saveLoginStatus(Activity context,String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin",true);
        editor.putString("loginUserName",name);
        editor.commit();
    }
}
