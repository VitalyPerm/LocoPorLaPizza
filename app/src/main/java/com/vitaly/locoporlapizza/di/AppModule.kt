package com.vitaly.locoporlapizza.di

import android.app.Application
import androidx.room.Room
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.vitaly.data.RepositoryImpl
import com.vitaly.data.db.PizzaDao
import com.vitaly.data.db.PizzaDataBase
import com.vitaly.data.network.PizzaApi
import com.vitaly.domain.interactors.CartInteractor
import com.vitaly.domain.interactors.DetailsAndPreviewInteractor
import com.vitaly.domain.interactors.MainInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val apiClient = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    ).build()

    @Singleton
    @Provides
    fun providePizzaApi(): PizzaApi {
        return Retrofit.Builder()
            .baseUrl("https://springboot-kotlin-demo.herokuapp.com/")
            .client(apiClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PizzaApi::class.java)
    }

    @Singleton
    @Provides
    fun providePizzaDao(app: Application): PizzaDao {
        return Room.databaseBuilder(app, PizzaDataBase::class.java, "db")
            .build().getPizzaDao()
    }

    @Provides
    fun provideProgressBar(app: Application): CircularProgressDrawable {
        return CircularProgressDrawable(app).apply {
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
}