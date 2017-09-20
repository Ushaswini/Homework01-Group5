package example.com.messageme;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ReadActivity extends AppCompatActivity {

    ActionBar actionBar;
    ImageButton deleteButton;
    ImageButton replyButton;
    ImageView imgPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        //menu.setIcon(R.drawable.ic_launcher);
        menu.setLogo(R.drawable.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);
        menu.setDisplayShowTitleEnabled(true);
        //menu.setTitle("Read Message");
        setContentView(R.layout.activity_read);

           /*deleteButton = new ImageButton(ReadActivity.this);
        deleteButton.setImageResource(R.drawable.ic_action_discard);
        replyButton = new ImageButton(ReadActivity.this);
        replyButton.setImageResource(R.drawable.ic_action_reply);*/
        //actionBar.setCustomView(deleteButton);
        //actionBar.setCustomView(replyButton);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_message_menu, menu);
        return true;
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }
}
