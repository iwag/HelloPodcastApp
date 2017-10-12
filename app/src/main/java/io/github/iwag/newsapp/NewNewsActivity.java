package io.github.iwag.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewNewsActivity extends AppCompatActivity {

    public static final String RESULT_NEW_NEWS = "aaa";
    private static final String ICON_URL = "https://scontent-sea1-1.cdninstagram.com/t51.2885-19/s320x320/22280759_695713487292785_369321441759330304_n.jpg";
    private static final String IMAGE_URL1 = "https://scontent-sea1-1.cdninstagram.com/t51.2885-15/e35/21148909_120267078631623_6907529347343581184_n.jpg";
    private static final String IMAGE_URL2 = "https://scontent-sea1-1.cdninstagram.com/t51.2885-15/e35/20986799_1992945870918662_4501813870163132416_n.jpg";
    private static final String USER = "gammi";
    private static final String DATE = "Sep 11, 2017";
    private static final String BODY = "Eating a curry";

    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_news);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container2) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            NewNewsFragment firstFragment = new NewNewsFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            Intent intent = getIntent();
            intent.putExtra(NewNewsFragment.DATA_USER, USER);
            intent.putExtra(NewNewsFragment.DATA_DATE, DATE);
            intent.putExtra(NewNewsFragment.DATA_BODY, BODY);
            intent.putExtra(NewNewsFragment.DATA_ICON_URL, ICON_URL);
            intent.putExtra(NewNewsFragment.DATA_IMAGE_URL1, IMAGE_URL1);
            intent.putExtra(NewNewsFragment.DATA_IMAGE_URL2, IMAGE_URL2);
            intent.putExtra(NewNewsFragment.DATA_LIKES, 2);
            intent.putExtra(NewNewsFragment.DATA_COMMENTS, 3);
            mBundle = intent.getExtras();

            firstFragment.setArguments(intent.getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container2, firstFragment).commit();
        }

    }

    public void doSave(View view) {

        Intent intent = new Intent(RESULT_NEW_NEWS);
        intent.putExtras(mBundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
