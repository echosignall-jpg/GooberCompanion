package com.example.goober;

import android.service.notification.NotificationListenerService;import android.service.notification.StatusBarNotification;import android.util.Log;
public class GooberNotificationListener extends NotificationListenerService { public void onNotificationPosted(StatusBarNotification n){CharSequence t=n.getNotification().extras.getCharSequence("android.text"); if(t!=null)Log.i("GooberNotification",n.getPackageName()+": "+t); } }
