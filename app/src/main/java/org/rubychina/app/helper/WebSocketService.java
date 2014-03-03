package org.rubychina.app.helper;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.saulpower.fayeclient.FayeClient;

import org.json.JSONObject;
import org.rubychina.app.R;
import org.rubychina.app.model.Message;
import org.rubychina.app.model.Notification;
import org.rubychina.app.ui.MainActivity;
import org.rubychina.app.ui.NotificationActivity;
import org.rubychina.app.utils.ApiUtils;
import org.rubychina.app.utils.UserUtils;

import java.net.URI;

/**
 * Created by mac on 14-3-3.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class WebSocketService extends IntentService implements FayeClient.FayeListener{
    private final static String TAG = "WebSocketService";

    FayeClient mClient;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public WebSocketService() {
        super("WebSocketService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Starting Web Socket");
        URI uri = URI.create(ApiUtils.FAYE_SERVER);
        String channel = String.format(ApiUtils.FAYE_CHANNEL, UserUtils.getUserTempToken());
        mClient = new FayeClient(null, uri, channel);
        mClient.setFayeListener(this);
        mClient.connectToServer(null);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void connectedToServer() {
        Log.i(TAG, "Connected to Server");
    }

    @Override
    public void disconnectedFromServer() {
        Log.i(TAG, "Disonnected to Server");
    }

    @Override
    public void subscribedToChannel(String subscription) {
        Log.i(TAG, String.format("Subscribed to channel %s on Faye", subscription));
    }

    @Override
    public void subscriptionFailedWithError(String error) {
        Log.i(TAG, String.format("Subscription failed with error: %s", error));
    }

    @Override
    public void messageReceived(JSONObject json) {
        Log.i(TAG, String.format("Received message %s", json.toString()));
        Message message = new Message().getInstance(json.toString());
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(message.title)
                        .setContentText(message.content)
                        .setAutoCancel(true);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class).putExtra(MainActivity.ACTIVITY_EXTRA, NotificationActivity.class.getName());

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }
}
