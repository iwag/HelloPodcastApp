package io.github.iwag.newsapp.channellist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.github.iwag.newsapp.models.PodcastChannel;

public class FireChannelRepository {

    static String TAG = "FireChannel";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FireChannelRepository() {

    }

    LiveData<List<PodcastChannel>> getChannelList() {
        MutableLiveData<List<PodcastChannel>> liveData = new MutableLiveData<>();
        liveData.setValue(new LinkedList<>());

//        db.collection("channels").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    LinkedList<PodcastChannel> channelList = new LinkedList<>();
//                    for (DocumentSnapshot doc : task.getResult()) {
//                        channelList.add(new PodcastChannel(doc.getId(), doc.get("title").toString(),  doc.get("url").toString()));
//                    }
//                    if (!channelList.isEmpty()) {
//                        List<PodcastChannel> list = liveData.getValue();
//                        liveData.setValue(channelList);
//                    }
//                } else {
//                    Log.d(TAG, "Error",  task.getException());
//                }
//            }
//        });

        // this is ideal form but hard to implement with diff
        ListenerRegistration registration = db.collection("channels").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }
                LinkedList<PodcastChannel> channelList = new LinkedList<>();
                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                   if (dc.getType() == DocumentChange.Type.ADDED && dc.getDocument().exists()) {
                       DocumentSnapshot doc = dc.getDocument();
                       if (doc.get("title") == null || doc.get("url") == null) {
                           continue;
                       }
                        channelList.add(new PodcastChannel(doc.getId(), doc.get("title").toString(),  doc.get("url").toString()));
                        Log.d(TAG, "ADDED " + doc.getId() + " " + doc.getData()); // + " " + doc.getData().get("title") + " " + doc.getData().get("url"));
                    } else if (dc.getType() == DocumentChange.Type.REMOVED) {

                   }
                }
                if (!channelList.isEmpty()) {
                    List<PodcastChannel> list = liveData.getValue();
                    list.addAll(channelList);
                    liveData.setValue(list);
                }
            }
        });

        return liveData;
    }

    public void add(String title, String url) {
        // TODO check existance
        Map<String, String> obj = new HashMap<>();
        obj.put("url", url);
        obj.put("title", title);
        db.collection("channels").add(obj);
    }
}
