package com.example.pendula.api

import com.amazonaws.handlers.AsyncHandler
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.model.ReceiveMessageRequest
import com.amazonaws.services.sqs.model.ReceiveMessageResult
import com.example.pendula.api.EmailType.DECLINE
import com.example.pendula.api.EmailType.THANK_YOU
import com.example.pendula.model.Customer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class QueueListener(
    private val emailService: EmailService,
    private val sqsAsync: AmazonSQSAsync
) {

    @Value("\${cloud.aws.endpoint.uri}")
    private lateinit var endpoint: String

    @Value("\${cloud.aws.endpoint.queue}")
    private lateinit var queue: String

    @Scheduled(fixedRate = 5000) // Move this to a application property
    // Could use spring annotation for listening to a queue (@SqsListener("$queue"))
    fun listenerOnQueue() {
        val request = ReceiveMessageRequest("$endpoint/queue/$queue").apply {
            maxNumberOfMessages = 5 // Move this to a application property
        }

        sqsAsync.receiveMessageAsync(request, object : AsyncHandler<ReceiveMessageRequest, ReceiveMessageResult> {
            override fun onSuccess(request: ReceiveMessageRequest?, result: ReceiveMessageResult?) {
                result?.messages?.run {
                    this.map {
                        Pair(it.receiptHandle, jacksonObjectMapper().readValue(it.body, Customer::class.java))
                    }.partition {
                        it.second.postcode.startsWith("NW8 ")
                    }.run {
                        first.forEach { // Postcodes starting with NW8
                            emailService.sendEmailToCustomer(customer = it.second, THANK_YOU);
                            sqsAsync.deleteMessage("$endpoint/queue/$queue", it.first);
                        } // All others
                        second.forEach {
                            emailService.sendEmailToCustomer(customer = it.second, DECLINE);
                            sqsAsync.deleteMessage("$endpoint/queue/$queue", it.first);
                        }
                    }
                }
            }

            override fun onError(exception: Exception?) {
                println(exception)
            }
        })
    }
}