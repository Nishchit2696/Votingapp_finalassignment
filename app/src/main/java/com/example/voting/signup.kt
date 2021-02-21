package com.example.voting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.voting.Userdb.UserDB
import com.example.voting.entity.User
import com.example.voting.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class signup : AppCompatActivity() {

    private lateinit var Frt: EditText
    private lateinit var lat: EditText
    private lateinit var eml: EditText
    private lateinit var ctz: EditText
    private lateinit var phn: EditText
    private lateinit var pws: EditText
    private lateinit var rpws: EditText
    private lateinit var sgn: Button


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
        sgn.setOnClickListener {

            sgn.setOnClickListener {
                val Firstname = Frt.text.toString()
                val lastname = lat.text.toString()
                val citizenship = ctz.text.toString()
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
                                        "Register Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (ex: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@signup,
                                    "Username already exists",
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
