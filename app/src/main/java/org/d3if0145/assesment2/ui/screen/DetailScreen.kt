package org.d3if0145.assesment2.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0145.assesment2.R
import org.d3if0145.assesment2.database.ParfumDb
import org.d3if0145.assesment2.ui.theme.Assesment2Theme
import org.d3if0145.assesment2.util.ViewModelFactory


const val KEY_ID_CATATAN = "idCatatan"


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Assesment2Theme {
        DetailScreen(rememberNavController())
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = ParfumDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var namaParfum by remember { mutableStateOf("") }
    var brandParfum by remember { mutableStateOf("") }
    var genderParfum by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true){
        if (id == null) return@LaunchedEffect
        val data = viewModel.getParfum(id) ?: return@LaunchedEffect
        namaParfum = data.namaParfum
        brandParfum = data.brandParfum
        genderParfum = data.genderParfum
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ){
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if(id == null)
                        Text(text = stringResource(id = R.string.tambah_parfum))
                    else
                        Text(text = stringResource(id = R.string.edit_parfum))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            if (namaParfum == "" || brandParfum == ""){
                                Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                                return@IconButton
                            }

                            if (id == null){
                                viewModel.insert(namaParfum, brandParfum, genderParfum)
                            } else {
                                viewModel.update(id, namaParfum, brandParfum, genderParfum)
                            }
                            navController.popBackStack()
                        }
                    ){
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null){
                        DeleteAction { showDialog = true}
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false },
                            onConfirmation = {
                                showDialog = false
                                viewModel.delete(id)
                                navController.popBackStack()
                            }
                        )
                    }
                }
            )
        }
    ) { padding ->
        FormParfum(
            modifier = Modifier.padding(padding),
            title = namaParfum,
            onTitleChange = {
                namaParfum = it
            },
            nim = brandParfum,
            onNimChange = {
                brandParfum = it
            },

            desc = genderParfum,
            onDescChange = {
                genderParfum = it
            }
        )
    }
}

@Composable
fun FormParfum(
    modifier: Modifier,
    title: String,
    onTitleChange: (String) -> Unit,

    nim: String,
    onNimChange: (String) -> Unit,

    desc: String,
    onDescChange: (String) -> Unit
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        OutlinedTextField(
            value = title,
            onValueChange = {
                onTitleChange(it)
            },
            label = {
                Text(text = stringResource(id = R.string.nama_parfum))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ) ,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nim,
            onValueChange = {
                onNimChange(it)
            },
            label = {
                Text(text = stringResource(id = R.string.brand_parfum))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ) ,
            modifier = Modifier.fillMaxWidth()
        )

        var selectedOption by remember { mutableStateOf(0) }
        if (desc.equals("Male")){
            selectedOption = 0
        } else if (desc.equals("Female")){
            selectedOption = 1
        }  else if (desc.equals("Unisex")){
            selectedOption = 2
        }

        val options = listOf("Male", "Female", "Unisex")

        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        )
        {
            options.forEachIndexed { index, text ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                )
                {
                    RadioButton(
                        selected = index == selectedOption,
                        onClick = { selectedOption = index },
                        colors = RadioButtonDefaults.colors(selectedColor = androidx.compose.ui.graphics.Color.Blue)
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            if (selectedOption == 0){
                onDescChange("Male")
            } else if (selectedOption == 1){
                onDescChange("Female")
            } else if (selectedOption == 2){
                onDescChange("Unisex")
            }

        }

    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.opsi_lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}