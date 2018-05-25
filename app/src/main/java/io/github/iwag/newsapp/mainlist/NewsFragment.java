package io.github.iwag.newsapp.mainlist;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import io.github.iwag.newsapp.R;
import io.github.iwag.newsapp.infra.PodcastFeedAPIClient;
import io.github.iwag.newsapp.infra.PodcastFeedApiService;
import io.github.iwag.newsapp.models.FeedItem;
import io.github.iwag.newsapp.models.Rss;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.LinkedList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NewsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_URL = "arg-url";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private NewsRecyclerViewAdapter mAdapter;
    private final int LOAD_COUNT = 10;
    private String mUrl = "";

    private static Serializer ser = new Persister(new AnnotationStrategy());

    public interface NewsFragmentClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public NewsFragment() {
    }

    @SuppressWarnings("unused")
    public static NewsFragment newInstance(int columnCount, String url) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mUrl = getArguments().getString(ARG_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            GridLayoutManager manager = new GridLayoutManager(context, mColumnCount);
            recyclerView.setLayoutManager(manager);

//            mAdapter = new NewsRecyclerViewAdapter(getContext(), new LinkedList<NewsItem>(), mListener);
//            mAdapter.setLayoutManager(manager);
//            mAdapter.shouldShowHeadersForEmptySections(true);
//            mAdapter.shouldShowFooters(true);
//            recyclerView.setAdapter(mAdapter);

            mAdapter = new NewsRecyclerViewAdapter(getContext(), new LinkedList<FeedItem>(), mListener);

            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new NewsFragmentClickListener() {
                @Override
                public void onClick(View view, int position) {
                    FeedItem item = mAdapter.getNews(position);
                    NewsActivity parent = (NewsActivity)getActivity();
                    parent.play(item, mAdapter.getChannel().itunesImage.getHref());
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

            recyclerView.setAdapter(mAdapter);

            loadNews();

        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void addNews(FeedItem item) {
        mAdapter.addItem(item);
        mAdapter.notifyDataSetChanged();
    }

    public void loadNews() {
        if (mAdapter != null && !mUrl.isEmpty()) {
            PodcastFeedAPIClient client = PodcastFeedApiService.create("http://example.com");
            client.getRssBody(mUrl).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String body = response.body();

                        Rss rss = null;
                        try {
                            rss = ser.read(Rss.class, body);
                        } catch (Exception e) {
                            Log.d("rssCliend", "error +"+ e.getMessage().toString());
                        }

                        rss.channel.items.forEach(item -> mAdapter.addItem(item));
                        mAdapter.setChannel(rss.channel);
                        mAdapter.notifyDataSetChanged();
                        Log.d("rssCliend", "load " + rss.channel.items.size() );
                    } else {
                        try {
                            Log.d("rssCliend", "error +"+ response.errorBody().string().toString());
                        } catch (IOException e) {

                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {

                }
            });


        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(FeedItem item);
    }
}
