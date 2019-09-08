package com.chintansoni.imagepicker.exception

const val MESSAGE_NO_APPS_FOUND_EXCEPTION = "No apps found that can handle this intent."

object NoAppsFoundException : Exception(MESSAGE_NO_APPS_FOUND_EXCEPTION)