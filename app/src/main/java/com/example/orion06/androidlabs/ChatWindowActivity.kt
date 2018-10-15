package com.example.orion06.androidlabs

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.EditText

class ChatWindowActivity : Activity() {

    val ACTIVITY_NAME = "ChatWindowActivity"
//Global
    private var myList = null as ListView?
    var messages = ArrayList<String>() //automatically grows
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)


        val dhHelper = MyOpenHelper()
        val db = dhHelper.writableDatabase
        val results =db.query(TABLE_NAME, arrayOf("_id", KEY_MESSAGES), null, null, null, null, null, null)
        val numRows = results.count
        //Row Count
        Log.i(ACTIVITY_NAME,"Cursor's row count:" + numRows);
        results.moveToFirst() // point to first row
        val idIndex = results.getColumnIndex("_id")
        val messagesIndex = results.getColumnIndex(KEY_MESSAGES)
        while(!results.isAfterLast())
        {
            var thisID = results.getInt(idIndex)
            val msg = results.getString(messagesIndex)
            messages.add(msg)
            print("FirstName $msg")
            Log.i(ACTIVITY_NAME,"SQL MESSAGE: $msg ")
            results.moveToNext()
        }
        val numOfColumns = results.columnCount
        for (i  in 0..numOfColumns-1)//in (numOfColumns -1).downTo(0) )
        {
            Log.i(ACTIVITY_NAME, "Column Name : "+ results.getColumnName(i))
        }


        val btnSend = findViewById<Button>(R.id.btnDone)
        val inputText = findViewById<EditText>(R.id.inputText)

        val messageAdapter = ChatAdapter( this )
        btnSend?.setOnClickListener {
            val userTyped = inputText.getText().toString()
            messages.add(userTyped)

            //write to the db
            val row = ContentValues()
            row.put(KEY_MESSAGES,userTyped)
            db.insert(TABLE_NAME, null, row)


            inputText.setText("")
            messageAdapter.notifyDataSetChanged()
            myList?.setSelection(messageAdapter.getCount() - 1);
        }

        myList = findViewById<ListView?>(R.id.myList)
        myList?.setAdapter (messageAdapter)

        inputText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                val actonId:Int = event.getAction()
                if ( actonId == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                            btnSend.callOnClick()
                            return true
                        }
                        else -> {
                        }
                    }
                }
                return false
            }
        })

    }


inner class ChatAdapter(ctx : Context) : ArrayAdapter<String>(ctx, 0 ){

    override fun getCount(): Int {

        return messages.size
    }

    override fun getItem(position: Int): String? {

        return messages.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = LayoutInflater.from(parent.getContext())
        var result = null as View?
        if(position %2 == 0) {
            result = inflater.inflate(R.layout.chat_row_incoming, null)
        }
        else {
            result = inflater.inflate(R.layout.chat_row_outgoing, null)
        }
       val message = result?.findViewById<TextView>(R.id.message_text)
        message?.setText( getItem(position) ) // get the string at position

        return result
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
        return position.toLong()
    }
}

    val DATABASE_NAME = "MyDatabaseFile.db"
    val VERSION_NUM = 1
    val TABLE_NAME = "Messages"
    val KEY_MESSAGES = "Messages"

inner class MyOpenHelper:SQLiteOpenHelper(this@ChatWindowActivity, DATABASE_NAME, null, VERSION_NUM){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME ( _id INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_MESSAGES TEXT);")


    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS$TABLE_NAME")
        onCreate(db)
    }
}

}
