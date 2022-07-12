package com.example.core.di

import androidx.room.Room
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.room.WestflixDatabase
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.repository.RepositoryMovie
import com.example.core.data.source.repository.RepositoryTvShow
import com.example.core.domain.repository.IMovieRepository
import com.example.core.domain.repository.ITvShowRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> {
        RepositoryMovie(
            get(),
            get(),
            get()
        )
    }
    single<ITvShowRepository> {
        RepositoryTvShow(
            get(),
            get(),
            get()
        )
    }

}

val databaseModule = module {
    factory { get<WestflixDatabase>().westflixDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            WestflixDatabase::class.java,
            "Westflix.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

