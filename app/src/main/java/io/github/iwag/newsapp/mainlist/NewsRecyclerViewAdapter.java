package io.github.iwag.newsapp.mainlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedViewHolder;
import com.squareup.picasso.Picasso;

import io.github.iwag.newsapp.R;
import io.github.iwag.newsapp.models.Channel;
import io.github.iwag.newsapp.models.FeedItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FeedItem} and makes a call to the
 * specified {@link NewsFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private final Context mContext;
    private final List<FeedItem> mValues;
    private Channel mChannel;
    private final NewsFragment.OnListFragmentInteractionListener mListener;
    private String channel;

    public NewsRecyclerViewAdapter(Context context, List<FeedItem> items, NewsFragment.OnListFragmentInteractionListener listener) {
        mContext = context;
        mValues = items;
        mChannel = null;
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
        SimpleDateFormat oldFmt = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
        SimpleDateFormat readableFmt = new SimpleDateFormat("dd MMM yyyy HH:mm");
        List<FeedItem> list = mValues;
        holder.mItem = list.get(position);

        holder.mEpisodeTitleView.setText(holder.mItem.title);
        holder.mContentView.setText(holder.mItem.subTitle);
        Date pubDate = null;
        try {
            pubDate = oldFmt.parse(holder.mItem.pubDate);
        } catch (ParseException e) {
            pubDate = new Date();
        }
        holder.mDateView.setText(readableFmt.format(pubDate));
        
        Picasso.with(mContext).load(mChannel.itunesImage.getHref()).placeholder(R.mipmap.ic_launcher).into(holder.mIconView);
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

    public void setChannel(Channel channel) {
        this.mChannel = channel;
    }

    public Channel getChannel() {
        return mChannel;
    }

    public class ViewHolder extends SectionedViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mEpisodeTitleView;
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
            mEpisodeTitleView = (TextView) view.findViewById(R.id.titleEpisodeTextView);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDateView = view.findViewById(R.id.dateTextView);
            mIconView = view.findViewById(R.id.iconView);
            mLikesView = view.findViewById(R.id.likeTextView);
            mCommentsView = view.findViewById(R.id.commentTextView);
            mButton = view.findViewById(R.id.removeButton);
            mButton.setOnClickListener(view1 -> {

                Log.d("RecyclerView", "Click");
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
