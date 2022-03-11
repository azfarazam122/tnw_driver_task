//package com.example.tnw_driver_navigation;
//
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.mapbox.android.core.permissions.PermissionsListener;
//import com.mapbox.android.core.permissions.PermissionsManager;
//import com.mapbox.mapboxsdk.Mapbox;
//import com.mapbox.mapboxsdk.location.LocationComponent;
//import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
//import com.mapbox.mapboxsdk.location.LocationComponentOptions;
//import com.mapbox.mapboxsdk.location.modes.CameraMode;
//import com.mapbox.mapboxsdk.maps.MapView;
//import com.mapbox.mapboxsdk.maps.MapboxMap;
//import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
//import com.mapbox.mapboxsdk.maps.Style;
//import com.mapbox.maps.CameraOptions;
//import com.mapbox.navigation.core.MapboxNavigation;
//
//
//
//public class MapViewJava extends AppCompatActivity implements OnMapReadyCallback ,PermissionsListener{
//
//    private MapView mapView;
//    private MapboxMap mapboxMap;
//    private LocationComponent locationComponent;
//    private MapboxNavigation navigation;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Mapbox.getInstance(this, "sk.eyJ1IjoiZW1lcmFsZHNvZnQzIiwiYSI6ImNsMDI4YmsweTAwcXMzbnFkN2NheWN2cXIifQ.eM00GFmtZlAse0wAZIfYlQ");
//        setContentView(R.layout.activity_mapbox_custom_view_navigation);
//        mapView = findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(this);
//
//    }
//
//    @Override
//    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
//
//        mapView.getMapboxMap().setCamera(
//                CameraOptions.Builder()
//                        .zoom(14.0)
//                        .build()
//        )
//        mapView.getMapboxMap().loadStyleUri(
//                Style.MAPBOX_STREETS
//        ) {
//            initLocationComponent()
//            setupGesturesListener()
//        }
//        this.mapboxMap = mapboxMap;
//
//
//
//        mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
//
//
//            @Override
//            public void onStyleLoaded(@NonNull Style style) {
//                enableLocationComponent(style);
//
//
//                initializeNavigation(mapboxMap);
//
//            }
//        });
//    }
//
//    @SuppressWarnings({"MissingPermission"})
//    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
//// Check if permissions are enabled and if not request
//        if (PermissionsManager.areLocationPermissionsGranted(this)) {
//// Activate the MapboxMap LocationComponent to show user location
//// Adding in LocationComponentOptions is also an optional parameter
//            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this)
//                    //   .foregroundDrawable(R.drawable.currentlocation)
//                    .elevation(5)
//                    .build();
//            locationComponent = mapboxMap.getLocationComponent();
//            LocationComponentActivationOptions locationComponentActivationOptions =
//                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
//                            .locationComponentOptions(customLocationComponentOptions)
//                            .build();
//            locationComponent.activateLocationComponent(locationComponentActivationOptions);
//
//// Enable to make component visible
//            locationComponent.setLocationComponentEnabled(true);
//            //   locationComponent.activateLocationComponent(this, loadedMapStyle);
//
//// Set the component's camera mode
//
//            locationComponent.setCameraMode(CameraMode.TRACKING);
//        } else {
//
//        }
//
//
//    }
//
//
//    private void initializeNavigation(MapboxMap mapboxMap) {
//
//
////        navigationMapboxMap.addProgressChangeListener(navigation);
//    }
//
//
//
//    @Override
//    public void onPermissionResult(boolean granted) {
//        if (granted) {
//            enableLocationComponent(mapboxMap.getStyle());
//        } else {
//            Toast.makeText(this, "you didn't grant location permission", Toast.LENGTH_LONG).show();
//            finish();
//        }
//    }
//    }
//
//
//
