package com.visa.timesreader.utils

sealed class Category(var title: String, var route: String) {
    object Home: Category("Home", "home")
    object Arts: Category("Arts", "arts")
    object Business: Category("Business", "business")
    object Fashion: Category("Fashion", "fashion")
    object Health: Category("Health", "health")
    object World: Category("World", "world")
}