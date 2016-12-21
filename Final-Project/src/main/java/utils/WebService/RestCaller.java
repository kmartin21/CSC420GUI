package utils.WebService;

import models.*;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by lwdthe1 on 3/11/2016.
 */
public class RestCaller
{
    public static RestCaller sharedInstance = new RestCaller();
    private final String TAG = "SuperMeditorRestCaller";
    private final String REST_API_URL = "http://lcontacts.herokuapp.com/api/i/";

    //prevent others from instantiating
    private RestCaller() {

    }

    /**
     * gets all the publications
     *
     * @return String array containing all the Pids
     * @throws URISyntaxException
     * @throws HttpException
     * @throws IOException
     */
    public List<Publication> getPublications() throws URISyntaxException, HttpException, IOException {
        // Create a new HttpClient and Get Sequence number
        HttpClient httpClient = new DefaultHttpClient();
        String restUri = REST_API_URL + "pubs?deliminatePubs=false";

        HttpGet httpGet = new HttpGet(restUri);

        HttpResponse response = httpClient.execute(httpGet);

        String resultJson = EntityUtils.toString(response.getEntity());
        List<Publication> publications = new ArrayList<>();
        JSONObject resultJsonObject = new JSONObject(resultJson);
        if (resultJsonObject.has("advertisedPubs")) {
            JSONArray jsonPublications = resultJsonObject.getJSONArray("advertisedPubs");
            for (int i = 0; i < jsonPublications.length(); i++) {
                publications.add(new Publication(jsonPublications.getJSONObject(i)));
            }
        } else {
            System.out.println("returned json did not contain publications JSON: " + resultJsonObject.toString());
        }
        return publications;
    }

    public List<ChatMessage> getChatMessages(String publicationId) throws URISyntaxException, HttpException, IOException {
        // Create a new HttpClient and Get Sequence number
        HttpClient httpClient = new DefaultHttpClient();
        String restUri = REST_API_URL + "chat/" + publicationId + "/" +CurrentUser.sharedInstance.getUsername();

        HttpGet httpGet = new HttpGet(restUri);
        HttpResponse response = httpClient.execute(httpGet);

        String resultJson = EntityUtils.toString(response.getEntity());
        List<ChatMessage> chatMessages = new ArrayList<>();
        JSONObject resultJsonObject = new JSONObject(resultJson);
        if (resultJsonObject.has("messages")) {
            JSONArray jsonChatMessages = resultJsonObject.getJSONArray("messages");
            System.out.println(jsonChatMessages);
            for (int i = 0; i < jsonChatMessages.length(); i++) {
                chatMessages.add(new ChatMessage(jsonChatMessages.getJSONObject(i)));
            }
        } else {
            System.out.println("returned json did not contain publications JSON: " + resultJsonObject.toString());
        }
        System.out.println(chatMessages.size());
        return chatMessages;
    }

    public List<RequestToContribute> getCurrentUserRequests() throws URISyntaxException, HttpException, IOException {
        // Create a new HttpClient and Get Sequence number
        HttpClient httpClient = new DefaultHttpClient();
        String restUri = REST_API_URL + format("fake/user/%s/requests", CurrentUser.sharedInstance.getId());

        HttpGet httpGet = new HttpGet(restUri);

        HttpResponse response = httpClient.execute(httpGet);

        String resultJson = EntityUtils.toString(response.getEntity());
        List<RequestToContribute> contributeRequests = new ArrayList<>();
        JSONObject resultJsonObject = new JSONObject(resultJson);

        if (resultJsonObject.has("requests")) {
            JSONArray jsonRequests = resultJsonObject.getJSONArray("requests");
            for (int i = 0; i < jsonRequests.length(); i++)
            {
                contributeRequests.add(new RequestToContribute(jsonRequests.getJSONObject(i)));
            }
        } else {
            System.out.println("returned json did not contain contributeRequests JSON: " + resultJsonObject.toString());
        }

        return contributeRequests;
    }


    public Boolean checkUserFollowsPublicationById(String publicationId, String userId) throws URISyntaxException, IOException, HttpException {
        // Create a new HttpClient and Get Sequence number
        HttpClient httpClient = new DefaultHttpClient();
        String restUri = REST_API_URL + format("fake/user/%s/follows/%s",userId, publicationId);

        HttpGet httpGet = new HttpGet(restUri);

        HttpResponse response = httpClient.execute(httpGet);
        String resultJson = EntityUtils.toString(response.getEntity());
        return resultJson == "true";
    }

