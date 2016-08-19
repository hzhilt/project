package com.meizu.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.meizu.AppList;
import com.meizu.appcenter.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by huangzhihao on 15-4-1.
 */
public class MyWidget extends AppWidgetProvider {

    private static Timer myTimer;
    private static int index = 0;

    //定义我们要发送的事件
    private static final String broadCastString = "com.meizu.appWidgetUpdate";

    public MyWidget() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(broadCastString)) {
            index++;

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_main);
            rv.setTextViewText(R.id.widget_update, Integer.toString(index));
            //将该界面显示到插件中
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, MyWidget.class);
            appWidgetManager.updateAppWidget(componentName, rv);

//            Intent intent1 = new Intent();
//            intent1.setClass(context, MainActivity.class);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent1.putExtra("Item","第"+intent.getIntExtra("Item", -1)+"微博");
//            context.startActivity(intent1);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int N = appWidgetIds.length;
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_main);

        Intent intent1 = new Intent();
        intent1.setClass(context, MyWidgetService.class);
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
        rv.setRemoteAdapter(R.id.AppWidget_list, intent1);

        Intent intent2 = new Intent();
        intent2.setAction(broadCastString);
        PendingIntent pendingIntentTemplate = PendingIntent.getBroadcast(context, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        //拼接PendingIntent
        rv.setPendingIntentTemplate(R.id.AppWidget_list, pendingIntentTemplate);
        appWidgetManager.updateAppWidget(appWidgetIds, rv);

        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.notifyAppWidgetViewDataChanged(appWidgetIds[0], R.id.AppWidget_list);

//        for (int i=0; i< N; i++) {
//            int appWidgetId = appWidgetIds[i];
//            Intent intent = new Intent(context, AppList.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//            rv.setOnClickPendingIntent(R.id.widget_button, pendingIntent);
//            appWidgetManager.updateAppWidget(appWidgetId, rv);
//        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
//        if(appWidgetIds.length > 0){
//            for(int i = 0; i <appWidgetIds.length ; i++){
//
//            }
//        }


    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        MyTask mMyTask = new MyTask(context);
        myTimer = new Timer();
        myTimer.schedule(mMyTask, 1000, 1000);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    class MyTask extends TimerTask {

        private Context mContext = null;
        private Intent intent = null;

        public MyTask(Context context) {

            //新建一个要发送的Intent
            mContext = context;
            intent = new Intent();
            intent.setAction(broadCastString);
        }

        @Override
        public void run() {
            System.out.println("2");
            //发送广播(由onReceive来接收)
            mContext.sendBroadcast(intent);
        }
    }
}
