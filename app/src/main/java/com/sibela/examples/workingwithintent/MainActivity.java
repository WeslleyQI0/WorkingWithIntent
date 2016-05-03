package com.sibela.examples.workingwithintent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.display_contacts_button)
    void displayContacts() {
        Uri uri = Uri.parse("content://com.android.contacts/contacts/");
        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        startActivity(intent);
    }

    @OnClick(R.id.make_call_button)
    void makeCall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            executeCall();
        }
    }

    private void executeCall() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intent.ACTION_CALL);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    intent.setData(Uri.parse("tel:912345678"));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    executeCall();
                }
            }
        }
    }

    @OnClick(R.id.open_map_button)
    void openMap() {

        // Fatec coordinates
        double latitude = -22.424973;
        double longitude = -46.950094;

        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @OnClick(R.id.map_source_to_destination)
    void openMapSourceToDestination() {

        double sourceLatitude = -22.424973;
        double sourceLongitude = -46.950094;
        double destinationLatitude = -22.5665354;
        double destinationLongitude = -47.3974368;

        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    @OnClick(R.id.map_current_location_to_destination)
    void openMapCurrentLocationToDestination() {

        double destinationLatitude = -22.5665354;
        double destinationLongitude = -47.3974368;

        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", destinationLatitude, destinationLongitude, "Where the party is at");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }


    @OnClick(R.id.send_mail_button)
    void sendMail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.random_recipient)});
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject));
        i.putExtra(Intent.EXTRA_TEXT, getString(R.string.mail_message));
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, R.string.no_mail_client_installed, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.send_sms_button)
    void sendSMS() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:983547000"));
        intent.putExtra("sms_body", getString(R.string.sms_message_to_be_sent));
        startActivity(intent);
    }

    @OnClick(R.id.use_browser_button)
    void useBrowser() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.send_whatsapp_messsage_button)
    void sendWhatsAppMessage() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_to_be_sent));
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    @OnClick(R.id.open_second_activity)
    void openNewActivity() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("key", "Just a test");
        startActivity(intent);
    }
}
