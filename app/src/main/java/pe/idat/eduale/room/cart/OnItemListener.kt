package pe.idat.eduale.room.cart

interface OnItemListener {
    fun onDeleteClick(position:Int)
    fun onAddClick(position: Int)
    fun onSubtractClick(position: Int)
}