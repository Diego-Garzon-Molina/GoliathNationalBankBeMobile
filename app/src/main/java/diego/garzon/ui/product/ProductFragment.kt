package diego.garzon.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import diego.garzon.R
import diego.garzon.databinding.ProductFragmentBinding
import diego.garzon.platform.Status
import diego.garzon.ui.products_landing.ProductTransactionsModel
import diego.garzon.ui.products_landing.RateDataModel
import diego.garzon.ui.products_landing.TransactionModel
import diego.garzon.utils.bankersRounding
import org.koin.android.viewmodel.ext.android.getViewModel

class ProductFragment : Fragment() {
    private var _binding: ProductFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: ProductFragmentArgs by navArgs()
    private lateinit var product: ProductTransactionsModel
    private lateinit var viewModel: ProductViewModel
    private val currencyList = RateDataModel.Currency.values()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel(ProductViewModel::class)
        product = args.product
        setProduct(product)
        setTransactionsAdapter(product.transactions)
        viewModel.rates = ArrayList(args.rates.asList())
        setCurrencyGroup()
        setObservers()
        viewModel.convertProducts(product.currentCurrency, product)
    }

    private fun setTransactionsAdapter(transactions: ArrayList<TransactionModel>?) {
        binding.rvTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTransactions.adapter =
            TransactionsAdapter(transactions, requireContext())
        binding.rvTransactions.adapter?.notifyDataSetChanged()
        binding.rvTransactions.isNestedScrollingEnabled = false

    }

    private fun showErrorMessage(message: String) {
        val builder = AlertDialog.Builder(
            requireContext(),
            R.style.Theme_AppCompat_Light_Dialog_Alert
        )
        builder.setMessage(message)
        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun setObservers() {
        viewModel.product.observe(requireActivity(), { productsData ->
            when (productsData.status) {
                Status.LOADING -> {
                    binding.cvRecyclerView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    hideLoading()
                    showErrorMessage(productsData.exception?.message!!)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    productsData.data?.let { setProduct(it) }
                    setTransactionsAdapter(productsData.data?.transactions)
                }
            }
        })
    }

    private fun setProduct(product: ProductTransactionsModel) {
        binding.tvProductName.text = product.sku
        binding.tvProductAmount.text = String.format(
            resources.getString(R.string.total_amount),
            product.totalAmount.bankersRounding().toString(),
            product.transactions?.first()?.currency?.name
        )
    }

    private fun setCurrencyGroup() {
        currencyList.forEachIndexed { index, currency ->
            val radioButton = RadioButton(this.requireContext())
            radioButton.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            radioButton.text = currency.name
            radioButton.id = index
            if (currency == product.transactions?.first()?.currency) {
                radioButton.isChecked = true
            }
            binding.rdGroup.addView(radioButton)
        }
        binding.rdGroup.setOnCheckedChangeListener { _, i ->
            viewModel.convertProducts(currencyList[i], product)
        }
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.cvRecyclerView.visibility = View.VISIBLE

    }
}