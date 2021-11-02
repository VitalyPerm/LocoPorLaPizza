package com.vitaly.locoporlapizza.di.modules

import com.vitaly.locoporlapizza.presentation.EndFragment
import com.vitaly.locoporlapizza.presentation.cart.CartFragment
import com.vitaly.locoporlapizza.presentation.details.DetailsDialogFragment
import com.vitaly.locoporlapizza.presentation.main.MainFragment
import com.vitaly.locoporlapizza.presentation.preview.PreviewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun contributeCartFragment(): CartFragment

    @ContributesAndroidInjector
    internal abstract fun contributeDetailsFragment(): DetailsDialogFragment

    @ContributesAndroidInjector
    internal abstract fun contributePreviewFragment(): PreviewFragment

    @ContributesAndroidInjector
    internal abstract fun contributeEndFragment(): EndFragment
}