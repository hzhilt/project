package com.meizu.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.meizu.appcenter.R;

public class HandlerActivity extends Activity {
    private Button btnTest;
    private TextView textView;
    private Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        btnTest = (Button)this.findViewById(R.id.btn_01);
        textView = (TextView)this.findViewById(R.id.view_01);
        //启动线程
        new MyThread().start();
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //这里handler的实例化在线程中
                //线程启动的时候就已经实例化了
                Message msg = handler.obtainMessage(1,1,1,"主线程发送的消息");
                handler.sendMessage(msg);
            }
        });
    }
    class MyHandler extends Handler{
        public MyHandler(Looper looper){
            super(looper);
        }
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            textView.setText("我是主线程的Handler，收到了消息："+(String)msg.obj);
        }
    }
    class MyThread extends Thread{
        public void run(){
            Looper.prepare(); //创建该线程的Looper对象，用于接收消息
            //注意了：这里的handler是定义在主线程中的哦，呵呵，
            //前面看到直接使用了handler对象，是不是在找，在什么地方实例化的呢？
            //现在看到了吧？？？呵呵，开始的时候实例化不了，因为该线程的Looper对象
            //还不存在呢。现在可以实例化了
            //这里Looper.myLooper()获得的就是该线程的Looper对象了
            handler = new ThreadHandler(Looper.myLooper());
            //这个方法，有疑惑吗？
            //其实就是一个循环，循环从MessageQueue中取消息。
            //不经常去看看，你怎么知道你有新消息呢？？？
            Looper.loop();
        }
        //定义线程类中的消息处理类
        class ThreadHandler extends Handler{
            public ThreadHandler(Looper looper){
                super(looper);
            }
            public void handleMessage(Message msg){
                //这里对该线程中的MessageQueue中的Message进行处理
                //这里我们再返回给主线程一个消息
                handler = new MyHandler(Looper.getMainLooper());
                Message msg2 = handler.obtainMessage(1,1,1,"子线程收到:"+(String)msg.obj);
                handler.sendMessage(msg2);
            }
        }
    }
}
