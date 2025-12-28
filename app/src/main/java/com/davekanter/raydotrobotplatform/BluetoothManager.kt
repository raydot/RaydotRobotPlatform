package com.davekanter.raydotrobotplatform

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class BluetoothManager {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null
    private var inputStream: InputStream? = null

    companion object {
        private const val TAG = "BluetoothManager"
        private val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    // The connection is not correct but TODO: suppressing error for now:
    @SuppressLint("MissingPermission")
    fun connect(deviceAddress: String): Boolean {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            Log.e(TAG, "Device doesn't support Bluetooth")
            return false
        }

        return try {
            val device = bluetoothAdapter?.getRemoteDevice(deviceAddress)
            bluetoothSocket = device?.createRfcommSocketToServiceRecord(MY_UUID)
            bluetoothSocket?.connect()

            outputStream = bluetoothSocket?.outputStream
            inputStream = bluetoothSocket?.inputStream

            Log.d(TAG, "Connected to $deviceAddress")
            true
        } catch (e: IOException) {
            Log.e(TAG, "Connection failed: ${e.message}")
            disconnect()
            false
        }
    }

    fun disconnect() {
        try {
            inputStream?.close()
            outputStream?.close()
            bluetoothSocket?.close()
            Log.d(TAG, "Disconnected")
        } catch (e: IOException) {
            Log.e(TAG, "Error disconnecting: ${e.message}")
        }
    }

    fun sendCommand(command: String) {
        try {
            outputStream?.write(command.toByteArray())
            Log.d(TAG, "Sent: $command")
        } catch (e: IOException) {
            Log.e(TAG, "Error sending command: ${e.message}")
        }
    }

    fun readData(): String? {
        return try{
            val buffer = ByteArray(1024)
            val bytes = inputStream?.read(buffer)
            if (bytes != null && bytes > 0) {
                String(buffer, 0, bytes)
            } else null
        } catch (e: IOException) {
            Log.e(TAG, "Error reading data: ${e.message}")
            null
        }
    }
}