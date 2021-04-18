package com.example.voting

import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.voting.Adapter.SliderAdapter
import com.example.voting.api.ServiceBuilder
import com.example.voting.entity.Passport
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.abs

class Dashboard : AppCompatActivity(), SensorEventListener  {

    private lateinit var viewPager2: ViewPager2
    private val sliderHandler = Handler()
    private lateinit var passport: ImageView
    private lateinit var licence: ImageView
    private lateinit var map: ImageView
    private lateinit var logout: ImageView
    private lateinit var ll1 : LinearLayout
    private lateinit var sensorManager: SensorManager
    private  var sensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        viewPager2 = findViewById(R.id.viewPager_ImageSlider)
        passport = findViewById(R.id.passport)
        licence = findViewById(R.id.licence)
        map = findViewById(R.id.map)
        logout = findViewById(R.id.logout)
        ll1 = findViewById(R.id.ll1)

        if (!checkSensor())
            return
        else{
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        logout.setOnClickListener{
            ServiceBuilder.token.equals("")
            val intent = Intent(this, com.example.voting.login::class.java)
            startActivity(intent)
        }

        passport.setOnClickListener{
            val intent = Intent(this, com.example.voting.passport::class.java)
            startActivity(intent)
        }
        map.setOnClickListener{
            val intent = Intent(this, com.example.voting.MapsActivity::class.java)
            startActivity(intent)
        }
        licence.setOnClickListener{
            val intent = Intent(this,com.example.voting.license::class.java)
            startActivity(intent)
        }


        val slideritem: MutableList<Slideritem> = ArrayList()
        slideritem.add(Slideritem(R.drawable.img1))
        slideritem.add(Slideritem(R.drawable.img2))
        slideritem.add(Slideritem(R.drawable.img3))
        slideritem.add(Slideritem(R.drawable.img4))
        slideritem.add(Slideritem(R.drawable.img5))

        viewPager2.adapter = SliderAdapter(slideritem, viewPager2)

        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(30))
        compositePageTransformer.addTransformer{ page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.25f
        }

        viewPager2.setPageTransformer(compositePageTransformer)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })

    }

    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null){
            flag = false
        }
        return flag

    }

    private val sliderRunnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.postDelayed(sliderRunnable,3000)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable,3000)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[0]
        if (values > 40 )
            ll1.setBackgroundColor(Color.BLACK)

        if (values < 40)
            ll1.setBackgroundColor(Color.WHITE)

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}