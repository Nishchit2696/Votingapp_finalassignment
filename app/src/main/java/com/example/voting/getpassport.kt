package com.example.voting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voting.Adapter.PassportAdapter
import com.example.voting.repository.PassportRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class getpassport : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getpassport)
        recyclerView = findViewById(R.id.recyclerview)

        loadPassport()
    }

    private fun loadPassport(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val passportRepository = PassportRepository()
                val response = passportRepository.getpassport()
                if (response.success == true){
                    val lstpassport = response.data
                    withContext(Dispatchers.Main){
                        recyclerView.adapter = PassportAdapter(this@getpassport,lstpassport!!)
                        recyclerView.layoutManager = LinearLayoutManager(this@getpassport)
                    }
                }
                else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@getpassport,
                                "Error : ${response.message.toString()}",
                                Toast.LENGTH_SHORT)
                                .show()
                    }
                }
            }catch (ex:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@getpassport,
                            "Error : ${ex.toString()}",
                            Toast.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }
}