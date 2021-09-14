package diego.garzon.ui.products_landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import diego.garzon.R
import diego.garzon.databinding.ProductsLandingFragmentBinding
import diego.garzon.platform.Status
import org.koin.android.viewmodel.ext.android.getViewModel

class ProductsLandingFragment : Fragment() {
    private var _binding: ProductsLandingFragmentBinding? = null
    private val binding get() = _binding!!
    private val currencyList = RateDataModel.Currency.values()
    private lateinit var viewModel: ProductsLandingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductsLandingFragmentBinding.inflate(inflater, container, false)
        binding.refreshLayout.setOnRefreshListener { setObservers() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel(ProductsLandingViewModel::class)
        setCurrencyGroup()
        setObservers()
        viewModel.load()
    }

    private fun setObservers() {
        viewModel.productsLanding.observe(requireActivity(), { productsData ->
            when (productsData.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    hideLoading()
                    showErrorMessage(productsData.exception?.message!!)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    setProductsRecycler(productsData.data?.listProductsConverted)
                }
            }
        })
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
            if (currency == RateDataModel.Currency.EUR) {
                radioButton.isChecked = true
            }
            binding.rdGroup.addView(radioButton)
        }
        binding.rdGroup.setOnCheckedChangeListener { _, i ->
            viewModel.convertProducts(currencyList[i])
        }
    }

    private fun hideLoading() {
        binding.refreshLayout.isRefreshing = false
        binding.progressBar.visibility = View.GONE
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

    private fun setProductsRecycler(productList: ArrayList<ProductTransactionsModel>?) {
        binding.message.text =
            String.format(resources.getString(R.string.total_products), productList?.size)
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.adapter =
            ProductsAdapter(productList, ::navigateToProduct, requireContext())
        binding.rvProducts.adapter?.notifyDataSetChanged()
    }

    private fun navigateToProduct(product: ProductTransactionsModel?) {
        product?.let {
            val action =
                ProductsLandingFragmentDirections.actionProductsLandingFragmentToProductFragment(
                    product,
                    viewModel.productsLanding.value?.data?.listRates?.toTypedArray() as Array<out RateDataModel>
                )
            this.findNavController().navigate(action)
        }
    }
}