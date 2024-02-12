package com.example.tourmanage.common.value

object Config {

    const val MAIN_MENU_KEY = "MENU"
    enum class FRAGMENT_CHANGE_TYPE(val value: String) {
        ADD("ADD"),
        REPLACE("REPLACE"),
        REMOVE("REMOVE")
    }

    enum class CARD_TYPE(value: String) {
        TYPE_A("TYPE_A"),
        TYPE_B("TYPE_B"),
    }
}