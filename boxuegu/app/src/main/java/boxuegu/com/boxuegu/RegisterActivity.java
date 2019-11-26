package boxuegu.com.boxuegu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static boxuegu.com.boxuegu.utils.MD5Utils.md5;

public class RegisterActivity extends AppCompatActivity {

    private TextView tv_back; //返回按钮
    private Button btn_register; //注册按钮
    private EditText et_user_name,et_psw,et_psw_again; //用户名、密码、再次输入的密码的控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();//系统完成返回栈的相关工作
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断文本框是否为空
                //判断用户名是否已经存在
                //两次密码是否相同
                String userName,psw,pswAgain;
                userName=et_user_name.getText().toString();
                psw=et_psw.getText().toString();
                pswAgain=et_psw_again.getText().toString();
                if (userName.equals("")){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_LONG).show();
                    return;
                }
                //用户名和密码都保存sp文件中，书上用户名为键，密码为值。思路读取sp文件中对应的键，是否存在
                SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
                String pswd=sharedPreferences.getString(userName,"");


                if (pswd!=null && pswd.length()!=0){
                    //判断用户名已经存在:sp文件中已经存在这一个用户名，同时该用户名对应的密码不为空
                    Toast.makeText(RegisterActivity.this,"该用户已经存在，请重新输入用户名",Toast.LENGTH_LONG).show();
                    return;
                }
                if (psw.equals("")){
                    //判断密码是否为空
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_LONG).show();
                    return;
                }
                if (pswAgain.equals("")){
                    //判断再次密码是否为空
                    Toast.makeText(RegisterActivity.this,"请再次输入密码",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!psw.equals(pswAgain)){
                    //判断密码是否相同
                    Toast.makeText(RegisterActivity.this,"两次密码不相同",Toast.LENGTH_LONG).show();
                    return;
                }
                //注册成功
                //1)用户名和密码密文保存到sp
                //2)提示
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(userName,md5(psw));
                editor.commit();
                //回跳到登陆界面，并带回用户名
                Intent data=new Intent();
                data.putExtra("userName",userName);
                data.putExtra("psw",psw);
                setResult(999,data);
                RegisterActivity.this.finish();
            }
        });
    }

    private  void  init(){
        tv_back=findViewById(R.id.tv_back);
        btn_register=findViewById(R.id.btn_register);
        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);
        et_psw_again=findViewById(R.id.et_psw_again);
    }
}
