package diego.garzon.ui.product

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import diego.garzon.R
import diego.garzon.databinding.TransactionItemBinding
import diego.garzon.ui.products_landing.TransactionModel
import diego.garzon.utils.bankersRounding

class TransactionsAdapter(
    private val transactionList: List<TransactionModel>?,
    private val context: Context
) :
    RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    private lateinit var binding: TransactionItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        binding = TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return transactionList?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val model = transactionList?.get(position)
        binding.tvTransactionAmount.text =
            String.format(context.resources.getString(R.string.amount), model?.amount?.bankersRounding().toString())
        binding.tvTransactionCurrency.text = model?.currency?.name
    }

    inner class TransactionViewHolder(binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}