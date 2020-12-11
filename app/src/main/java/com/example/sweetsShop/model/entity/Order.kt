package com.example.sweetsShop.model.entity

import android.os.Parcelable
import com.example.sweetsShop.model.item.OrderStatus
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
        @Expose val id: Long = 0,
        @Expose val products: ArrayList<String> = arrayListOf(),
        @Expose var status: OrderStatus = OrderStatus.WAIT_CONFIRM,
        @Expose val operatorId: Long? = null,
        @Expose val clientId: Long,
        @Expose var chefId: Long? = null,
        @Expose val error: String? = null
) : Parcelable