package com.mushroomrobot.redex.utils;

import android.os.Environment;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;

/**
 * Created by Nick.
 */
public class MyCache {

    static private String cacheDirectory = "/Android/data/com.mushroomrobot.redex/cache";

    static {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            cacheDirectory = Environment.getExternalStorageDirectory() + cacheDirectory;
            File f = new File(cacheDirectory);
            f.mkdirs();
        }
    }

    static public String convertToCacheName(String url){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            byte[] b = digest.digest();
            BigInteger bi = new BigInteger(b);
            return "mycache_" + bi.toString(16) + ".cac";
        } catch (Exception e){
            return null;
        }
    }

    private static boolean tooOld(long time){
        long now = new Date().getTime();
        long diff = now - time;
        if (diff>1000*60*5) //5 minutes
            return true;
        return false;
    }

    public static byte[] read(String url){
        try{
            String file = cacheDirectory + "/" + convertToCacheName(url);
            File f = new File(file);
            if (!f.exists() || f.length() < 1) return null;
            //Delete cache if too old
            if (f.exists() && tooOld(f.lastModified()))
                f.delete();
            byte data[] = new byte[(int)f.length()];
            DataInputStream fis = new DataInputStream(new FileInputStream(f));
            fis.readFully(data);
            fis.close();
            return data;
        } catch (Exception e){
            return null;
        }
    }

    public static void write(String url, String data){
        try {
            String file = cacheDirectory + "/" + convertToCacheName(url);
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.print(data);
            pw.close();
        } catch (Exception e){

        }
    }
}
