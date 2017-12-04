package yellowduck.com.chargingnotification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    Switch notificationSwitch;
    SharedPreferences sp;
    boolean isEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getApplicationContext().getSharedPreferences(getString(R.string.file_name), Context.MODE_PRIVATE);
        isEnabled = sp.getBoolean("value", false);
        notificationSwitch = (Switch) findViewById(R.id.sShowNotification);
        notificationSwitch.setChecked(isEnabled);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) enableNotification();
                else  disableNotification();
            }
        });
        //startService(new Intent(this, NotificationService.class).putExtra("action", "android.intent.action.ACTION_POWER_CONNECTED"));
}

    private void disableNotification() {
        isEnabled = false;
        saveData(isEnabled);

    }

    private void enableNotification() {
        isEnabled = true;
        saveData(isEnabled);
    }

    private void saveData(boolean isEnabled) {
        sp = getApplicationContext().getSharedPreferences(getString(R.string.file_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("value", isEnabled);
        editor.apply();

    }
}
