package api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exceptions.DownloadException;
import api.twitchAPI;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rest.Clip;

public class ClipHelper {

    // maybe later special handling for another prompt download
    public static void downloadClip(Clip clip) throws DownloadException{
        String invalidPattern = "[\\\\\\/:*?\"<>|]|[ \\f\\t\\v]$";      //A filename cannot contain any of the following characters: \ / : * ? " < > |
        String title = clip.getTitle().replaceAll(invalidPattern, "");
        try {
            Files.createDirectories(Paths.get(".\\saved_clips\\" + clip.getBroadcaster()));      // for new startup
            System.out.println("downloaded: " + "\"" + title +"\"" +  " from: " + clip.getUrl());
            String clipPath = String.format(".\\saved_clips\\%s\\%s.mp4", clip.getBroadcaster(), title);
            FileUtils.copyURLToFile(new URL(clip.getMp4_url()), new File(clipPath));
        } catch (IOException e) {
            throw new DownloadException(String.format("%s-%s could not be downloaded.", clip.getTitle(), clip.getUrl()));
        }

    }

    // "clean" mp4s in /saved_vids
    public static void cleanVideos() throws IOException {
//      File saved_clips = new File (".\\src\\main\\resources\\saved_clips\\");
        FileUtils.cleanDirectory(new File(".\\saved_clips\\"));
    }



}
