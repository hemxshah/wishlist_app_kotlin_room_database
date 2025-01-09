package com.example.mywishlistapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WishRepository(private val wishDao : WishDao) {

    suspend fun addWish(wish: Wish) {
        wishDao.addAWish(wish)
    }

    suspend fun getWishes(): Flow<List<Wish>> = wishDao.getAllWishes()

    fun getAWishById(id:Long): Flow<Wish> {
        return wishDao.getAWish(id)
    }


    suspend fun updateAWish(wish: Wish){
        wishDao.updateAWish(wish)
    }

    suspend fun deleteAWish(wish: Wish){
        wishDao.deleteAWish(wish)
    }


}