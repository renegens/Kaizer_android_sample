package eu.giant.kaizen.util

import okio.IOException

class NoConnectionException(cause: IOException) : IOException("No connection exception", cause)
