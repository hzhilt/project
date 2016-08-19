package com.meizu.calendarevent;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meizu.appcenter.R;

import java.util.List;

/**
 * Created by root on 15-3-7.
 */
public class EventListAdapter extends BaseAdapter {

    Context mContext;
    int count;
    List<Event> mList;

    EventListAdapter(Context context,List<Event> list){
        this.mContext = context;
        this.mList = list;
        //initCalendarEventData();
    }

    public void setEvent(List<Event> list){
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.calendar_eventview,null);
            viewHolder.content = (TextView)convertView.findViewById(R.id.eventcontent);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.content.setText(mList.get(position).title+" "+
                mList.get(position).startTime+"~~"+
                mList.get(position).endTime+" "+
                mList.get(position).descripetion+" "+
                mList.get(position).location);
        return convertView;
    }

    class ViewHolder{
        TextView content;
    }
}
