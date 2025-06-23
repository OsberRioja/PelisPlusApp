package com.ucb.coffeespotapp.screen

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ucb.coffeespotapp.R

fun sendWelcomeNotification(context: Context, username: String) {
    val channelId = "welcome_channel"
    val channelName = "Welcome Notifications"
    val notificationId = 1

    // Crear el canal de notificación (solo para Android 8.0 o superior)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Canal para notificaciones de bienvenida"
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // Verifica el permiso antes de intentar enviar la notificación
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        // Crear y mostrar la notificación
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Cambia al ícono que desees
            .setContentTitle("¡Bienvenido de nuevo!")
            .setContentText("Hola $username, disfruta explorando tus películas favoritas.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true) // Desaparece al tocar la notificación
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }
    } else {
        // Si el permiso no se concede, no envía la notificación
    }
}
