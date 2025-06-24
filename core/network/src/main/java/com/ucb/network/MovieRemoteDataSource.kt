package com.ucb.network

class MovieRemoteDataSource (
    val retrofitService: RetrofitBuilder
)
{
    suspend fun getListResponse(): MoviesResponseDto {
        return retrofitService.apiService.getListMovies()
    }

    suspend fun getListResponsePartTwo(): MoviesResponseDto {
        return retrofitService.apiService.getListMoviesPartTwo()
    }

    suspend fun getListResponsePartThree(): MoviesResponseDto {
        return retrofitService.apiService.getListMoviesPartThree()
    }
}