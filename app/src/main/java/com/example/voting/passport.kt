package com.example.voting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.voting.entity.Passport
import com.example.voting.repository.PassportRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class passport : AppCompatActivity() {

    private lateinit var simage: ImageView
    private lateinit var Firstname: EditText
    private lateinit var Lastname: EditText
    private lateinit var Fathername: EditText
    private lateinit var CitizenshipNo: EditText
    private lateinit var Ocupation: EditText
    private lateinit var Education: EditText
    private lateinit var Phonenumber: EditText
    private lateinit var btnsave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passport)

        simage = findViewById(R.id.simage)
        Firstname = findViewById(R.id.Firstname)
        Lastname = findViewById(R.id.Lastname)
        Fathername = findViewById(R.id.Fathername)
        CitizenshipNo = findViewById(R.id.CitizenshipNo)
        Ocupation = findViewById(R.id.Ocupation)
        Education = findViewById(R.id.Education)
        Phonenumber = findViewById(R.id.Phonenumber)
        btnsave = findViewById(R.id.btnsave)

        btnsave.setOnClickListener {
            val Firstname = Firstname.text.toString()
            val Lastname = Lastname.text.toString()
            val Fathername = Fathername.text.toString()
            val CitizenshipNo = CitizenshipNo.text.toString()
            val Ocupation = Ocupation.text.toString()
            val Education = Education.text.toString()
            val Phonenumber = Phonenumber.text.toString()
            val passport =
                    Passport(Firstname = Firstname, Lastname = Lastname, Fathername = Fathername, CitizenshipNo = CitizenshipNo,Ocupation = Ocupation,Education = Education, Phonenumber = Phonenumber  )

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val passportRepostory = PassportRepository()
                    val response = passportRepostory.addpassport(passport)
                    if (response.success == true) {
                        if(imageUrl!= null){
                            uploadImage(response.data !! . _id!!)
                        }
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    this@passport,
                                    "Passport Request Added",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                this@passport,
                                ex.toString(),
                                Toast.LENGTH_SHORT
                        )
                                .show()
                    }
                }

            }

        }
    }
}