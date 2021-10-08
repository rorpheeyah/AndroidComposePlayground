package com.rorpheeyah.androidcomposeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.NonNull
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.rorpheeyah.androidcomposeplayground.ui.theme.AndroidComposePlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposePlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ImageList()
                }
            }
        }
    }
}

const val baseImageUrl : String = "https://source.unsplash.com/random/800x800/?img="

@Composable
fun PhotographerCard(@NonNull imgUrl : String){
    Row{
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
        ) {
            Image(
                painter = rememberImagePainter(
                    data = imgUrl,
                    builder = {
                        placeholder(R.drawable.loading)
                        error(R.drawable.placeholder)
                    }
                ),
                contentDescription = "",
                contentScale = ContentScale.Inside
            )
        }

        Column (
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ){
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            // LocalContentAlpha is defining opacity level of its children
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun ImageList(){
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 5.dp)
    ){
        items(50){
            ImageListItem( "$baseImageUrl/$it", (it + 1).toString())
        }
    }
}

@Composable
fun ImageListItem(@NonNull imgUrl: String,@NonNull text: String) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = {}),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Surface(
            modifier = Modifier.padding(16.dp, 5.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically){
                PhotographerCard(imgUrl)
                Spacer(Modifier.width(10.dp))
                Box(modifier = Modifier.fillMaxSize()){
                    Text(text,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    )
                }
            }
        }
    }
    Spacer(Modifier.height(5.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidComposePlaygroundTheme {
        ImageList()
    }
}