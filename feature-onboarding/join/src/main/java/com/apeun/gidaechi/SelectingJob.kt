package com.apeun.gidaechi

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apeun.gidaechi.designsystem.animation.bounceClick
import com.apeun.gidaechi.designsystem.component.ButtonType
import com.apeun.gidaechi.designsystem.component.SeugiFullWidthButton
import com.apeun.gidaechi.designsystem.component.SeugiTopBar
import com.apeun.gidaechi.designsystem.theme.Gray100
import com.apeun.gidaechi.designsystem.theme.Primary500
import com.apeun.gidaechi.designsystem.theme.SeugiTheme
import com.apeun.gidaechi.join.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectingJobScreen() {
    var studentOnOff by remember {
        mutableStateOf(true)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SeugiTopBar(
                title = { Text(text = "회원가입", style = MaterialTheme.typography.titleLarge) },
                onNavigationIconClick = { Log.d("TAG", "뒤로가기: ") },
                backIconCheck = true,
            )
        },
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.weight(0.4f))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = "학생이신가요?\n" +"아니면 선생님이신가요?",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.bounceClick({}))
                }
                Row{
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(shape = RoundedCornerShape(12.dp), color = Gray100)
                        .border(
                            border = BorderStroke(1.dp, color = if (studentOnOff) Primary500 else Gray100,),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .bounceClick({
                                    studentOnOff = true
                                    Log.d("TAG", "$studentOnOff: ")
                                }),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Image(painter = painterResource(id = if (studentOnOff)R.drawable.img_student_on else R.drawable.img_student_off), contentDescription = "")
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "학생", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(shape = RoundedCornerShape(12.dp), color = Gray100)
                        .border(
                            border = BorderStroke(1.dp, color = if (!studentOnOff) Primary500 else Gray100,),
                            shape = RoundedCornerShape(12.dp)
                        )
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .bounceClick({
                                    studentOnOff = false
                                    Log.d("TAG", "$studentOnOff: ")
                                }),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Image(painter = painterResource(id = if (!studentOnOff)R.drawable.img_teacher_on else R.drawable.img_teacher_off), contentDescription = "")
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "선생님", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "이미 계정이 있으신가요?",
                    style = MaterialTheme.typography.bodySmall,
                    color = Primary500,
                    modifier = Modifier.bounceClick({}))
                SeugiFullWidthButton(
                    onClick = { /*TODO*/ },
                    type = ButtonType.Primary,
                    text = "계속하기",
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            
            
        }
    }
}

@Composable
@Preview
private fun PreviewJoinScreen() {
    SeugiTheme {
        SelectingJobScreen()
    }
}