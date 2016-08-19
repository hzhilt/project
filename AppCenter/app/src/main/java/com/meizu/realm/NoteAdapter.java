package com.meizu.realm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by huangzhihao on 16-2-19.
 */
public class NoteAdapter extends RealmBaseAdapter<Note> {

    RealmResults<Note> mRealmResults;

    public NoteAdapter(Context context, RealmResults<Note> realmResults) {
        super(context, realmResults);
        mRealmResults = realmResults;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.content = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Note note = mRealmResults.get(position);
        viewHolder.title.setText(note.getTitle());
        viewHolder.content.setText(note.getContent());
        return convertView;
    }

    public RealmResults<Note> getRealmResults() {
        return mRealmResults;
    }

    private static class ViewHolder {
        TextView title;
        TextView content;

    }

}
