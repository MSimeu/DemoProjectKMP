package com.example.android_footballmatches.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.android_footballmatches.data.model.League
import com.example.android_footballmatches.data.model.Logos
import com.example.android_footballmatches.presentation.ui.theme.FootballMatchTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FootballMatchTheme {
                val uiState by viewModel.uiState.collectAsState()
                LeagueMatchScreen(uiState = uiState)
            }
        }
    }
}

@Composable
private fun LeagueMatchScreen(uiState: UiState) {
    when (uiState) {
        is UiState.Loading -> LoadingView()
        is UiState.Success -> LeagueMatchList(uiState)
        is UiState.Error -> ErrorView(uiState.message)
    }
}

@Composable
private fun LeagueMatchList(uiState: UiState.Success) {
    LazyColumn {
        items(uiState.leagueList) { league ->
            LeagueMatchCard(
                league = league
            )
        }
    }
}

@Composable
private fun ErrorView(reason: String) {
    Text(reason)
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
internal fun LeagueMatchCard(league: League) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { /* Handle click here if needed */ }
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            LeagueLogo(logoUrl = league.logos.light)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = league.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = league.abbr,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun LeagueLogo(logoUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(logoUrl),
        contentDescription = null,
        modifier = Modifier.size(50.dp)
    )
}

@Preview(name = "Loading Preview")
@Composable
private fun LoadingPreview() {
    LoadingView()
}

@Preview(name = "Error Preview")
@Composable
private fun ErrorPreview() {
    ErrorView("Failed to fetch data")
}

@Preview(name = "League Match Card Preview")
@Composable
private fun LeagueMatchCardPreview() {
    val frenchLeague = League(
        id = "1",
        name = "French Ligue 1",
        abbr = "Ligue 1",
        logos = Logos(
            light = "https://a.espncdn.com/i/leaguelogos/soccer/500/9.png",
            dark = "https://a.espncdn.com/i/leaguelogos/soccer/500-dark/9.png"
        )
    )
    LeagueMatchCard(league = frenchLeague)
}

@Preview(showBackground = true)
@Composable
fun LeagueListPreview() {
    val frenchLeague = League(
        id = "1",
        name = "French Ligue 1",
        abbr = "Ligue 1",
        logos = Logos(
            light = "https://a.espncdn.com/i/leaguelogos/soccer/500/9.png",
            dark = "https://a.espncdn.com/i/leaguelogos/soccer/500-dark/9.png"
        )
    )

    val germanLeague = League(
        id = "2",
        name = "German Bundesliga",
        abbr = "Bundesliga",
        logos = Logos(
            light = "https://a.espncdn.com/i/leaguelogos/soccer/500/10.png",
            dark = "https://a.espncdn.com/i/leaguelogos/soccer/500-dark/10.png"
        )
    )

    val spanishLeague = League(
        id = "3",
        name = "Spanish La Liga",
        abbr = "La Liga",
        logos = Logos(
            light = "https://a.espncdn.com/i/leaguelogos/soccer/500/15.png",
            dark = "https://a.espncdn.com/i/leaguelogos/soccer/500-dark/15.png"
        )
    )
    val leagues = listOf(frenchLeague, germanLeague, spanishLeague)
    LeagueMatchList(uiState = UiState.Success(leagueList = leagues))
}