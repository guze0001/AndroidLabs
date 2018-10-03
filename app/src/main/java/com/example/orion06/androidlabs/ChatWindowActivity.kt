package com.example.orion06.androidlabs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_chat_window.*
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.KeyEvent.KEYCODE_DPAD_CENTER

import android.widget.EditText



class ChatWindowActivity : Activity() {

//Global
    var myList = null as ListView?
    var messages = ArrayList<String>() //automatically grows
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)


        var btnSend = findViewById<Button>(R.id.btnDone)
        var inputText = findViewById<EditText>(R.id.inputText)

        var messageAdapter = ChatAdapter( this )
        btnSend?.setOnClickListener( {
            var userTyped = inputText.getText().toString()
            messages.add(userTyped)
            inputText.setText("")
            messageAdapter.notifyDataSetChanged()
            myList?.setSelection(messageAdapter.getCount() - 1);
        })

        myList = findViewById<ListView?>(R.id.myList)
        myList?.setAdapter (messageAdapter)



        inputText.setOnKeyListener(object : View.OnKeyListener {

            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.getAction() === KeyEvent.ACTION_DOWN) {
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


        var inflater = LayoutInflater.from(parent.getContext())
        var result = null as View?

        if (position %2 == 0) {
            result = inflater.inflate(R.layout.chat_row_incoming, null)
        }
        else {
            result = inflater.inflate(R.layout.chat_row_outgoing, null)
        }
       val message = result?.findViewById<TextView>(R.id.message_text)
       // val message = result.findViewById(R.id.message_text)

        message?.setText( getItem(position) ) // get the string at position

        return result
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
        return position.toLong()
    }
}

}
