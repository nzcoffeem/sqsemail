package com.example.pendula.api

import com.example.pendula.mapping.toNewCustomerEvent
import com.example.pendula.model.Customer
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
class CustomerController(
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @MessageMapping("/register")
    @SendTo("/topic/newcustomers")
    fun register(newCustomer: Customer): Customer {
        applicationEventPublisher.publishEvent(newCustomer.toNewCustomerEvent())
        return newCustomer
    }

    @RequestMapping(
        value = ["/services/payload"],
        method = [POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun newCustomer(@RequestBody(required = true) event: Customer) {
        applicationEventPublisher.publishEvent(event.toNewCustomerEvent())
    }
}