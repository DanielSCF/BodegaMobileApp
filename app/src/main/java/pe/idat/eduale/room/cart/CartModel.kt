package pe.idat.eduale.room.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="CartModel")
data class CartModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val nombre:String,
    val marca: String,
    val precio:Double,
    var cantidad:Int,
    val stock: Int,
    val imagen:String,
    var subtotal:Double,
    val productId: Int
)