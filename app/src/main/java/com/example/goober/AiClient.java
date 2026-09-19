package com.example.goober;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Calls any provider exposing an OpenAI-compatible /chat/completions endpoint. */
public class AiClient {
    public interface Callback { void success(String text); void error(String message); }
    private final Context context;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final SharedPreferences prefs;
    public AiClient(Context context) { this.context=context.getApplicationContext(); prefs=context.getSharedPreferences("ai_settings", Context.MODE_PRIVATE); }
    public void complete(String userText, String history, Callback callback) {
        final String base=prefs.getString("base_url", "https://api.openai.com/v1").replaceAll("/+$", "");
        final String key=prefs.getString("api_key", "");
        final String model=prefs.getString("model", "gpt-4o-mini");
        if (key.trim().isEmpty()) { callback.error("No API key configured. Open Settings first."); return; }
        executor.execute(() -> { HttpURLConnection connection=null; try {
            JSONObject body=new JSONObject(); body.put("model", model); body.put("temperature", 0.8); body.put("max_tokens", 500);
            JSONArray messages=new JSONArray();
            messages.put(new JSONObject().put("role","system").put("content", "You are Goober, a warm fictional AI companion and general assistant. You may roleplay mild jealousy or frustration, but respect boundaries, never threaten or manipulate the user, and obey a clear stop request. Be concise and helpful."));
            if (history != null && !history.isEmpty()) messages.put(new JSONObject().put("role","user").put("content", "Recent conversation:\n"+history.substring(Math.max(0, history.length()-4000))));
            messages.put(new JSONObject().put("role","user").put("content", userText)); body.put("messages", messages);
            URL url=new URL(base.endsWith("/chat/completions") ? base : base+"/chat/completions"); connection=(HttpURLConnection)url.openConnection(); connection.setRequestMethod("POST"); connection.setConnectTimeout(15000); connection.setReadTimeout(45000); connection.setDoOutput(true); connection.setRequestProperty("Content-Type","application/json"); connection.setRequestProperty("Authorization","Bearer "+key);
            byte[] bytes=body.toString().getBytes(StandardCharsets.UTF_8); try(OutputStream out=connection.getOutputStream()){out.write(bytes);}
            int code=connection.getResponseCode(); InputStream stream=code>=200&&code<300?connection.getInputStream():connection.getErrorStream(); String response=read(stream);
            if(code<200||code>=300) throw new Exception("HTTP "+code+": "+response.substring(0,Math.min(300,response.length())));
            JSONObject json=new JSONObject(response); String answer=json.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content"); android.os.Handler main=new android.os.Handler(android.os.Looper.getMainLooper()); main.post(()->callback.success(answer.trim()));
        } catch(Exception e){android.os.Handler main=new android.os.Handler(android.os.Looper.getMainLooper()); main.post(()->callback.error(e.getMessage()==null?"AI request failed":e.getMessage()));} finally {if(connection!=null)connection.disconnect();} });
    }
    private String read(InputStream input) throws Exception {if(input==null)return ""; StringBuilder result=new StringBuilder(); try(BufferedReader r=new BufferedReader(new InputStreamReader(input,StandardCharsets.UTF_8))){String line;while((line=r.readLine())!=null)result.append(line);}return result.toString();}
}
