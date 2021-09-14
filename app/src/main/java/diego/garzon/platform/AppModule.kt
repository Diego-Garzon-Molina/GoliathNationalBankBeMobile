package diego.garzon.platform

import diego.garzon.data.repositories.GNBRepository
import diego.garzon.domain.GNBIRepository
import diego.garzon.domain.use_cases.GetConversionUseCase
import diego.garzon.domain.use_cases.GetProductsListUseCase
import diego.garzon.domain.use_cases.GetRateListUseCase
import diego.garzon.ui.product.ProductViewModel
import diego.garzon.ui.products_landing.ProductsLandingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<GNBIRepository> {
        GNBRepository()
    }
    single<InteractorExecutor> {
        AsyncInteractorExecutor(
            runOnBgThread = BackgroundRunner(),
            runOnMainThread = MainRunner()
        )
    }
    factory { GetProductsListUseCase(repository = get()) }
    factory { GetRateListUseCase(repository = get()) }
    factory { GetConversionUseCase() }

    viewModel {
        ProductsLandingViewModel(
            executor = get(),
            useCaseProducts = get(),
            useCaseRate = get(),
            useCaseConversion = get()
        )
    }

    viewModel {
        ProductViewModel(
            executor = get(),
            useCaseConversion = get()
        )
    }
}
