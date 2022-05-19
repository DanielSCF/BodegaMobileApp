package pe.idat.eduale.room.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="CartModel")
data class CartModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val cantidad:Int?,
    val subtotal:Double?,
    val total:Double?,

    val productId: Int?
)