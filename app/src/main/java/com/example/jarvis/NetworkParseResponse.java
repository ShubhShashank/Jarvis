package com.example.jarvis;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Siddharth Venu on Aug 2016.
 */
public final class NetworkParseResponse {
    private static URL url = null;
    private static String botResponse = null;


    public static void formURL(String talkWord, long uid) {
        try {
            talkWord = talkWord.replace(" ", "%20").replace("!", "");
            String urlBuild="http://api.brainshop.ai/get?bid=261&key=zVD8G6DFUDCU63zQ&uid="
                    +Long.toString(uid)+"&msg="+talkWord;
            Log.v("Project JARVIS",urlBuild);
            url = new URL(urlBuild);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void establishConnectionGetResponse() {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("X-Mashape-Key", "9y0yWglKG9mshIE43rz8TP9RHdnwp1RvLzmjsnrr3o0PODJYh1");
            urlConnection.connect();
            String jsonData = getStringFromInputStream(urlConnection.getInputStream());
            botResponse = parseBotResponse(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getStringFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static String parseBotResponse(String jsonData) {
        try {
            JSONObject rootObject = new JSONObject(jsonData);
            Log.v("Project JARVIS",rootObject.getString("cnt").replace("\\",""));
            return rootObject.getString("cnt").replace("\\","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBotResponse(){
        return botResponse;
    }

}
