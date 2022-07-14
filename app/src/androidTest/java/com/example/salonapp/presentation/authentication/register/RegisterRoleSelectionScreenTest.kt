package com.example.salonapp.presentation.authentication.register

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.SalonAppTheme
import com.example.salonapp.common.Constants
import com.example.salonapp.dependency_injection.AppModule
import com.example.salonapp.presentation.MainActivity
import com.example.salonapp.presentation.navigation.AuthScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.salonapp.R

@HiltAndroidTest
@UninstallModules(AppModule::class)
class RegisterRoleSelectionScreenTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            SalonAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = AuthScreen.RegisterDetails.route,
                ){
                    composable(AuthScreen.RegisterDetails.route){
                        RegisterDetailsScreen(role = Constants.ROLE_OWNER, onRegisterSuccess = {})
                    }

                }
            }
        }
    }


    @Test
    fun clickRegister_validationMessagesIsVisible(){
        composeRule.onNodeWithTag(Constants.TEST_TAG_REGISTER_BUTTON).performClick()
        composeRule.onNodeWithTag(Constants.TEST_TAG_EMAIL_ERROR_TEXT).assertIsDisplayed()
    }

}