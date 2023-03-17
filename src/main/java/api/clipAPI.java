package api;

import org.json.JSONArray;
import org.json.JSONObject;
import rest.Clip;
import rest.ClipQuery;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: twitch api requires dates in RFC3399 format???
// a lot of Clip objects: maybe memory management by deleting clip objects when their finished downloading?
public class clipAPI extends twitchAPI{

    public clipAPI(String CLIENT_ID, String CLIENT_SECRET) {
        super(CLIENT_ID, CLIENT_SECRET);
    }

    // clips can be downloaded either query / url.
    public Clip getClip(ClipQuery query){
        HttpResponse<String> response = clipRequest(query.toString());
        return parseClipJSON(response.body());
    }
    public Clip getClip (String clip_url){     // all endpoints at twitch API require clientid/authorization
        String queryString = "id=" + clip_url;
        HttpResponse<String> response = clipRequest(queryString);
        return parseClipJSON(response.body());
    }

    //https://dev.twitch.tv/docs/api/reference#get-clips
    private HttpResponse<String> clipRequest(String queryString) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format("https://api.twitch.tv/helix/clips?%s", queryString)))
                .setHeader("Client-Id", CLIENT_ID)
                .setHeader("Authorization", "Bearer " + AUTH_TOKEN)
                .build();

        return send(request);
    }


    public Clip parseClipJSON(String body){
        System.out.println(body);
        String url = "not_found.mp4";

        JSONObject obj =  new JSONObject(body);
        JSONArray arr = obj.getJSONArray("data");
        JSONObject object = arr.getJSONObject(0);

        Pattern pattern = Pattern.compile("^(.*?)-preview-");   // some random regex
        Matcher matcher = pattern.matcher(object.getString("thumbnail_url"));
        if (matcher.find()) url = matcher.group(1) + ".mp4";

        return new Clip(object.getString("title"), url, object.getString("broadcaster_name"), object.getString("url"));
    }
}


