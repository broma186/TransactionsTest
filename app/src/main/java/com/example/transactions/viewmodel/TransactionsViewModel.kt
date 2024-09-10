package com.example.transactions.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.transactions.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(): ViewModel() {

    private val transactionList = listOf(
        Transaction("2024-09-22", "Restaurant", -35.00),
        Transaction("2024-09-24", "Car Repair", -150.00),
        Transaction("2024-09-11", "Utilities", -150.00),
        Transaction("2024-09-19", "Clothing Store", -100.00),
        Transaction("2024-09-12", "Car Repair", -200.00),
        Transaction("2024-09-13", "Book Purchase", -30.00),
        Transaction("2024-09-14", "Electronics", -500.00),
        Transaction("2024-09-17", "Groceries", -70.00),
        Transaction("2024-09-26", "Electronics", -300.00),
        Transaction("2024-09-18", "Gym Membership", -50.00),
        Transaction("2024-09-01", "Coffee Shop", -15.00),
        Transaction("2024-09-02", "Grocery Store", -75.00),
        Transaction("2024-09-05", "Clothing Store", -120.00),
        Transaction("2024-09-06", "Gym Membership", -50.00),
        Transaction("2024-09-30", "Gym Membership", -50.00),
        Transaction("2024-09-15", "Vacation", -1500.00),
        Transaction("2024-09-07", "Movie Tickets", -30.00),
        Transaction("2024-09-08", "Salary", 6500.00),
        Transaction("2024-09-09", "Groceries", -80.00),
        Transaction("2024-09-23", "Groceries", -90.00),
        Transaction("2024-09-10", "Rent", -1200.00),
        Transaction("2024-09-20", "Movie Tickets", -25.00),
        Transaction("2024-09-21", "Gas Station", -55.00),
        Transaction("2024-09-25", "Utilities", -120.00),
        Transaction("2024-09-27", "Vacation", -1000.00),
        Transaction("2024-09-28", "Restaurant", -45.00),
        Transaction("2024-09-29", "Groceries", -85.00),
        Transaction("2024-09-16", "Restaurant", -40.00),
        Transaction("2024-09-03", "Restaurant", -35.00),
        Transaction("2024-09-04", "Gas Station", -60.00),
    )

    private val _fromDate: MutableState<LocalDate?> = mutableStateOf(null)

    private val _toDate: MutableState<LocalDate?> = mutableStateOf(null)

    private val _fromDateStr: MutableState<String> = mutableStateOf(formatStringDate(_fromDate.value))
    val fromDateStr: MutableState<String> = _fromDateStr

    private val _toDateStr: MutableState<String> = mutableStateOf(formatStringDate(_toDate.value))
    val toDateStr: MutableState<String> = _toDateStr

    private val _transactions: MutableState<List<Transaction>> = mutableStateOf(calculateRelevantTransactions(transactionList))
    val transactions: State<List<Transaction>> = _transactions

    private val _availableBalance: MutableState<String> = mutableStateOf(calculateCurrentBalance())
    val availableBalance: State<String> = _availableBalance

    private val _showDatePicker: MutableState<Boolean> = mutableStateOf(false)
    val showDatePicker: State<Boolean> = _showDatePicker

    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

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

    private fun formatStringDate(dateToFormat: LocalDate?): String {
        return dateToFormat?.let {
            dateFormat.format(dateToFormat)
        } ?: "Add Date"
    }

    fun toggleDatePicker() {
        _showDatePicker.value = !_showDatePicker.value
    }

    fun updateFromDate(updatedDate: Long) {
        _fromDate.value = LocalDateTime.ofInstant(Instant.ofEpochMilli(updatedDate), ZoneId.systemDefault()).toLocalDate()
        _fromDateStr.value = formatStringDate(_fromDate.value)
        _transactions.value = calculateRelevantTransactions(transactionList)
        _availableBalance.value = calculateCurrentBalance()
    }

    fun updateToDate(updatedDate: Long) {
        _toDate.value = LocalDateTime.ofInstant(Instant.ofEpochMilli(updatedDate), ZoneId.systemDefault()).toLocalDate()
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


