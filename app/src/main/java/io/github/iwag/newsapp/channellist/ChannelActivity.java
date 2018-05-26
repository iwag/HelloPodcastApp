package io.github.iwag.newsapp.channellist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import io.github.iwag.newsapp.MainActivity;
import io.github.iwag.newsapp.R;
import io.github.iwag.newsapp.mainlist.NewNewsFragment;
import io.github.iwag.newsapp.mainlist.NewsActivity;
import io.github.iwag.newsapp.mainlist.NewsFragment;
import io.github.iwag.newsapp.models.PodcastChannel;

public class ChannelActivity extends Activity implements ChannelFragment.OnListFragmentInteractionListener{

    public static final String RESULT_NEW_NEWS = "aaa";
    public static final String RESULT_EMPTY = "empty";

    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container3) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            ChannelFragment firstFragment = ChannelFragment.newInstance(1);

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments

            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container3, firstFragment).commit();
        }

    }


    @Override
    public void onListFragmentInteraction(PodcastChannel item) {
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("channel", item);
        startActivity(intent);
    }
}
