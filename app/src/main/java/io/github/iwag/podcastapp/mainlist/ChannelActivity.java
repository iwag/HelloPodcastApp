package io.github.iwag.podcastapp.mainlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.github.iwag.podcastapp.BrowserActivity;
import io.github.iwag.podcastapp.R;
import io.github.iwag.podcastapp.channellist.ChannelListFragment;
import io.github.iwag.podcastapp.models.Channel;
import io.github.iwag.podcastapp.models.FeedItem;
import io.github.iwag.podcastapp.models.PodcastChannel;
import io.github.iwag.podcastapp.player.PlayerActivity;
import io.github.iwag.podcastapp.service.DownloadService;

public class ChannelActivity extends AppCompatActivity implements ChannelFragment.OnListFragmentInteractionListener {
    public static final int RESULT_START_MUSIC = 2;

    private static final String ICON_URL = "https://scontent-sea1-1.cdninstagram.com/t51.2885-19/s320x320/22280759_695713487292785_369321441759330304_n.jpg";
    private static final String IMAGE_URL1 = "https://scontent-sea1-1.cdninstagram.com/t51.2885-15/e35/21148909_120267078631623_6907529347343581184_n.jpg";
    private static final String IMAGE_URL2 = "https://scontent-sea1-1.cdninstagram.com/t51.2885-15/e35/20986799_1992945870918662_4501813870163132416_n.jpg";
    private static final String USER = "gammi";
    private static final String DATE_STRING = "2017-10-10 10:00:00";
    private static final String BODY = "Eating a curry";

    private Long mDownloadId;

    private DownloadService mDownloadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

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

            PodcastChannel channel = (PodcastChannel) getIntent().getSerializableExtra("channel");

            // Create a new Fragment to be placed in the activity layout
            ChannelFragment firstFragment = ChannelFragment.newInstance(1, channel.url);

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void doLoad(View view) {
        ChannelListFragment firstFragment = ChannelListFragment.newInstance(1);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, firstFragment).commit();
    }

    public void play(FeedItem item, String imageUrl) {

        // try download
        if (mDownloadId == null) {
            Long id = mDownloadService.downloadFile(this, item.title, item.enclosure.url);
            mDownloadId = id;
        }
        Intent intent = new Intent(this, PlayerActivity.class);
        if (mDownloadId != null) {
            Uri uri = mDownloadService.getDownloadUri(this, mDownloadId);
            if (uri == null) return;
            intent.putExtra("image_url", imageUrl);
            intent.putExtra("url", uri.toString());
            mDownloadId = null;
            startActivityForResult(intent, RESULT_START_MUSIC);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onListFragmentInteraction(FeedItem item, Channel channel) {
       // play(item, channel.itunesImage.getHref());

        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("url", item.link);
        startActivity(intent);
    }
}
