package com.vilo.muradyan.task.utils

interface PermissionCallback {
    fun onGranted(permission:Array<String>)
    fun onDenied(permission: Array<String>)
    fun onShowRationale(permission: Array<String>)
}