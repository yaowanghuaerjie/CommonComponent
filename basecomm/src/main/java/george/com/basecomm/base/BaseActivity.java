package george.com.basecomm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * =================================================
 * 作    者： George
 * 时    间：2018/2/7 16:18
 * ===================================================
 */


public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面未初始化之前调用初始化窗口
        initWindow();
        if (initArgs(getIntent().getExtras())) {
            int layoutId = getContentLayoutId();
            setContentView(layoutId);
            initBefore();
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    /**
     * 初始化窗口
     */
    protected void initWindow() {
        // supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 在控件初始化之前调用
     */
    protected void initBefore() {
    }

    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 检查相关参数是否有误
     *
     * @param extras 参数bundle
     * @return 正确返回true ，错误返回false
     */
    protected boolean initArgs(Bundle extras) {
        return true;
    }

    /**
     * 获取页面的布局文件LayoutId
     *
     * @return LayoutId
     */
    protected abstract int getContentLayoutId();


    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航返回时，finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null && fragmentList.size() > 0) {
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof BaseFragment) {
                    if (((BaseFragment) fragment).onBackPressed()) {
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
