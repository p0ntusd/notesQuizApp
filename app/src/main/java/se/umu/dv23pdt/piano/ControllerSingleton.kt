/**
 * ViewModel to keep the same Controller
 * instance when app is restarted.
 *
 * @author  Pontus Dahlkvist
 * @date    20/02 -25
 */

package se.umu.dv23pdt.piano

/**
 * ViewModel to keep the same Controller
 * instance when app is restarted.
 */
object ControllerSingleton {
    val controller: Controller = Controller()
}
