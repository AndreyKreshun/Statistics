package com.example.statistics.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.statistics.ui.theme.FemaleColor
import com.example.statistics.ui.theme.MaleColor

@Composable
fun AgeGroupItem(
    ageGroup: String,
    malePercentage: Float,
    femalePercentage: Float
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)) {

        Text(
            text = ageGroup,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(80.dp))
            LinearProgressIndicator(
                progress = malePercentage / 100f,
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaleColor,
                trackColor = Color.Transparent
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${"%.1f".format(malePercentage)}%",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(80.dp))
            LinearProgressIndicator(
                progress = femalePercentage / 100f,
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = FemaleColor,
                trackColor = Color.Transparent
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${"%.1f".format(femalePercentage)}%",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}