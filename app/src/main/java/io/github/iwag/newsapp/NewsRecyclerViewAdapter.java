package io.github.iwag.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.github.iwag.newsapp.NewsFragment.OnListFragmentInteractionListener;
import io.github.iwag.newsapp.dummy.NewsContent.NewsItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NewsItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private final Context mContext;
    private final List<NewsItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public NewsRecyclerViewAdapter(Context context, List<NewsItem> items, OnListFragmentInteractionListener listener) {
        mContext = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mUserNameView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mDateView.setText(mValues.get(position).date);
        Picasso.with(mContext).load(mValues.get(position).iconUrl).placeholder(R.mipmap.ic_launcher).into(holder.mIconView);
        Picasso.with(mContext).load(mValues.get(position).imageUrl1).placeholder(R.mipmap.ic_launcher).into(holder.mImageView1);
        Picasso.with(mContext).load(mValues.get(position).imageUrl2).placeholder(R.mipmap.ic_launcher).into(holder.mImageView2);
        holder.mLikesView.setText("Likes:"+mValues.get(position).likes);
        holder.mCommentsView.setText("Comments:"+mValues.get(position).comments);

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
        return mValues.size();
    }

    public void addItem(NewsItem item) {
        mValues.add(item);
    }

    public void removeAt(int pos) {
        mValues.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, mValues.size());
    }

    public NewsItem getNews(int pos) {
        return mValues.get(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUserNameView;
        public final TextView mContentView;
        public final TextView mDateView;
        public final ImageView mIconView;
        public final ImageView mImageView1;
        public final ImageView mImageView2;
        public final TextView mLikesView;
        public final TextView mCommentsView;
        public final Button mButton;

        public NewsItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserNameView = (TextView) view.findViewById(R.id.userNameTextView);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDateView = view.findViewById(R.id.dateTextView);
            mIconView = view.findViewById(R.id.iconView);
            mImageView1 = view.findViewById(R.id.imageView1);
            mImageView2 = view.findViewById(R.id.imageView2);
            mLikesView = view.findViewById(R.id.likeTextView);
            mCommentsView = view.findViewById(R.id.commentTextView);
            mButton = view.findViewById(R.id.removeButton);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(getAdapterPosition());
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }
}
