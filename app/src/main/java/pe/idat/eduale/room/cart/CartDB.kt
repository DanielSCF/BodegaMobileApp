package pe.idat.eduale.room.cart

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CartModel::class), version = 1)
abstract class CartDB : RoomDatabase(){
    abstract fun cartDao(): CartDao
}