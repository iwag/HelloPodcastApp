package io.github.iwag.newsapp.channellist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.github.iwag.newsapp.R;
import io.github.iwag.newsapp.mainlist.ChannelActivity;
import io.github.iwag.newsapp.mainlist.NewChannelFragment;
import io.github.iwag.newsapp.models.PodcastChannel;

public class ChannelListActivity extends AppCompatActivity implements ChannelListFragment.OnListFragmentInteractionListener, NewChannelFragment.NewChannelInteraction{
    public static final int RESULT_NEW_PODCAST = 0;

    public static final String RESULT_NEW_NEWS = "aaa";
    public static final String RESULT_EMPTY = "empty";


    private final FireChannelRepository channelRepository;
    private FloatingActionButton mPlusButton;

    public ChannelListActivity() {
        channelRepository = new FireChannelRepository();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        mPlusButton = findViewById(R.id.floatingActionButton);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container3) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            ChannelListFragment firstFragment = ChannelListFragment.newInstance(1);

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments

            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container3, firstFragment).commit();
        }

    }


    public void doAdd(View view) {
        Fragment fragment = new NewChannelFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container3, fragment).addToBackStack(null).commit();
        mPlusButton.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onListFragmentInteraction(PodcastChannel item) {
        Intent intent = new Intent(this, ChannelActivity.class);
        intent.putExtra("channel", item);
        startActivity(intent);
    }

    @Override
    public void saveNewChannel(String title, String url) {
        channelRepository.add(title, url);
        getSupportFragmentManager().popBackStackImmediate();
        mPlusButton.setVisibility(View.VISIBLE);

    }
}