    public Boolean requestToContributeToPublicationById(String publicationId) throws URISyntaxException, IOException, HttpException {
        // Create a new HttpClient and Get Sequence number
        HttpClient httpClient = new DefaultHttpClient();
        String restUri = REST_API_URL + format("fake/user/%s/pub/%s/contribute", CurrentUser.sharedInstance.getId(), publicationId);

        HttpPost httpPost = new HttpPost(restUri);

        HttpResponse response = httpClient.execute(httpPost);
        EntityUtils.toString(response.getEntity());
        return true;
    }

    public Boolean retractRequestToContributeToPublicationById(String publicationId) throws URISyntaxException, IOException, HttpException {
        // Create a new HttpClient and Get Sequence number
        HttpClient httpClient = new DefaultHttpClient();
        String restUri = REST_API_URL + format("fake/pubs/contribute/%s/%s", publicationId, CurrentUser.sharedInstance.getId());

        HttpDelete httpDelete = new HttpDelete(restUri);

        HttpResponse response = httpClient.execute(httpDelete);
        //if null, the server ended the request successfully.
        return response.getEntity() == null;
    }

    public UserRestCallResult loginUser(String userName, String password) throws URISyntaxException, IOException, HttpException {
        // Create a new HttpClient and Get Sequence number
        UserRestCallResult resultData;
        HttpClient httpClient = new DefaultHttpClient();
        String restUri = REST_API_URL + format("fake/medium/login/%s/%s", userName, password);

        HttpPost httpPost = new HttpPost(restUri);

        HttpResponse response = httpClient.execute(httpPost);
        //if null, the server ended the request successfully

        String resultJson = EntityUtils.toString(response.getEntity());
        JSONObject resultJsonObject = new JSONObject(resultJson);
        System.out.println(resultJsonObject);
        if (resultJsonObject.has("password")) {
            User user = new User(resultJsonObject);
            resultData = new UserRestCallResult(user);
        } else if (resultJsonObject.has("error")) {
            resultData = new UserRestCallResult(resultJsonObject.getString("error"));
        } else {
            resultData = new UserRestCallResult("Unknown error.");
        }

        return resultData;
    }

    public UserSettingsRestCallResult getCurrentUserSettings() throws URISyntaxException, IOException, HttpException {
        UserSettingsRestCallResult userSettingsRestCallResult;
        if(CurrentUser.sharedInstance.getIsLoggedIn()){
            userSettingsRestCallResult = new UserSettingsRestCallResult();
            HttpClient httpClient = new DefaultHttpClient();
            String restUri = REST_API_URL + format("fake/user/%s/settings", CurrentUser.sharedInstance.getId());
            HttpGet httpGet = new HttpGet(restUri);
            HttpResponse response = httpClient.execute(httpGet);

            String resultJson = EntityUtils.toString(response.getEntity());
            JSONObject resultJsonObject = new JSONObject(resultJson);
            JSONArray settings = resultJsonObject.getJSONArray("settings");

            if (settings.length() > 0) {
                boolean instantMessageBool = false;
                boolean requestDecisionBool = false;
                userSettingsRestCallResult = new UserSettingsRestCallResult(instantMessageBool, requestDecisionBool);
                for(Object object: settings){
                    JSONObject obj = (JSONObject) object;
                    if(obj.getString("key").equals(UserSettingsRestCallResult.new_message_notification_instant)){
                        userSettingsRestCallResult.setInstantMessage(obj.getBoolean("value"));
                    } else if(obj.getString("key").equals(UserSettingsRestCallResult.contribution_request_decision)){
                        userSettingsRestCallResult.setStatusRequest(obj.getBoolean("value"));
                    }
                }
            }

        } else {
            userSettingsRestCallResult =  new UserSettingsRestCallResult();
        }
        return userSettingsRestCallResult;
    }

    public boolean updateCurrentUserSettings(String key, boolean value) throws URISyntaxException, IOException, HttpException {
        HttpClient httpClient = new DefaultHttpClient();
        String restUri = REST_API_URL + format("fake/user/%s/setting/%s/%s",CurrentUser.sharedInstance.getId(),key,value);
        HttpPut httpPut = new HttpPut(restUri);
        HttpResponse response = httpClient.execute(httpPut);
        System.out.println(response.getEntity());
        return false;
    }
}
