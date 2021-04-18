package com.example.voting

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.voting.entity.User
import com.example.voting.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class signup : AppCompatActivity(), SensorEventListener {

    private lateinit var Frt: EditText
    private lateinit var lat: EditText
    private lateinit var eml: EditText
    private lateinit var ctz: EditText
    private lateinit var phn: EditText
    private lateinit var pws: EditText
    private lateinit var rpws: EditText
    private lateinit var sgn: Button
    private lateinit var log: TextView
    private lateinit var sensorManager: SensorManager
    private  var sensor: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        Frt = findViewById(R.id.Frt);
        lat = findViewById(R.id.lat);
        eml = findViewById(R.id.eml);
        ctz = findViewById(R.id.ctz);
        phn = findViewById(R.id.phn);
        pws = findViewById(R.id.pws);
        rpws = findViewById(R.id.rpws);
        sgn = findViewById(R.id.sgn)
        log = findViewById(R.id.log)

        if (!checkSensor())
            return
        else{
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        log.setOnClickListener{
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

            sgn.setOnClickListener {
                val Firstname = Frt.text.toString()
                val lastname = lat.text.toString()
                val citizenship = ctz.text.toString()
                val email = eml.text.toString()
                val phonenumber = phn.text.toString()
                val password = pws.text.toString()
                val repassword = rpws.text.toString()
                if (password != repassword) {

                    pws.error = "Password does not match"
                    pws.requestFocus()
                    return@setOnClickListener
                } else {
                    val user = User(
                            Firstname = Firstname,
                            Lastname = lastname,
                            Email= email,
                            Citizenship = citizenship,
                            Phonenumber = phonenumber,
                            Password = password
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val userRepository = UserRepository()
                            val response = userRepository.registerUser(user)
                            if (response.success == true) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@signup,
                                        response.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this@signup, login::class.java))
                                }
                            }
                            else{
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                            this@signup,
                                            response.message.toString(),
                                            Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (ex: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@signup,
                                    ex.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    // Api code goes here
                }
            }
        }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null){
            flag = false
        }
        return flag

    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[0]
        val params = this.window.attributes
        if (values < 4) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@signup, login::class.java)
                        startActivity(intent)
                    }
                }catch (ex: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@signup, "Error:$ex", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}

