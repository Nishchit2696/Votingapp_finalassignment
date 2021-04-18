package com.example.voting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voting.Adapter.LicenseAdapter
import com.example.voting.Adapter.PassportAdapter
import com.example.voting.repository.LicenseRepository
import com.example.voting.repository.PassportRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class getlicense : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getlicense)

        recyclerView = findViewById(R.id.recyclerview)

        loadPassport()
    }

    private fun loadPassport() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val licenseRepository = LicenseRepository()
                val response = licenseRepository.getlicense()
                if (response.success == true){
                    val lstlicense = response.data
                    withContext(Dispatchers.Main){
                        recyclerView.adapter = LicenseAdapter(this@getlicense,lstlicense!!)
                        recyclerView.layoutManager = LinearLayoutManager(this@getlicense)
                    }
                }
                else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@getlicense,
                            "Error : ${response.message.toString()}",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }catch (ex:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@getlicense,
                        "Error : ${ex.toString()}",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}