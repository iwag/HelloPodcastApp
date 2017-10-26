package io.github.iwag.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;

import io.github.iwag.newsapp.dummy.NewsContent;
import io.github.iwag.newsapp.dummy.NewsContent.NewsItem;
import io.github.iwag.newsapp.event.Events;
import io.github.iwag.newsapp.event.GlobalBus;

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
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private NewsRecyclerViewAdapter mAdapter;
    private final int LOAD_COUNT = 10;

    public interface NewsFragmentClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public NewsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NewsFragment newInstance(int columnCount) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
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

            mAdapter = new NewsRecyclerViewAdapter(getContext(), new LinkedList<NewsItem>(), mListener);

            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new NewsFragmentClickListener() {
                @Override
                public void onClick(View view, int position) {
                    NewsItem news = mAdapter.getNews(position);

                    Events.NewsFragmentClickMessage newsFragmentClickMessageEvent =
                            new Events.NewsFragmentClickMessage(news);

                    GlobalBus.getBus().post(newsFragmentClickMessageEvent);

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        GlobalBus.getBus().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        GlobalBus.getBus().unregister(this);
//    }

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

    public void addNews(NewsItem item) {
        mAdapter.addItem(item);
        mAdapter.notifyDataSetChanged();
    }

    public void loadNews() {
        if (mAdapter != null) {
            for (int i = 0; i < LOAD_COUNT; i++) {
                NewsItem news = NewsContent.createNewsItem(i);
                mAdapter.addItem(news);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void callbackRemoveMessage(Events.NewsFragmentRemoveMessage message) {
        mAdapter.removeAt(message.getIndex());
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
        // TODO: Update argument type and name
        void onListFragmentInteraction(NewsItem item);
    }
}
