package yellowduck.com.chargingnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class ChargingReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Receiver", Toast.LENGTH_SHORT).show();
        context.startService(new Intent(context, NotificationService.class).putExtra("action", intent.getAction()));;
    }
}
