package io.github.iwag.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.github.iwag.newsapp.dummy.NewsContent;

public class NewsActivity extends AppCompatActivity implements NewsFragment.OnListFragmentInteractionListener {
    public static final int RESULT_NEW_NEWS_REQUEST = 0;
    public static final int RESULT_DETAIL_NEWS_REQUEST = 1;
    public static final int RESULT_START_MUSIC = 2;

    private static final String ICON_URL = "https://scontent-sea1-1.cdninstagram.com/t51.2885-19/s320x320/22280759_695713487292785_369321441759330304_n.jpg";
    private static final String IMAGE_URL1 = "https://scontent-sea1-1.cdninstagram.com/t51.2885-15/e35/21148909_120267078631623_6907529347343581184_n.jpg";
    private static final String IMAGE_URL2 = "https://scontent-sea1-1.cdninstagram.com/t51.2885-15/e35/20986799_1992945870918662_4501813870163132416_n.jpg";
    private static final String USER = "gammi";
    private static final String DATE_STRING = "2017-10-10 10:00:00";
    private static final String BODY = "Eating a curry";

    private Long mDownloadId;

    NewsFragment newsFragment;
    private DownloadService mDownloadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mDownloadId = null;
        mDownloadService = new DownloadService();


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            NewsFragment firstFragment = new NewsFragment();
            newsFragment = firstFragment;

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void doAdd(View view) {
        Intent intent = new Intent(this, NewNewsActivity.class);

        Long timestamp = null;
        try {
            timestamp = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse(DATE_STRING).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        intent.putExtra(NewNewsFragment.DATA_USER, USER);
        intent.putExtra(NewNewsFragment.DATA_DATE, timestamp);
        intent.putExtra(NewNewsFragment.DATA_BODY, BODY);
        intent.putExtra(NewNewsFragment.DATA_ICON_URL, ICON_URL);
        intent.putExtra(NewNewsFragment.DATA_IMAGE_URL1, IMAGE_URL1);
        intent.putExtra(NewNewsFragment.DATA_IMAGE_URL2, IMAGE_URL2);
        intent.putExtra(NewNewsFragment.DATA_LIKES, 2);
        intent.putExtra(NewNewsFragment.DATA_COMMENTS, 3);

        // download
        Long id = mDownloadService.downloadFile(this, "http://cache.rebuild.fm/podcast-ep168.mp3");
        mDownloadId = id;

        startActivityForResult(intent, RESULT_NEW_NEWS_REQUEST);
    }

    public void doLoad(View view) {
        Intent intent = new Intent(this, PlayerActivity.class);

        if (mDownloadId != null) {
            Uri uri = mDownloadService.getDownloadUri(this, mDownloadId);
            if (uri == null) return;
            intent.putExtra("url", uri.toString());
            startActivityForResult(intent, RESULT_START_MUSIC);
        }
    }

    public void doLoad2(View view) {
        if (newsFragment!=null) {
            newsFragment.loadNews();
        }
    }


    @Override
    public void onListFragmentInteraction(NewsContent.NewsItem item) {
        onActivityResult(0, 0, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Make sure the request was successful
        if (requestCode == RESULT_NEW_NEWS_REQUEST && resultCode == RESULT_OK) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe
    public void callbackAddMessage(Events.NewsFragmentAddMessage message) {
        newsFragment.addNews(message.getNews());
    }

    @Subscribe
    public void callbackClickMessage(Events.NewsFragmentClickMessage message) {
        Intent intent = new Intent(this, NewNewsActivity.class);

        intent.putExtra(NewNewsFragment.DATA_USER, message.getNews().id);
        intent.putExtra(NewNewsFragment.DATA_DATE, message.getNews().date.getTime());
        intent.putExtra(NewNewsFragment.DATA_BODY, message.getNews().content);
        intent.putExtra(NewNewsFragment.DATA_ICON_URL, message.getNews().iconUrl);
        intent.putExtra(NewNewsFragment.DATA_IMAGE_URL1, message.getNews().imageUrl1);
        intent.putExtra(NewNewsFragment.DATA_IMAGE_URL2, message.getNews().imageUrl2);
        intent.putExtra(NewNewsFragment.DATA_LIKES, message.getNews().likes);
        intent.putExtra(NewNewsFragment.DATA_COMMENTS, message.getNews().comments);
        intent.putExtra(NewNewsFragment.DATA_KIND, "detail");

        startActivityForResult(intent, RESULT_DETAIL_NEWS_REQUEST);
    }

}
