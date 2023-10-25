@file:Suppress("FunctionName")

package bg.dalexiev.rss.common.compose.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import bg.dalexiev.rss.common.data.Item

@Composable
fun Feed(viewModel: FeedViewModel, padding: PaddingValues, onClick: (Item) -> Unit) {
    val uiState: FeedUiState by viewModel.uiState.collectAsState()

    when (uiState) {
        FeedUiState.Error -> EmptyFeed()
        is FeedUiState.Loaded -> FeedItemList((uiState as FeedUiState.Loaded).items, onClick)
        FeedUiState.Loading -> FeedProgressIndicator()
    }
}

@Composable
fun FeedItemList(items: List<Item>, onClick: (Item) -> Unit) {
    LazyColumn(
        modifier = Modifier.testTag("feed_item_list"),
        contentPadding = PaddingValues(
            top = 32.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(items = items, key = { _, item -> item.guid.value }) { idx, item ->
            FeedItem(idx, item, onClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedItem(index: Int, item: Item, onClick: (Item) -> Unit) {
    Card(
        modifier = Modifier.testTag("item_$index"),
        onClick = { onClick(item) },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = item.title.value, style = MaterialTheme.typography.h5)
            Text(text = item.author.name, style = MaterialTheme.typography.caption)
            Text(text = item.description.value, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun FeedProgressIndicator() {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.size(128.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Loading feed",
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun EmptyFeed() {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(128.dp),
            imageVector = Icons.Default.List,
            contentDescription = "Empty feed icon"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Couldn't load feed",
            style = MaterialTheme.typography.body1
        )
    }
}
