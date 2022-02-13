package com.example.myfirstapp

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import org.json.JSONTokener
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);


        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    while (!this.isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            val sdf = SimpleDateFormat("dd/MM/yyyy")
                            val currentDate = sdf.format(Date()).toString()
                            val stf = SimpleDateFormat("hh:mm:ss")
                            val currentTime = stf.format(Date()).toString()

                            textViewDate.text = currentDate
                            textViewTime.text = currentTime
                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }

        thread.start()

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        imageButton_Search.setOnClickListener { v ->
            val vehicleReg = editTextTextReg.text

            val response = URL("http://mag.iptvspain.xyz/dvla/requestreg.php?vehicleReg=$vehicleReg").readText()

            val jsonObject = JSONTokener(response).nextValue() as JSONObject

            val vehiclemake = jsonObject.getString("make")
            println("Make: $vehiclemake")
            textMake.text = vehiclemake

            val fuelType = jsonObject.getString("fuelType")
            println("Fuel Type: $fuelType")
            textFuelType.text = fuelType

            val motStatus = jsonObject.getString("motStatus")
            println("M.O.T. Status: $motStatus")
            textMOT.text = motStatus

            val taxDueDate = jsonObject.getString("taxDueDate")
            println("Tax Due: $taxDueDate")
            textTaxDue.text = taxDueDate

        }

    }
}