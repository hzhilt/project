package com.meizu.flyme.calendar.subcription_new.recommend.cards.show;

import com.meizu.flyme.calendar.subcription_new.recommend.basecard.ItemContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhihao on 16-8-10.
 */
public class ShowItemContent implements ItemContent {

    List<Show> mShows;
    ShowItemAdapter adapter;

    public ShowItemContent(List<Show> shows) {

        if (shows == null) {
            shows = createData();
            adapter = new ShowItemAdapter(shows);
        }
        this.mShows = shows;
    }

    private List<Show> createData() {
        List<Show> shows = new ArrayList<>();


        Show show = new Show();

        show.setName("24小时");
        show.setActionUrl("http://www.meizu.com");
        show.setCounts(10000);
        show.setImgUrl("http://bbsimage.meizu.com/forum/201608/08/094953k7jw777gkok0ybz7.jpg");
        //show.setReleaseTime("9.8上映");
        show.setDescription("很好看啊");

        for (int i = 0;  i <= 5; i++) {
            shows.add(show);
        }

        return shows;
    }

}
