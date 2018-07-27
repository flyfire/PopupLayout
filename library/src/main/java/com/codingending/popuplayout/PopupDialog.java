package com.codingending.popuplayout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 实际使用的的弹出窗口
 * Created by CodingEnding on 2018/7/24.
 */
public class PopupDialog extends Dialog{
    private static final int DEFAULT_CONTENT_LAYOUT= R.layout.layout_dialog_default;
    private int mContentLayoutId=-1;//弹出窗体的内容layout
    private View mContentLayout;//弹出窗体的内容布局
    private int mGravity=Gravity.BOTTOM;//窗体的弹出位置
    private boolean mUseRadius =true;//是否使用圆角效果

    public PopupDialog(@NonNull Context context) {
        super(context);
    }

    public PopupDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PopupDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private int getContentLayoutId(){
        if(mContentLayoutId<=0){
            return DEFAULT_CONTENT_LAYOUT;
        }
        return mContentLayoutId;
    }

    //设置内容布局的layout
    protected void setContentLayout(@LayoutRes int contentLayoutId){
        mContentLayoutId=contentLayoutId;
    }

    //设置内容布局的layout
    protected void setContentLayout(View contentLayout){
        this.mContentLayout=contentLayout;
    }

    //设置窗体的弹出位置
    protected void setWindowGravity(int gravity){
        this.mGravity=gravity;
    }

    //是否使用圆角特性
    protected void setUseRadius(boolean useRadius){
        this.mUseRadius=useRadius;
    }


    /**
     * 生成窗体配置
     */
    private void configWindow(){
        Window window=getWindow();
        if(window!=null){
            WindowManager.LayoutParams params=window.getAttributes();
            configWindowBackground(window);//配置窗体背景
            params.gravity= mGravity;
            //设置窗体大小
            if(mGravity==Gravity.LEFT||mGravity==Gravity.RIGHT){
                params.width= WindowManager.LayoutParams.WRAP_CONTENT;
                params.height=WindowManager.LayoutParams.MATCH_PARENT;
            }else if(mGravity==Gravity.TOP||mGravity==Gravity.BOTTOM){
                params.width= WindowManager.LayoutParams.MATCH_PARENT;
                params.height=WindowManager.LayoutParams.WRAP_CONTENT;
            }else{
                params.width= WindowManager.LayoutParams.WRAP_CONTENT;
                params.height=WindowManager.LayoutParams.WRAP_CONTENT;
            }
            window.setAttributes(params);
            configWindowAnimations(window);//配置动画效果
        }
    }

    //配置动画效果
    private void configWindowAnimations(Window window){
        switch(mGravity){
            case Gravity.LEFT:
                window.setWindowAnimations(R.style.LeftDialogAnimation);
                break;
            case Gravity.RIGHT:
                window.setWindowAnimations(R.style.RightDialogAnimation);
                break;
            case Gravity.CENTER:
                //从中心弹出使用默认动画，不作额外处理
                break;
            case Gravity.TOP:
                window.setWindowAnimations(R.style.TopDialogAnimation);
                break;
            case Gravity.BOTTOM:
            default:
                window.setWindowAnimations(R.style.BottomDialogAnimation);
                break;
        }
    }

    //配置窗体背景
    private void configWindowBackground(Window window){
        if(!mUseRadius){
            window.setBackgroundDrawableResource(R.drawable.background_normal);
            return;
        }
        switch(mGravity){
            case Gravity.LEFT:
                window.setBackgroundDrawableResource(R.drawable.background_left);
                break;
            case Gravity.RIGHT:
                window.setBackgroundDrawableResource(R.drawable.background_right);
                break;
            case Gravity.CENTER:
                window.setBackgroundDrawableResource(R.drawable.background_center);
                break;
            case Gravity.TOP:
                window.setBackgroundDrawableResource(R.drawable.background_top);
                break;
            case Gravity.BOTTOM:
            default:
                window.setBackgroundDrawableResource(R.drawable.background_bottom);
                break;
        }
    }

    //配置主内容布局
    private void configContentView(){
        if(mContentLayout!=null){//优先使用View作为内容布局
            setContentView(mContentLayout);
        }else{
            setContentView(getContentLayoutId());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configContentView();
        configWindow();
    }
}
