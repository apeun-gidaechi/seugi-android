package com.seugi.workspacedetail.feature.workspacemember

import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seugi.designsystem.R
import com.seugi.designsystem.animation.bounceClick
import com.seugi.designsystem.component.SeugiMemberList
import com.seugi.designsystem.component.SeugiSegmentedButton
import com.seugi.designsystem.component.SeugiSegmentedButtonLayout
import com.seugi.designsystem.component.SeugiSmallDropDown
import com.seugi.designsystem.component.SeugiTopBar
import com.seugi.designsystem.theme.Gray100
import com.seugi.designsystem.theme.SeugiTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class TestMember(
    val name: String,
    val profile: String?,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceMemberScreen(
    viewModel: WorkspaceMemberViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    workspaceId: String
) {
    val dummyList = listOf("1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2")
    val members = listOf(TestMember("노영재", null), TestMember("노영재", null), TestMember("노영재", null))
    var selectedItem by remember { mutableStateOf("전체") }
    var isExpanded by remember { mutableStateOf(false) }

    val icon = if (isExpanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        Log.d("TAG", "workspace Id :$workspaceId ")
        viewModel.getAllMember("")
    }
    Log.d("TAG", "${state.member}: ")
    SeugiTheme {
        Scaffold(
            topBar = {
                SeugiTopBar(

                    title = {
                        Text(text = "멤버", style = MaterialTheme.typography.titleLarge)
                    },
                    onNavigationIconClick = {
                        popBackStack()
                    },
                    backIconCheck = true,
                    actions = {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.ic_search,
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .bounceClick({ Log.d("TAG", "검색: ") })
                                .padding(end = 16.dp)
                                .size(28.dp),
                        )
                    },
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(it),
            ) {
                Spacer(modifier = Modifier.height(6.dp))
                BoxWithConstraints(
                    modifier = Modifier
                        .height(48.dp)
                        .padding(horizontal = 20.dp),
                ) {
                    val dummyItems: ImmutableList<String> = persistentListOf("선생님", "학생")
                    val itemWidth = maxWidth / dummyItems.size
                    var selectedTabIndex by remember { mutableIntStateOf(0) }
                    SeugiSegmentedButtonLayout(
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth(),
                        containerColor = Gray100,
                        shape = RoundedCornerShape(12.dp),
                        indicatorShape = RoundedCornerShape(8.dp),
                        selectedIndex = selectedTabIndex,
                    ) {
                        dummyItems.fastForEachIndexed { index, text ->
                            SeugiSegmentedButton(
                                modifier = Modifier
                                    .width(itemWidth),
                                text = text,
                                selected = index == selectedTabIndex,
                            ) {
                                selectedTabIndex = index
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                SeugiSmallDropDown(
                    item = dummyList.toImmutableList(),
                    title = "전체",
                    onItemSelected = { selectedItem = it },
                    isExpanded = isExpanded,
                    selectedItem = selectedItem,
                    icon = icon,
                    onExpandedChanged = { isExpanded = it },
                    modifier = Modifier.padding(start = 24.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp),
                ) {
                    items(members) {
                        SeugiMemberList(
                            userName = it.name,
                            userProfile = it.profile,
                            onClick = {
                            },
                        )
                    }
                }
            }
        }
    }
}
