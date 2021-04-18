package com.example.voting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.voting.entity.License
import com.example.voting.repository.LicenseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateLicense : AppCompatActivity() {

    private lateinit var Firstname: EditText
    private lateinit var Lastname: EditText
    private lateinit var BloodGroup: EditText
    private lateinit var Ocupation: EditText
    private lateinit var Education: EditText
    private lateinit var Province: EditText
    private lateinit var City: EditText
    private lateinit var Phonenumber: EditText
    private lateinit var btnupdate: Button
    private lateinit var btnshow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_license)

        Firstname = findViewById(R.id.Firstname)
        Lastname = findViewById(R.id.Lastname)
        BloodGroup = findViewById(R.id.BloodGroup)
        Ocupation = findViewById(R.id.Ocupation)
        Education = findViewById(R.id.Education)
        Province = findViewById(R.id.Province)
        City = findViewById(R.id.City)
        Phonenumber = findViewById(R.id.Phonenumber)
        btnupdate = findViewById(R.id.btnupdate)
        btnshow = findViewById(R.id.btnshow)

        btnshow.setOnClickListener{
            val intent = Intent(this, getlicense::class.java)
            startActivity(intent)
        }

        val license = intent.getParcelableExtra<License>("license")!!
        Firstname.hint = license.Firstname
        Lastname.setText(license.Lastname)
        BloodGroup.setText(license.BloodGroup)
        Ocupation.setText(license.Ocupation)
        Education.setText(license.Education)
        Province.setText(license.Province)
        City.setText(license.City)
        Phonenumber.setText(license.Phonenumber)

        btnupdate.setOnClickListener {
            val Firstname = Firstname.text.toString()
            val Lastname = Lastname.text.toString()
            val BloodGroup = BloodGroup.text.toString()
            val Ocupation = Ocupation.text.toString()
            val Education = Education.text.toString()
            val Province = Province.text.toString()
            val City = City.text.toString()
            val Phonenumber = Phonenumber.text.toString()
            val license = License(
                _id = license._id,
                Firstname = Firstname,
                Lastname = Lastname,
                BloodGroup = BloodGroup,
                Ocupation = Ocupation,
                Education = Education,
                Province = Province,
                City = City,
                Phonenumber = Phonenumber
            )
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val licenseRepository = LicenseRepository()
                    val response = licenseRepository.updatelicense(license._id!!, license)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@UpdateLicense,
                                response.success.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (ex: Exception) {
//                    Toast.makeText(this@UpdateLicense, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}