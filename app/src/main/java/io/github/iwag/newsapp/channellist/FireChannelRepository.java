package io.github.iwag.newsapp.channellist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

import io.github.iwag.newsapp.models.PodcastChannel;

public class FireChannelRepository {

    static String TAG = "FireChannel";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FireChannelRepository() {

    }

    LiveData<List<PodcastChannel>> getChannelList() {
        MutableLiveData<List<PodcastChannel>> liveData = new MutableLiveData<>();

        db.collection("channels").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    LinkedList<PodcastChannel> channelList = new LinkedList<>();
                    for (DocumentSnapshot doc : task.getResult()) {
                        channelList.add(new PodcastChannel(doc.getId(), doc.get("title").toString(),  doc.get("url").toString()));
                    }
                    if (!channelList.isEmpty()) {
                        liveData.setValue(channelList);
                    }
                } else {
                    Log.d(TAG, "Error",  task.getException());
                }
            }
        });

        return liveData;
    }
}
