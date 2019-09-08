package com.chintansoni.imagepicker.exception

const val MESSAGE_PERMISSION_PERMANENTLY_DENIED_EXCEPTION = "Permission permanently denied"

class PermissionPermanentlyDeniedException :
    Exception(MESSAGE_PERMISSION_PERMANENTLY_DENIED_EXCEPTION)