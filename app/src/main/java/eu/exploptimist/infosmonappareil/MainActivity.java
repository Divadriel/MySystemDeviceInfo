package eu.exploptimist.infosmonappareil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String allInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(myToolbar);

        // binding UI
        TextView brandValue = findViewById(R.id.info_pane_brand_value);
        TextView manufacturerValue = findViewById(R.id.info_pane_manufacturer_value);
        TextView modelValue = findViewById(R.id.info_pane_model_value);
        TextView deviceValue = findViewById(R.id.info_pane_device_value);
        TextView productValue = findViewById(R.id.info_pane_product_value);
        TextView hardwareValue = findViewById(R.id.info_pane_hardware_value);
        TextView sensorsValue = findViewById(R.id.info_pane_sensors_value);
        TextView releaseVersionValue = findViewById(R.id.sdk_pane_release_version_value);
        TextView releaseNameValue = findViewById(R.id.sdk_pane_release_name_value);
        TextView sdkValue = findViewById(R.id.sdk_pane_sdk_value);
        TextView buildStringValue = findViewById(R.id.sdk_pane_build_string_value);

        // finding the release name
        Field[] fields = Build.VERSION_CODES.class.getFields();
        String codeName = "UNKNOWN";
        for (Field field : fields) {
            try {
                if (field.getInt(Build.VERSION_CODES.class) == Build.VERSION.SDK_INT) {
                    codeName = field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // finding all sensors and filling them to a string
        StringBuilder allSensors = new StringBuilder();
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> allSensorsList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : allSensorsList) {
            allSensors.append(sensor.getName()).append("\n");
        }

        // filling the TextViews
        brandValue.setText(Build.BRAND);
        manufacturerValue.setText(Build.MANUFACTURER);
        modelValue.setText(Build.MODEL);
        deviceValue.setText(Build.DEVICE);
        productValue.setText(Build.PRODUCT);
        hardwareValue.setText(Build.HARDWARE);
        sensorsValue.setText(allSensors.toString());

        sdkValue.setText(String.valueOf(Build.VERSION.SDK_INT));
        releaseVersionValue.setText(Build.VERSION.RELEASE);
        releaseNameValue.setText(codeName);
        buildStringValue.setText(Build.DISPLAY);

        // Building all infos string to be able to share it
        StringBuilder allInfosBuilder = new StringBuilder();
        allInfosBuilder
                .append(getString(R.string.about_author_name_label)).append("\t").append(getString(R.string.about_author_name)).append("\n")
                .append(getString(R.string.about_contact_email_label)).append("\t").append(getString(R.string.about_contact_email)).append("\n")
                .append(getString(R.string.about_app_version)).append("\t").append(getString(R.string.app_version)).append("\n\n")
                .append(getString(R.string.info_pane)).append("\n")
                .append(getString(R.string.info_pane_brand)).append("\t").append(Build.BRAND).append("\n")
                .append(getString(R.string.info_pane_manufacturer)).append("\t").append(Build.MANUFACTURER).append("\n")
                .append(getString(R.string.info_pane_model)).append("\t").append(Build.MODEL).append("\n")
                .append(getString(R.string.info_pane_device)).append("\t").append(Build.DEVICE).append("\n")
                .append(getString(R.string.info_pane_product)).append("\t").append(Build.PRODUCT).append("\n")
                .append(getString(R.string.info_pane_hardware)).append("\t").append(Build.HARDWARE).append("\n")
                .append(getString(R.string.info_pane_sensors)).append("\n").append(allSensors).append("\n\n")
                .append(getString(R.string.sdk_pane)).append("\n")
                .append(getString(R.string.sdk_pane_release_version)).append("\t").append(Build.VERSION.RELEASE).append("\n")
                .append(getString(R.string.sdk_pane_sdk)).append("\t").append(Build.VERSION.SDK_INT).append("\n")
                .append(getString(R.string.sdk_pane_release_name)).append("\t").append(codeName).append("\n")
                .append(getString(R.string.sdk_pane_build_string)).append("\t").append(Build.DISPLAY).append("\n");
        allInfos = allInfosBuilder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            // launch about activity
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.menu_share){
            // share the content of this tab
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, allInfos);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}