package com.example.voting

import android.app.NotificationChannel
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.voting.Notification.NotificationChannels
import com.example.voting.api.ServiceBuilder
import com.example.voting.entity.User
import com.example.voting.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class login : AppCompatActivity() {

    private val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var cts: EditText
    private lateinit var paw : EditText
    private lateinit var Register : TextView
    private lateinit var submit : Button
    private lateinit var chkRememberMe: CheckBox
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        cts = findViewById(R.id.cts);
        paw = findViewById(R.id.paw);
        Register = findViewById(R.id.Register);
        submit = findViewById(R.id.submit)
        chkRememberMe = findViewById(R.id.chkRememberMe)
        linearLayout = findViewById(R.id.linearLayout)

        checkRunTimePermission()

        Register.setOnClickListener{
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        submit.setOnClickListener{
            Login()
            showHighPriorityNotification()
        }
    }

    private fun showHighPriorityNotification() {
        val notificationManager = NotificationManagerCompat.from(this)

        val notificationChannel = NotificationChannels (this)
        notificationChannel.createNotificationChannels()

        val notification = NotificationCompat.Builder(this, notificationChannel.CHANNEL_1)
            .setSmallIcon(R.drawable.np)
            .setContentTitle("Citizen notifies you")
            .setContentText("Hello ${cts.text} Welcome to Citizen App")
            .setColor(Color.GRAY)
            .build()
        notificationManager.notify(1, notification)
    }

    private fun checkRunTimePermission() {
        if (!hasPermission()) {
            requestPermission()
        }
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            permission
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@login, permissions, 1)
    }

    private fun Login() {
        val citizenship = cts.text.toString()
        val password = paw.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.checkUser(citizenship, password)
                if (response.success == true) {
                    ServiceBuilder.token = "Bearer " + response.token
                    startActivity(
                            Intent(
                                    this@login,
                                    Dashboard::class.java
                            )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                                Snackbar.make(
                                        linearLayout,
                                        response.message.toString(),
                                        Snackbar.LENGTH_LONG
                                )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                            this@login,
                            ex.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}