package com.example.mywishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(

    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title : String = "",
    @ColumnInfo(name = "wish-desc")
    val description : String = ""
)

object DummyWish{

    val wishlist = listOf(
        Wish(title = "Samsung Galaxy Watch Ultra", description = "An android wathc"),
        Wish(title = "Oculus Quest 2", description = "A VR Headset for playing games"),
        Wish(title = "A Scifi Book", description = "A science fiction book from any bestseller")
    )
}
