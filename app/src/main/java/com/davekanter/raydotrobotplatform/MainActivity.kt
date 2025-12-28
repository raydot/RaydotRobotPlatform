package com.davekanter.raydotrobotplatform

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var statusText: TextView
    private lateinit var connectButton: Button
    private lateinit var forwardButton: Button
    private lateinit var stopButton: Button

    // HC-05 default MAC address, TODO: need to change
    private val HC05_MAC = "00:00:00:00:00:00" // TODO: replace with actual value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothManager = BluetoothManager()

        // Get references to UI elements
        statusText = findViewById(R.id.statusText)
        connectButton = findViewById(R.id.connectButton)
        forwardButton = findViewById(R.id.forwardButton)
        stopButton = findViewById(R.id.stopButton)


        // Connect button handler
        connectButton.setOnClickListener {
            statusText.text = "Connecting..."

            Thread {
                val success = bluetoothManager.connect(HC05_MAC)

                runOnUiThread {
                    if (success) {
                        statusText.text = "Connected to HC-05 ${HC05_MAC}"
                        forwardButton.isEnabled = true
                        stopButton.isEnabled = true
                        connectButton.isEnabled = false
                    } else {
                        statusText.text = "Connection failed."
                    }
                }
            }.start()
        }
        // Forward button handler
        forwardButton.setOnClickListener {
            bluetoothManager.sendCommand("F200\n")
            statusText.text = "Sent: Forward"
        }

        stopButton.setOnClickListener {
            bluetoothManager.sendCommand("STOP\n")
            statusText.text = "Sent : Stop"
        }
    }

    override fun onDestroy () {
        super.onDestroy()
        bluetoothManager.disconnect()
    }


}