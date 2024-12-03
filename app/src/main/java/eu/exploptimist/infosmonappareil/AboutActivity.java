package eu.exploptimist.infosmonappareil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.about_toolbar_id);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // bind UI
        MaterialButton websiteButton = findViewById(R.id.about_website_button);
        MaterialButton gpsButton = findViewById(R.id.about_gps_button);

        // actions
        websiteButton.setOnClickListener(view -> {
            Uri webpage = Uri.parse("https://apps.exploptimist.eu/mydevicesysteminfo/");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            try {
                startActivity(webIntent);
            } catch (ActivityNotFoundException e){
                Snackbar.make(view,
                        getResources().getString(R.string.website_action_error_message),
                Snackbar.LENGTH_SHORT
                ).show();
            }
        });
        gpsButton.setOnClickListener(view -> {
            Uri googlePlayPage = Uri.parse("https://play.google.com/store/apps/dev?id=5022612225819754448");
            Intent googlePlayPageIntent = new Intent(Intent.ACTION_VIEW, googlePlayPage);
            googlePlayPageIntent.setPackage("com.android.vending");

            try {
                startActivity(googlePlayPageIntent);
            } catch (ActivityNotFoundException e){
                Snackbar.make(view,
                        getResources().getString(R.string.google_play_action_error_message),
                        Snackbar.LENGTH_SHORT
                ).show();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    //TODO: add google play billing system
}