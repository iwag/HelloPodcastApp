package io.github.iwag.newsapp.mainlist;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.iwag.newsapp.R;
import io.github.iwag.newsapp.channellist.ChannelFragment;
import io.github.iwag.newsapp.channellist.FireChannelRepository;
import io.github.iwag.newsapp.models.PodcastChannel;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewChannelFragment extends Fragment {

    private NewChannelInteraction mListener;

    public NewChannelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_podcast, container, false);

        final Bundle bundle = getArguments();

        TextView titleTextView = view.findViewById(R.id.new_podcast_title_text_edit);

        TextView urlTextView = view.findViewById(R.id.new_podcast_url_text_edit);

        view.findViewById(R.id.new_podcast_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validTexts(titleTextView.getText().toString(), urlTextView.getText().toString())) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                    in.hideSoftInputFromWindow(urlTextView.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    mListener.saveNewChannel(titleTextView.getText().toString(), urlTextView.getText().toString());
                }
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewChannelInteraction) {
            mListener = (NewChannelInteraction) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement NewChannelInteraction");
        }
    }


    private boolean validTexts(String title, String url) {
        if (title.isEmpty() || url.isEmpty()) return false;
        return (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url) );
    }

    public interface NewChannelInteraction {
        void saveNewChannel(String title, String url);
    }
}
