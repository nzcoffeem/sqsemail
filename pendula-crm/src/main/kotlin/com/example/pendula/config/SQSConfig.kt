package com.example.pendula.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SQSConfig {

    @Value("\${cloud.aws.endpoint.uri}")
    private lateinit var endpointUrl: String

    @Value("\${cloud.aws.region.static}")
    private lateinit var region: String

    @Value("\${cloud.aws.credentials.access-key:x}")
    private lateinit var accessKey: String

    @Value("\${cloud.aws.credentials.secret-key:x}")
    private lateinit var secretKey: String

    @Bean
    fun amazonSQSAsync(): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder.standard()
            .withEndpointConfiguration(EndpointConfiguration(
                endpointUrl, region))
            .withCredentials(AWSStaticCredentialsProvider(
                BasicAWSCredentials(accessKey, secretKey)
            )).build()
    }
}