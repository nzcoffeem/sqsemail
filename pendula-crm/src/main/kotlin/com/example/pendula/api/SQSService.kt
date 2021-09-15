package com.example.pendula.api

import com.amazonaws.handlers.AsyncHandler
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.services.sqs.model.SendMessageResult
import com.example.pendula.model.Customer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class SQSService(
    private val amazonSQSAsync: AmazonSQSAsync
) {
    @Value("\${cloud.aws.endpoint.uri}")
    private lateinit var endpoint: String

    @Value("\${cloud.aws.endpoint.queue}")
    private lateinit var queue: String

    fun addCustomerToQueue(customer: Customer) {
        val json = jacksonObjectMapper().writeValueAsString(customer)
        with(SendMessageRequest()) {
            messageBody = json
            queueUrl = "$endpoint/queue/$queue"
            amazonSQSAsync.sendMessageAsync(this, object : AsyncHandler<SendMessageRequest, SendMessageResult> {
                override fun onSuccess(request: SendMessageRequest?, result: SendMessageResult?) {
                    print("success") // temporary
                }
                override fun onError(exception: Exception?) {
                    print(exception)
                    throw IllegalStateException("Message failed to post to QUEUE")
                }
            })
        }
    }
}