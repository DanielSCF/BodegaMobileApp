package pe.idat.eduale.room.cart

import android.app.Application
import androidx.room.Room

class CartApp : Application(){

    companion object{
        lateinit var database: CartDB
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,CartDB::class.java,"CartDB").build()
    }
}