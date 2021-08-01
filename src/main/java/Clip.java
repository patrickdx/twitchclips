public class Clip {

    private String title;
    private String mp4_url;
    private String broadcaster;
    private String url;


    public Clip (String title, String mp4_url, String broadcaster, String url){

        this.title = title;
        this.mp4_url = mp4_url;
        this.broadcaster = broadcaster;
        this.url = url;

    }

    public String getMp4_url(){
        return this.mp4_url;
    }
    public String getTitle(){
        return this.title;
    }
    public String getBroadcaster(){
        return this.broadcaster;
    }
    public String getUrl(){
        return this.url;
    }
    public static void main(String[] args)  {
     
    }


}
