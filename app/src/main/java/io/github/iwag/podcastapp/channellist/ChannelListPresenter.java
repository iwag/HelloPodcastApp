package io.github.iwag.podcastapp.channellist;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.github.iwag.podcastapp.models.PodcastChannel;

public class ChannelListPresenter {
    private final FireChannelRepository channelRepository;
    List<PodcastChannel> mList;

    public ChannelListPresenter(ChannelListContract.View view, LifecycleOwner lifeCycler) {
        mList = new ArrayList<>();
        channelRepository = new FireChannelRepository();
        channelRepository.getChannelList().observe(lifeCycler, new Observer<List<PodcastChannel>>() {
            @Override
            public void onChanged(@Nullable List<PodcastChannel> podcastChannels) {
                mList.clear();
                mList.addAll(podcastChannels);
                view.notifyDataChanged();
            }
        });
    }

    public PodcastChannel get(int position) {
        return mList.get(position);
    }

    public int size() {
        return mList.size();
    }
}
