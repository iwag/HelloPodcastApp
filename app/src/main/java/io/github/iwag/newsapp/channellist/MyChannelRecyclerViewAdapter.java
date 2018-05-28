package io.github.iwag.newsapp.channellist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.iwag.newsapp.R;
import io.github.iwag.newsapp.channellist.ChannelListFragment.OnListFragmentInteractionListener;
import io.github.iwag.newsapp.models.PodcastChannel;


/**
 * {@link RecyclerView.Adapter} that can display a {@link PodcastChannel} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyChannelRecyclerViewAdapter extends RecyclerView.Adapter<MyChannelRecyclerViewAdapter.ViewHolder> {

    private final ChannelListPresenter mPresenter;
    private final OnListFragmentInteractionListener mListener;

    public MyChannelRecyclerViewAdapter(ChannelListPresenter presenter, OnListFragmentInteractionListener listener) {
        mPresenter = presenter;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_channel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mPresenter.get(position);
        holder.mContentView.setText(mPresenter.get(position).title);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPresenter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mContentView;
        public PodcastChannel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.channel_image_view);
            mContentView = (TextView) view.findViewById(R.id.channel_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
