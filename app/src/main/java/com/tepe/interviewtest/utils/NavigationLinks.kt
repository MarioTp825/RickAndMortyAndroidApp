package com.tepe.interviewtest.utils


enum class NavLinks(val route: String) {
    Home("home"),
    HomeCharacterList("home/characters"),
    HomeCharacterDetail("home/characters/{id}")
}