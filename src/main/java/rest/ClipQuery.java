package rest;

import java.time.LocalDate;

public class ClipQuery extends Query {
    private String broadcaster;
    private String channelID;
    private int clipAmount;
    private LocalDate startingDate;
    private LocalDate endingDate;


    // Can delegate all input validation to the api response call
    public ClipQuery(String broadcaster, String channelID, int amount, LocalDate startingDate, LocalDate endingDate)  {
        this.broadcaster = broadcaster;
        this.channelID = channelID;
        this.clipAmount = amount;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }





    @Override
    public String toString() {
        return String.format("?broadcaster_id=%s&first=%d&started_at=%s&ended_at=%s", channelID, clipAmount, startingDate, endingDate);
    }

    public String getBroadcaster() {
        return broadcaster;
    }

    public String getChannelID() {
        return channelID;
    }

    public int getAmount() {
        return clipAmount;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }


    //    private void verifyQueryInfo() {
//        boolean validAmount = clipAmount > 1 && clipAmount <= 1000;
//        boolean validDate = endingDate.isAfter(startingDate);
//
//        if (!validAmount) throw new IllegalArgumentException("Invalid clip amount, specify 1-1000");
//        if (!validDate) throw new IllegalArgumentException("Illegal dates specified.");
//
//    }
//
//    private void verifyCredentials(){
//        String validUsername = "^[a-zA-Z0-9_]{4,25}+$";  // "Usernames must be at least 4-25 characters long and must only contain alphanumeric characters."
//
//        if (!broadcaster.matches(validUsername)) throw new IllegalArgumentException("Invalid username entered.");       // credientals / broadcaster names are only made up of alphanumeric characetrs
//        if (!channelID.matches("[0-9]+")) throw new IllegalArgumentException("channelID must consist of numbers.");
//    }
}