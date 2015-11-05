package emoto.winddy.cn.emotodemo.fragments.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;

import emoto.winddy.cn.emotodemo.R;
import emoto.winddy.cn.emotodemo.TabActivity;
import emoto.winddy.cn.emotodemo.global.Aging_test;
import emoto.winddy.cn.emotodemo.util.ONHttpCallBack;
import emoto.winddy.cn.emotodemo.util.net_util;


/**
 * Created by john on 15-9-9.
 */
public class login_activity extends Activity  implements ONHttpCallBack,Serializable{
    private EditText loginName,passwd;
    private  String lname, pd;
    private EditText unitnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkForUser()) {
            Intent intent=new Intent(login_activity.this,TabActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.login_activity);
        loginName = (EditText) findViewById(R.id.edit_text_usrname);
        passwd = (EditText) findViewById(R.id.edit_text_passwd);
        TextView reg_label = (TextView) findViewById(R.id.reg_label);
        reg_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(login_activity.this, register_activity.class);
                startActivity(intent);

            }
        });
        Button loginbtn = (Button) findViewById(R.id.login_btn);
           loginbtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   login();
               }
           });
    }

    boolean checkForUser() {
        Aging_test aging_test = Aging_test.getAgingTest(this);
        if(aging_test.getToken() == null) {
            return false;
        }
        return true;
    }

    void login() {
        lname = loginName.getText().toString();
        pd = passwd.getText().toString();
        if (lname.equals("") && pd.equals("")) {
            lname = "admin";
            pd = "123";
        }
        JsonObject object = new JsonObject();
        object.addProperty("action", "ChkUsr");
        object.addProperty("user", lname);
        object.addProperty("pw", pd);
        net_util.postForLogin(object, new ONHttpCallBack() {
            @Override
            public void onHttpCallBack(JsonObject jsonObject) {
                if (jsonObject == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login_activity.this, "登录失败，没有返回值！", Toast.LENGTH_LONG).show();

                        }
                    });
                } else {
                    String rltStr = jsonObject.get("state").getAsString();
                    if (rltStr.equals("success".toUpperCase())) {
//                        jsonObject.get("token").getAsString()
                        Aging_test.getAgingTest(login_activity.this).loginSucceed("qwertyuiop0987654321", lname, login_activity.this);
                        Intent intent = new Intent(login_activity.this, TabActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(login_activity.this, "登录失败！！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);

    }

    @Override
    public void onHttpCallBack(JsonObject jsonObject) {

    }
}
