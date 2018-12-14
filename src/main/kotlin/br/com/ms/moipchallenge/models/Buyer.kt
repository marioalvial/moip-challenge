package br.com.ms.moipchallenge.models

import br.com.ms.moipchallenge.resources.BuyerResource
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.hibernate.validator.constraints.br.CPF
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@JsonSerialize(using = BuyerResource::class)
data class Buyer(
        @field:NotBlank(message = "{buyer.name.not.blank}")
        @Column(nullable = false)
        val name: String,
        @field:NotBlank(message = "{buyer.email.not.blank}")
        @field:Email(message = "{buyer.email.invalid}")
        @Column(nullable = false)
        val email: String,
        @field:CPF(message = "{buyer.cpf.invalid}")
        @field:NotBlank(message = "{buyer.cpf.not.blank}")
        @Column(nullable = false)
        val cpf: String
) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0

    @JsonProperty(access = READ_ONLY)
    val createdAt = LocalDateTime.now()
    @JsonProperty(access = READ_ONLY)
    val updatedAt = LocalDateTime.now()
}