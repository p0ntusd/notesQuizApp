/**
 * ViewModel to keep the same Controller
 * instance when app is restarted.
 *
 * @author  Pontus Dahlkvist
 * @date    20/02 -25
 */

package com.example.mobou3

import com.example.mobilou3.Controller

/**
 * ViewModel to keep the same Controller
 * instance when app is restarted.
 */
object ControllerSingleton {
    val controller: Controller = Controller()
}
