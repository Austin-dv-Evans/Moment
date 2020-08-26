package com.moment2.moment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.MediaController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_moments.*


class MyMomentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_moments)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = MyMomentAdapter()


        val cameraActivityButton = findViewById<Button>(R.id.camera_view_button)
        cameraActivityButton.setOnClickListener { openCameraActivity() }
        val setUpActivityButton = findViewById<Button>(R.id.set_up_button)
        setUpActivityButton.setOnClickListener { openSetUpActivity() }
    }
    fun openSetUpActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

    }
    fun openCameraActivity(){
        val intent = Intent(this, DirectCameraAccess::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_to_left, R.anim.slide_from_right);
    }

}

