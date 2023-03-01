package com.ns.theendcompose.ui.components.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ns.theendcompose.data.model.Member
import com.ns.theendcompose.ui.components.chips.MemberResultChip
import com.ns.theendcompose.ui.components.texts.SectionLabel
import com.ns.theendcompose.ui.theme.spacing

@Composable
fun MemberSection(
    members: List<Member>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    title: String? = null,
    onMemberClick: (Int) -> Unit = {}
) {
    val memberGroups = members.groupBy { member -> member.id }.toList()

    Column(modifier = modifier) {
        title?.let {
            SectionLabel(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium),
                text = title
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            contentPadding = contentPadding
        ){
            items(memberGroups){(id, members)->
                val profilePath = members.firstNotNullOfOrNull { member -> member.profilePath }
                val firstLine = members.firstNotNullOfOrNull { member -> member.firstLine }
                val secondLine = members.mapNotNull { member -> member.secondLine }
                    .joinToString(separator = ", ")

                MemberResultChip(
                    modifier = Modifier.width(72.dp),
                    profilePath = profilePath,
                    firstLine = firstLine,
                    secondLine = secondLine,
                    onClick = { onMemberClick(id) }
                )
            }
        }
    }
}