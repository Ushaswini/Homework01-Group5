package example.com.messageme;

import android.os.*;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nitin on 6/6/2017.
 */

public class JSONUtil {

    public static ArrayList<CustMessage> parseMessageJSON(String s){
        ArrayList<CustMessage> messageArrayList = new ArrayList<CustMessage>();

        try {
            JSONObject root = new JSONObject(s);
            JSONArray newsArray = root.getJSONArray("articles");
            for(int i = 0; i < newsArray.length(); i++) {
                JSONObject newsJSONObject = newsArray.getJSONObject(i);
                CustMessage msg = new CustMessage();
                if(root.has("Sender")){
                    msg.setSender(root.getString("Sender"));
                }
                if(root.has("Receiver")) {
                    msg.setReceiver(root.getString("Receiver"));
                }
                if(root.has("message")) {
                    msg.setTextMessage(root.getString("message"));
                }
                if(root.has("region")) {
                    msg.setRegionName(root.getString("region"));
                }
                if(root.has("isRread")){
                    msg.setRead(root.getBoolean("isRead"));
                }
                messageArrayList.add(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messageArrayList;
    }

    public static String parseTokenJSON(String s)  {
        JSONObject root;// = null;
        String token = "";
        try {
            root = new JSONObject(s);
            Log.d("demo",s);
            token = root.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return token;
    }
}
