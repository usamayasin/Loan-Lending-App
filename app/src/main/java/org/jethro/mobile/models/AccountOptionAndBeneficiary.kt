package org.jethro.mobile.models

import org.jethro.mobile.models.beneficiary.Beneficiary
import org.jethro.mobile.models.templates.account.AccountOptionsTemplate

/**
 * Created by dilpreet on 23/6/17.
 */

data class AccountOptionAndBeneficiary(
        val accountOptionsTemplate: AccountOptionsTemplate,
        val beneficiaryList: List<Beneficiary>)
