@file:JsNonModule
@file:JsModule("@material/circular-progress")
package mdc.circularProgress

import org.w3c.dom.HTMLElement

external class MDCCircularProgress {

    fun close()

    companion object {

        fun attachTo(root: HTMLElement?): MDCCircularProgress

    }
}