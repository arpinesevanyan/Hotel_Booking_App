package com.arpinesevanyan.hotelbookingapp.model.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

data class BookingData(
    @SerializedName("arrival_country") val arrival_country: String? = null,
    @SerializedName("departure") val departure: String? = null,
    @SerializedName("fuel_charge") val fuel_charge: Int? = null,
    @SerializedName("horating") val horating: Int? = null,
    @SerializedName("hotel_adress") val hotel_adress: String? = null,
    @SerializedName("hotel_name") val hotel_name: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("number_of_nights") val number_of_nights: Int? = null,
    @SerializedName("nutrition") val nutrition: String? = null,
    @SerializedName("rating_name") val rating_name: String? = null,
    @SerializedName("room") val room: String? = null,
    @SerializedName("service_charge") val service_charge: Int? = null,
    @SerializedName("tour_date_start")
    @JsonAdapter(DateDeserializer::class)
    val tourDateStart: Date,
    @SerializedName("tour_date_stop")
    @JsonAdapter(DateDeserializer::class)
    val tourDateStop: Date,
    @SerializedName("tour_price") val tour_price: Int? = null
) : Serializable

class DateDeserializer : JsonDeserializer<Date> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date {
        val dateString = json?.asString

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        return try {
            dateFormat.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
            Date()
        }
    }
}


