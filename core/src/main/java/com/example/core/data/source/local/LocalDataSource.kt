package com.example.core.data.source.local

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.MovieSearchEntity
import com.example.core.data.source.local.entity.TvShowSearchEntity
import com.example.core.data.source.local.entity.TvShowEntity
import com.example.core.data.source.local.room.WestflixDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val westflixDao: WestflixDao) {

    // Tv Show

    fun getAllPopularTvShow(): Flow<List<TvShowEntity>> = westflixDao.getAllPopularTvShow()

    suspend fun insertTvShow(listTvShow: List<TvShowEntity>) = westflixDao.insertTvShow(listTvShow)

    fun searchTvShow(query: String): Flow<List<TvShowSearchEntity>> = westflixDao.searchTvShow(query)

    fun getFavoritePopularTvShow(): Flow<List<TvShowEntity>> = westflixDao.getFavoritePopularTvShow()

    fun setFavoritePopularTvShow(tvShow: TvShowEntity, newState: Boolean){
        tvShow.isFavorite = newState
        westflixDao.updateFavoritePopularTvShow(tvShow)
    }

    suspend fun  insertSearchTvShow(listSearchTvShow: List<TvShowSearchEntity>) = westflixDao.insertSearchTvShow(listSearchTvShow)


    // Movie

    fun getAllPopularMovie(): Flow<List<MovieEntity>> = westflixDao.getAllPopularMovie()

    fun getFavoritePopularMovie(): Flow<List<MovieEntity>> = westflixDao.getFavoritePopularMovie()

    fun searchMovie(query: String): Flow<List<MovieSearchEntity>> = westflixDao.searchMovie(query)

    suspend fun insertMovie(listMovie: List<MovieEntity>) = westflixDao.insertMovie(listMovie)

    suspend fun insertSearchMovie(listSearchMovie: List<MovieSearchEntity>) = westflixDao.insertSearchMovie(listSearchMovie)

    fun setFavoritePopularMovie(movie: MovieEntity, newState: Boolean){
        movie.isFavorite = newState
        westflixDao.updateFavoritePopularMovie(movie)
    }


}