package com.example.voting

import android.app.NotificationChannel
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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

class login : AppCompatActivity(), SensorEventListener {

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
    private lateinit var sensorManager: SensorManager
    private  var sensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        cts = findViewById(R.id.cts);
        paw = findViewById(R.id.paw);
        Register = findViewById(R.id.Register);
        submit = findViewById(R.id.submit)
        chkRememberMe = findViewById(R.id.chkRememberMe)
        linearLayout = findViewById(R.id.linearLayout)

        checkRunTimePermission()

        if (!checkSensor())
            return
        else{
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        Register.setOnClickListener{
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        submit.setOnClickListener{
            Login()
            showHighPriorityNotification()
        }
    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null){
            flag = false
        }
        return flag

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

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[1]
        if(values > 5){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@login, signup::class.java)
                        startActivity(intent)
                    }
                }catch (ex: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@login, "Error:$ex", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}