
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.fitness.model.gym.ExerciseLog
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


@Composable
fun ExerciseProgressChart(
    data: List<ExerciseLog>,
    exerciseName: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White),
    ) {
        if (data.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "No progress data available for '$exerciseName'.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                )
            }
            return@Card
        }

        val entries = data.mapIndexed { idx, log ->
            val maxWeight = log.sets.maxOfOrNull { it.weight } ?: 0
            Entry(idx.toFloat(), maxWeight.toFloat())
        }

        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    description.isEnabled = false
                    axisRight.isEnabled   = false
                    xAxis.position        = XAxis.XAxisPosition.BOTTOM
                    legend.isEnabled      = false
                }
            },
            update = { chart ->
                val dataSet = LineDataSet(entries, "Progress").apply {
                    setDrawCircles(true)
                    lineWidth = 2f
                    setDrawValues(false)
                }
                chart.data = LineData(dataSet)
                chart.invalidate()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )

    }
}

