package com.jredu.myprojects.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jredu.myprojects.R;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends Activity {
    public static final int Login = 1001;
    public static final int PhoneLogin = 1002;
    public static final int PhoneLogin1 = 1003;
    Button qq;
    Button sina;
    TextView login;
    TextView phonelogin;
    private Tencent mTencent;
    private String APP_ID = "222222";
    private IUiListener loginListener;
    private String SCOPE = "all";
    private IUiListener userInfoListener;
    Bundle bundle;
    String nickname;
    String path;
    static LoginActivity instance ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);
        instance = this;
        qq = (Button) findViewById(R.id.qq);
        sina = (Button) findViewById(R.id.sina);
        login = (TextView) findViewById(R.id.text2);
        phonelogin = (TextView) findViewById(R.id.text);
        phonelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
                bundle = new Bundle();
                bundle.putString("name", "登录");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
                bundle = new Bundle();
                bundle.putString("name", "注册");
                intent.putExtras(bundle);
               startActivity(intent);
            }
        });
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FirstPageActivity.class);

                bundle = new Bundle();
                bundle.putString("flag", "sina");
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqLogin();
            }
        });

    }

    public void qqLogin() {
        initQqLogin();
        mTencent.login(this, SCOPE, loginListener);

    }

    //初始化QQ登录分享的需要的资源
    private void initQqLogin() {
        mTencent = Tencent.createInstance(APP_ID, this);
        //创建QQ登录回调接口
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //登录成功后调用的方法
                JSONObject jo = (JSONObject) o;
                Log.e("COMPLETE:", jo.toString());
                String openID;
                try {
                    openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                //登录失败后回调该方法
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                Log.e("LoginError:", uiError.toString());
            }

            @Override
            public void onCancel() {
                //取消登录后回调该方法
                Toast.makeText(LoginActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
            }
        };

        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject json = (JSONObject) o;
                // 昵称
                try {
                    nickname = ((JSONObject) o).getString("nickname");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 头像
                try {
                    path = json.getString("figureurl_qq_1");
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }finally {
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
                Tencent.handleResultData(data, loginListener);
                final UserInfo info = new UserInfo(this, mTencent.getQQToken());
                info.getUserInfo(userInfoListener);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this, FirstPageActivity.class);
                        bundle = new Bundle();
                        bundle.putString("name", nickname);
                        bundle.putString("icon", path);
                        bundle.putString("flag", "qq");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }, 2000);
            }

        }

    }

    /*@Override
    protected void onDestroy() {
        if (mTencent != null) {
            //注销登录
            mTencent.logout(LoginActivity.this);
        }
        super.onDestroy();
    }*/
}
