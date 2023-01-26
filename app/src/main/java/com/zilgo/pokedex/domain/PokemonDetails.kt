//package com.zilgo.pokedex.domain
//
//import android.os.Parcel
//import android.os.Parcelable
//import kotlinx.android.parcel.Parcelize
//import kotlinx.android.parcel.RawValue
//
//@Parcelize
//class PokemonDetails(
//    val number: Int?,
//    val name: String?,
////    val types: @RawValue List<String>,
////    val ability: @RawValue List<Unit>,
//    val weight: Int,
////    val moves: @RawValue List<Unit>,
////    val stats: @RawValue List<Unit>
//): Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readValue(Int::class.java.classLoader) as? Int,
//        parcel.readString(),
////        listOf(parcel.readList(List::class.java.classLoader as List<Unit>)),
////        parcel.readList(),
//        parcel.readInt(),
////        parcel.readList(),
////        parcel.readList()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeValue(number)
//        parcel.writeString(name)
//        parcel.writeInt(weight)
////        parcel.writeList(types)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<PokemonDetails> {
//        override fun createFromParcel(parcel: Parcel): PokemonDetails {
//            return PokemonDetails(parcel)
//        }
//
//        override fun newArray(size: Int): Array<PokemonDetails?> {
//            return arrayOfNulls(size)
//        }
//    }
//}