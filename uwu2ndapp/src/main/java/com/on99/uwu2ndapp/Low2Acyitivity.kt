package com.on99.uwu2ndapp

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService

class Low2Acyitivity : AppCompatActivity() {
    private val requestDataLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){
            val data = result.data?.getStringExtra("data")
            Log.e("ActivityResultContracts.StartActivityForResult()","${data}")
        }
    }
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            // User allow the permission.
            Log.e("ActivityResultContracts.RequestPermission()","Permissions Allow ${granted} Granted")
        } else {
            // User deny the permission.
            Log.e("ActivityResultContracts.RequestPermission()","Permissions Deny ${granted} Granted")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_low2)
        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
        if (bluetoothAdapter == null) {
            Log.e("BluetoothAdapter", "Device doesn't support Bluetooth")
        }
        requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH)
        requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_ADMIN)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            requestDataLauncher.launch(enableBtIntent)
        }
        val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
            putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        }
        requestDataLauncher.launch(discoverableIntent)
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        pairedDevices?.forEach { device ->
            val deviceName = if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.BLUETOOTH
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("pairedDevicescheckSelfPermission", "Not!!!!!PackageManager.PERMISSION_GRANTED is Not!")
            } else {
                device.name
            }
            val deviceHardwareAddress = device.address // MAC address
            Log.e("pairedDevices","Name = ${deviceName} , Address = ${deviceHardwareAddress}")
        }
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver,filter)
        if (bluetoothAdapter != null) {
            bluetoothAdapter.startDiscovery()
        }
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isDiscovering) {
                Log.e(
                    "bluetoothAdapter.isDiscovering",
                    "Is it discovering ? ${bluetoothAdapter.isDiscovering}"
                )
            } else {
                Log.e(
                    "bluetoothAdapter.isDiscovering",
                    "Is it discovering ? ${bluetoothAdapter.isDiscovering}"
                )
            }
            bluetoothAdapter.startDiscovery()
        }
    }
    private  val receiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context, intent: Intent) {
            val action: String? = intent.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = if (ActivityCompat.checkSelfPermission(
                            p0, Manifest.permission.BLUETOOTH
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Log.e("BroadcastReceivercheckSelfPermission", "Not!!!!!PackageManager.PERMISSION_GRANTED is Not!")
                    } else {
                        device?.name
                    }
                    val deviceHardwareAddress = device?.address // MAC address
                    Log.e("BroadcastReceiver","Name = ${deviceName} , Address = ${deviceHardwareAddress}")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("onDestroy","DestroyDestroyDestroyDestroyDestroyDestroyDestroyDestroyDestroyDestroy")
        unregisterReceiver(receiver)
    }
}





















