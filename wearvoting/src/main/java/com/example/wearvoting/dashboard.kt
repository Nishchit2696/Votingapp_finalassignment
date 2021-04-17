package com.example.wearvoting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.wearvoting.repository.PassportRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class dashboard : AppCompatActivity() {

    private lateinit var textResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        textResult = findViewById(R.id.textResult)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val passportRepository = PassportRepository()
                val response = passportRepository.getAllPassport()
                if (response.sucess ==true){
                    withContext(Dispatchers.Main){
                        textResult.setText("There are total ${response.count} passport resquested by you")
                    }
                }
            }
            catch (ex:Exception){

            }
        }
    }
}