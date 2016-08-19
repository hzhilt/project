package com.meizu.md;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.widget.TextView;
import android.widget.Toast;

import com.meizu.appcenter.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by huangzhihao on 15-12-2.
 */
public class CalendarDbActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        setContentView(textView);

        textView.setText("helllllllllo");

        File f = new File("/data/data/com.android.provider.calendar/databases/calendar.db"); //比如  "/data/data/com.hello/databases/test.db"
        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Time time = new Time();
        time.setToNow();
        long millis = time.toMillis(false);

        File o = new File(sdcardPath+"calendar" + millis + ".db" ); //sdcard上的目标地址
        if(f.exists()) {
            FileChannel outF;
            try {
                outF = new FileOutputStream(o).getChannel();
                new FileInputStream(f).getChannel().transferTo(0, f.length(),outF);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(CalendarDbActivity.this, "完成", Toast.LENGTH_SHORT).show();
        }
    }
}
