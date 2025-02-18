package com.fitness.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.R

@Composable
fun BottomBar(
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.background))
            .padding(bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IconButton(
                onClick = { onClick("HOME") },
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            }
            Text(
                text = "Home",
            )
        }
        Column(
        ) {
            IconButton(
                onClick = { onClick("MACROS") },
            ) {
                Icon(
                    imageVector = Icons.Default.PieChart,
                    contentDescription = "macros"
                )
            }
            Text(
                text = "Macros",
            )
        }
        Column(
        ) {
            IconButton(
                onClick = { onClick("PROFILE") },
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "profile"
                )
            }
            Text(
                text = "Profile",
            )
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(onClick = {})
}