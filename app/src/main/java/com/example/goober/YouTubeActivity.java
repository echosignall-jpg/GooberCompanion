package com.example.goober;

import android.app.Activity;import android.content.Intent;import android.net.Uri;import android.os.Bundle;import android.widget.*;
public class YouTubeActivity extends Activity { protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_youtube); EditText url=findViewById(R.id.videoUrl); findViewById(R.id.openVideo).setOnClickListener(v->{String s=url.getText().toString().trim();if(s.isEmpty())s="https://www.youtube.com/";startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s)));}); findViewById(R.id.backChat).setOnClickListener(v->startActivity(new Intent(this,ChatActivity.class))); }}
