package com.example.mywishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mywishlistapp.data.Wish
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text


@Composable
fun AddEditDetailView(
    id : Long,
    viewModel: WishViewModel,
    navController: NavController
) {
    val snackMessage = remember{
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    if(id != 0L){
        val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    }else{
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
        AppBarView(title =
        if(id != 0L) stringResource(id = R.string.update_wish)
        else stringResource(id = R.string.add_wish))

        {
            navController.navigateUp()
        }
    }
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(
                label = "Title",
                value = viewModel.wishTitleState,
                onValueChange = {
                    viewModel.onWishTitleChanged(it)
                })

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(
                label = "Description",
                value = viewModel.wishDescriptionState,
                onValueChange = {
                    viewModel.onWishDescriptionChanged(it)
                })

            Spacer(modifier = Modifier.height(10.dp))
            
            Button(onClick = {
                if(viewModel.wishTitleState.isNotEmpty()
                    && viewModel.wishDescriptionState.isNotEmpty()){

                    if(id!=0L){
                        //TODO Update wish
                        viewModel.updateWish(
                            Wish(
                                id = id,
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim()
                            )
                        )

                        snackMessage.value = "Wish has been updated"

                    } else{
                        //Add wish
                        viewModel.addWish(
                            Wish(
                                title =  viewModel.wishTitleState.trim(),
                                 description = viewModel.wishDescriptionState.trim()
                            )
                        )

                        snackMessage.value = "Wish has been created"
                    }
                }else
                {
                    snackMessage.value = "Enter fields to create a wish"
                }

                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }
            }) {
                
                Text(
                    text = if(id != 0L) stringResource(id = R.string.update_wish)
                else stringResource(id = R.string.add_wish),

                    style = TextStyle(fontSize = 18.sp)
                )
                
            }
        }
    }
    }

@Composable
fun WishTextField(
    label : String,
    value : String,
    onValueChange : (String)->Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = Color.Black)},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            focusedIndicatorColor = Color.Blue,
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
            cursorColor = Color.Blue

        )    )

}

@Preview
@Composable

fun WishTestFieldPrev(){
    WishTextField(
        label = "Text",
        value = "Text",
        onValueChange = {})
}

