package io.github.iwag.newsapp.mainlist;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import io.github.iwag.newsapp.R;
import io.github.iwag.newsapp.dummy.NewsContent;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewNewsFragment extends Fragment {

    public static final String DATA_USER = "0";
    public static final String DATA_DATE = "1";
    public static final String DATA_BODY = "2";
    public static final String DATA_ICON_URL = "3";
    public static final String DATA_IMAGE_URL1 = "4";
    public static final String DATA_IMAGE_URL2 = "5";
    public static final String DATA_LIKES = "6";
    public static final String DATA_COMMENTS = "7";
    public static final String DATA_KIND = "8";

    public NewNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_news, container, false);

        final Bundle bundle = getArguments();
        NewsContent.NewsItem content = new NewsContent.NewsItem(bundle.getString(DATA_USER),
                bundle.getString(DATA_BODY), "", bundle.getLong(DATA_DATE),
                bundle.getString(DATA_ICON_URL), bundle.getString(DATA_IMAGE_URL1), bundle.getString(DATA_IMAGE_URL2),
                bundle.getInt(DATA_LIKES), bundle.getInt(DATA_COMMENTS));

        ImageView iconImageView = view.findViewById(R.id.icon_image_view);
        ImageView photoImageView1 = view.findViewById(R.id.photo_image_view1);
        ImageView photoImageView2 = view.findViewById(R.id.photo_image_view2);

        Picasso.with(getContext()).load(content.iconUrl).placeholder(R.mipmap.ic_launcher).into(iconImageView);
        Picasso.with(getContext()).load(content.imageUrl1).placeholder(R.mipmap.ic_launcher).into(photoImageView1);
        Picasso.with(getContext()).load(content.imageUrl2).placeholder(R.mipmap.ic_launcher).into(photoImageView2);

        TextView nameTextView = view.findViewById(R.id.name_label_text_view);
        TextView dateTextView = view.findViewById(R.id.date_label_text_view);
        TextView bodyTextView = view.findViewById(R.id.news_body_text_view);

        nameTextView.setText(content.id);
        dateTextView.setText((new SimpleDateFormat("yyyy MMM dd").format(content.date))); // TODO simple date format
        bodyTextView.setText(content.content);

        return view;
    }

}
