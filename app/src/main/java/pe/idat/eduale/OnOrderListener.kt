package pe.idat.eduale

interface OnOrderListener {
    fun onOrderClick(position: Int)
    fun onCancelOrderClick(position: Int)
}