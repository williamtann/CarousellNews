package com.example.feature.component.sort

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.core.util.crop
import com.example.core.util.rippleClickable
import com.example.feature.R

@Composable
fun SortButton(
    selectedSort: SortType,
    onSortSelected: (SortType) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    Box {
        IconButton(
            modifier = Modifier.size(32.dp),
            onClick = {
                menuExpanded = true
            }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                tint = Color.White,
                contentDescription = null
            )
        }
        DropdownMenu(
            modifier = Modifier
                .crop(vertical = 8.dp)
                .widthIn(min = 160.dp)
                .background(Color.White),
            expanded = menuExpanded,
            offset = DpOffset(0.dp, 8.dp),
            onDismissRequest = {
                menuExpanded = false
            }
        ) {
            SortItem(
                label = stringResource(R.string.sort_recent),
                isSelected = selectedSort == SortType.Recent,
                onClick = {
                    onSortSelected(SortType.Recent)
                    menuExpanded = false
                }
            )
            HorizontalDivider()
            SortItem(
                label = stringResource(R.string.sort_popular),
                isSelected = selectedSort == SortType.Popular,
                onClick = {
                    onSortSelected(SortType.Popular)
                    menuExpanded = false
                }
            )
        }
    }
}

@Composable
fun SortItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .rippleClickable {
                onClick()
            }
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = label
        )
        if (isSelected) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun SortButtonPreview() {
    SortButton(
        selectedSort = SortType.Recent,
        onSortSelected = {}
    )
}