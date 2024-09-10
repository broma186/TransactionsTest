package com.example.kbandroidtechassessment.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kbandroidtechassessment.components.TransactionItem
import com.example.kbandroidtechassessment.model.Transaction
import com.example.kbandroidtechassessment.ui.theme.KBAndroidTechAssessmentTheme
import com.example.kbandroidtechassessment.viewmodel.TravelSavingsViewModel

@Composable
fun TravelSavingsScreen() {
    val viewModel: TravelSavingsViewModel = viewModel()
    KBAndroidTechAssessmentTheme {
        TravelSavingsScreenContent(viewModel.transactions.value)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelSavingsScreenContent(
    transactions: List<Transaction>
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text("Travel Savings Account")
                },
                actions = {
                    IconButton(onClick = { /* Handle filter action */ }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Filter"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Available Balance:  ",
                    fontWeight = FontWeight.W300,
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                )
                Text(
                    text = "$0.00",
                    fontWeight = FontWeight.W600,
                    fontSize = TextUnit(24f, TextUnitType.Sp),
                )
            }
            HorizontalDivider()

            LazyColumn {
                items(transactions) { transaction ->
                    TransactionItem(transaction = transaction)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelSavingsScreenPreview() {
    KBAndroidTechAssessmentTheme {
        TravelSavingsScreenContent((viewModel() as TravelSavingsViewModel).transactions.value)
    }
}