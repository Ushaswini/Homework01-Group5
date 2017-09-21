package example.com.messageme;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ComposeMessageActivity extends AppCompatActivity {

    AlertDialog contactsList;
    AlertDialog.Builder contactsListBuilder;
    AlertDialog regionsList;
    AlertDialog.Builder regionsListBuilder;
    ImageView imgPerson, imgRegion;
    TextView toName, region;
    private BeaconManager beaconManager;
    private BeaconRegion region1, region2, region3;
    ArrayList<String> regionArrayList;
    ArrayList<String> allUsersList;
    boolean isareply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        //menu.setIcon(R.drawable.ic_launcher);
        menu.setLogo(R.drawable.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);
        menu.setDisplayShowTitleEnabled(true);
        menu.setTitle("Compose Message");
        setContentView(R.layout.activity_compose_message);

        isareply = false;
        regionArrayList = new ArrayList<>();

        toName = (TextView)findViewById(R.id.textViewToName);
        region = (TextView)findViewById(R.id.textViewRegionName);
        if(getIntent().getExtras() != null){
            toName.setText("TO: " + getIntent().getExtras().getString(ReadActivity.SENDER_KEY));
            region.setText("REGION: " + getIntent().getExtras().getString(ReadActivity.REGION_KEY));
            isareply = getIntent().getExtras().getBoolean(ReadActivity.REPLY_KEY,false);
        }

        contactsListBuilder = new AlertDialog.Builder(ComposeMessageActivity.this);

        if(!isareply) {
            imgPerson = (ImageView) findViewById(R.id.imageViewPerson);
            imgPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new GetUsersAsyncTask(new GetUsersAsyncTask.IGetProfile() {
                        @Override
                        public void getSenderName(ArrayList<String> names) {
                            allUsersList = names;
                            contactsListBuilder.setTitle("Contacts")
                                    .setCancelable(false)
                                    .setItems(allUsersList.toArray(new String[allUsersList.size()]), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            toName.setText("TO: " + allUsersList.get(i));
                                        }
                                    });
                            contactsList = contactsListBuilder.create();
                            contactsList.show();
                        }
                    }).execute("http://homework01.azurewebsites.net/api/Users");

                }
            });
        }

        regionsListBuilder = new AlertDialog.Builder(ComposeMessageActivity.this);
        regionsListBuilder.setTitle("Regions")
                .setCancelable(false)
                .setItems(new CharSequence[10], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        region.setText("REGION: " + regionArrayList.get(i));
                    }
                });

        regionsList = regionsListBuilder.create();
        imgRegion = (ImageView)findViewById(R.id.imageViewRegion);
        imgRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SystemRequirementsChecker.checkWithDefaultDialogs(ComposeMessageActivity.this);

                beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                    @Override
                    public void onServiceReady() {
                        try {
                            beaconManager.startRanging(region1);
                            beaconManager.startRanging(region2);
                            beaconManager.startRanging(region3);
                        }catch(Exception ex){
                            Log.d("Exception","Cannot start ranging");
                        }

                    }
                });
                if(regionArrayList.size() > 0) {
                    regionsList.show();
                    beaconManager.stopRanging(region1);
                    beaconManager.stopRanging(region2);
                    beaconManager.stopRanging(region3);
                }else{
                    Toast.makeText(ComposeMessageActivity.this,"No regions found",Toast.LENGTH_SHORT).show();
                }
            }
        });

        region1 = new BeaconRegion("Region 1", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),1564,null);
        region2 = new BeaconRegion("Region 2", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),15212,null);
        region3 = new BeaconRegion("Region 3", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),26535,null);
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {
                if(!beacons.isEmpty()){
                    for (Beacon b : beacons){
                        regionArrayList.add(b.getUniqueKey());
                    }
                }else{
                    Toast.makeText(ComposeMessageActivity.this,"No regions available",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
