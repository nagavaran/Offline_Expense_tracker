package com.compose.offline.expencetracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ExpensePieChart(data: List<Pair<String, Float>>) {

    val total = data.sumOf { it.second.toDouble() }.toFloat()

    val colors = listOf(
        Color(0xFFE57373), // red
        Color(0xFF64B5F6), // blue
        Color(0xFF81C784), // green
        Color(0xFFFFB74D), // orange
        Color(0xFFBA68C8), // purple
        Color(0xFF4DB6AC), // teal
        Color(0xFFFF8A65), // coral
        Color(0xFFA1887F)  // brown
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .size(220.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {

                var startAngle = -90f

                data.forEachIndexed { index, (_, value) ->

                    val sweep = (value / total) * 360f

                    drawArc(
                        color = colors[index % colors.size],
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter = true
                    )

                    startAngle += sweep
                }
            }

            Text(
                text = "₹${total.toInt()}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {

            data.forEachIndexed { index, (category, value) ->

                val color = colors[index % colors.size]

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Color Box
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .background(color, shape = RoundedCornerShape(4.dp))
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    // Category Name
                    Text(
                        text = category,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    // Amount
                    Text(
                        text = "₹${value.toInt()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}