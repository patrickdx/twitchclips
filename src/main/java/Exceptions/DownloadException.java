package Exceptions;

import java.io.IOException;

public class DownloadException extends IOException {
    public DownloadException(String error) {
        super("Download error occurred. " + error);
    }
}
