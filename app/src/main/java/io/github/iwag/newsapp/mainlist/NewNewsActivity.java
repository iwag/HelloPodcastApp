package io.github.iwag.newsapp.mainlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import io.github.iwag.newsapp.R;

public class NewNewsActivity extends Activity {

    public static final String RESULT_NEW_NEWS = "aaa";
    public static final String RESULT_EMPTY = "empty";

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

            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container2, firstFragment).commit();
        }

        Intent intent = getIntent();
        Button saveButton = (Button) findViewById(R.id.save_news_button);
        if (intent != null && saveButton != null) {
            if (Objects.equals(intent.getExtras().getString(NewNewsFragment.DATA_KIND), "detail")) {
                saveButton.setText("Close");
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RESULT_EMPTY);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                });
            } else { // "save"
                saveButton.setText("Save");
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doSave(view);
                    }
                });

            }
            mBundle = intent.getExtras();
        }
    }


        public void doSave(View view) {
            Intent intent = new Intent(RESULT_NEW_NEWS);
            setResult(Activity.RESULT_OK, intent);
            finish();
    }

}
