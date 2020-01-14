package com.example.poleuniversev20;

import android.util.Log;

import com.example.poleuniversev20.SportsmeDataHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QueryUtils {
    /**
     * Sample JSON response for a USGS query
     */
    private static String SAMPLE_JSON_RESPONSE;
    private static ArrayList<SportsmeDataHolder> sportsmens;

    private static boolean ratedTechnique = false;
    private static boolean ratedChoreography = false;
    private static boolean ratedArtistic = false;
    private static boolean ratedPenalty = false;

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    public QueryUtils() {
    }

    /**
     * Return a list of {@link SportsmeDataHolder} objects that has been built up from
     * parsing a JSON response.
     */


    public static ArrayList<SportsmeDataHolder> extractSportmsmens(String url) {
        SAMPLE_JSON_RESPONSE = makeHTTPRequest(createURL(url));
        Log.e("QueryUtils", "I am here");
        // Create an empty ArrayList to adding sportsmens
        //if (sportsmens != null) return sportsmens;
        ArrayList<SportsmeDataHolder> sportsmens = new ArrayList<>();
        try {
            JSONObject reader = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray jsonArray = reader.getJSONArray("values");
            String name;
            // boolean rated = false;
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
//               if (a.getString(2).equals(""))
//                   rated = false;
//                else rated = true;
//                Log.d("QueryUtils", name + "  " + rated);
                // Log.v("Техника ", name + " Техника- "  + ratedTechnique + "  Хореография - " + ratedChoreography + " Штрафы - " + ratedPenalty +" Артистизм - " +ratedArtistic);

                //CREATING A Sportsmens list
                if (!name.equals(""))
                    sportsmens.add(new SportsmeDataHolder(name, ratedTechnique, ratedChoreography, ratedArtistic, ratedPenalty));
                else break;
                ratedTechnique = false;
                ratedArtistic = false;
                ratedChoreography = false;
                ratedPenalty = false;

            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return sportsmens;
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
