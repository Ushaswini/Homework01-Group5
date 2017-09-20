package example.com.messageme;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Nitin on 8/27/2017.
 */

public class GetMessagesAsyncTask extends AsyncTask<RequestParams,Void,ArrayList<CustMessage>> {

    ProgressDialog progressDialog;
    IAsyncPassMessages iAsyncPassMessages;
    public GetMessagesAsyncTask(IAsyncPassMessages iMessages){
        this.iAsyncPassMessages = iMessages;
    }

    public interface IAsyncPassMessages{
        void getArrayList(ArrayList<CustMessage> messagesArrayList);
        Context getContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(iAsyncPassMessages.getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected ArrayList<CustMessage> doInBackground(RequestParams... params) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection con = params[0].createConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line = "";

            while ((line = reader.readLine()) != null){
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONUtil.parseMessageJSON(sb.toString());
    }

    @Override
    protected void onPostExecute(ArrayList<CustMessage> trackArrayList) {
        super.onPostExecute(trackArrayList);
        iAsyncPassMessages.getArrayList(trackArrayList);
        progressDialog.dismiss();
    }
}
