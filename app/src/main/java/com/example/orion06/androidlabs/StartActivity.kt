package com.example.orion06.androidlabs

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class StartActivity : Activity() {

    val ACTIVITY_NAME = "StartActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(ACTIVITY_NAME, "In onCreate()")
        setContentView(R.layout.activity_start)
        var btnStart = findViewById(R.id.btnStart) as? Button
        btnStart?.setOnClickListener( { btnStartClick() })

        var btnStartChat = findViewById(R.id.btnStartChat) as? Button
        btnStartChat?.setOnClickListener( { btnStartChatClick() })

    }

    override fun onResume() {
        super.onResume()
        Log.i(ACTIVITY_NAME, "In onResume()")
    }

    override fun onStart() {
        super.onStart()
        Log.i(ACTIVITY_NAME, "In onStart()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(ACTIVITY_NAME, "In onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(ACTIVITY_NAME, "In onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(ACTIVITY_NAME, "In onDestroy()")
    }

    fun btnStartClick(){
        val intent = Intent(this@StartActivity, ListItemsActivity::class.java)
        startActivityForResult(intent,50)
    }


    fun btnStartChatClick(){
        Log.i(ACTIVITY_NAME, "User clicked Start Chat")
        val intent = Intent(this@StartActivity, ChatWindowActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 50 && resultCode == 1)
        {
            val messagePassed = data?.getStringExtra("Response")
            var duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(this , messagePassed.toString(), duration) //this is the ListActivity
            toast.show() //display your message box

        }


    }
}
