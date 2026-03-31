package com.imasha.hydrateme.ui.init

sealed  class InitState {
    object Intro : InitState()
    object Login : InitState()
    object Main : InitState()
}