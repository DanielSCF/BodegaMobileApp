package pe.idat.eduale.room.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="CartModel")
data class CartModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val nombre:String,
    val precio:Double,
    val cantidad:Int,
    val imagen:String,
    val subtotal:Double,
    val productId: Int
)