package com.seugi.workspacedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seugi.designsystem.R
import com.seugi.designsystem.component.ButtonType
import com.seugi.designsystem.component.DividerType
import com.seugi.designsystem.component.SeugiButton
import com.seugi.designsystem.component.SeugiDivider
import com.seugi.designsystem.component.SeugiRoundedCircleImage
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.component.Size
import com.seugi.designsystem.theme.Gray400
import com.seugi.designsystem.theme.Gray500
import com.seugi.designsystem.theme.SeugiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceDetailScreen() {
    SeugiTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SeugiTopBar(
                    title = {
                        Text(
                            text = "내 학교", style = MaterialTheme.typography.titleLarge
                        )
                    },
                    onNavigationIconClick = {},
                    backIconCheck = true
                )
            }
        ) {
            Column(
              modifier = Modifier
                  .padding(it)
                  .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 24.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SeugiRoundedCircleImage(
                        size = Size.ExtraSmall,
                        image = "",
                        onClick = {},
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 20.dp)
                            .background(Color.White)
                            .fillMaxSize()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            text = "경북대학교 사범대학 부설 고등학교",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        SeugiButton(
                            onClick = { /*TODO*/ },
                            type = ButtonType.Gray,
                            text = "학교 전환"
                        )
                    }
                }
                SeugiDivider(
                    type = DividerType.WIDTH,
                    size = 8.dp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(28.dp),
                        painter = painterResource(id = R.drawable.ic_setting_fill),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Color.Black),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "일반", style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_expand_right_line),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Gray400),
                        contentScale = ContentScale.Crop,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "알림 설정", style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_expand_right_line),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Gray400),
                        contentScale = ContentScale.Crop,
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(28.dp),
                        painter = painterResource(id = R.drawable.ic_person_fill),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Color.Black),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "멤버", style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_expand_right_line),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Gray400),
                        contentScale = ContentScale.Crop,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "멤버 초대", style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_expand_right_line),
                        contentDescription = "설정 톱니바퀴",
                        colorFilter = ColorFilter.tint(Gray400),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}
