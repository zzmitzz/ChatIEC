package com.example.iec.ui.feature.main.message.list_chat

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.uidata.ChatPreview
import com.example.data.uidata.MessageChatItem
import com.example.iec.R
import com.example.iec.ui.feature.LoadingDialog
import com.example.iec.ui.feature.main.message.ChatMessageVM
import com.example.iec.ui.feature.main.message.ChatScreenState
import com.example.iec.ui.theme.AtomicLoadingDialog


@Composable
fun ListChatScreen(
    navToMessageID: (String) -> Unit,
    viewModel: ChatMessageVM = hiltViewModel()
){
    // Collect like this if uiState in viewModel is State
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    // Collect by using : viewModel.uiState.collectAsState() if it is stateflow for more complex
    Scaffold(
        topBar = { TopBar(
            onSearchClick = {viewModel.updateStringQuery("")}
        ) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            ListChatScreenStateLess(
                stringQuery = uiState.stringQuery,
                updateStringQuery = {viewModel.updateStringQuery(it)},
                navigateToPersonalID = navToMessageID,
                chats = uiState.dataReady
            )
        }
        if(uiState.isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ){
                AtomicLoadingDialog()
            }
        }
    }
}


@Composable
fun ListChatScreenStateLess(
    stringQuery: String? = "124214",
    updateStringQuery: (String) -> Unit = {
    },
    navigateToPersonalID: (String) -> Unit = {},
    chats: List<ChatPreview> = listOf(),
){
    SideEffect {
        Log.d("ListChatScreen", "onCreate entered $stringQuery")
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if(stringQuery!=null){
            TextField(
                value = stringQuery,
                onValueChange = updateStringQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp)
                    .padding(horizontal = 16.dp),
                placeholder = { Text("Search...", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Gray
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp)
            )
        }
        MessageChatBody(
            navigateToMessage = navigateToPersonalID,
            items = chats
        )
    }
}

@Composable
fun TopBar(
    onSearchClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaderItem(name = "ZzmitzZ")
        Row(
        ) {
            Icon(
                modifier = Modifier.size(30.dp).clickable {
                    onSearchClick()
                },
                imageVector = Icons.Default.Search,
                contentDescription = ""
            )
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.MoreVert,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun HeaderItem(
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(start = 8.dp)
    ) {
        Text(
            text = "Hola, ",
            color = Color.DarkGray,
            modifier = Modifier.alpha(0.5f)
        )
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun MessageChatBody(
    navigateToMessage: (String) -> Unit,
    items: List<ChatPreview> = listOf()
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        itemsIndexed(
            items = items,
            key = { index, item -> item.hashCode() + index },
        ) { index, item ->
            Box(modifier = Modifier.clickable {
                navigateToMessage(item.senderName)
            }){
                ChatItemStateless(
                    chatItem = item
                )
            }
        }
    }
}

@Composable
fun ChatItemStateless(
    chatItem: ChatPreview,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar with online indicator
        Box(
            modifier = Modifier.size(40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ptit_iec), // Replace with actual image loading
                contentDescription = "Profile picture of ${chatItem.senderName}",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            if (chatItem.isOnline) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF6200EE))
                        .align(Alignment.BottomEnd)
                        .padding(2.dp)
                        .border(2.dp, Color.White, CircleShape)
                )
            }
        }

        // Text content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chatItem.senderName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Text(
                    text = chatItem.timestamp.toString(),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            if(chatItem.isOnline){
                Text(
                    text = "${chatItem.senderName} is typing ... ",
                    fontSize = 14.sp,
                    color = Color(0xFF6200EE),
                    maxLines = 1,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }else{
                Text(
                    text = chatItem.lastMessage,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

@Preview(showBackground = true)
@Composable
fun ContentBodyPreview() {
    MessageChatBody(
        navigateToMessage = {},)
}

@Preview(showBackground = true)
@Composable
fun ChatMessagePreview() {
    ChatItemStateless(
        ChatPreview(
            avatarUrl = "url_to_avatar",
            senderName = "Larry Machigo",
            lastMessage = "Ok. Let me check",
            timestamp = 0L,
            isOnline = true,
            id = "9"
        )
    )
}
@Preview
@Composable
fun ListChatPreview(){
    ListChatScreen({})
}