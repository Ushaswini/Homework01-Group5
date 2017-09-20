package example.com.messageme;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class InboxActivity extends AppCompatActivity {

    ListView messagesListView;
    ArrayList<CustMessage> messagesList;
    MessagesArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        //menu.setIcon(R.drawable.ic_launcher);
        menu.setLogo(R.drawable.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);
        menu.setDisplayShowTitleEnabled(true);
        menu.setTitle("Inbox");
        setContentView(R.layout.activity_inbox);

        messagesListView = (ListView)findViewById(R.id.listViewMessages);
        final RequestParams params = new RequestParams("POST", "http://apidevelopment-inclass01.azurewebsites.net/Token");
        /*params.addParams("username", username);
        params.addParams("password", pwd);
        params.addParams("grant_type", "password");*/
        new GetMessagesAsyncTask(new GetMessagesAsyncTask.IAsyncPassMessages() {
            @Override
            public void getArrayList(ArrayList<CustMessage> messagesArrayList) {
                messagesList = messagesArrayList;
                sortList();
                adapter = new MessagesArrayAdapter(InboxActivity.this,R.layout.listitem,messagesList);
                messagesListView.setAdapter(adapter);
                adapter.setNotifyOnChange(true);
            }

            @Override
            public Context getContext() {
                return null;
            }
        }).execute(params);

        messagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inbox_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void sortList(){

        /*Collections.sort(messagesList, new Comparator<CustMessage>() {
            @Override
            public int compare(CustMessage o1, CustMessage o2) {
                //return Double.parseDouble(o1.getTrackPrice()) - Double.parseDouble(o2.getTrackPrice());

                if(o1.getDate() > o2.getDate())

                    return 1;
                else
                    return -1;
            }
        });*/


    }
}
