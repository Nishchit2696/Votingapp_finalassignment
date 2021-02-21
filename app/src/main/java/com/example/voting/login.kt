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
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class login : AppCompatActivity() {
    private lateinit var cts: EditText
    private lateinit var paw : EditText
    private lateinit var Register : TextView
    private lateinit var submit : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        cts = findViewById(R.id.cts);
        paw = findViewById(R.id.paw);
        Register = findViewById(R.id.Register);
        submit = findViewById(R.id.submit)

        Register.setOnClickListener{
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        submit.setOnClickListener{
            Login()
        }
    }

    private fun Login() {
        val citizeship = cts.text.toString()
        val password = paw.text.toString()

        var user: User? = null
        CoroutineScope(Dispatchers.IO).launch {
            user = UserDB
                    .getInstance(this@login)
                    .getUserDao()
                    .loginUser(citizeship,password)
            if(user == null){
                withContext(Main){
                    Toast.makeText( this@login, "Invalid Citizen",Toast.LENGTH_SHORT)
                            .show()
                }
            }else{
                startActivity(Intent(this@login,
                Dashboard::class.java))
            }
        }
    }
}