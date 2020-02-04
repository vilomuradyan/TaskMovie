package com.vilo.muradyan.task.utils

class Constants {
    companion object {

        const val BASE_URL =  "https://api.themoviedb.org/3/"
        const val ENG_LANG = "en"
        const val OFFSET = 20
        const val LIMIT = 20
        const val DATABASE_NAME = "movie_database.db"
        const val LIST_SCROLLING = 20
        @JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
            "Verbose WorkManager Notifications"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1

    }

    enum class LoggedInMode constructor(val type : Int){
        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_SERVER(1)
    }
}