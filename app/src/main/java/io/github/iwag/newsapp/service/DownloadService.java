package io.github.iwag.newsapp.service;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Loader;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class DownloadService {

    public Long downloadFile(Context context, String title, String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        request.setTitle(title);
        request.setDescription(url);
//        request.setDestinationInExternalPublicDir(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PODCASTS).getAbsolutePath(), "podcast");

// get download service and enqueue file
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Long id = manager.enqueue(request);
        return id;
    }

    public Uri getDownloadUri(Context context, Long id) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
       return manager.getUriForDownloadedFile(id);
    }

}
