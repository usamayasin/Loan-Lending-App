package org.jethro.mobile.models.accounts

import org.jethro.mobile.models.accounts.savings.SavingAccount

import java.util.ArrayList

/**
 * @author Vishwajeet
 * @since 13/08/16
 */
data class SavingAccountsListResponse (
    var savingsAccounts: List<SavingAccount> = ArrayList()
)
