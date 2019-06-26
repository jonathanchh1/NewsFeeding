package com.emi.newsfeeding

import androidx.databinding.ObservableInt
import androidx.room.TypeConverter


class Converter{

    @TypeConverter
    fun fromObservableInt(observableInt: ObservableInt): Int {
        return observableInt.get()
    }

    @TypeConverter
    fun toObservableInt(data: Int): ObservableInt {
        val observableInt = ObservableInt()
        observableInt.set(data)
        return observableInt
    }
}