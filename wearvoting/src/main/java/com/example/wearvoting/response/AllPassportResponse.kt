package com.example.wearvoting.response

import com.example.wearvoting.entity.PassportList

class AllPassportResponse (

    val sucess : Boolean?=true,
    val count: Int? = 0,
    val data : ArrayList<PassportList>? = null
)