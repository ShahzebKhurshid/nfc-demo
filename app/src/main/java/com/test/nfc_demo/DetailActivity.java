package com.test.nfc_demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private int selected;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView address;
    private TextView linkedin;


    //Data array
    private String[] nameArr = {"James Pepe", "Shahzeb Khurshid", "Katie P", "Boxin Chao"};
    private String[] phoneNoArr = {" +1 999 999 0000", " +1 978 930 0000", " +1 978 930 0000", " +1 978 920 0000"};
    private String[] emailArr = {"pepe.j@bentley.edu", "Shahzeb_K@bentley.edu", "Katie_p@bentley.edu", "Boxin_C@bentley.edu"};
    private String[] addressArr = {"175 Forest Street, Waltham, 02452, MA."};
    private String[] linkedInArr = {"www.linkedin.com/in/James-Pepe", "www.linkedin.com/in/shahzeb-khurshid", "www.linkedin.com/in/Katie-P", "www.linkedin.com/in/Boxin-Chao"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //the selected data position from previous screen
        selected = getIntent().getIntExtra("key", 0);

        //declare the views
        name = findViewById(R.id.name_txt);
        phone = findViewById(R.id.phone_txt);
        email = findViewById(R.id.email_txt);
        address = findViewById(R.id.address_txt);
        linkedin = findViewById(R.id.linkedin_txt);

        //set On clicks
        phone.setOnClickListener(this);
        email.setOnClickListener(this);
        address.setOnClickListener(this);
        linkedin.setOnClickListener(this);


        //set data
        name.setText(nameArr[selected]);
        phone.setText(phoneNoArr[selected]);
        email.setText(emailArr[selected]);
        address.setText(addressArr[0]);
        linkedin.setText(linkedInArr[selected]);

    }


    //on back button press to go back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //on click of data action occurs here
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.address_txt) {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 42.390900, -71.222220);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        if (id == R.id.email_txt) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailArr[selected]});
            intent.putExtra(Intent.EXTRA_SUBJECT, "NFC Demo");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        if (id == R.id.linkedin_txt) {
            String url = linkedInArr[selected];
            if (!url.startsWith("https://") && !url.startsWith("http://")){
                url = "http://" + url;
            }
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        if (id == R.id.phone_txt) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setMessage("Complete Action Using");
            builder.setCancelable(true);

            builder.setPositiveButton(
                    "Call",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent call = new Intent(Intent.ACTION_DIAL);
                            call.setData(Uri.parse("tel:"+phoneNoArr[selected]));
                            if (call.resolveActivity(getPackageManager()) != null) {
                                startActivity(call);
                            }
                            dialog.cancel();
                        }
                    });

            builder.setNegativeButton(
                    "Text",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setData(Uri.parse("smsto:" + phoneNoArr[selected]));
                            intent.putExtra("sms_body", "NFC Demo");
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
