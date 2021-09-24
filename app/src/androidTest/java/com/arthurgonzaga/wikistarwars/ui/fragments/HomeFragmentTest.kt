package com.arthurgonzaga.wikistarwars.ui.fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.arthurgonzaga.wikistarwars.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeFragmentTest {

    lateinit var navController: NavController
    lateinit var scenario: FragmentScenario<HomeFragment>

    @Before
    fun setup(){
        // Create a TestNavHostController
        navController = mock(NavController::class.java)

        // Create a graphical FragmentScenario
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_WikiStarWars_Yellow
        )

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }


    @Test
    fun isFavoriteButtonDisplayed() {
        onView(withId(R.id.favorite_list_button)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavigateToFavoriteListFragment() {
        onView(withId(R.id.favorite_list_button)).perform(click())
        verify(navController).navigate(R.id.goToFavoritesListFragment)
    }

    @Test
    fun testNavigateToDetailFragment() {
        TODO("Not yet implemented")
    }
}