package com.daryse.daryse.util.logging

import timber.log.Timber

/**
 * A Timber Tree for debug builds.
 * Automatically shows a Hyperlink to the calling Class and Linenumber in the Logs.
 * Allows quick lookup of the caller source just by clicking on the Hyperlink in the Log.
 */
class HyperlinkedTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        with(element) {
            return "($fileName:$lineNumber)$methodName()"
        }
    }
}