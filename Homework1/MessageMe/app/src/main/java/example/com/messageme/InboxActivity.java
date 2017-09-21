package example.com.messageme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class InboxActivity extends AppCompatActivity {

    ListView messagesListView;
    ArrayList<CustMessage> messagesList;
    MessagesArrayAdapter adapter;
    String receiverId;
    String token;
    final static String MSG_KEY = "MESSAGE";
    User user;
    final static String USER_KEY = "USER";


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
        messagesList = new ArrayList<>();

        if(getIntent().getExtras() != null){
            token = getIntent().getExtras().getString(LoginActivity.TOKEN_KEY);
        }
        new GetUserinfoAsyncTask(new GetUserinfoAsyncTask.IGetProfile() {
            @Override
            public void getUserProfile(User userProfile) {
                user = userProfile;
                receiverId= user.getId();



        receiverId = "b5a0df98-3b5e-460c-8bd5-bf9fd3ab5241";
        final RequestParams params = new RequestParams("GET", "http://homework01.azurewebsites.net/api/Messages");
        params.addParams("receiverId", receiverId);

        new GetMessagesAsyncTask(new GetMessagesAsyncTask.IAsyncPassMessages() {
            @Override
            public void getArrayList(ArrayList<CustMessage> messagesArrayList) {

                messagesList = messagesArrayList;
                sortList();
                adapter = new MessagesArrayAdapter(InboxActivity.this, R.layout.listitem, messagesList);
                messagesListView.setAdapter(adapter);
                adapter.setNotifyOnChange(true);
            }

            @Override
            public Context getContext() {
                return InboxActivity.this;
            }
        }).execute(params);

            }
        },token).execute("http://homework01.azurewebsites.net/api/Account/UserInfo");


        messagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new MessagesReadMarkAsncTask().execute("http://homework01.azurewebsites.net/api/Messages/EditReadStatus?messageId="+messagesList.get(i).getMsgId());
                Intent intent = new Intent(InboxActivity.this,ReadActivity.class);
                intent.putExtra(MSG_KEY,messagesList.get(i));
                startActivity(intent);
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
        if(item.getItemId() == R.id.composeButton){
            Intent intent = new Intent(InboxActivity.this,ComposeMessageActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.refreshButton){
            SharedPreferences mPrefs;
            mPrefs = getSharedPreferences("authToken",MODE_PRIVATE);
            token = mPrefs.getString("token","");
            if(!token.isEmpty()){
                Intent intent = new Intent(InboxActivity.this, InboxActivity.class);
                intent.putExtra(LoginActivity.TOKEN_KEY,token);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void sortList(){

        Collections.sort(messagesList, new Comparator<CustMessage>() {
            @Override
            public int compare(CustMessage o1, CustMessage o2) {
                int returnValue = 0;
                try {
                    if(new SimpleDateFormat("MM-dd-yyyy").parse(o1.getDate()).before(new SimpleDateFormat("MM-dd-yyyy").parse(o2.getDate())))// > 0 ? 1 : 0;
                    {returnValue = -1;}
                    else if(new SimpleDateFormat("MM-dd-yyyy").parse(o1.getDate()).after(new SimpleDateFormat("MM-dd-yyyy").parse(o2.getDate())))// > 0 ? 1 : 0;
                    {returnValue = 1; }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return returnValue;
            }
        });
    }
}
