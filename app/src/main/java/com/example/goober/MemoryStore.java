package com.example.goober;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/** AES/GCM encrypted local memory. The key is non-exportable in Android Keystore. */
public class MemoryStore {
    private static final String STORE="private_memory_v2", KEY_ALIAS="goober_memory_key";
    private final SharedPreferences prefs;
    public MemoryStore(Context context){ prefs=context.getSharedPreferences(STORE,Context.MODE_PRIVATE); ensureKey(); }
    public synchronized String history(){ return decrypt(prefs.getString("history","")); }
    public synchronized void history(String value){ prefs.edit().putString("history",encrypt(value)).apply(); }
    public synchronized void clear(){ prefs.edit().remove("history").apply(); }
    private void ensureKey(){ try { KeyStore ks=KeyStore.getInstance("AndroidKeyStore"); ks.load(null); if(!ks.containsAlias(KEY_ALIAS)){ KeyGenerator g=KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore"); g.init(new KeyGenParameterSpec.Builder(KEY_ALIAS,KeyProperties.PURPOSE_ENCRYPT|KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE).setUserAuthenticationRequired(false).build()); g.generateKey(); } }catch(Exception e){throw new IllegalStateException("Cannot initialize secure memory",e);} }
    private SecretKey key() throws Exception { KeyStore ks=KeyStore.getInstance("AndroidKeyStore");ks.load(null);return ((KeyStore.SecretKeyEntry)ks.getEntry(KEY_ALIAS,null)).getSecretKey(); }
    private String encrypt(String plain){ try { Cipher c=Cipher.getInstance("AES/GCM/NoPadding");c.init(Cipher.ENCRYPT_MODE,key());byte[] iv=c.getIV(), data=c.doFinal(plain.getBytes(StandardCharsets.UTF_8));byte[] all=new byte[iv.length+data.length];System.arraycopy(iv,0,all,0,iv.length);System.arraycopy(data,0,all,iv.length,data.length);return Base64.encodeToString(all,Base64.NO_WRAP); }catch(Exception e){return "";} }
    private String decrypt(String encoded){ if(encoded==null||encoded.isEmpty())return "";try{byte[] all=Base64.decode(encoded,Base64.NO_WRAP);byte[] iv=new byte[12];System.arraycopy(all,0,iv,0,iv.length);byte[] data=new byte[all.length-iv.length];System.arraycopy(all,iv.length,data,0,data.length);Cipher c=Cipher.getInstance("AES/GCM/NoPadding");c.init(Cipher.DECRYPT_MODE,key(),new GCMParameterSpec(128,iv));return new String(c.doFinal(data),StandardCharsets.UTF_8);}catch(Exception e){return "";} }
}
