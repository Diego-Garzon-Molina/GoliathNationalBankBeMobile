package diego.garzon.ui.products_landing

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import diego.garzon.R
import diego.garzon.databinding.ProductItemBinding
import diego.garzon.utils.bankersRounding
import kotlin.reflect.KFunction1

class ProductsAdapter(
    private val productViewModelList: List<ProductTransactionsModel>?,
    private val navigateToProductFragment: KFunction1<ProductTransactionsModel?, Unit>,
    private val context: Context
) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private lateinit var binding: ProductItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productViewModelList?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val model = productViewModelList?.get(position)
        binding.tvProductName.text = model?.sku
        binding.tvProductDate.text = String.format(
            context.resources.getString(R.string.format_2_strings),
            model?.totalAmount?.bankersRounding().toString(),
            model?.transactions?.first()?.currency?.name
        )
        binding.ivShowProduct.setOnClickListener { navigateToProductFragment(model) }

    }

    inner class ProductViewHolder(binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}