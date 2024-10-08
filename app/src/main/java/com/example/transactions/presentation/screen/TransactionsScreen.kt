package com.example.transactions.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.transactions.presentation.components.TransactionDateRangeFilter
import com.example.transactions.presentation.components.TransactionItem
import com.example.transactions.data.Transaction
import com.example.transactions.theme.TransactionsTheme
import com.example.transactions.presentation.viewmodel.TransactionsViewModel

@Composable
fun TravelSavingsScreen() {
    val viewModel: TransactionsViewModel = viewModel()
    TransactionsTheme {
        TravelSavingsScreenContent(
            viewModel.transactions.value,
            viewModel.availableBalance.value,
            viewModel.showDatePicker.value,
            viewModel.fromDateStr.value,
            viewModel.toDateStr.value,
            viewModel::toggleDatePicker,
            viewModel::updateFromDate,
            viewModel::updateToDate,
            viewModel::resetDateRange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelSavingsScreenContent(
    transactions: List<Transaction>,
    availableBalance: String,
    showDatePicker: Boolean,
    fromDateStr: String,
    toDateStr: String,
    toggleDatePicker: () -> Unit,
    updateFromDate: (updatedDate: Long) -> Unit,
    updateToDate: (updatedDate: Long) -> Unit,
    resetDateRange: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text("Travel Savings Account")
                },
                actions = {
                    IconButton(onClick = {
                        toggleDatePicker()
                    }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Filter Transactions"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (showDatePicker) {
            TransactionDateRangeFilter(onDateRangeSelected = {
                it.first?.let { fromDate ->
                    updateFromDate(fromDate)
                }
                it.second?.let { toDate ->
                    updateToDate(toDate)
                }
            }) {
                toggleDatePicker()
            }
        }
        Column(modifier = Modifier.padding(innerPadding)) {

            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Available Balance:  ",
                    fontWeight = FontWeight.W300,
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                )
                Text(
                    text = availableBalance,
                    fontWeight = FontWeight.W600,
                    fontSize = TextUnit(24f, TextUnitType.Sp),
                )
            }
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.padding(end = 16.dp)) {
                    Row {
                        Text(
                            text = "From Date: $fromDateStr",
                            fontWeight = FontWeight.W300,
                            fontSize = TextUnit(16f, TextUnitType.Sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Text(
                            text = "To Date: $toDateStr",
                            fontWeight = FontWeight.W300,
                            fontSize = TextUnit(16f, TextUnitType.Sp)
                        )
                    }
                }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            resetDateRange()
                        }
                    ) {
                        Text(
                            text = "RESET",
                            fontWeight = FontWeight.W600,
                            fontSize = TextUnit(16f, TextUnitType.Sp)
                        )
                    }
            }
            HorizontalDivider()

            LazyColumn {
                items(transactions.value) { transaction ->
                    TransactionItem(transaction = transaction)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelSavingsScreenPreview() {
    TransactionsTheme {
        TravelSavingsScreenContent(
            (viewModel() as TransactionsViewModel).transactions.value,
            "$0.00",
            false,
            "01/09/2024",
            "31/09/2024",
            { 3 },
            { 4 },
            {}
        ) {}
    }
}