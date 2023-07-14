package com.example.recipes_yape_android.presentation.features.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.request.RequestOptions
import com.example.recipes_yape_android.R
import com.example.recipes_yape_android.core.extensions.failure
import com.example.recipes_yape_android.core.extensions.observe
import com.example.recipes_yape_android.core.functional.Failure
import com.example.recipes_yape_android.data.remote.entity.Recipe
import com.example.recipes_yape_android.databinding.ActivityMapsBinding
import com.example.recipes_yape_android.presentation.utils.BindingUtil
import com.example.recipes_yape_android.presentation.utils.ConfigUtil
import com.example.recipes_yape_android.presentation.utils.imageLoader.ImageLoaderGlide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewModel: MapsViewModel
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        viewModel = ViewModelProvider(this)[MapsViewModel::class.java]

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapsFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.topBarInclude.btnBack.setOnClickListener {
            finish()
        }

        with(viewModel) {
            observe(detailRecipe, ::renderRecipe)
            failure(failure, ::handleFailure)
        }

        val data = intent.getSerializableExtra(ConfigUtil.RECIPE_PUT) as? Recipe
        if (data != null) {
            viewModel.setData(data)
        }

    }

    private fun renderRecipe(recipe: Recipe?) {

        mMap?.let { it ->
            if (viewModel.getData() != null) {
                binding.topBarInclude.title = recipe?.name

                if(recipe?.latitude != null && recipe?.longitude != null){
                    val location = LatLng(recipe?.latitude!!.toDouble(), recipe.longitude.toDouble())
                    it.addMarker(MarkerOptions().position(location).title(recipe?.name))
                    it.moveCamera(CameraUpdateFactory.newLatLng(location))

                    val zoomLevel = 10f
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, zoomLevel)
                    it.moveCamera(cameraUpdate)
                    it.animateCamera(cameraUpdate)
                } else {
                    BindingUtil.showSnackBar(binding.root, getString(R.string.error_location))
                }

            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mMap.isMyLocationEnabled = true
                }
            }
        }
    }


    private fun handleFailure(failure: Failure?) {
        BindingUtil.showSnackBar(binding.root, getString(R.string.error_show_data))
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}