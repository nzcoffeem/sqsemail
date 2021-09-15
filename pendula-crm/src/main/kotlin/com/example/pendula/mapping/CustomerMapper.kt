package com.example.pendula.mapping

import com.example.pendula.model.Customer
import com.example.pendula.model.event.NewCustomerEvent
import java.util.UUID

fun Customer.toNewCustomerEvent() = NewCustomerEvent(this)