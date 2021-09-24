package com.arthurgonzaga.wikistarwars.ui.fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import com.arthurgonzaga.wikistarwars.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteListFragmentTest {

    lateinit var navController: NavController
    lateinit var scenario: FragmentScenario<FavoriteListFragment>

    @Before
    fun setup() {
        navController = mock(NavController::class.java)
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_WikiStarWars_Yellow
        )

        scenario.onFragment{
            Navigation.setViewNavController(it.requireView(), navController)
        }
    }

    @Test
    fun testPopupBackStack() {

        pressBack()

        verify(navController).popBackStack()
    }
}