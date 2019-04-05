package com.chtan.cartrack.network
import com.google.gson.annotations.SerializedName;
public class RetroDetail {


    val id: Int? = null
    val name: String? = null
    val username: String? = null
    val email: String? = null
    val address: Address? = null
    val phone: String? = null
    val website: String? = null
    val company: Company? = null
    @SerializedName("body")
    val info: String? = null


}

class Address {
    val street: String? = null
    val suite: String? = null
    val city: String? = null
    val zipcode: String? = null
    val geo: Geo? = null

}

class Geo{
    val lat: String? = null
    val lng: String? = null
}

class Company {
    val name: String? = null
    val catchPhrase: String? = null
    val bs: String? = null
}