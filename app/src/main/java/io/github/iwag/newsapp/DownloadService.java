package io.github.iwag.newsapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Loader;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class DownloadService {

    public File getTempFile(Context context, String url) {
        File file = null;
        String fileName = Uri.parse(url).getLastPathSegment();

//        File musicDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PODCASTS), "podcast");
//        file = new File(musicDir.getPath() + File.separator + fileName);

        try {
            file = File.createTempFile("mp3", fileName, context.getCacheDir());
        } catch (IOException e) {
            file =null;
        }
        return file;
    }

    public Long downloadFile(Context context,  String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Some descrition");
        request.setTitle("Some title");

        File file = getTempFile(context, url);
        request.setTitle(file.getName());
        request.setDescription(file.getAbsolutePath());
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
