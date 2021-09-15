package com.example.pendula.event.handler

import com.example.pendula.api.SQSService
import com.example.pendula.model.Customer
import com.example.pendula.model.event.NewCustomerEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class AddNewCustomerToQueueHandler(
    private val sqsService: SQSService
) {
    @EventListener
    fun addToQueue(newCustomerEvent: NewCustomerEvent) {
        sqsService.addCustomerToQueue(newCustomerEvent.customer)
    }
}