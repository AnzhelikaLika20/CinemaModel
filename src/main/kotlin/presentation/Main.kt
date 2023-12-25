package org.example.Presentation

import data.*
import di.DI
import di.DI.inputManager
import presentation.InputManager

fun main() {
    val inputManager = DI.inputManager
    inputManager.processUserOuterInput()
}