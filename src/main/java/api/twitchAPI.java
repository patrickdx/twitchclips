package api;

import Exceptions.APIException;
import org.json.JSONArray;
import org.json.JSONObject;
import rest.Clip;
import rest.ClipQuery;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static api.Validator.validate;


public class twitchAPI {


    protected String CLIENT_ID;
    protected String CLIENT_SECRET;
    protected String AUTH_TOKEN;

    public twitchAPI(String CLIENT_ID, String CLIENT_SECRET) {
        this.CLIENT_ID = CLIENT_ID;
        this.CLIENT_SECRET = CLIENT_SECRET;
        this.AUTH_TOKEN = genAuthToken();
    }

    // one instance, reuse
    private HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();


    //https://dev.twitch.tv/docs/authentication#validating-requests
    //"App access tokens expire after about 60 days, so you should check that your app access token is valid by submitting
    // a request to the validation endpoint (see Validating Requests). If your token has expired, generate a new one."


    public String genAuthToken() {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString("client_id=" + CLIENT_ID +
                        "&client_secret=" + CLIENT_SECRET +
                        "&grant_type=client_credentials"))
                .uri(URI.create("https://id.twitch.tv/oauth2/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response1 = send(request);
        JSONObject obj = new JSONObject(response1.body());
        return obj.getString("access_token");
    }

    public String getChannelID(String broadcasterName) {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format("https://api.twitch.tv/helix/users?login=%s", broadcasterName)))
                .setHeader("Client-Id", CLIENT_ID)
                .setHeader("Authorization", "Bearer " + AUTH_TOKEN)
                .build();

        HttpResponse<String> response = send(request);
        JSONObject json = new JSONObject(response.body());
        JSONArray data = json.getJSONArray("data");
        return data.getJSONObject(0).getString("id");
    }

    protected HttpResponse<String> send(HttpRequest request) throws APIException {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            validate(response.body());
            return response;
        } catch (IOException | InterruptedException e) {
            throw new APIException();
        }
    }
}

//    public static void generateToken(){         // will use same if not expired yet...
//
//
//        //validate token
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .GET()
//                .uri(URI.create("https://id.twitch.tv/oauth2/validate"))
//                .setHeader("Authorization", "OAuth " + api.twitchAPI.AUTH_TOKEN)
//                .build();
//
//        HttpResponse<String> response = null;
//        try {
//            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        //parse JSON
//        JSONObject obj1 = new JSONObject(response.body());
//
//
//        System.out.println(response.body());
//
//
//        int timeLeft= obj1.getInt("expires_in");
//        System.out.println("time left:" + timeLeft);
//
//        if (timeLeft < 24 * 2 * 60 * 60 ) {     // renew token if less than 2 days to expiry.
//
//            getAuthToken();
//            System.out.println("time expired.. renewed!" + AUTH_TOKEN);
//        }
//        else{
//            System.out.println("token already good :3 " + AUTH_TOKEN);
//        }
//    }



