package com.randomperson.magiskdetection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchForMagisk();
    }

    private void searchForMagisk() {
        PackageManager pm = getPackageManager();
        @SuppressLint("QueryPermissionsNeeded") List<PackageInfo> installedPackages = pm.getInstalledPackages(0);

        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo info = installedPackages.get(i);
            ApplicationInfo appInfo = info.applicationInfo;

            String nativeLibraryDir = appInfo.nativeLibraryDir;
            String packageName = appInfo.packageName;

            Log.i("Magisk Detection", "Checking App: " + nativeLibraryDir);

            File f = new File(nativeLibraryDir + "/libstub.so");
            if (f.exists()) {
                ShowMessageBox("Magisk Detection", "Magisk was Detected! \nTry using Magisk Hide :)");
            }
        }
    }

    private void ShowMessageBox(String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    System.exit(0);
                }
            }
        ).show();
    }
}