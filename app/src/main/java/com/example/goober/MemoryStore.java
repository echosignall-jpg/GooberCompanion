package com.example.goober;

import android.content.*;
public class MemoryStore { private final SharedPreferences p; public MemoryStore(Context c){p=c.getSharedPreferences("private_memory",Context.MODE_PRIVATE);} public String history(){return p.getString("history","");} public void history(String v){p.edit().putString("history",v).apply();} }
