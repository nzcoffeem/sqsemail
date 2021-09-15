package com.example.pendula.model.event

import com.example.pendula.model.Customer
import org.springframework.context.ApplicationEvent

class NewCustomerEvent(customer: Customer) : ApplicationEvent(customer) {
    var customer = customer
        private set
}