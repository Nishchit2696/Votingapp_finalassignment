package com.example.voting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class signup : AppCompatActivity() {

    private lateinit var Frt: EditText
    private lateinit var lat: EditText
    private lateinit var eml: EditText
    private lateinit var ctz: EditText
    private lateinit var phn: EditText
    private lateinit var pws: EditText
    private lateinit var rpws: EditText
    private lateinit var sgn: Button
    private lateinit var log: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        Frt = findViewById(R.id.Frt);
        lat = findViewById(R.id.lat);
        eml = findViewById(R.id.eml);
        ctz = findViewById(R.id.ctz);
        phn = findViewById(R.id.phn);
        pws = findViewById(R.id.pws);
        rpws = findViewById(R.id.rpws);
        sgn = findViewById(R.id.sgn)
        log = findViewById(R.id.log)

        log.setOnClickListener{
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        sgn.setOnClickListener {

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
    }
}
