package com.maxencelav.vittasciencetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button microbitButton = findViewById(R.id.microbitButton);
        microbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToWebView("https://vittascience.com/microbit?embed=1");
            }
        });

        Button aiButton = findViewById(R.id.aiButton);
        aiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},1);
                    // request code is at 1 to bind it in the onRequestPermissionsResult method
                }
            }
        });

    }


    private void switchToWebView(String url) {
        Intent intent = new Intent(MainActivity.this, VittaActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    /* from https://stackoverflow.com/questions/33666071/android-marshmallow-request-permission */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    switchToWebView("https://vittascience.com/ia/images.php?mode=mixed&embed=1");
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied — we can't let you load the AI interface. Sorry!", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}