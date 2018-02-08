package george.com.basecomm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * =================================================
 * 作    者： George
 * 时    间：2018/2/7 16:18
 * ===================================================
 */


public abstract class BaseFragment extends Fragment {

    private View mRoot;
    private boolean mIsFirstInitData = false;

    protected Unbinder mUnRootBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            int layoutId = getContentLayoutId();
            View root =  inflater.inflate(layoutId,container,false);
            initWidget(root);
            mRoot=root;
        }else{
            if(mRoot.getParent()!=null){
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }
    /**
     * View 创建成功
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mIsFirstInitData) {
            //触发一次之后就不会再触发了
            mIsFirstInitData = false;
            onFirstInit();
        }
        //View创建完成后初始化数据
        initData();
    }


    /**
     * 初始化相关参数
     *
     * @param arguments bundle
     */
    protected void initArgs(Bundle arguments) {

    }

    /**
     * 当首次初始化数据的时候会调用的方法
     */
    protected void onFirstInit() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化控件
     * @param root
     */
    protected void initWidget(View root) {
        mUnRootBinder= ButterKnife.bind(this,root);
    }
    /**
     * 获取页面布局LayoutId
     * @return layoutId
     */
    protected abstract int getContentLayoutId();

    /**
     * 返回按键盘时触发
     * @return 返回true代表我自己处理逻辑，activity不用自己finish
     *          返回false代表我没有处理逻辑，Activity自己走自己的逻辑
     */
    public boolean onBackPressed(){
        return false;
    }
}
