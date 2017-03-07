package com.example.oleg.recyclerviewandfragments;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Обращение к REST-совместимому веб-сервису за данными id и jokes
 */
public class JsonLoader extends AsyncTask<String, String, String> {
    public static String LOG_TAG = "my_log";
    private final String JSON_URL = "http://api.icndb.com/jokes/random/10";

    private String id;
    private String joke;
    public static ArrayList<String> content = new ArrayList<>();

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resultJson = "";


    @Override
    protected String doInBackground(String... params) {
        // получаем данные с внешнего API - 10 фактов о Чак Норрисе
        try {
            URL url = new URL(JSON_URL);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultJson;
    }


    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
        // выводим целиком полученную json-строку
        Log.d(LOG_TAG, strJson);

        JSONObject dataJsonObj = null; // ссылка на обьект данных JSON
        String strId = "";
        String strJoke = "";

        try {
            dataJsonObj = new JSONObject(strJson); // создаем обьект данных JSON

            //получаем массив value из json-обьекта
            JSONArray arrValue = dataJsonObj.getJSONArray("value");

            content.clear();
            for (int i = 0; i < arrValue.length(); i++) {

                //получаем i-елемент массива value
                JSONObject arrValueObj = arrValue.getJSONObject(i);

                // получаем строку id из массива value - индекс 0
                strId = arrValueObj.getString("id");
                Log.d(LOG_TAG, "id: " + strId);

                // получаем строку joke из массива value - индекс 1
                strJoke = arrValueObj.getString("joke");
                Log.d(LOG_TAG, "joke: " + strJoke);

                //записываем в arraylist данные id-key  joke-value
                content.add(strId + "\n" + strJoke);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
