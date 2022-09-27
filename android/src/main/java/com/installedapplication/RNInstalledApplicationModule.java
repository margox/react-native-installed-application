package com.installedapplication;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.Arguments;

import android.content.pm.PackageInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.annotation.Nullable;

import com.helper.*;

public class RNInstalledApplicationModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNInstalledApplicationModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNInstalledApplication";
  }

  @ReactMethod
  public void getApps(Promise promise) {
    try {
      PackageManager pm = this.reactContext.getPackageManager();
      List<PackageInfo> pList = pm.getInstalledPackages(0);
      WritableArray list = Arguments.createArray();
      for (int i = 0; i < pList.size(); i++) {
        PackageInfo packageInfo = pList.get(i);
        WritableMap appInfo = Arguments.createMap();

        appInfo.putString("packageName", packageInfo.packageName);
        appInfo.putString("versionName", packageInfo.versionName);
        appInfo.putDouble("versionCode", packageInfo.versionCode);
        appInfo.putDouble("firstInstallTime", (packageInfo.firstInstallTime));
        appInfo.putDouble("lastUpdateTime", (packageInfo.lastUpdateTime));
        appInfo.putString("appName", ((String) packageInfo.applicationInfo.loadLabel(pm)).trim());

        Drawable icon = pm.getApplicationIcon(packageInfo.applicationInfo);
        appInfo.putString("icon", Utility.convert(icon));

        Boolean isSystemApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        appInfo.putBoolean("isSystemApp", isSystemApp);

        String apkDir = packageInfo.applicationInfo.publicSourceDir;
        appInfo.putString("apkDir", apkDir);

        list.pushMap(appInfo);
      }
      promise.resolve(list);
    } catch (Exception ex) {
      promise.reject(ex);
    }
  }

  @ReactMethod
  public void getNonSystemApps(Promise promise) {
    try {
      PackageManager pm = this.reactContext.getPackageManager();
      List<PackageInfo> pList = pm.getInstalledPackages(0);
      WritableArray list = Arguments.createArray();
      for (int i = 0; i < pList.size(); i++) {
        PackageInfo packageInfo = pList.get(i);
        WritableMap appInfo = Arguments.createMap();

        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
          appInfo.putString("packageName", packageInfo.packageName);
          appInfo.putString("versionName", packageInfo.versionName);
          appInfo.putDouble("versionCode", packageInfo.versionCode);
          appInfo.putDouble("firstInstallTime", (packageInfo.firstInstallTime));
          appInfo.putDouble("lastUpdateTime", (packageInfo.lastUpdateTime));
          appInfo.putString("appName", ((String) packageInfo.applicationInfo.loadLabel(pm)).trim());

          Drawable icon = pm.getApplicationIcon(packageInfo.applicationInfo);
          appInfo.putString("icon", Utility.convert(icon));

          String apkDir = packageInfo.applicationInfo.publicSourceDir;
          appInfo.putString("apkDir", apkDir);

          list.pushMap(appInfo);
        }
      }
      promise.resolve(list);
    } catch (Exception ex) {
      promise.reject(ex);
    }

  }

  @ReactMethod
  public void getSystemApps(Promise promise) {
    try {
      PackageManager pm = this.reactContext.getPackageManager();
      List<PackageInfo> pList = pm.getInstalledPackages(0);
      WritableArray list = Arguments.createArray();
      for (int i = 0; i < pList.size(); i++) {
        PackageInfo packageInfo = pList.get(i);
        WritableMap appInfo = Arguments.createMap();

        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
          appInfo.putString("packageName", packageInfo.packageName);
          appInfo.putString("versionName", packageInfo.versionName);
          appInfo.putDouble("versionCode", packageInfo.versionCode);
          appInfo.putDouble("firstInstallTime", (packageInfo.firstInstallTime));
          appInfo.putDouble("lastUpdateTime", (packageInfo.lastUpdateTime));
          appInfo.putString("appName", ((String) packageInfo.applicationInfo.loadLabel(pm)).trim());

          Drawable icon = pm.getApplicationIcon(packageInfo.applicationInfo);
          appInfo.putString("icon", Utility.convert(icon));

          String apkDir = packageInfo.applicationInfo.publicSourceDir;
          appInfo.putString("apkDir", apkDir);

          list.pushMap(appInfo);
        }
      }
      promise.resolve(list);
    } catch (Exception ex) {
      promise.reject(ex);
    }
  }

  @ReactMethod
  private boolean isPackageInstalled(String packageName) {
    PackageManager pm = this.reactContext.getPackageManager();
    try {
      pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
