package com.example.goober;

import android.app.*;import android.os.*;import android.content.*;import android.widget.*;
public class ChatActivity extends Activity { private final MoodEngine engine=new MoodEngine(); private MemoryStore memory; private TextView history; public void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_chat);memory=new MemoryStore(this);history=findViewById(R.id.history);history.setText(memory.history()); EditText input=findViewById(R.id.input);findViewById(R.id.send).setOnClickListener(v->{String text=input.getText().toString().trim();if(text.isEmpty())return;String h=history.getText()+"\nYou: "+text+"\nGoober: "+engine.reply(text)+"\n";history.setText(h);memory.history(h);input.setText("");});}}
