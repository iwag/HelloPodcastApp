package io.github.iwag.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.github.iwag.newsapp.dummy.NewsContent;

public class NewsActivity extends AppCompatActivity implements NewsFragment.OnListFragmentInteractionListener {
    public static final int RESULT_NEW_NEWS_REQUEST = 0;

    NewsFragment newsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


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
        startActivityForResult(intent, RESULT_NEW_NEWS_REQUEST);

//        NewNewsFragment fragment = new NewNewsFragment();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_right);
//        transaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    public void doLoad(View view) {
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
        if (requestCode == RESULT_NEW_NEWS_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                NewsContent.NewsItem item = new NewsContent.NewsItem(bundle.getString(NewNewsFragment.DATA_USER),
                        bundle.getString(NewNewsFragment.DATA_BODY), "", bundle.getString(NewNewsFragment.DATA_DATE),
                        bundle.getString(NewNewsFragment.DATA_ICON_URL), bundle.getString(NewNewsFragment.DATA_IMAGE_URL1), bundle.getString(NewNewsFragment.DATA_IMAGE_URL2),
                        bundle.getInt(NewNewsFragment.DATA_LIKES), bundle.getInt(NewNewsFragment.DATA_COMMENTS));

                newsFragment.addNews(item);
            }
        }
    }
}
