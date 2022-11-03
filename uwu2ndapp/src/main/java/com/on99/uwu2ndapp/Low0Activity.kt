package com.on99.uwu2ndapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Low0Activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_low0)
        val button0:Button = findViewById(R.id.l0Buttion0)
        val picL0:ImageView = findViewById(R.id.l0imgView)
        var jiou:Long = 0L
        val testVisButton:Button = findViewById(R.id.testVisButton)
        picL0.setOnClickListener{
            println("back--")
            val intent =Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        button0.setOnClickListener{
            Toast.makeText(this,"click", Toast.LENGTH_SHORT).show()
            println(jiou)
            if (jiou.and(1L).equals(0L)){
                jiou = jiou.xor(1L)
                picL0.setImageResource(R.drawable.diy002)
                button0.setTextColor(resources.getColor(R.color.navyblue))
                button0.setBackgroundResource(R.color.lightskyblue)
                testVisButton.visibility = View.INVISIBLE
                testVisButton.setText(R.string.forSwitchOff)
                print(testVisButton.visibility)
                println(testVisButton.text)
            }
            else if (jiou.and(1L).equals(1L)){
                jiou = jiou.xor(1L)
                picL0.setImageResource(R.drawable.diy001)
                button0.setTextColor(resources.getColor(R.color.white))
                button0.setBackgroundResource(R.color.orangered3)
                testVisButton.visibility = View.VISIBLE
                testVisButton.setText(R.string.app_name)
                print(testVisButton.visibility)
                println(testVisButton.text)
            }
        }
    }
}