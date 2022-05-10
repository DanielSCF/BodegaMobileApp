package pe.idat.eduale.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserLoginResponse {
    @SerializedName("status")
    @Expose
    var status: Int?=null

    @SerializedName("data")
    @Expose
    var data:  String?=null

    @SerializedName("error")
    @Expose
    var error:  String?=null

}