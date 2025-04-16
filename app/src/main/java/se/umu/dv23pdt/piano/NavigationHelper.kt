/**
 * Function used for the bottom action/window -bar
 * to not have to reuse code in every activity.
 *
 * @author Pontus Dahlkvist
 * @date 16/04 -25
 */

package se.umu.dv23pdt.piano

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

fun setupBottomNav(navView: BottomNavigationView, currentContext: Context) {
    navView.setOnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_game -> {
                if (currentContext !is MainActivity) {
                    currentContext.startActivity(Intent(currentContext, MainActivity::class.java))
                }
                true
            }
            R.id.nav_stats -> {
                if (currentContext !is StatsActivity) {
                    currentContext.startActivity(Intent(currentContext, StatsActivity::class.java))
                }
                true
            }
            R.id.nav_settings -> {
                if (currentContext !is SettingsActivity) {
                    currentContext.startActivity(Intent(currentContext, SettingsActivity::class.java))
                }
                true
            }
            else -> false
        }
    }
}