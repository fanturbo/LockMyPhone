package pub.war3.lockmyphone.single;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.war3.lockmyphone.R;
import pub.war3.lockmyphone.util.AppManager;
import pub.war3.lockmyphone.util.DialogHelp;
import pub.war3.lockmyphone.util.SPUtils;
import pub.war3.lockmyphone.util.SettingsCompat;
import pub.war3.lockmyphone.util.ViewManager;

import static android.text.InputType.TYPE_CLASS_NUMBER;

/**
 * 主界面
 */
public class SingleActivity extends AppCompatActivity {

    @BindView(R.id.btn_open)
    Button btnOpen;
    @BindView(R.id.btn_auto_open)
    Button btnAutoOpen;
    @BindView(R.id.btn_set_status)
    Button btnSetStatus;
    @BindView(R.id.btn_dismiss_policy)
    Button btnDismissPolicy;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.btn_show_floatball)
    Button btnShowFloatball;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        mContext = this;
        boolean appOps = SettingsCompat.canDrawOverlays(mContext);
        if (!appOps) {
            //打开悬浮窗权限
            DialogHelp.getConfirmDialog(mContext, "设置悬浮窗权限", "退出app", "前往设置", "亲，需要给予悬浮窗权限哦！", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SettingsCompat.manageDrawOverlays(mContext);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            }).show();
        }
    }


    @OnClick(R.id.btn_open)
    public void btn_open() {
        ViewManager manager = ViewManager.getInstance(getApplicationContext());
        manager.showEmpty();
        if ("0".equals(SPUtils.getAutoStatus(mContext))) {
            finish();
        }
    }

    @OnClick(R.id.btn_auto_open)
    public void btn_auto_open() {
        String[] array = {"是", "否"};
        DialogHelp.getSingleChoiceDialog(mContext, "自动打开并且回到桌面", array, Integer.parseInt(SPUtils.getAutoStatus(mContext)), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SPUtils.setAutoStatus(mContext, (i) + "");
            }
        }).show();
    }

    @OnClick(R.id.btn_dismiss_policy)
    public void btn_dismiss_policy() {
        int index = 2;
        String clickNum = SPUtils.getClickNum(mContext);
        if ("10".equals(clickNum)) {
            index = 0;
        } else if ("50".equals(clickNum)) {
            index = 1;
        }
        String[] array = {"点击屏幕10次", "点击屏幕50次", index == 2 ? "自定义次数--" + clickNum + "次" : "自定义次数"};
        DialogHelp.getSingleChoiceDialog(mContext, "设置空白界面消失规则", array, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("======", "i = " + i);
                SPUtils.setClickNum(mContext, i == 0 ? "10" : "50");
            }
        }).show();
    }

    @OnClick(R.id.btn_set_clickNum)
    public void btn_set_clickNum() {
        final EditText editText = new EditText(mContext);
        editText.setInputType(TYPE_CLASS_NUMBER);
        DialogHelp.getDialog(mContext).setTitle("请输入点击屏幕").setView(editText).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String s = editText.getText().toString();
                if ("".equals(s))
                    s = "10";
                SPUtils.setClickNum(mContext, s);
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    @OnClick(R.id.btn_set_status)
    public void btn_set_status() {
        //ps:下次启动才会生效是指的是app彻底关闭，即杀死app后再重新启动app才会生效，简单的按back键是没用的。。。
        String[] array = {"是", "否"};
        DialogHelp.getSingleChoiceDialog(mContext, "设置是否覆盖状态栏", array, SPUtils.getCoverStatus(mContext) ? 0 : 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(mContext, "下次启动app才会生效", Toast.LENGTH_SHORT).show();
                SPUtils.setCoverStatus(mContext, i == 0 ? true : false);
            }
        }).show();
    }

    @OnClick(R.id.btn_show_floatball)
    public void btn_show_floatball() {
        ViewManager manager = ViewManager.getInstance(getApplicationContext());
        if (manager.isShowing) {
            manager.hideFloatBall();
            btnShowFloatball.setText("显示小球");
        } else {
            manager.showFloatBall();
            btnShowFloatball.setText("隐藏小球");
        }
    }
}
