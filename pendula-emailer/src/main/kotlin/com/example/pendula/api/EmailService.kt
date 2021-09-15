package com.example.pendula.api

import com.example.pendula.model.Customer
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

enum class EmailType {
    THANK_YOU,
    DECLINE
}

@Service
class EmailService(
    private val emailSender: JavaMailSender
) {
    companion object {
        private val templates = HashMap<EmailType, Pair<String, String>>()

        init {
            templates[EmailType.THANK_YOU] = Pair("Thank you", """Hello {firstName} {lastName}. Thank you for your email""")
            templates[EmailType.DECLINE] = Pair("Sorry", """Hello {firstName} {lastName}. Sorry!""")
        }
    }

    fun sendEmailToCustomer(customer: Customer, emailType: EmailType) {
        emailSender.send(SimpleMailMessage().apply {
            setFrom("contact@church.com")
            setTo(customer.email)
            setSubject(templates[emailType]!!.first)
            setText(templates[emailType]!!.second.run {
                this.replace("{firstName}", customer.firstName).replace("{lastName}", customer.lastName)
            })
        })
    }
}