package io.github.iwag.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedViewHolder;
import com.squareup.picasso.Picasso;

import io.github.iwag.newsapp.NewsFragment.OnListFragmentInteractionListener;
import io.github.iwag.newsapp.dummy.NewsContent.NewsItem;
import io.github.iwag.newsapp.event.Events;
import io.github.iwag.newsapp.event.GlobalBus;
import io.github.iwag.newsapp.models.FeedItem;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NewsItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private final Context mContext;
    private final List<FeedItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public NewsRecyclerViewAdapter(Context context, List<FeedItem> items, OnListFragmentInteractionListener listener) {
        mContext = context;
        mValues = items;
        mListener = listener;
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_news, parent, false);
//        return new ViewHolder(view);
//    }


//    @Override
//    public int getSectionCount() {
//        return 2;
//    }
//
//    @Override
//    public int getItemCount(int section) {
//        return getNewsList(section).size();
//    }
//
//    @Override
//    public void onBindHeaderViewHolder(ViewHolder holder, int section, boolean expanded) {
//        if (section==0) {
//            holder.mContentView.setText(String.format("Recent Movies (" + getItemCount(section) +")", section));
//        } else {
//            holder.mContentView.setText(String.format("Old Moviews (" + getItemCount(section) +")", section));
//        }
//    }
//
//    @Override
//    public void onBindFooterViewHolder(ViewHolder holder, int section) {
//    }

 //   @Override
//    public void onBindViewHolder(final ViewHolder holder, int section, int position, int absolutePositive) {
//    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // Change inflated layout based on type
//        int layoutRes;
//        switch(viewType) {
//            case VIEW_TYPE_HEADER:
//                layoutRes = R.layout.list_item_header;
//                break;
//            case VIEW_TYPE_FOOTER:
//                // if footers are enabled
//                layoutRes = R.layout.list_item_footer;
//                break;
//            default:
//                layoutRes = R.layout.fragment_news;
//                break;
//        }
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(layoutRes, parent, false);
//        return new ViewHolder(v);
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");

        List<FeedItem> list = mValues;
        holder.mItem = list.get(position);

        holder.mUserNameView.setText(holder.mItem.title);
        holder.mContentView.setText(holder.mItem.subTitle);
        holder.mDateView.setText(holder.mItem.pubDate);
//        Picasso.with(mContext).load(holder.mItem.iconUrl).placeholder(R.mipmap.ic_launcher).into(holder.mIconView);
//        Picasso.with(mContext).load(holder.mItem.imageUrl1).placeholder(R.mipmap.ic_launcher).into(holder.mImageView1);
//        Picasso.with(mContext).load(holder.mItem.imageUrl2).placeholder(R.mipmap.ic_launcher).into(holder.mImageView2);
        holder.mLikesView.setText(holder.mItem.duration);
//        holder.mCommentsView.setText("Comments:"+holder.mItem.comments);

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addItem(FeedItem item) {
        mValues.add(item);
    }

    public void removeAt(int pos) {
        mValues.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, mValues.size());
    }

    public FeedItem getNews(int pos) {
        return mValues.get(pos);
    }

    public class ViewHolder extends SectionedViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mUserNameView;
        public final TextView mContentView;
        public final TextView mDateView;
        public final ImageView mIconView;
        public final TextView mLikesView;
        public final TextView mCommentsView;
        public final Button mButton;

        public FeedItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserNameView = (TextView) view.findViewById(R.id.userNameTextView);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDateView = view.findViewById(R.id.dateTextView);
            mIconView = view.findViewById(R.id.iconView);
            mLikesView = view.findViewById(R.id.likeTextView);
            mCommentsView = view.findViewById(R.id.commentTextView);
            mButton = view.findViewById(R.id.removeButton);
            mButton.setOnClickListener(view1 -> {
                Events.NewsFragmentRemoveMessage message = new Events.NewsFragmentRemoveMessage(getAdapterPosition());
                GlobalBus.getBus().post(message);
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {

        }
    }
}
