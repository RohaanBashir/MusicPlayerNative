package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.AndroidException
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File

class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        var tabLayout:TabLayout = findViewById(R.id.Tablayout)
        var ViewPager:ViewPager2 = findViewById(R.id.viewPager)


        RequestRuntimePermission()

        var Myadapter = ViewPagerAdapter(this)

        ViewPager.adapter = Myadapter

        TabLayoutMediator(tabLayout,ViewPager){tab,position ->
            when(position){
                0->{
                    tab.text = "Music"
                }
                1->{
                    tab.text = "About Me"
                }
            }
        }.attach()
    }

    //permission requesting
    private fun RequestRuntimePermission(){

        //checking if permission given
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO),13)

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 13){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show()
            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO),13)
            }

        }
    }


}