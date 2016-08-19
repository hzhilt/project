package com.meizu.appcenter;

import android.annotation.TargetApi;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.meizu.share.ShareActicity;
import com.robotium.solo.Solo;

/**
 * Created by huangzhihao on 15-3-31.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class MyActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    @TargetApi(Build.VERSION_CODES.FROYO)
    public MyActivityTest() {
        super("com.meizu.appcenter", MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testUI() throws Exception{
//        boolean expected = true;
//        boolean actual = solo.searchButton("保存") && solo.searchButton("分享");
//        assertEquals("This and/or is are not found", expected, actual);
//
//        int i = 0;
//        while (i<100){
//            solo.clearEditText(0);
//            solo.enterText(0,"hello"+i);
//            solo.clickOnButton("保存");
//            actual = solo.searchText("hello"+i);
//            assertEquals("This and/or is are not found", expected, actual);
//
//            solo.clickOnEditText(0);
//            solo.clearEditText(0);
//            i++;
//        }
        ListView  listView = (ListView) solo.getView(R.id.listView);

        while (true){
            solo.scrollListToTop(listView);
            Thread.sleep(1000);
            solo.scrollListToBottom(listView);
        }




    }
}
