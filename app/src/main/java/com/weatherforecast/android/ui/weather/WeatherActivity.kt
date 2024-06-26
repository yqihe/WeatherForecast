package com.weatherforecast.android.ui.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.weatherforecast.android.R
import com.weatherforecast.android.customview.RealtimeWeatherView
import com.weatherforecast.android.logic.model.weather.HourlyForecast
import com.weatherforecast.android.logic.model.weather.PlaceManage
import com.weatherforecast.android.logic.model.weather.Sky.getSky
import com.weatherforecast.android.logic.model.weather.Weather
import com.weatherforecast.android.ui.course.CourseActivity
import com.weatherforecast.android.ui.media.MediaActivity
import com.weatherforecast.android.ui.placesearch.PlaceSearchActivity
import com.weatherforecast.android.ui.placesearch.PlaceSearchViewModel
import com.weatherforecast.android.ui.weather.placemanage.PlaceManageAdapter
import com.weatherforecast.android.ui.weather.placemanage.PlaceManageViewModel
import com.weatherforecast.android.ui.weather.weathershow.HourlyAdapter
import com.weatherforecast.android.ui.weather.weathershow.WeatherViewModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherActivity : AppCompatActivity(), LocationListener {
    companion object {
        private const val REQUEST_LOCATION_PERMISSION_CODE = 100
    }

    private lateinit var locationManager: LocationManager

    private lateinit var placeName: TextView

    private lateinit var realtimeWeatherView: RealtimeWeatherView

    private lateinit var nowLayout: RelativeLayout

    private lateinit var forecastLayout: LinearLayout

    private lateinit var coldRiskText: TextView

    private lateinit var dressingText: TextView

    private lateinit var ultravioletText: TextView

    private lateinit var carWashingText: TextView

    private lateinit var weatherLayout: ScrollView

    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var navBtn: Button

    private lateinit var courseBtn: Button

    private lateinit var hourlyRecyclerView: RecyclerView

    private val hourlyForecastList = ArrayList<HourlyForecast>()

    private lateinit var hourlyAdapter: HourlyAdapter

    private lateinit var searchPlaceEntrance: EditText

    private lateinit var addBtn: Button

    private lateinit var locateBtn: Button

    private lateinit var musicBtn: Button

    private lateinit var placeManageRecyclerView: RecyclerView

    private lateinit var placeManageAdapter: PlaceManageAdapter

    lateinit var drawerLayout: DrawerLayout

    val weatherViewModel  by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }

    val placeManageViewModel by lazy { ViewModelProvider(this)[PlaceManageViewModel::class.java] }

    val placeSearchViewModel by lazy { ViewModelProvider(this)[PlaceSearchViewModel::class.java] }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT


        placeName = findViewById(R.id.placeName)
        realtimeWeatherView = findViewById(R.id.realtimeWeather)
        nowLayout = findViewById(R.id.nowLayout)
        forecastLayout = findViewById(R.id.forecastLayout)
        coldRiskText = findViewById(R.id.coldRiskText)
        dressingText = findViewById(R.id.dressingText)
        ultravioletText = findViewById(R.id.ultravioletText)
        carWashingText = findViewById(R.id.carWashingText)
        weatherLayout = findViewById(R.id.weatherLayout)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        navBtn = findViewById(R.id.navBtn)
        drawerLayout = findViewById(R.id.drawerLayout)
        hourlyRecyclerView = findViewById(R.id.hourlyRecyclerView)
        searchPlaceEntrance = findViewById(R.id.searchPlaceEntrance)
        addBtn = findViewById(R.id.addBtn)
        courseBtn = findViewById(R.id.courseBtn)
        locateBtn = findViewById(R.id.locateBtn)
        musicBtn = findViewById(R.id.musicBtn)
        placeManageRecyclerView = findViewById(R.id.placeManageRecyclerView)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager


        //启动PlaceSearchActivity
        searchPlaceEntrance.setOnClickListener {
            val intent = Intent(this, PlaceSearchActivity::class.java).apply {
                putExtra("FROM_ACTIVITY","WeatherActivity")
            }
            startActivity(intent)
        }


        //设置24小时预报的RecyclerView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        hourlyRecyclerView.layoutManager = layoutManager
        hourlyAdapter = HourlyAdapter(hourlyForecastList)
        hourlyRecyclerView.adapter = hourlyAdapter

        //设置地点管理的RecyclerView
        val layoutManager2 = LinearLayoutManager(this)
        placeManageRecyclerView.layoutManager = layoutManager2
        placeManageAdapter = PlaceManageAdapter(this,placeManageViewModel.placeManageList)
        placeManageRecyclerView.adapter = placeManageAdapter

        if (weatherViewModel.locationLng.isEmpty()) {
            weatherViewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (weatherViewModel.locationLat.isEmpty()) {
            weatherViewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (weatherViewModel.placeName.isEmpty()) {
            weatherViewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        if (weatherViewModel.placeAddress.isEmpty()) {
            weatherViewModel.placeAddress = intent.getStringExtra("place_address") ?: ""
        }

        weatherViewModel.weatherLiveData.observe(this) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            swipeRefresh.isRefreshing = false
        }

        placeManageViewModel.placeManageLiveData.observe(this) { result ->
            val placeManages = result.getOrNull()
            if (placeManages != null) {
                placeManageViewModel.placeManageList.clear()
                placeManageViewModel.placeManageList.addAll(placeManages)
                placeManageAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this,"无法获取地点管理数据", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }

        placeManageViewModel.toastLiveData.observe(this) {
            if (it != "") {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        swipeRefresh.setColorSchemeResources(R.color.royal_blue)
        refreshWeather()                    //刷新天气
        placeManageViewModel.refreshPlaceManage()     //刷新地点管理

        swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
        navBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        addBtn.setOnClickListener {
            drawerLayout.open()
            val addPlaceManage =
                PlaceManage(
                    weatherViewModel.placeName,
                    weatherViewModel.locationLng,
                    weatherViewModel.locationLat,
                    weatherViewModel.placeAddress,
                    weatherViewModel.placeRealtimeTem,
                    weatherViewModel.placeSkycon
                )
            placeManageViewModel.addPlaceManage(addPlaceManage)
        }
        locateBtn.setOnClickListener {
            if(!isGpsAble(locationManager)){
                Toast.makeText(this, "请打开GPS", Toast.LENGTH_SHORT).show()
                openGPS2()
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
                //开启定位权限,200是标识码
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION_CODE)
            } else {
                getLocation()
                Log.d("locateBtn", "dui")
                Toast.makeText(this, "已开启定位权限", Toast.LENGTH_LONG).show()
            }
        }
        musicBtn.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            startActivity(intent)
        }
        courseBtn.setOnClickListener {
            val intent = Intent(this, CourseActivity::class.java)
            startActivity(intent)
        }
        drawerLayout.addDrawerListener(object: DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        drawerLayout.closeDrawers()
        weatherViewModel.locationLng = intent?.getStringExtra("location_lng") ?: ""
        weatherViewModel.locationLat = intent?.getStringExtra("location_lat") ?: ""
        weatherViewModel.placeName = intent?.getStringExtra("place_name") ?: ""
        weatherViewModel.placeAddress = intent?.getStringExtra("place_address") ?: ""
        refreshWeather()
    }

    override fun onStop() {
        super.onStop()
        placeManageViewModel.clearToast()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showWeatherInfo(weather: Weather) {
        placeName.text = weatherViewModel.placeName
        val realtime = weather.realtime
        val hourly = weather.hourly
        val daily = weather.daily
        // 填充now.xml布局中的数据
        val realtimeTemInt = realtime.temperature.toInt()
        val currentSkyInfo = getSky(realtime.skycon).info
        val currentPM25 = realtime.airQuality.aqi.chn.toInt()
        val currentApparentTemInt = realtime.apparentTemperature.toInt()
        val currentWindDir = realtime.wind.direction
        val currentWindScale = calculateWindScale(realtime.wind.speed.toInt())
        val currentHumidity = (realtime.humidity * 100).toInt()
        realtimeWeatherView.setRealtimeWeather(realtimeTemInt,currentSkyInfo,currentPM25,
            currentApparentTemInt,currentWindDir,currentWindScale,currentHumidity)
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)

        //将网络请求的当前温度和Skycon保存到ViewModel中
        weatherViewModel.placeRealtimeTem = realtimeTemInt
        weatherViewModel.placeSkycon = currentSkyInfo

        //是否是点击地点管理中的地点更新该地点温度和天气信息
        if (weatherViewModel.isUpdatePlaceManage) {
            val updatePlaceManage =
                PlaceManage(
                    weatherViewModel.placeName, weatherViewModel.locationLng,
                    weatherViewModel.locationLat, weatherViewModel.placeAddress,
                    weatherViewModel.placeRealtimeTem, weatherViewModel.placeSkycon
                )
            placeManageViewModel.updatePlaceManage(updatePlaceManage)
            weatherViewModel.isUpdatePlaceManage = false
        }

        //填充forecast_hourly.xml布局中的数据
        hourlyForecastList.clear()
        val hours = hourly.skycon.size
        for (i in 0 until hours) {
            val temVal = hourly.temperature[i].value
            val skyVal = hourly.skycon[i].value
            val datetime = hourly.skycon[i].datetime
            hourlyForecastList.add(
                HourlyForecast(
                    temVal,
                    skyVal,
                    datetime
                )
            )
        }
        hourlyAdapter.notifyDataSetChanged()

        // 填充forecast_daily.xml布局中的数据
        forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this).inflate(R.layout.forecast_daily_item,
                forecastLayout, false)
            val dateInfo: TextView = view.findViewById(R.id.dateInfo)
            val skyIcon: ImageView = view.findViewById(R.id.skyIcon)
            val skyInfo: TextView = view.findViewById(R.id.skyInfo)
            val temperatureInfo: TextView = view.findViewById(R.id.temperatureInfo)
            val simpleDateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
            val dateInfoStr = when(i) {
                0 -> "今天  ${simpleDateFormat.format(skycon.date)}"
                1 -> "明天  ${simpleDateFormat.format(skycon.date)}"
                else -> "${getDayOfWeek(skycon.date)}  ${simpleDateFormat.format(skycon.date)}"
            }
            dateInfo.text = dateInfoStr
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }

    fun refreshWeather() {
        weatherViewModel.refreshWeather(weatherViewModel.locationLng,weatherViewModel.locationLat)
        swipeRefresh.isRefreshing = true
    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val geocoder = Geocoder(this, Locale.getDefault())
        if (location != null){// 定义位置解析
            try{
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                Log.d("locateBtn", addresses.toString())
                if(!addresses.isNullOrEmpty()){
                    weatherViewModel.locationLng = addresses[0].longitude.toString()
                    weatherViewModel.locationLat = addresses[0].latitude.toString()
                    weatherViewModel.placeName = addresses[0].locality
                    runOnUiThread{
                        placeSearchViewModel.searchPlaces(weatherViewModel.placeName + "\n");
                        placeSearchViewModel.placeLiveData.observe(this){ result ->
                            val places = result.getOrNull()
                            if (places!= null) {
                                placeSearchViewModel.placeList.clear()
                                placeSearchViewModel.placeList.addAll(places)
                            }
                            val place = placeSearchViewModel.placeList[0];
                            weatherViewModel.placeName = place.name
                            weatherViewModel.placeAddress = place.address
                            weatherViewModel.locationLng = place.location.lng.toString()
                            weatherViewModel.locationLat = place.location.lat.toString()
                            refreshWeather()
                        }
                    }

                }
            }catch (e: IOException){
                e.printStackTrace()
            }
        }else{
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 0f, this
            )
        }
    }

    override fun onLocationChanged(location: Location) {
        Log.e("onLocationChanged", location.toString())
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("Log.e(\"onStatusChanged\", provider)", "android.util.Log")
    )
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        Log.e("onStatusChanged", provider)
    }

    override fun onProviderEnabled(provider: String) {
        Log.e("onProviderEnabled", provider)
    }

    override fun onProviderDisabled(provider: String) {
        Log.e("onProviderDisabled", provider)
    }


    private fun getDayOfWeek(date: Date): String {
        val sdf = SimpleDateFormat("E", Locale.getDefault())
        return sdf.format(date)
    }
    private fun calculateWindScale(windSpeed: Int): Int {
        return when {
            windSpeed < 1 -> 0 // Calm
            windSpeed < 6 -> 1 // Light air
            windSpeed < 12 -> 2 // Light breeze
            windSpeed < 20 -> 3 // Gentle breeze
            windSpeed < 29 -> 4 // Moderate breeze
            windSpeed < 39 -> 5 // Fresh breeze
            windSpeed < 50 -> 6 // Strong breeze
            windSpeed < 62 -> 7 // Near gale
            windSpeed < 75 -> 8 // Gale
            windSpeed < 89 -> 9 // Strong gale
            windSpeed < 103 -> 10 // Storm
            windSpeed <= 117 -> 11 // Violent storm
            else -> 12 // Hurricane
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    private fun isGpsAble(lm: LocationManager): Boolean {
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    //打开设置页面让用户自己设置
    private fun openGPS2() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivityForResult(intent, 0)
    }
}