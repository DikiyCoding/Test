package com.example.test.utils

import com.example.test.R

object Constants {

    /**
     * Parsing
     */
    const val PARSE_BASE_URL = "https://tproger.ru/tag/"
    const val PARSE_ARTICLES = "article[id^=post-]"
    const val PARSE_IMAGE = "img"
    const val PARSE_NO_IMAGE = "noimg"
    const val PARSE_IMAGE_ATTR = "data-src"
    const val PARSE_TITLES = "entry-title"
    const val PARSE_REFERENCE = "a"
    const val PARSE_REFERENCE_ATTR = "href"
    const val PARSE_DESCRIPTION = ".entry-content p"

    /**
     * Picasso (Empty image URLs)
     */
    const val IMAGE_STATUS_NONE = ""
    const val NO_IMAGE_PLACEHOLDER = R.drawable.logo_tproger_placeholder

    /**
     * Database
     */
    const val DB_NAME = "db_tproger"
    const val DB_TABLE_ID_DEFAULT = 0L

    /**
     * APIs
     * 1) Google Search API
     */
    const val GOOGLE_BASE_URL = "https://www.googleapis.com/"
    const val GOOGLE_SEARCH = "customsearch/v1?"
    const val ENGINE_ID = "002236351975587597562:fj4yhqmerk8"
    const val GOOGLE_API_KEY = "AIzaSyDC_pvdcEEHgStu46L6FbU3ao57zVAlD6U"
    const val SEARCH_TYPE = "image"
    const val RESPONSE_TYPE = "json"
    const val IMAGE_SIZE_DEFAULT = "xlarge"
    const val START_POINT_DEFAULT = "1"
    const val IMAGE_NUMBER_DEFAULT = 7
    const val IMAGE_LIST_POSITION_DEFAULT = 0

    /**
     * Toasts
     */
    const val SEARCH_ERROR_RESULT = "Nothing found for this request"

    /**
     * Request Codes
     */
    const val HISTORY_LIST_CHANGED = 1
    const val HISTORY_LIST_NOT_CHANGED = 2

    /**
     * Negative Values
     */
    const val ID_NOT_FOUND_VALUE = -1L

    /**
     * Layout params
     */
    const val TAG_BOTTOM_VIEW_DEFAULT = "BottomView"

    /**
     * Logs
     */
    enum class LOGS(val tag: String) {
        PARSING("AppParsing"),
        DATABASE("AppDatabase"),
        EVENTS("AppEvent"),
        DATA("AppData")
    }

    /**
     * Margins
     */
    enum class MARGINS(val value: Int) {
        MARGIN5(5),
        MARGIN15(15)
    }
}
