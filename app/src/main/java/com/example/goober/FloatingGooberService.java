package com.example.goober;

import android.app.*;import android.content.*;import android.graphics.PixelFormat;import android.os.*;import android.provider.Settings;import android.view.*;import android.widget.*;
public class FloatingGooberService extends Service { private WindowManager wm; private View view; private final String channel="goober";
 public IBinder onBind(Intent i){return null;}
 public void onCreate(){super.onCreate(); if(Build.VERSION.SDK_INT>=26){NotificationChannel c=new NotificationChannel(channel,"Goober overlay",NotificationManager.IMPORTANCE_LOW);getSystemService(NotificationManager.class).createNotificationChannel(c);startForeground(7,new Notification.Builder(this,channel).setContentTitle("Goober is available").setSmallIcon(com.example.goober.R.drawable.ic_goober).build());} if(!Settings.canDrawOverlays(this)) {stopSelf();return;} wm=(WindowManager)getSystemService(WINDOW_SERVICE);view=LayoutInflater.from(this).inflate(R.layout.floating_goober,null); WindowManager.LayoutParams p=new WindowManager.LayoutParams(72,72,Build.VERSION.SDK_INT>=26?WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY:WindowManager.LayoutParams.TYPE_PHONE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT); wm.addView(view,p); view.setOnClickListener(v->{Intent i=new Intent(this,ChatActivity.class);i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);startActivity(i);}); }
 public void onDestroy(){if(view!=null&&wm!=null)wm.removeView(view);super.onDestroy();}
}
