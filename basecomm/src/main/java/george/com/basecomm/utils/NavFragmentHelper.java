package george.com.basecomm.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

/**
 * =================================================
 * 作    者： George
 * 时    间：2018/2/7 17:45
 * ===================================================
 */


public class NavFragmentHelper<T> {
    //所有的tab集合
    private final SparseArray<Tab<T>> tabs = new SparseArray<>();
    //用于初始化的必须参数
    private final Context context;
    private final int containerId;
    private final FragmentManager fragmentManager;
    private final OnTabChangeListener<T> listener;
    //返回当前选中的Tab
    private Tab<T> currentTab;

    public NavFragmentHelper(Context context,
                             int containerId,
                             FragmentManager fragmentManager,
                             OnTabChangeListener<T> listener) {
        this.context = context;
        this.containerId = containerId;
        this.fragmentManager = fragmentManager;
        this.listener = listener;
    }

    /**
     * 添加Tab
     *
     * @param menuId Tab对应的菜单Id
     * @param tab    Tab
     * @return NavFragmentHelper
     */
    public NavFragmentHelper add(int menuId, Tab<T> tab) {
        tabs.put(menuId, tab);
        return this;
    }

    /**
     * 获取当前的Tab
     *
     * @return
     */
    public Tab<T> getCurrentTab() {
        return currentTab;
    }

    /**
     * 执行菜单选择操作
     *
     * @param menuId 菜单的Id
     * @return 是否能够处理这个点击
     */
    public boolean performClickMenu(int menuId) {
        //集合中寻找点击的菜单对应的Tab
        //如果有则进行处理
        Tab<T> tab = tabs.get(menuId);
        if (tab != null) {
            doSelect(tab);
            return true;
        }
        return false;
    }

    /**
     * 进行真实tab点击操作
     *
     * @param tab tab
     */
    private void doSelect(Tab<T> tab) {
        Tab<T> oldTab = null;
        if (currentTab != null) {
            oldTab = currentTab;
            if (oldTab == tab) {
                //如果当前的tab就是点击的tab
                //不做任何处理
                notifyTabReselect(tab);
                return;
            }
        }
        currentTab = tab;
        doTabChanged(currentTab, oldTab);
    }


    /**
     * 进行Fragment的真实调度操作
     *
     * @param newTab 新的fragment
     * @param oldTab 旧的fragment
     */
    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (oldTab != null) {
            if (oldTab.fragment != null) {
                ft.detach(oldTab.fragment);
            }
        }
        if (newTab != null) {
            if (newTab.fragment == null) {
                //首次新建
                Fragment fragment = Fragment.instantiate(context, newTab.clx.getName(), null);
                //缓存
                newTab.fragment = fragment;
                ft.add(containerId, fragment, newTab.clx.getName());
            } else {
                ft.attach(newTab.fragment);
            }
        }
        //提交事物
        ft.commit();
        //通知回调
        notifyTabReselect(newTab, oldTab);
    }

    private void notifyTabReselect(Tab<T> newTab, Tab<T> oldTab) {
        if (listener != null) {
            listener.onTabChanged(newTab, oldTab);
        }
    }

    private void notifyTabReselect(Tab<T> tab) {
        //TODO 点击二次要做的操作

    }

    public static class Tab<T> {
        public Tab(Class<?> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }

        //fragment对应的class
        public Class<?> clx;
        //额外的字段，用户需要自己设定
        public T extra;
        //内部缓存的Fragment权限
        Fragment fragment;
    }

    public interface OnTabChangeListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }
}
