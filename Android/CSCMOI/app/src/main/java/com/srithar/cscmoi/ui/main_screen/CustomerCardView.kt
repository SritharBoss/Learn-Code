package com.srithar.cscmoi.ui.main_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.srithar.cscmoi.domain.model.Customer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun CustomerCardView(person: Customer, isTranslated: Boolean, modifier: Modifier) {

    //val viewModel: MainViewModel= hiltViewModel()

    val disabledContainer = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    val disabledContent = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = if (person.amount != 0) {
            CardDefaults.cardElevation(defaultElevation = 2.dp)
        } else {
            CardDefaults.cardElevation(defaultElevation = 0.dp)
        },
        colors = if (person.amount != 0) {
            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        } else {
            CardDefaults.cardColors(
                containerColor = disabledContainer,
                contentColor = disabledContent
            )
        }
    ) {
        val name=(if(!isTranslated) person.first_name else (person.tr_first_name?:person.first_name))+" . "+person.last_name

        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                AnimatedContent(targetState = name, transitionSpec = {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                }) {
                        it ->
                    Text(text = it, fontSize = 18.sp, fontWeight = FontWeight.Bold,maxLines = 1,
                        overflow = TextOverflow.Visible,
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .basicMarquee())
                }
                Text(
                    text = "₹ " + (person.amount),
                    fontSize = 18.sp,
                    maxLines = 1,
                    color = if (person.amount > 0) MaterialTheme.colorScheme.primary else if (person.amount < 0) Color.Red else Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            if(person.note.isNotEmpty()){
                Text("Notes: ${person.note}", style = MaterialTheme.typography.titleMedium)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val village=if(!isTranslated) person.village else (person.tr_village?:person.village)

                AnimatedContent(targetState = village, transitionSpec = {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                }) {
                        it -> Text(text = it, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Text(text = "Pg. ${person.page_no} | ID. ${person.id}", style = MaterialTheme.typography.titleMedium, color  = Color.LightGray)
            }
        }
    }

}