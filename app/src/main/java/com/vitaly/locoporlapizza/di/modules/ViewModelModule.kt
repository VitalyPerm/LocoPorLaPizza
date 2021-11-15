package com.vitaly.locoporlapizza.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vitaly.locoporlapizza.di.ViewModelFactory
import com.vitaly.presentation.cart.CartFragmentViewModel
import com.vitaly.presentation.details.DetailsFragmentViewModel
import com.vitaly.presentation.main.MainFragmentViewModel
import com.vitaly.presentation.preview.PreviewFragmentViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    abstract fun bindMainFragmentViewModel(ViewModel: MainFragmentViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CartFragmentViewModel::class)
    abstract fun bindCartFragmentViewModel(cartViewModel: CartFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsFragmentViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PreviewFragmentViewModel::class)
    abstract fun bindPreviewViewModel(previewViewModel: PreviewFragmentViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)