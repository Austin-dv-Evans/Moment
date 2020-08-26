package com.moment2.moment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.muddzdev.styleabletoastlibrary.StyleableToast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val RQ_SPEECH_REC = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val toggle = findViewById<ToggleButton>(R.id.on_off_service)
        toggle.setOnCheckedChangeListener{ intent, isChecked ->
            if (isChecked) {
                toggle.setOnClickListener { startService(intent) }
            } else {
                toggle.setOnClickListener { stopService(intent) }
            }
        }

        btn_button.setOnClickListener {
            askSpeechInput()
        }
        findViewById<View>(R.id.record).setOnClickListener {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(intent, RecordActivity.VIDEO_CAPTURE)
        }
        val cameraActivityButton = findViewById<Button>(R.id.camera_view_button)
        cameraActivityButton.setOnClickListener { openCameraActivity() }
        val myMomentsActivityButton = findViewById<Button>(R.id.my_moments)
        myMomentsActivityButton.setOnClickListener { openMyMomentsActivity() }
    }

    fun openMyMomentsActivity(){
        val intent = Intent(this, MyMomentsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
    fun openCameraActivity(){
        val intent = Intent(this, DirectCameraAccess::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            tv_text.text = result?.get(0).toString()
            if(tv_text.text == "moment record"){
                val serviceIntent = Intent(this, MomentService::class.java)
                startService(serviceIntent)
                StyleableToast.makeText(this, "Moment is Ready!", R.style.startedToast).show()
//                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
//                startActivityForResult(intent, RecordActivity.VIDEO_CAPTURE)
            }
        }
    }

    fun askSpeechInput(){
        if(!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this, "Speech recognition is not available", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Moment Record!")
            startActivityForResult(i, RQ_SPEECH_REC)
        }
    }
    fun startService(v:View) {
        val serviceIntent = Intent(this, MomentService::class.java)
        startService(serviceIntent)
        StyleableToast.makeText(this, "Moment is Ready!", R.style.startedToast).show()
    }
    fun stopService(v:View) {
        val serviceIntent = Intent(this, MomentService::class.java)
        stopService(serviceIntent)
        StyleableToast.makeText(this, "Moment's Notification off", R.style.startedToast).show()
    }
}

class RecordActivity : AppCompatActivity() {


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == VIDEO_CAPTURE);
        val videoBitmap = data!!.extras!!.get("data") as Bitmap
        val builder = AlertDialog.Builder(this)
        val videoView = VideoView(this)

        videoView.setVideoURI(data.data)
        videoView.start()
        builder.setView(videoView).show()
    }

    companion object {
        const val VIDEO_CAPTURE = 1
    }
}
