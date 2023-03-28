package fr.isen.melvingachet.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter.STATE_CONNECTED
import android.bluetooth.BluetoothAdapter.STATE_DISCONNECTED
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.melvingachet.androidsmartdevice.databinding.ActivityDeviceBinding

class DeviceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceBinding
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bluetoothDevice: BluetoothDevice? = intent.getParcelableExtra("device")
        val bluetoothGatt= bluetoothDevice?.connectGatt(this,false, bluetoothGattCallback)
        bluetoothGatt?.connect()
    }

    override fun onStop() {
        super.onStop()
        bluetoothGatt?.close()
    }
    private val bluetoothGattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                runOnUiThread {
                    displayContentConnected()
                }
            }
        }
    }
    private fun displayContentConnected(){
        binding.detailloaderTitle.text= getString(R.string.device_led_text)
        binding.detailloader.isVisible=false
        binding.led1.isVisible = true
    }

}