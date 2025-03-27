package com.fitness.model.gym

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Session(
    val id: Int,
    val name: String,
    @ServerTimestamp val date: Timestamp? = null,
    val duration: Int,
    val log: List<Exercise>
){
    constructor() : this(0,"", null, 0, emptyList())

}