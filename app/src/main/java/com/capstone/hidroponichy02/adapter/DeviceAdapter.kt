package com.capstone.hidroponichy02.adapter

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hidroponichy02.activity.DetailActivity
import com.capstone.hidroponichy02.databinding.ItemListDeviceBinding
import com.capstone.hidroponichy02.model.UserModel
import com.capstone.hidroponichy02.response.DataItem

class DeviceAdapter :
    PagingDataAdapter<DataItem, DeviceAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var user: UserModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class ViewHolder(private var binding: ItemListDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {


        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(device: DataItem) {
            with(binding) {
                tvProduct.text = device.product.title
                tvPlant.text = device.planted.name
                tvDescPlant.text = device.description

                tvProduct.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ID, device.id)
                    intent.putExtra(DetailActivity.EXTRA_NAME, device.product.title)
                    intent.putExtra(DetailActivity.EXTRA_PLANT, device.planted.name)
                    intent.putExtra(DetailActivity.EXTRA_STATUS, device.statuss)
                    //intent.putExtra(DetailActivity.EXTRA_TOKEN, user.token)


                    it.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return when {
                    oldItem.id != newItem.id -> {
                        true
                    }
                    oldItem.description != newItem.description -> {
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
