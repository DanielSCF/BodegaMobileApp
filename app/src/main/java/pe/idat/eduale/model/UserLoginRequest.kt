package pe.idat.eduale.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserLoginRequest {

    @SerializedName("nickname")
    @Expose
    var nickname: String?=null

    @SerializedName("clave")
    @Expose
    var clave: String?=null

}