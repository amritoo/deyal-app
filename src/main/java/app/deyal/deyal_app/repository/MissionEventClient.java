package app.deyal.deyal_app.repository;

import app.deyal.deyal_app.data.Constants;
import app.deyal.deyal_app.data.MissionEvent;
import app.deyal.deyal_app.managers.DataManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class MissionEventClient {

    public static boolean getMissionEventList(String token, String missionId) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(Constants.URL_EVENT_LIST);
            uriBuilder.setParameter("token", token);
            uriBuilder.setParameter("missionId", missionId);
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            // Executing the Get request
            HttpResponse httpResponse = httpclient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
                String json = scanner.nextLine();
                JsonArray jsonList = JsonParser.parseString(json).getAsJsonObject().get("payload").getAsJsonArray();

                // Getting mission event array from json array
                Gson gson = new Gson();
                Type missionEventListType = new TypeToken<ArrayList<MissionEvent>>() {
                }.getType();
                ArrayList<MissionEvent> missionEventArray = gson.fromJson(jsonList, missionEventListType);

                DataManager.getInstance().tempMissionEventList = missionEventArray;

                return true;
            } else {
                // Printing the status line
                System.out.println("Inside get mission event list\n" + httpResponse.getStatusLine());
                Scanner sc = new Scanner(httpResponse.getEntity().getContent());
                while (sc.hasNext()) {
                    System.out.println(sc.nextLine());
                }
                System.out.println("Outside get mission event list");
                return false;
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addEvent(String token, MissionEvent missionEvent) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(Constants.URL_EVENT_ADD);
            uriBuilder.setParameter("token", token);
            HttpPost httpPost = new HttpPost(uriBuilder.build());

            // Adding missionEvent data
            Gson gson = new Gson();
            String json = gson.toJson(missionEvent);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(json));

            // Executing the Put request
            HttpResponse httpresponse = httpclient.execute(httpPost);

            int statusCode = httpresponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return true;
            } else {
                // Printing the status line
                System.out.println("Inside add mission event\n" + httpresponse.getStatusLine());
                Scanner sc = new Scanner(httpresponse.getEntity().getContent());
                while (sc.hasNext()) {
                    System.out.println(sc.nextLine());
                }
                System.out.println("Outside add mission event");
                return false;
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

}
