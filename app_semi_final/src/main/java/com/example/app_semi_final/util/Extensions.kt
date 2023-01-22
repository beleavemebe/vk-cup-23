package com.example.app_semi_final.util

fun <E> List<E>.modifiedAt(index: Int, modify: (E) -> E): List<E> = buildList {
    addAll(this@modifiedAt)
    set(index, modify(this[index]))
}

fun <E> MutableList<E>.swap(from: Int, to: Int) {
    add(to, removeAt(from))
}