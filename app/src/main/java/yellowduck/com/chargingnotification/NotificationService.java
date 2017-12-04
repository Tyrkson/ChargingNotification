package yellowduck.com.chargingnotification;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;



public class NotificationService extends Service {

    boolean isEnabled;
    boolean isShowing;
    SharedPreferences sp;
    String action;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        onBind(intent);
        stopSelf();
        return super.onStartCommand(intent,flags,startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        sp = getApplicationContext().getSharedPreferences(getString(R.string.file_name), MODE_PRIVATE);
        isEnabled = sp.getBoolean("value", false);
        isShowing = sp.getBoolean("isShowing", false);
        action = intent.getExtras().getString("action", "");
        if(action.equals("android.intent.action.ACTION_POWER_CONNECTED")){
            if(isEnabled) showNotification();
        }else if(action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")){
            if(isShowing) closeNotification();
        }
        return null;
    }

    private void closeNotification() {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(123);

        saveData(false);
    }

    private void showNotification() {
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Charging")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Your phone is charging")
                .setOngoing(true);
        mNotifyMgr.notify(123, mBuilder.build());
        saveData(true);
    }

    private void saveData(boolean isShowing) {
        sp = getApplicationContext().getSharedPreferences(getString(R.string.file_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isShowing", isShowing);
        editor.apply();

    }
}
