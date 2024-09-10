package com.example.transactions

import com.example.transactions.model.Transaction
import com.example.transactions.viewmodel.TransactionsViewModel
import io.mockk.MockKAnnotations
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TransactionsViewModelTest {

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

    private var viewModel: TransactionsViewModel? = null

    private val defaultDateStr = "Add Date"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = TransactionsViewModel()
    }

    @Test
    fun `the initial value of the date strings is set to the default`() {
        assertEquals(defaultDateStr, viewModel?.toDateStr?.value)
        assertEquals(defaultDateStr, viewModel?.fromDateStr?.value)
    }

    @Test
    fun `initially there is a full list of transactions`() {
        assertEquals(transactionList.size, viewModel?.transactions?.value?.size)
    }

    @Test
    fun `the available balance is set`() {
        assertNotEquals("", viewModel?.availableBalance?.value)
    }

    @Test
    fun `date picker is not shown`() {
        assertNotEquals(false, viewModel?.showDatePicker?.value)
    }

    @Test
    fun `given the to and from date is updated, filter the transactions correctly`() {
        viewModel?.updateFromDate(1726006387766)
        viewModel?.updateToDate(1729999999999)
        assertTrue(viewModel?.transactions?.value?.size!! < transactionList.size && viewModel?.transactions?.value?.size!! > 0)

        // Reset the date range
        viewModel?.resetDateRange()
        assertEquals(defaultDateStr, viewModel?.fromDateStr?.value) // Dates default
        assertEquals(defaultDateStr, viewModel?.toDateStr?.value)

        // Filter reset so all transactions should be present
        assertEquals(viewModel?.transactions?.value?.size!!, transactionList.size)
    }
}