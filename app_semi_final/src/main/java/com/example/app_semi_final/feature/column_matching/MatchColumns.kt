package com.example.app_semi_final.feature.column_matching

data class MatchColumns(
    val question: String,
    val columnsSize: Int,
    val keys: List<String>,
    val values: List<String>,
    val valuesInCorrectOrder: List<String>,
    val isSendButtonActive: Boolean = true,
    val isCompleted: Boolean = false,
) {
    fun isMatchedCorrectly(): Boolean = values == valuesInCorrectOrder
}

val SampleColumnMatching = MatchColumns(
    question = "Как считать данные?",
    columnsSize = 4,
    keys = listOf("Boolean", "Int", "Long", "String"),
    values = listOf(
        "readln()",
        "readln().toInt()",
        "readln().toLong()",
        "readln().toBoolean()"
    ),
    valuesInCorrectOrder = listOf(
        "readln().toBoolean()",
        "readln().toInt()",
        "readln().toLong()",
        "readln()"
    )
)