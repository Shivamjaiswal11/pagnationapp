package com.example.pagenationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.example.pagenationapp.databinding.ActivityMainBinding;
import com.example.pagenationapp.databinding.ActivityZoomBinding;
import com.jsibbold.zoomage.ZoomageView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ZoomActivity extends AppCompatActivity {
    ZoomageView myZoomageView;
    private ActivityZoomBinding binding;
    private String url;
    File file;
    String dirPath, fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        binding = ActivityZoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       PRDownloader.initialize(getApplicationContext());
        myZoomageView=findViewById(R.id.myZoomageView);

        url=getIntent().getStringExtra("image");

        Glide.with(this).load(url).into(myZoomageView);
      downLoadimage();
      binding.ShareBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              shareimage();
          }
      });
    }

    private void shareimage() {



    }


   /* private void checkPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()){
                    downLoadimage();
                }else{
                    Toast.makeText(ZoomActivity.this, "Please allow permission", Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {*//* ... *//*}
        }).check();
    }*/

    private void downLoadimage() {
      /*  ProgressDialog pd= new ProgressDialog(this);
        pd.setMessage("Downloading...");
        pd.setCancelable(true);
        pd.show();*/

        // Initialization Of DownLoad Button
        AndroidNetworking.initialize(getApplicationContext());

        //Folder Creating Into Phone Storage
        dirPath = Environment.getExternalStorageDirectory() + "/SearchImage";
        int min=1;
        int max=1000;
        int random = ThreadLocalRandom.current().nextInt(min, max);
        fileName = +random+".jpeg";

        //file Creating With Folder & Fle Name
        file = new File(dirPath, fileName);

        //Click Listener For DownLoad Button
       binding.DownLoadBtn. setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AndroidNetworking.download(url, dirPath, fileName)

                        .setTag("downloadTest")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .setDownloadProgressListener(new DownloadProgressListener() {
                            @Override
                            public void onProgress(long bytesDownloaded, long totalBytes) {

                                long percent= bytesDownloaded *100 / totalBytes;
                              //  pd.setMessage("Downloading..("+percent+")%");
                            }
                        })
                        .startDownload(new com.androidnetworking.interfaces.DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                //pd.dismiss();
                                Toast.makeText(ZoomActivity.this, "DownLoad Complete", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(ZoomActivity.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        });


            }

        });

       /* File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        PRDownloader.download(url, file.getPath(), URLUtil.guessFileName(url,null,null))
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long percent=progress.currentBytes*100/ progress.totalBytes;
                        pd.setMessage("Downloading..("+percent+")%");

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        pd.dismiss();
                        Toast.makeText(ZoomActivity.this, "Downloading Completed..", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Error error) {
                        pd.dismiss();
                        Toast.makeText(ZoomActivity.this, "Error..", Toast.LENGTH_SHORT).show();


                    }
                });*/

    }
}