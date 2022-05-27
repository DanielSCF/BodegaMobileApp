package pe.idat.eduale.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.idat.eduale.R
import pe.idat.eduale.model.ProductModel
import pe.idat.eduale.OnProductListener

class ProductAdapter(
    val productList: List<ProductModel>,
    private val OnProductListener: OnProductListener
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = productList[position]
        holder.render(item)

        holder.addButton.setOnClickListener {
            OnProductListener.onProductButtonClick(position)
        }
        holder.productCard.setOnClickListener {
            OnProductListener.onProductClick(position)
        }

    }

    override fun getItemCount(): Int = productList.size

}
