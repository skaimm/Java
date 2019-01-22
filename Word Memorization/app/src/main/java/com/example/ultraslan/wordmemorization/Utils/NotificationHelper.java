package com.example.ultraslan.wordmemorization.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.example.ultraslan.wordmemorization.Activity.StudyActivity;
import com.example.ultraslan.wordmemorization.R;

public class NotificationHelper extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent ıntent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, StudyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_down_float)
                .setContentTitle(String.valueOf(R.string.notificationtitle))
                .setContentText(String.valueOf(R.string.notificationtext))
                .setAutoCancel(true);
        notificationManager.notify(100,builder.build());
    }
}
