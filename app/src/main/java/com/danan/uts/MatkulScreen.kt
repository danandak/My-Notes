package com.danan.uts

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danan.uts.model.MataKuliah
//import androidx.compose.foundation.lazy.items

@Composable
fun LoadMatkul(matkulList: MutableList<MataKuliah>, contentPadding : PaddingValues = PaddingValues(0.dp)) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        contentPadding = contentPadding,
        modifier = Modifier
            .padding(bottom = 10.dp)
    ) {
        items(matkulList) { matkul ->
            MatkulCard(
                matkul = matkul,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun MatkulCard(matkul : MataKuliah, modifier: Modifier = Modifier) {
    Card (
        modifier = modifier
    ) {
        Row {
            Box(
                modifier = Modifier
//                    .weight(1f)
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = matkul.logo),
                    contentDescription = LocalContext.current.getString(matkul.nama),
                    modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = LocalContext.current.getString(matkul.nama),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = LocalContext.current.getString(matkul.sks),
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}