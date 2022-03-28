package com.example.pagenationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
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
        getSupportActionBar().hide();

        SharedPreferences sp = getSharedPreferences("dataFile", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();



        PRDownloader.initialize(getApplicationContext());
        myZoomageView = findViewById(R.id.myZoomageView);

        url = getIntent().getStringExtra("image");

        Glide.with(this).load(url).into(myZoomageView);
        downLoadimage();
        binding.ShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareimage();

            }

        });

        if (sp.getBoolean("firstTime",true)){
            ed.putBoolean("firstTime",false);
            ed.commit();
            fortabtarget();
        }



    }

    private void fortabtarget() {


        new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(binding.ShareBtn, "Send your first Photo", "Tap the envelope to start Sharing your first Photo")
                                .outerCircleColor(R.color.blue)
                                .outerCircleAlpha(0.96f)
                                .targetCircleColor(R.color.white)
                                .titleTextSize(20)
                                .titleTextColor(R.color.white)
                                .descriptionTextSize(10)
                                .descriptionTextColor(R.color.black)
                                .textColor(R.color.white)
                                .textTypeface(Typeface.SANS_SERIF)
                                .dimColor(R.color.white)
                                .drawShadow(false)
                                .cancelable(false)
                                .tintTarget(true)
                                .transparentTarget(true)
                                .targetRadius(60),
                        TapTarget.forView(binding.likeBtn, "Like The Photo", "Tap the envelope to start Like your first Photo")
                                .outerCircleColor(R.color.pink)
                                .outerCircleAlpha(0.96f)
                                .targetCircleColor(R.color.white)
                                .titleTextSize(20)
                                .titleTextColor(R.color.white)
                                .descriptionTextSize(10)
                                .descriptionTextColor(R.color.black)
                                .textColor(R.color.white)
                                .textTypeface(Typeface.SANS_SERIF)
                                .dimColor(R.color.white)
                                .drawShadow(true)
                                .cancelable(false)
                                .tintTarget(true)
                                .transparentTarget(true)
                                .targetRadius(60),
                        TapTarget.forView(binding.DownLoadBtn, "Download The Photo", "Tap the envelope to start download your first Photo")
                                .outerCircleColor(R.color.teal_200)
                                .outerCircleAlpha(0.96f)
                                .targetCircleColor(R.color.white)
                                .titleTextSize(20)
                                .titleTextColor(R.color.white)
                                .descriptionTextSize(10)
                                .descriptionTextColor(R.color.black)
                                .textColor(R.color.white)
                                .textTypeface(Typeface.SANS_SERIF)
                                .dimColor(R.color.white)
                                .drawShadow(true)
                                .cancelable(false)
                                .tintTarget(true)
                                .transparentTarget(true)
                                .targetRadius(60))
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {



                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {



                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                }).start();
    }


    private void shareimage() {
        BitmapDrawable drawable = (BitmapDrawable) myZoomageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String bitmappath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
        Uri uri = Uri.parse(bitmappath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share"));


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


        AndroidNetworking.initialize(getApplicationContext());


        dirPath = Environment.getExternalStorageDirectory() + "/SearchImage";

        binding.DownLoadBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog(ZoomActivity.this);
                pd.setMessage("Downloading...");
                pd.setCancelable(false);
                pd.show();

                AndroidNetworking.download(url, dirPath, URLUtil.guessFileName(url, null, null) + ".jpeg")
                        .setTag("downloadTest")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .setDownloadProgressListener(new DownloadProgressListener() {
                            @Override
                            public void onProgress(long bytesDownloaded, long totalBytes) {

                                long percent = bytesDownloaded * 100 / totalBytes;
                                pd.setMessage("Downloading..(" + percent + ")%");
                            }
                        })
                        .startDownload(new com.androidnetworking.interfaces.DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                pd.dismiss();
                                Toast.makeText(ZoomActivity.this, "DownLoad Complete", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onError(ANError anError) {
                                pd.dismiss();
                                Toast.makeText(ZoomActivity.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        });


            }

        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}