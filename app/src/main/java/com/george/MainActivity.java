package com.george;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import george.com.basecomm.base.BaseActivity;
import george.com.basecomm.base.BaseFragment;
import george.com.basecomm.utils.NavFragmentHelper;

/**
 * 整体底部切换没有完成  需要引入图片及selector
 */
public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavFragmentHelper.OnTabChangeListener<Integer> {
    @BindView(R.id.lay_container)
    FrameLayout layContainer;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    private BaseFragment fragment;
    private NavFragmentHelper<Integer> mNavHelper;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }



    @SuppressWarnings("unchecked")
    @Override
    protected void initWidget() {
        super.initWidget();
        //初始化底部辅助工具类
        mNavHelper = new NavFragmentHelper<>(this,
                R.id.lay_container,
                getSupportFragmentManager(),
                this);
        mNavHelper.add(R.id.action_home, new NavFragmentHelper.Tab<>(HomeFragment.class, R.string.action_home))
                .add(R.id.action_message, new NavFragmentHelper.Tab<>(MessageFragment.class, R.string.action_message))
                .add(R.id.action_mine, new NavFragmentHelper.Tab<>(MineFragment.class, R.string.action_mine));
        mNavigation.setOnNavigationItemSelectedListener(this);
    }


    @Override
    protected void initData() {
        super.initData();
        //默认选中第一个按钮
        Menu menu = mNavigation.getMenu();
        menu.performIdentifierAction(R.id.action_home, 0);
    }

    @Override
    public void onTabChanged(NavFragmentHelper.Tab newTab, NavFragmentHelper.Tab oldTab) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return mNavHelper.performClickMenu(item.getItemId());
    }
}
