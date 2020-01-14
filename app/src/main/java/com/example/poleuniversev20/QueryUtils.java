package com.example.poleuniversev20;

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
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {

    private static String SAMPLE_JSON_RESPONSE;
    private static ArrayList<SportsmeDataHolder> sportsmens;
    private static boolean ratedTechnique = false;
    private static boolean ratedChoreography = false;
    private static boolean ratedArtistic = false;
    private static boolean ratedPenalty = false;


    public QueryUtils() {
    }

    //Parsing a JSON response.

    public static ArrayList<SportsmeDataHolder> extractSportmsmens(String url) {
        SAMPLE_JSON_RESPONSE = makeHTTPRequest(createURL(url));
        ArrayList<SportsmeDataHolder> sportsman = new ArrayList<>();
        try {
            JSONObject reader = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray jsonArray = reader.getJSONArray("values");
            String name;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray a = jsonArray.getJSONArray(i);
                name = a.getString(1);
                for (int k = 0; k < a.length(); k++) {
                    if (a.getString(k).equals("Техника"))
                        ratedTechnique = true;
                    if (a.getString(k).equals("Артистизм"))
                        ratedArtistic = true;
                    if (a.getString(k).equals("Хореография"))
                        ratedChoreography = true;
                    if (a.getString(k).equals("Штрафы"))
                        ratedPenalty = true;
                }
                if (!name.equals(""))
                    sportsman.add(new SportsmeDataHolder(name, ratedTechnique, ratedChoreography, ratedArtistic, ratedPenalty));
                else break;
                ratedTechnique = false;
                ratedArtistic = false;
                ratedChoreography = false;
                ratedPenalty = false;
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem with parsing the earthquake JSON results", e);
        }

        // Return the list of athletes
        return sportsman;
    }

    private static URL createURL(String url) {
        URL oURL = null;
        try {
            oURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return oURL;
    }

    private static String makeHTTPRequest(URL url) {
        String jSONResp = "";
        HttpURLConnection urlconnection = null;
        InputStream inputstream = null;
        try {
            urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setReadTimeout(10000);
            urlconnection.setConnectTimeout(10000);
            urlconnection.setRequestMethod("GET");
            urlconnection.connect();
            if (urlconnection.getResponseCode() != 200) {
                return jSONResp;
            } else {
                inputstream = urlconnection.getInputStream();
                jSONResp = readFromStream(inputstream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlconnection != null) urlconnection.disconnect();
            try {
                if (inputstream != null) inputstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jSONResp;
    }

    private static String readFromStream(InputStream inputStream) {
        String jsonResp = "";
        if (inputStream != null) {
            StringBuilder sbuilder = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                jsonResp = bufferedReader.readLine();
                while (jsonResp != null) {
                    sbuilder.append(jsonResp);
                    jsonResp = bufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sbuilder.toString();
        }
        return jsonResp;
    }
}
