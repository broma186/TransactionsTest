package com.example.transactions.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.transactions.data.Transactions.transactionList
import com.example.transactions.data.Transaction
import com.example.transactions.utils.DateFormatter.dateFormat
import com.example.transactions.utils.DateFormatter.formatStringDate
import com.example.transactions.utils.DateFormatter.retrieveLocalDateFromInstant
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(): ViewModel() {

    private val _fromDate: MutableState<LocalDate?> = mutableStateOf(null)

    private val _toDate: MutableState<LocalDate?> = mutableStateOf(null)

    private val _fromDateStr: MutableState<String> = mutableStateOf(formatStringDate(_fromDate.value))
    val fromDateStr: MutableState<String> = _fromDateStr

    private val _toDateStr: MutableState<String> = mutableStateOf(formatStringDate(_toDate.value))
    val toDateStr: MutableState<String> = _toDateStr

    private val _transactions: MutableState<List<Transaction>> = mutableStateOf(calculateRelevantTransactions(transactionList))
    val transactions: MutableState<List<Transaction>> = _transactions

    private val _availableBalance: MutableState<String> = mutableStateOf(calculateCurrentBalance())
    val availableBalance: State<String> = _availableBalance

    private val _showDatePicker: MutableState<Boolean> = mutableStateOf(false)
    val showDatePicker: State<Boolean> = _showDatePicker


    private fun calculateRelevantTransactions(transactions: List<Transaction>): List<Transaction> {
        return if (_fromDate.value != null && _toDate.value != null) {
            transactions.filter {
                val transactionDate = LocalDate.parse(it.date, dateFormat)
                transactionDate >= _fromDate.value
                        && transactionDate <= _toDate.value
            }
        } else {
            transactions
        }
    }

    private fun calculateCurrentBalance(): String {
        return "${Currency.getInstance(Locale.getDefault()).symbol}${_transactions.value.sumOf { it.amount }.toFloat()}"
    }

    fun toggleDatePicker() {
        _showDatePicker.value = !_showDatePicker.value
    }

    fun updateFromDate(updatedDate: Long) {
        _fromDate.value = retrieveLocalDateFromInstant(updatedDate)
        _fromDateStr.value = formatStringDate(_fromDate.value)
        _transactions.value = calculateRelevantTransactions(transactionList)
        _availableBalance.value = calculateCurrentBalance()
    }

    fun updateToDate(updatedDate: Long) {
        _toDate.value = retrieveLocalDateFromInstant(updatedDate)
        _toDateStr.value = formatStringDate(_toDate.value)
        _transactions.value = calculateRelevantTransactions(transactionList)
        _availableBalance.value = calculateCurrentBalance()
    }

    fun resetDateRange() {
        _fromDate.value = null
        _toDate.value = null
        _fromDateStr.value = formatStringDate(null)
        _toDateStr.value = formatStringDate(null)
        _transactions.value = calculateRelevantTransactions(transactionList)
        _availableBalance.value = calculateCurrentBalance()
    }
}


