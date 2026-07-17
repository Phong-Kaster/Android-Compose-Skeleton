package com.example.skeleton.ui.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.skeleton.R
import com.example.skeleton.ui.fragment.home.component.isNotificationGranted

/**
 * The purpose of this object is to post system status-bar notifications — it is the "sending"
 * side of notifications, while the existing Home permission bottom sheet is the "asking" side.
 *
 * Think of it like mailing a letter:
 * 1. [createChannel] builds the mailbox once (Android 8+ requires a channel; on older
 *    Android versions the call safely does nothing).
 * 2. [postSimpleMessage] drops a letter (title + message) into that mailbox.
 *
 * It never crashes when the user has not allowed notifications — it simply stays silent.
 * Reusable in any project: copy this file, keep the channel id, swap the strings.
 *
 * Usage example (e.g. in `Application.onCreate()`):
 * ```kotlin
 * NotificationUtil.createChannel(context = this)
 * NotificationUtil.postSimpleMessage(
 *     context = this,
 *     title = getString(R.string.skeleton),
 *     message = getString(R.string.hello_world),
 * )
 * ```
 *
 * @author Phong-Kaster
 */
object NotificationUtil {

    /**
     * Creates the default notification channel used by [postSimpleMessage].
     *
     * Channels only exist on Android 8.0+ (API 26). `NotificationManagerCompat` already knows
     * this: below API 26 this call is a no-op, so no `Build.VERSION` guard is needed here.
     * Calling it repeatedly is safe — Android ignores re-creation of an existing channel.
     *
     * @param context any context; the application context is enough.
     * @author Phong-Kaster
     */
    fun createChannel(context: Context) {
        val channel = NotificationChannelCompat.Builder(
            CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_DEFAULT,
        )
            .setName(context.getString(R.string.notification))
            .setDescription(context.getString(R.string.important_updates_and_alerts))
            .build()

        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    /**
     * Posts a simple text notification to the status bar.
     *
     * Permission story, explained simply:
     * - On Android 13+ the user must grant `POST_NOTIFICATIONS` first (the Home screen's
     *   permission bottom sheet asks for it). Not granted → we silently do nothing.
     * - On older Android versions we only check that notifications are enabled in settings.
     *
     * The lint suppression is safe because [isNotificationGranted] performs the exact
     * permission check lint asks for; the extra try/catch is a last line of defense so a
     * `SecurityException` can never crash the app.
     *
     * @param context any context; the application context is enough.
     * @param title short bold first line of the notification.
     * @param message the content text shown under the title.
     * @param notificationId re-posting with the same id replaces the previous notification.
     * @author Phong-Kaster
     */
    @SuppressLint("MissingPermission")
    fun postSimpleMessage(
        context: Context,
        title: String,
        message: String,
        notificationId: Int = DEFAULT_NOTIFICATION_ID,
    ) {
        if (!isNotificationGranted(context)) return

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(notificationId, notification)
        } catch (e: SecurityException) {
            Log.e(TAG, "postSimpleMessage: POST_NOTIFICATIONS not granted", e)
        }
    }

    private const val TAG = "NotificationUtil"

    /** One general-purpose channel for the whole skeleton app. */
    private const val CHANNEL_ID = "skeleton_general"

    /** Default id — cold-start greetings replace each other instead of piling up. */
    private const val DEFAULT_NOTIFICATION_ID = 1
}
