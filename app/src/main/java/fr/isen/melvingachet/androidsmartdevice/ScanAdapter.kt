package fr.isen.melvingachet.androidsmartdevice


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.melvingachet.androidsmartdevice.databinding.ScanItemBinding

class ScanAdapter(var devices: ArrayList<android.bluetooth.BluetoothDevice>, var onDeviceClickListener:(android.bluetooth.BluetoothDevice)->Unit ) :
    RecyclerView.Adapter<ScanAdapter.ScanViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ScanItemBinding.inflate(inflater, parent, false)
        return ScanViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return devices.size
    }


    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ScanViewHolder, position: Int) {
        holder.deviceName.text = devices[position].name ?: "Inconnu"
        holder.deviceAddress.text = devices[position].address
        holder.itemView.setOnClickListener{
            onDeviceClickListener(devices[position])
        }

    }
    class ScanViewHolder(binding: ScanItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val deviceName=binding.deviceName
        val deviceAddress=binding.deviceAddress
    }

    fun addDevice(device: android.bluetooth.BluetoothDevice){
        var shouldAddDevice = true
        devices.forEachIndexed { index, bluetoothDevice ->
            if (bluetoothDevice.address == device.address){
                devices[index]= device
                shouldAddDevice = false
            }
        }
        if (shouldAddDevice){
            devices.add(device)
        }
    }

}

