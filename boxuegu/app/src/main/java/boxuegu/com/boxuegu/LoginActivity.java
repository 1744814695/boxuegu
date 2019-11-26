package boxuegu.com.boxuegu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import boxuegu.com.boxuegu.activity.FindPswActivity;
import boxuegu.com.boxuegu.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {

    private TextView tv_back,tv_main_title,tv_register,tv_find_psw; //返回按钮
    private Button btn_login; //登陆按钮
    private EditText et_user_name,et_psw; //用户名、密码的控件

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //当注册页面回跳回来自动执行下面代码
        //将携带回来的数据显示到登录的文本框
        if (resultCode==999){//这里检查从哪个地方跳回来的
            if (data!=null){
                et_user_name.setText(data.getStringExtra("userName"));
                et_psw.setText(data.getStringExtra("psw"));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();//系统完成返回栈的相关工作
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=et_user_name.getText().toString();
                String psw=et_psw.getText().toString();
                if (userName.equals("")){//判断用户名为空
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_LONG).show();
                    return;
                }
                if (psw.equals("")){//判断密码为空
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_LONG).show();
                    return;
                }
                //接下来判断用户名和密码是否正确
                String md5psw=MD5Utils.md5(psw);
                String spPsw;
                SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
                spPsw=sharedPreferences.getString(userName,"");

                if (spPsw==null || spPsw.length()==0){
                    Toast.makeText(LoginActivity.this,"该用户不存在",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!spPsw.equals(md5psw)){
                    Toast.makeText(LoginActivity.this,"输入的密码不正确",Toast.LENGTH_LONG).show();
                    return;
                }
                //登陆成功
                //提示，保存登陆状态到sp文件中
                Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("isLogin",true);
                editor.putString("loginUserName",userName);
                editor.commit();

                //回跳到主界面，带过去用户名（此处为第三章难点）
                Intent data=new Intent();
                data.putExtra("isLogin",true);
                setResult(111,data);
                LoginActivity.this.finish();

            }
        });

        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到找回密码界面
                Intent intent=new Intent(LoginActivity.this,FindPswActivity.class);
                startActivity(intent);
            }
        });


        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册界面
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,123);

            }
        });
    }

    private  void  init(){
        tv_back=findViewById(R.id.tv_back);
        btn_login=findViewById(R.id.btn_login);
        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("登陆");
        tv_register=findViewById(R.id.tv_register);
        tv_find_psw=findViewById(R.id.tv_find_psw);
    }
}
