package br.com.ms.moipchallenge.validator

import br.com.ms.moipchallenge.error.ErrorObject
import br.com.ms.moipchallenge.models.Card
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.beanvalidation.SpringValidatorAdapter
import javax.validation.Validation
import javax.validation.Validator

class CardValidator {

    companion object {
        fun validate(card: Card) = getValidator()
                .let { validate(card, it) }
                .let { ErrorObject.toErrorObject(it.fieldErrors) }

        private fun validate(card: Card, validator: Validator) = card
                .let { BeanPropertyBindingResult(card, "card") }
                .also { SpringValidatorAdapter(validator).validate(card, it) }

        private fun getValidator() = Validation.buildDefaultValidatorFactory().validator
    }

}