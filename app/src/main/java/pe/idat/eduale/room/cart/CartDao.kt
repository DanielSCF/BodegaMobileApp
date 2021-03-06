package pe.idat.eduale.room.cart

import androidx.room.*

@Dao
interface CartDao {
    @Query("SELECT * FROM CartModel")
    fun cartList(): MutableList<CartModel>

    @Query("SELECT * FROM CartModel  WHERE id = :id")
    fun getById(id: Int): CartModel

    @Insert
    fun addItem(cartModel: CartModel)

    @Delete
    fun deleteItem(cartModel: CartModel)

    @Update
    fun editItem(cartModel: CartModel)

    @Query("DELETE FROM CartModel")
    fun clearCart()

}