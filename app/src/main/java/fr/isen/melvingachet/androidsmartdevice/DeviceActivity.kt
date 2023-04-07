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
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import fr.isen.melvingachet.androidsmartdevice.databinding.ActivityDeviceBinding
import java.util.*

@SuppressLint("MissingPermission")
class DeviceActivity : AppCompatActivity() {
    private val serviceUUID = UUID.fromString("0000feed-cc7a-482a-984a-7f2ed5b3e58f")
    private val characteristicLedUUID = UUID.fromString("0000abcd-8e22-4541-9d4c-21edae82ed19")
    private val characteristicButtonUUID = UUID.fromString("00001234-8e22-4541-9d4c-21edae82ed19")
    private lateinit var binding: ActivityDeviceBinding
    private var bluetoothGatt: BluetoothGatt? = null
    private var cptClick=0

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
        bluetoothGatt?.let { gatt ->
            gatt.close()
            bluetoothGatt = null
        }
    }

    private val bluetoothGattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                runOnUiThread {
                    displayContentConnected()
                }
            }
        }
    }

    private fun clickOnLed(){;
        val characteristic = bluetoothGatt?.getService(serviceUUID)?.getCharacteristic(characteristicLedUUID)
        binding.led1.setOnClickListener{
            if(binding.led1.imageTintList == getColorStateList(R.color.red)){
                binding.led1.imageTintList = getColorStateList(R.color.black)
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)
            } else{
                binding.led1.imageTintList = getColorStateList(R.color.red)
                cptClick ++
                binding.cptClick.text="Nombre de click : $cptClick"
                characteristic?.value = byteArrayOf(0x01)
                bluetoothGatt?.writeCharacteristic(characteristic)

            }
        }
        binding.led2.setOnClickListener{
            if(binding.led2.imageTintList == getColorStateList(R.color.blue)){
                binding.led2.imageTintList = getColorStateList(R.color.black)
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)
            } else{
                binding.led2.imageTintList = getColorStateList(R.color.blue)
                cptClick ++
                binding.cptClick.text="Nombre de click : $cptClick"
                characteristic?.value = byteArrayOf(0x02)
                bluetoothGatt?.writeCharacteristic(characteristic)
            }
        }
        binding.led3.setOnClickListener{
            if(binding.led3.imageTintList == getColorStateList(R.color.yellow)){
                binding.led3.imageTintList = getColorStateList(R.color.black)
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)
            } else{
                binding.led3.imageTintList = getColorStateList(R.color.yellow)
                cptClick ++
                binding.cptClick.text="Nombre de click : $cptClick"
                characteristic?.value = byteArrayOf(0x03)
                bluetoothGatt?.writeCharacteristic(characteristic)
            }
        }
    }
    private fun displayContentConnected(){
        binding.detailloaderTitle.text= getString(R.string.device_led_text)
        binding.group.isVisible = true
        binding.group2.isInvisible = true
        clickOnLed()
    }

}