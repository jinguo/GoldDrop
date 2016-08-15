package com.janggwa.zkw.golddrop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zkw on 16/8/10.
 */

public class GoldAnimation extends Activity {

    // 掉落动画的主体动画
    private FlakeView flakeView;
    private Button btnAll, btnthree;
    private TextView text_view;
    private MediaPlayer player;
    public boolean isFirstStart = true;
//    private MyApplication application;
    private final Timer timer = new Timer();
    private TimerTask task;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showPopWindows(text_view, "", false);
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gold_animation);
//        application = (MyApplication) getApplication();
        flakeView = new FlakeView(this);
        text_view = (TextView) findViewById(R.id.text_view);
    }

    @Override
    protected void onResume() {
        flakeView.resume();
        super.onResume();
    }

    @Override
    public void onAttachedToWindow() {
        task = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(task, 1000);
        super.onAttachedToWindow();
    }


    @Override
    protected void onPause() {
        flakeView.pause();
        super.onPause();

    }

    private PopupWindow pop;

    private PopupWindow showPopWindows(View v, String moneyStr, boolean show) {
        View view = LayoutInflater.from(this).inflate(
                R.layout.view_login_reward, null, false);
        // this.getLayoutInflater().inflate(R.layout.view_login_reward, null);
        TextView tvTips = (TextView) view.findViewById(R.id.tv_tip);
        TextView money = (TextView) view.findViewById(R.id.tv_money);
        tvTips.setText("打造国内领先的掌上投资理财");
        money.setText(moneyStr);
        final LinearLayout container = (LinearLayout) view
                .findViewById(R.id.container);
        // 将flakeView 添加到布局中
        container.addView(flakeView);
        // 设置背景
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        // 设置同时出现在屏幕上的数量 建议64以内 过多会引起卡顿
        flakeView.addFlakes(38);
        /**
         * 绘制的类型
         *
         * @see View.LAYER_TYPE_HARDWARE
         * @see View.LAYER_TYPE_SOFTWARE
         * @see View.LAYER_TYPE_NONE
         */
        flakeView.setLayerType(View.LAYER_TYPE_NONE, null);
        pop = new PopupWindow(view, FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(getResources().getColor(
                R.color.half_color));
        pop.setBackgroundDrawable(dw);
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAtLocation(v, Gravity.CENTER, 0, 0);

        player = MediaPlayer.create(this, R.raw.shake);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * 移除动画
                 */

                player.start();
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                        mp.release();
                        container.removeAllViews();
                        Intent intent = new Intent();
                        intent.setClass(GoldAnimation.this, MainActivity.class);
                        pop.dismiss();
                        startActivity(intent);
                        GoldAnimation.this.finish();
                    }

                });
            }
        },10000);


        return pop;
    }

}

