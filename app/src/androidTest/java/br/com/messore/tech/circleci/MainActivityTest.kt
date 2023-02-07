package br.com.messore.tech.circleci

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.messore.tech.circleci.ui.theme.CIrcleCITestingTheme
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        composeTestRule.setContent {
            CIrcleCITestingTheme {
                MainScreen()
            }
        }

        composeTestRule.onNodeWithContentDescription("Button Add").performClick()
        composeTestRule.onNodeWithContentDescription("Button Add").performClick()

        composeTestRule.onNodeWithText("Clicou 2 vezes").assertIsDisplayed()
    }
}
