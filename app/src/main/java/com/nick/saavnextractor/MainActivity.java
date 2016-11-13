package com.nick.saavnextractor;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;

/**
 * Created by Nick on 12-Nov-16.
 */
public class MainActivity extends AppCompatActivity {
    public static final String logtag = "Nick";
    public String extension = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy);
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error");
            builder.setMessage("App can't run without permissions.\nPlease grant permissions first.");
            builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.finish();
                    System.exit(0);
                }
            });
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 23);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23);

        Button mButton;
        final EditText mText;

        mButton = (Button) findViewById(R.id.savebtn);
        mText = (EditText) findViewById(R.id.edittext);
        final TextView logger = (TextView) findViewById(R.id.logview);

        // Check and populate logger
        String sdcard = Environment.getExternalStorageDirectory().toString();
        String saavndir = sdcard+"/Android/data/com.saavn.android";
        File saavnsong = new File (saavndir+"/songs/curr.mp3");
        File saavnsong2 = new File (saavndir+"/songs/curr.mp4");

        try {
            if (saavnsong.exists()){
                logger.setText("Saavn is installed. Song found. Good to go.");
                extension=".mp3";
            }
            else if (saavnsong2.exists()){
                logger.setText("Saavn is installed. Song found. Good to go.");
                extension=".mp4";
            }
            else{
                logger.setText("Song not found. Saavn not installed or you forgot to download, play & pause the song.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v){
                        // Declare Variables
                        String sdcard = Environment.getExternalStorageDirectory().toString();
                        String saavndir = sdcard+"/Android/data/com.saavn.android";
                        File saavnsong = new File (saavndir+"/songs/curr.mp3");
                        File saavnsong2 = new File (saavndir+"/songs/curr.mp4");
                        String outdir = sdcard + "/Music";
                        File  songname = new File (outdir+"/"+mText.getText().toString()+extension);

                        try {
                            if (saavnsong.exists()) {
                                // Proceed
                                if (saavnsong.renameTo(songname)) {
                                    // Operation Successful
                                    logger.setText("File Mp3 Not Copied.");
                                    Log.v(logtag, "File Mp3 Copied.");
                                } else {
                                    // Operation Failed
                                    logger.setText("File Mp3 Not Copied.");
                                    Log.v(logtag, "File Mp3 Not Copied.");
                                }

                            }
                            else if (saavnsong2.exists()){
                                // Proceed
                                if (saavnsong2.renameTo(songname)){
                                    // Successful
                                    logger.setText("File MP4 copied.");
                                    Log.v(logtag, "File MP4 copied.");
                                }
                                else{
                                    // Failed
                                    logger.setText("File MP4 not copied.");
                                    Log.v(logtag, "File MP4 not copied.");
                                }
                            }
                            else {
                                // File is non existent
                                logger.setText("File Not Found!");
                                Log.v(logtag, "File Not Found");
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
        );
            mButton = (Button) findViewById(R.id.helpbtn);
            mButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showHelp();
                        }
                    }
            );

        mButton = (Button) findViewById(R.id.aboutbtn);
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAbout();
                    }
                }
        );
    }

    public void showHelp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.dialog_title_help));
        builder.setMessage(getString(R.string.dialog_text_help));
        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void showAbout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.dialog_title_about));
        builder.setMessage((getString(R.string.dialog_text_about)));
        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


}
