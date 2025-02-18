package com.fitness.model.macros

import com.google.firebase.Timestamp

data class DailyMacroRecord(
    val id: Int,
    val date: Timestamp,
    var targetMacros: TargetMacros,
    val currentMacros: CurrentMacros,
    var foodList: List<Food> = emptyList()
){
    constructor() : this(0,Timestamp.now(), TargetMacros(), CurrentMacros(), emptyList())
}