package com.example.tourmanage.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@Composable
fun AreaIconWidget(
    modifier: Modifier,
    areaItem: AreaItem?,
    isChild: Boolean
) {
    val backgroundColor = if (isChild) colorResource(id = R.color.cornflower_blue) else colorResource(id = R.color.lightpink)
    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = areaItem?.name.isEmptyString(),
            style = TextStyle(
                fontSize = 11.sp,
                fontFamily = spoqaHanSansNeoFont,
                fontWeight = FontWeight.Medium,
            )
        )
    }
}

@Preview
@Composable
fun AreaIconWidgetPreview() {
    TourManageTheme {
        AreaIconWidget(
            modifier = Modifier
                .width(60.dp)
                .wrapContentHeight()
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .padding(5.dp),
            areaItem = AreaItem(rNum = "1", name = "서울", code = "1"),
            isChild = false
        )
    }
}