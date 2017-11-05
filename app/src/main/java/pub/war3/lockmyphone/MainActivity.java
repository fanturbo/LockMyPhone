package pub.war3.lockmyphone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.war3.lockmyphone.util.MD5Util;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_open_single)
    Button btnOpenSingle;
    @BindView(R.id.btn_open_controller)
    Button btnOpenController;
    @BindView(R.id.btn_setting)
    Button btnSetting;
    @BindView(R.id.linear_main)
    LinearLayout linearMain;
    @BindView(R.id.btn_produce_key)
    Button btnProduceKey;
    @BindView(R.id.et_key)
    EditText etKey;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_copy)
    Button btnCopy;
    @BindView(R.id.btn_generate_code)
    Button btnGenerateCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_open_single, R.id.btn_open_controller, R.id.btn_setting, R.id.btn_produce_key, R.id.btn_copy, R.id.btn_generate_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open_single:
                break;
            case R.id.btn_open_controller:
                break;
            case R.id.btn_setting:
                linearMain.setVisibility(View.GONE);
                break;
            case R.id.btn_produce_key:
                final AVObject todo = new AVObject("PhoneList");
                todo.put("key", App.getAndroidId());
                todo.put("password", MD5Util.encode(Math.random() + App.getAndroidId()));
                todo.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            etKey.setText(todo.get("key").toString());
                            etPassword.setText(todo.get("password").toString());
                        } else {
                            Toast.makeText(MainActivity.this, "获取key和密码失败，请检查网络稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btn_copy:
                break;
            case R.id.btn_generate_code:
                break;
        }
    }
}
