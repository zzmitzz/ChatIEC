package com.example.iec.ui.feature.main.tools.components.notes

import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.IECNote
import com.example.iec.ui.feature.LoadingDialog
import com.example.iec.ui.feature.main.tools.ToolVM
import com.example.iec.ui.theme.AtomicLoadingDialog
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    viewModel: ToolVM?
){
    val chips = mutableListOf(NoteType.ALL, NoteType.IMPORTANT, NoteType.UNIMPORTANT)
    var selectedChip by remember { mutableStateOf(NoteType.ALL) }


    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val uiState = viewModel!!.uiState.collectAsStateWithLifecycle()


    val notes = uiState.value.notes.toMutableStateList()

    val isLoading = uiState.value.isLoading

    SideEffect() {
        Log.d("NoteScreen", "onCreate entered $isLoading")
//        coroutineScope.launch {
//            viewModel.getNotes().onEach {
//                notes.addAll(it.notes)
//            }
//        }
//
//        onDispose {
//            coroutineScope.cancel()
//        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = "My Notes",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        FilterChipsRow(
            modifier = Modifier.fillMaxWidth(),
            chips = chips,
            selectedChip = selectedChip,
            onChipSelected = { selectedChip = it }
        )
        if(isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ){
                AtomicLoadingDialog()
            }
        }else{
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                itemsIndexed(
                    items = notes,
                    key = { index, item -> item.hashCode() + index }
                ) { index, item ->
                    ListItem(
                        item = item.title,
                        index = index,
                        onItemClick = { /* Handle item click */ },
                        onItemDelete = { notes.removeAt(index) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ListItem(
    item: String,
    index: Int,
    onItemClick: () -> Unit,
    onItemDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Item #${index + 1}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(onClick = onItemDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete item"
                )
            }
        }
    }
}
enum class NoteType(val title: String) {
    ALL("All"),
    IMPORTANT("Important"),
    UNIMPORTANT("Unimportant");
}


@Composable
fun FilterChipsRow(
    chips: List<NoteType>,
    selectedChip: NoteType,
    onChipSelected: (NoteType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chips.forEach { chip ->
            FilterChip(
                selected = chip == selectedChip,
                onClick = { onChipSelected(chip) },
                label = { Text(chip.title) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    enabled = true,
                    selected = chip == selectedChip
                )
            )
        }
    }
}