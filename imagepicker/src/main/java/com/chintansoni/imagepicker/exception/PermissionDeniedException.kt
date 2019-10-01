package com.chintansoni.imagepicker.exception

const val MESSAGE_PERMISSION_DENIED = "Permission Denied"

object PermissionDeniedException : Exception(MESSAGE_PERMISSION_DENIED)