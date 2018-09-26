package com.example.orion06.androidlabs
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*


class ListItemsActivity : Activity() {

    val ACTIVITY_NAME = "ListItemsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_items)
        Log.i(ACTIVITY_NAME, "In onCreate()")


        var btnCam = findViewById(R.id.btnCam) as? ImageButton
        btnCam?.setOnClickListener( { btnCamClick() })

        var swtCheck = findViewById(R.id.swtCheck) as? Switch

        swtCheck?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            var text = "Switch is On" // "Switch is Off"

            var duration = Toast.LENGTH_SHORT //= Toast.LENGTH_LONG if Off
            if (isChecked) {
                Log.i(ACTIVITY_NAME, "In switch On()")
                text = "Switch is On"
            } else {
                Log.i(ACTIVITY_NAME, "In switch Off()")
                text = "Switch is Off"
                duration = Toast.LENGTH_LONG
            }

            val toast = Toast.makeText(this , text, duration) //this is the ListActivity
            toast.show() //display your message box
        })


        var chkList = findViewById(R.id.chkList) as? CheckBox

        chkList?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.dialog_message)
                    .setTitle(R.string.dialog_title)
                    .setPositiveButton(R.string.ok) { dialog, id ->
                        val resultIntent = Intent( )
                        resultIntent.putExtra("Response",getResources().getString(R.string.res_msg) )
                        setResult(1, resultIntent);
                        finish()
                    }.setNegativeButton(R.string.cancel, { dialog, id ->
                        // User cancelled the dialog
                    }).show()
        })

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


    fun btnCamClick(){
        val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(photoIntent.resolveActivity(packageManager)!= null)
        {
            startActivityForResult(photoIntent,555)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 555 && resultCode == RESULT_OK)
        {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val btnCam = findViewById<ImageButton>(R.id.btnCam)
            btnCam.setImageBitmap( imageBitmap )
        }
    }


}
