package pub.war3.lockmyphone.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.war3.lockmyphone.R;

/**
 * 主界面
 */
public class ControllerActivity extends AppCompatActivity {

    @BindView(R.id.btn_open_camera)
    Button btnOpenCamera;
    @BindView(R.id.tv_key)
    TextView tvKey;
    @BindView(R.id.et_key)
    EditText etKey;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_connect)
    Button btnConnect;
    @BindView(R.id.btn_set_status)
    Button btnSetStatus;
    @BindView(R.id.btn_dismiss_policy)
    Button btnDismissPolicy;
    @BindView(R.id.btn_set_clickNum)
    Button btnSetClickNum;
    @BindView(R.id.btn_open)
    Button btnOpen;
    @BindView(R.id.linear_controller)
    LinearLayout linearController;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        ButterKnife.bind(this);
        mContext = this;
    }

    @OnClick({R.id.btn_connect, R.id.btn_set_status, R.id.btn_dismiss_policy, R.id.btn_set_clickNum, R.id.btn_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_connect:
                break;
            case R.id.btn_set_status:
                break;
            case R.id.btn_dismiss_policy:
                break;
            case R.id.btn_set_clickNum:
                break;
            case R.id.btn_open:
                break;
        }
    }
}
