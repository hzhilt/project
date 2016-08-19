package com.meizu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.meizu.appcenter.MainActivity;
import com.meizu.appcenter.R;
import com.meizu.centerui.SmartBarActivity;
import com.meizu.contentprovider.NoteActivity;
import com.meizu.customview.CustomListActivity;
import com.meizu.floatingwindow.FloatingWindowActivity;
import com.meizu.gridview.GridActivity;
import com.meizu.listviewload.ListViewLoad;
import com.meizu.loadimage.LoadImageActivity;
import com.meizu.paint.PaintActivity;
import com.meizu.photos.PhotoListActivity;
import com.meizu.recycleview.RecycleActivity;
import com.meizu.share.ShareActicity;
import com.meizu.test.EventActivity;
import com.meizu.volleyPicassoTest.VolleyImageActivity;
import com.meizu.webview.WebViewActivty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14-11-8.
 */
public class ListProject extends Activity {

    private ListView mListView= null;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //setTheme(R.style.MyThemeOne);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_project);

        mListView = (ListView)findViewById(R.id.listProject);

        getData();

        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>
                (this,android.R.layout.simple_list_item_1,mList);

        mListView.setAdapter(myArrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:intent = new Intent(ListProject.this, MainActivity.class);
                        break;
                    case 1:intent = new Intent(ListProject.this, GridActivity.class);
                        break;
                    case 2:intent = new Intent(ListProject.this, ListViewLoad.class);
                        break;
                    case 3:intent = new Intent(ListProject.this, WebViewActivty.class);
                        break;
                    case 4:intent = new Intent(ListProject.this, ShareActicity.class);
                        break;
                    case 5:intent = new Intent(ListProject.this, NoteActivity.class);
                        break;
                    case 6:intent = new Intent(ListProject.this, LoadImageActivity.class);
                        break;
                    case 7:intent = new Intent(ListProject.this, VolleyImageActivity.class);
                        break;
                    case 8:intent = new Intent(ListProject.this, PhotoListActivity.class);
                        break;
                    case 9:intent = new Intent(ListProject.this, SmartBarActivity.class);
                        break;
                    case 10:intent = new Intent(ListProject.this, EventActivity.class);
                        break;
                    case 11:intent = new Intent(ListProject.this, CustomListActivity.class);
                        break;
                    case 12:intent = new Intent(ListProject.this, FloatingWindowActivity.class);
                        break;
                    case 13:intent = new Intent(ListProject.this, RecycleActivity.class);
                        break;
                    case 14:intent = new Intent(ListProject.this, PaintActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });

    }

    private void getData() {
        mList.add("AppCenter");
        mList.add("GridView");
        mList.add("ListViewLoading");
        mList.add("WebView");
        mList.add("Share");
        mList.add("Note");
        mList.add("LoadImage");
        mList.add("VolleyImage");
        mList.add("Photos");
        mList.add("UISmartBar");
        mList.add("EventTest");
        mList.add("CustomList");
        mList.add("FloatingWindow");
        mList.add("RecycleView");
        mList.add("PaintActivity");
    }


}
