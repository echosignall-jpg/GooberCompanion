package com.example.goober;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class SettingsActivity extends Activity {
    protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_settings); android.content.SharedPreferences p=getSharedPreferences("ai_settings",MODE_PRIVATE); EditText base=findViewById(R.id.baseUrl), key=findViewById(R.id.apiKey), model=findViewById(R.id.model); base.setText(p.getString("base_url","https://api.openai.com/v1")); key.setText(p.getString("api_key","")); model.setText(p.getString("model","gpt-4o-mini")); findViewById(R.id.save).setOnClickListener(v->{p.edit().putString("base_url",base.getText().toString().trim()).putString("api_key",key.getText().toString().trim()).putString("model",model.getText().toString().trim()).apply(); Toast.makeText(this,"AI settings saved",Toast.LENGTH_SHORT).show();finish();}); }
}
