package io.github.untungs.weatherapp.domain.commands

interface Command<out T> {
    fun execute(): T
}