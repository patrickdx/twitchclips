package api;

import Exceptions.APIException;
import org.json.JSONObject;

// validates api responses
// status codes: https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#server_error_responses
public class Validator {
    //
    public static void validate(String responseBody) throws APIException {      // example: {"error":"Unauthorized","status":401,"message":"Invalid OAuth token"}
        System.out.println(responseBody);
        JSONObject obj =  new JSONObject(responseBody);

        if (obj.toMap().containsKey("status") && obj.getInt("status") >= 400){         // if response was bad
            throw new APIException(responseBody);
        }
    }

    public static void main (String [] args){
        JSONObject obj =  new JSONObject("{\"error\":\"Unauthorized\",\"status\":401,\"message\":\"Invalid OAuth token\"}\n");
        System.out.println(obj.get("status"));
        int status = obj.getInt("status");
    }
}
