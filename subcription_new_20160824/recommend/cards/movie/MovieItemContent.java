package com.meizu.flyme.calendar.subcription_new.recommend.cards.movie;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.ItemContent;
import com.meizu.flyme.calendar.subcription_new.recommend.basecard.MultiItemTypeSupport;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by huangzhihao on 16-8-8.
 */
public class MovieItemContent implements ItemContent, MultiItemTypeSupport<Movie>{

    List<Movie> movies;
    MovieItemAdapter adapter;


    public MovieItemContent(List<Movie> movies) {

        if (movies == null) {
            movies = createData();
            adapter = new MovieItemAdapter(movies);
        }

        this.movies = movies;
    }

    private List<Movie> createData() {

        List<Movie> movies = new ArrayList<>();


        Movie movie1 = new Movie();

        movie1.setName("24小时");
        movie1.setActionUrl("http://www.meizu.com");
        movie1.setCounts(10000);
        movie1.setImgUrl("http://bbsimage.meizu.com/forum/201608/08/094953k7jw777gkok0ybz7.jpg");
        movie1.setReleaseTime("9.8上映");
        movie1.setDescription("很好看啊");
        movie1.setType(1);

        Movie movie2 = new Movie();

        movie2.setName("24小时");
        movie2.setActionUrl("http://www.meizu.com");
        movie2.setCounts(10000);
        movie2.setImgUrl("http://bbsimage.meizu.com/forum/201608/08/094953k7jw777gkok0ybz7.jpg");
        movie2.setReleaseTime("9.8上映");
        movie2.setDescription("很好看啊");
        movie2.setType(2);

        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie2);
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie1);

        return movies;
    }

    @Override
    public int getLayoutId(int itemType) {

        if (itemType ==1) {
            return  R.layout.subscribe_new_movie_item;
        } else {
            return R.layout.subscribe_new_movie_item2;
        }
    }

    @Override
    public int getItemViewType(int position, Movie movie) {
        return movie.getType();
    }
}
