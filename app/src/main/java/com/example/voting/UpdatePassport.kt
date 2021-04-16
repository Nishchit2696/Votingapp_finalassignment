package com.example.voting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.voting.entity.Passport
import com.example.voting.repository.PassportRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdatePassport : AppCompatActivity() {

    private lateinit var Firstname: EditText
    private lateinit var Lastname: EditText
    private lateinit var Fathername: EditText
    private lateinit var CitizenshipNo: EditText
    private lateinit var Ocupation: EditText
    private lateinit var Education: EditText
    private lateinit var Phonenumber: EditText
    private lateinit var btnsave: Button
    private lateinit var btnshow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_passport)


        Firstname = findViewById(R.id.Firstname)
        Lastname = findViewById(R.id.Lastname)
        Fathername = findViewById(R.id.Fathername)
        CitizenshipNo = findViewById(R.id.CitizenshipNo)
        Ocupation = findViewById(R.id.Ocupation)
        Education = findViewById(R.id.Education)
        Phonenumber = findViewById(R.id.Phonenumber)
        btnsave = findViewById(R.id.btnsave)
        btnshow = findViewById(R.id.btnshow)

        val passport = intent.getParcelableExtra<Passport>("passport")!!
        Firstname.hint = passport.Firstname
        Lastname.setText(passport.Lastname)
        Fathername.setText(passport.Fathername)
        CitizenshipNo.setText(passport.CitizenshipNo)
        Ocupation.setText(passport.Ocupation)
        Education.setText(passport.Education)
        Phonenumber.setText(passport.Phonenumber)

        btnsave.setOnClickListener {
            val Firstname = Firstname.text.toString()
            val Lastname = Lastname.text.toString()
            val Fathername = Fathername.text.toString()
            val CitizenshipNo = CitizenshipNo.text.toString()
            val Ocupation = Ocupation.text.toString()
            val Education = Education.text.toString()
            val Phonenumber = Phonenumber.text.toString()
            val passport = Passport(
                _id = passport._id,
                Firstname = Firstname,
                Lastname = Lastname,
                Fathername = Fathername,
                CitizenshipNo = CitizenshipNo,
                Ocupation = Ocupation,
                Education = Education,
                Phonenumber = Phonenumber
            )
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val passportRepository = PassportRepository()
                    val response = passportRepository.updatepassport(passport._id!!,passport)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@UpdatePassport,
                                response.success.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (ex: Exception) {
                    Toast.makeText(this@UpdatePassport, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}