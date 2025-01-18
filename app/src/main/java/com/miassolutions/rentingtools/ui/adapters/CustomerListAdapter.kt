package com.miassolutions.rentingtools.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miassolutions.rentingtools.R
import com.miassolutions.rentingtools.data.models.Customer
import com.miassolutions.rentingtools.databinding.ItemCustomerBinding

class CustomerListAdapter(
    val dialerClickListener: (Customer) -> Unit,
    val navigationClickListener: (Customer) -> Unit,
    val navToDetailsClickListener: (Customer) -> Unit,
) : ListAdapter<Customer, CustomerListAdapter.CustomerVH>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Customer>() {
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean = oldItem.customerId == newItem.customerId

            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean  = oldItem == newItem


        }
    }


    inner class CustomerVH(private val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(customer: Customer) {
            binding.apply {
                tvCustomerName.text = customer.customerName
                tvConstructionPlace.text = customer.constructionPlace
                tvCustomerPhone.text = customer.customerPhone

                if (customer.customerPic.isNotEmpty()) {
                    val customerPicUri = Uri.parse(customer.customerPic)
                    ivCustomer.setImageURI(customerPicUri)
                } else {
                    ivCustomer.setImageResource(R.drawable.place_holder_image)
                }


                ivPhone.setOnClickListener {
                    dialerClickListener(customer)
                }

                root.setOnClickListener {
                    navToDetailsClickListener(customer)
                }

                ivCustomer.setOnLongClickListener {
                    navigationClickListener(customer)
                    true
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerVH {
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = ItemCustomerBinding.inflate(inflater, parent, false)
        return CustomerVH(mBinding)
    }

    override fun onBindViewHolder(holder: CustomerVH, position: Int) = holder.bind(getItem(position))
}