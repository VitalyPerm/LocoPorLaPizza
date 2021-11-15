package com.vitaly.locoporlapizza.di.modules

import android.content.Context
import androidx.room.Room
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.data.db.PizzaDao
import com.vitaly.data.db.PizzaDataBase
import com.vitaly.data.RepositoryImpl
import com.vitaly.data.network.PizzaApi
import com.vitaly.locoporlapizza.di.App
import com.vitaly.domain.interactors.CartInteractor
import com.vitaly.domain.interactors.DetailsAndPreviewInteractor
import com.vitaly.domain.interactors.MainInteractor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [FragmentModule::class, ViewModelModule::class])
class AppModule {
    private val apiClient = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    ).build()

    @Singleton
    @Provides
    fun providePizzaApi(): PizzaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(apiClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PizzaApi::class.java)
    }

    @Singleton
    @Provides
    fun provideContext(application: App): Context = application.applicationContext

    @Singleton
    @Provides
    fun providePizzaDao(context: Context): PizzaDao {
        return Room.databaseBuilder(context, PizzaDataBase::class.java, "db")
            .build().getPizzaDao()
    }

    @Singleton
    @Provides
    fun provideProgressBar(context: Context): CircularProgressDrawable {
        return CircularProgressDrawable(context).apply {
            strokeWidth = 10f
            centerRadius = 50f
            start()
        }
    }

    @Singleton
    @Provides
    fun provideRepository(pizzaApi: PizzaApi, pizzaDao: PizzaDao): RepositoryImpl {
        return RepositoryImpl(pizzaApi, pizzaDao)
    }


    @Singleton
    @Provides
    fun provideDetailsAndPreviewInteractor(repositoryImpl: RepositoryImpl): DetailsAndPreviewInteractor {
        return DetailsAndPreviewInteractor(repositoryImpl)
    }

    @Singleton
    @Provides
    fun provideMainInteractor(repositoryImpl: RepositoryImpl): MainInteractor {
        return MainInteractor(repositoryImpl)
    }

    @Singleton
    @Provides
    fun provideCartInteractor(repositoryImpl: RepositoryImpl): CartInteractor {
        return CartInteractor(repositoryImpl)
    }


    companion object {
        const val BASE_URL = "https://springboot-kotlin-demo.herokuapp.com/"
    }
}