package com.example.sms_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val PERMISSION_SEND_SMS = 100
        val phoneNumber = findViewById<EditText>(R.id.etPhone)
        val message = findViewById<EditText>(R.id.etMessage)
        val send = findViewById<Button>(R.id.btnSend)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), PERMISSION_SEND_SMS)
        }


        send.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                val smsManager = SmsManager.getDefault()
                val phone = phoneNumber.text.toString()
                val text = message.text.toString()

                if (phone.isNotEmpty() && text.isNotEmpty()){
                    smsManager.sendTextMessage(phone, null, text, null, null)
                    Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Kindly enter a phone number and message", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Permission denied... Cannot send SMS", Toast.LENGTH_SHORT).show()
            }

        }
    }
}