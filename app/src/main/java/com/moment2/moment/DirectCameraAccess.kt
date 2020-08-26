package com.moment2.moment

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ToggleButton
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import com.muddzdev.styleabletoastlibrary.StyleableToast
import kotlinx.android.synthetic.main.activity_direct_camera_access.*

class DirectCameraAccess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direct_camera_access)

//        gallery.setOnClickListener()
//        {
//            var intent = Intent()
//            intent.setType("video/*")
//            intent.setAction(Intent.ACTION_GET_CONTENT)
//            startActivityForResult(intent, 101)
//        }

        val toggle = findViewById<ToggleButton>(R.id.on_off_service)
        toggle.setOnCheckedChangeListener{ intent, isChecked ->
            if (isChecked) {
                toggle.setOnClickListener { startService(intent) }
            } else {
                toggle.setOnClickListener { stopService(intent) }
            }
        }
        findViewById<View>(R.id.record).setOnClickListener {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(intent, RecordActivity.VIDEO_CAPTURE)
        }
        val myMomentsActivityButton = findViewById<Button>(R.id.my_moments)
        myMomentsActivityButton.setOnClickListener { openMyMomentsActivity() }
        val setUpActivityButton = findViewById<Button>(R.id.set_up_button)
        setUpActivityButton.setOnClickListener { openSetUpActivity() }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(resultCode == Activity.RESULT_OK && data!=null)
//        {
//            if(requestCode == 101)
//            {
//                var uri: Uri? = data.data
//                var selectedImage:String = getPath(uri)
//            }
//        }
//    }
//
//    private fun getPath(uri: Uri?): String {
//        var projectionArray = arrayOf(MediaStore.Video.Media.DATA)
//        var cursor: Cursor = applicationContext.contentResolver.query(uri.projectionArray)
//
//    }


    fun openMyMomentsActivity(){
        val intent = Intent(this, MyMomentsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
    }
    fun openSetUpActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_to_right, R.anim.slide_from_left);
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
class RecordActivity2 : AppCompatActivity() {


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == VIDEO_CAPTURE);

        val builder = AlertDialog.Builder(this)
        val videoView = VideoView(this)

        videoView.setVideoURI(data!!.data)
        videoView.start()
        builder.setView(videoView).show()
    }

    companion object {
        const val VIDEO_CAPTURE = 1
    }
}