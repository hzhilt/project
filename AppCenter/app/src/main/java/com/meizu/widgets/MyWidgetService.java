package com.meizu.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.meizu.appcenter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhihao on 15-5-28.
 */
public class MyWidgetService extends RemoteViewsService {

    private static final String BROADCAST_STRING = "com.meizu.appWidgetUpdate";
    @Override
    public void onStart(Intent intent, int startId){
        super.onCreate();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(),intent);
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

        private final Context mContext;
        private final List<String> mList;
        private int[] appWidgetIds ;
        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            mList = new ArrayList<String>();
            mList.add("第一条微博");
            mList.add("第二条微博");
            mList.add("第三条微博");
            mList.add("第四条微博");
            mList.add("第五条微博");
            mList.add("第六条微博");
            mList.add("第七条微博");
            mList.add("第八条微博");

            if(Looper.myLooper() == null){
                Looper.prepare();
            }
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            mList.clear();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if(position<0 || position>=mList.size())
                return null;
            String content = mList.get(position);
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

            Intent intent = new Intent();
            intent.setAction(BROADCAST_STRING);
            intent.putExtra("Item",position+1);

            // 与CustomWidget中remoteViews.setPendingIntentTemplate配对使用，共同作用。
            rv.setTextViewText(R.id.widget_list_item_tv, content);
            rv.setOnClickFillInIntent(R.id.widget_list_item_layout, intent);
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
