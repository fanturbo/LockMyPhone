package pub.war3.lockmyphone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_open_single)
    Button btnOpenSingle;
    @BindView(R.id.btn_open_controller)
    Button btnOpenController;
    @BindView(R.id.btn_setting)
    Button btnSetting;
    @BindView(R.id.linear_main)
    LinearLayout linearMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_open_single, R.id.btn_open_controller, R.id.btn_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open_single:
                break;
            case R.id.btn_open_controller:
                break;
            case R.id.btn_setting:
                linearMain.setVisibility(View.GONE);
                break;
        }
    }
}
