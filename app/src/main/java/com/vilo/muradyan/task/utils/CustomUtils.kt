package com.vilo.muradyan.task.utils

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.vilo.muradyan.task.R
import com.vilo.muradyan.task.utils.Constants.Companion.CHANNEL_ID
import com.vilo.muradyan.task.utils.Constants.Companion.NOTIFICATION_ID
import com.vilo.muradyan.task.utils.Constants.Companion.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.vilo.muradyan.task.utils.Constants.Companion.VERBOSE_NOTIFICATION_CHANNEL_NAME
import com.google.android.material.snackbar.Snackbar

fun isNetworkConnected(context: Context): Boolean {
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (manager != null) {
        val networkInfo = manager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected && networkInfo.isAvailable) {
            return true
        }
    }
    return false
}

fun hideSoftInput(activity: Activity) {
    var view = activity.currentFocus
    if (view == null) view = View(activity)
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showSoftInput(edit: EditText, context: Context) {
    edit.isFocusable = true
    edit.isFocusableInTouchMode = true
    edit.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(edit, 0)
}

fun Snackbar.customMake(@NonNull view: View, @NonNull text: CharSequence, actionText: CharSequence,
                        duration: Int = Snackbar.LENGTH_LONG, listener: View.OnClickListener): Snackbar {

    Snackbar.make(view, text, duration).setAction(actionText) {
        listener.onClick(view)
        this.dismiss()
    }.setActionTextColor(ContextCompat.getColor(context, R.color.md_yellow_500)).show()

    return this
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun displayNotification(task:String ,desc:String , context: Context){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(task)
        .setContentText(desc)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())


}