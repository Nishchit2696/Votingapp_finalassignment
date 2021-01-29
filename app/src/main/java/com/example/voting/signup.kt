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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class signup : AppCompatActivity() {

    private lateinit var log : TextView
    private lateinit var Frt: EditText
    private lateinit var lat: EditText
    private lateinit var eml: EditText
    private lateinit var pro: EditText
    private lateinit var cty: EditText
    private lateinit var ctz: EditText
    private lateinit var phn: EditText
    private lateinit var psw: EditText
    private lateinit var rpw: EditText
    private lateinit var sgn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        Frt = findViewById(R.id.Frt);
        lat = findViewById(R.id.lat);
        eml = findViewById(R.id.eml);
        pro = findViewById(R.id.pro);
        cty = findViewById(R.id.cty);
        ctz = findViewById(R.id.ctz);
        phn = findViewById(R.id.phn);
        psw = findViewById(R.id.psw);
        rpw = findViewById(R.id.rpw);
        sgn = findViewById(R.id.sgn)
        sgn.setOnClickListener {

            sgn.setOnClickListener {
                val Firstname = Frt.text.toString()
                val lastname = lat.text.toString()
                val email = eml.text.toString()
                val province = pro.text.toString()
                val  city = cty.text.toString()
                val citizenship = ctz.text.toString()
                val phonenumber = phn.text.toString()
                val password = psw.text.toString()
                val repassword = rpw.text.toString()
                if (password != repassword) {

                    psw.error = "Password does not match"
                    psw.requestFocus()
                    return@setOnClickListener
                } else {
                    val user = User(citizenship, password)
                    CoroutineScope(Dispatchers.IO).launch {
                        UserDB
                                .getInstance(this@signup)
                                .getUserDao()
                                .registerUser(user)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    this@signup,
                                    "User Registered", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        log = findViewById(R.id.log)
        log.setOnClickListener{
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }
}