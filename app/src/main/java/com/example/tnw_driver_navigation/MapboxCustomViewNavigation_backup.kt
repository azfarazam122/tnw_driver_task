//package com.example.tnw_driver_navigation
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import com.mapbox.android.core.permissions.PermissionsListener
//import com.mapbox.android.core.permissions.PermissionsManager
//import com.mapbox.geojson.Point
//import com.mapbox.mapboxsdk.Mapbox
//import com.mapbox.mapboxsdk.geometry.LatLng
//import com.mapbox.mapboxsdk.location.LocationComponent
//import com.mapbox.mapboxsdk.location.modes.CameraMode
//import com.mapbox.mapboxsdk.maps.MapView
//import com.mapbox.mapboxsdk.maps.MapboxMap
//import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
//import com.mapbox.mapboxsdk.maps.Style
//
//
//class MapboxCustomViewNavigation : AppCompatActivity(), OnMapReadyCallback,
//    PermissionsListener, MapboxMap.OnMapClickListener {
//
//    private lateinit var mapView: MapView
//    private lateinit var mapboxMap: MapboxMap
//    private lateinit var locationComponent: LocationComponent
//    private lateinit var permissionsManager: PermissionsManager
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setTheme(R.style.NavigationViewLight)
//
//        Mapbox.getInstance(
//            applicationContext,
//            "sk.eyJ1IjoiZW1lcmFsZHNvZnQzIiwiYSI6ImNsMDI4YmsweTAwcXMzbnFkN2NheWN2cXIifQ.eM00GFmtZlAse0wAZIfYlQ"
//        )
//        setContentView(R.layout.activity_mapbox_custom_view_navigation)
//        //
//        mapView = findViewById(R.id.mapView)
//        mapView.onCreate(savedInstanceState)
//
//        mapView.getMapAsync(this)
//
//
//    }
//
//    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onPermissionResult(granted: Boolean) {
//        if (granted) {
////            enableLocationComponent(mapboxMap.style.)
//        } else {
//            // Permission Not Granted
//            finish()
//        }
//    }
//
//    override fun onMapClick(point: LatLng): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun onMapReady(mapboxMap: MapboxMap) {
//        this.mapboxMap = mapboxMap
////        mapboxMap.setStyle(
////            getString(R.string.navigation_guidance_day)
////        )
//
//        mapboxMap.setStyle(
//            getString(R.string.navigation_guidance_day),
//            Style.OnStyleLoaded() {
//                enableLocationComponent(it)
//                val originpoint = Point.fromLngLat(
//                    locationComponent.lastKnownLocation!!.longitude,
//                    locationComponent.lastKnownLocation!!.latitude
//                )
//                val test ="";
//                fun onStyleLoaded(style: Style) {
//                    enableLocationComponent(style)
//
////                    initializeNavigation(mapboxMap)
//                    val originpoint = Point.fromLngLat(
//                        locationComponent.lastKnownLocation!!.longitude,
//                        locationComponent.lastKnownLocation!!.latitude
//                    )
//
//                    val test =""
//                }
//            })
//val check =""
//
//    }
//
//    private fun enableLocationComponent(loadedMapStyle: Style) {
//        if (PermissionsManager.areLocationPermissionsGranted(this)) {
//            locationComponent = mapboxMap.locationComponent
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return
//            }
//
//            locationComponent.activateLocationComponent(this, loadedMapStyle)
//            locationComponent.isLocationComponentEnabled = true
//            locationComponent.setCameraMode(CameraMode.TRACKING)
//        } else {
//            permissionsManager.requestLocationPermissions(this)
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//    // Activity lifecycle methods
//    override fun onStart() {
//        super.onStart()
//        mapView.onStart()
//    }
//
//
//    override fun onResume() {
//        super.onResume()
//        mapView.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mapView.onPause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mapView.onStop()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mapView.onDestroy()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        mapView.onLowMemory()
//    }
//
//
//}