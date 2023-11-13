package com.kauproject.placepick.model.repository
import com.kauproject.placepick.model.FireBaseInstance

class BoardRepository {
    private val database = FireBaseInstance.database
    private val boardRef = database.getReference("boards")

    /*fun getBoardDetail(board: String): List<Post>{
        val boardDetailRef = boardRef.child(board)
        val detailList = mutableListOf<Post>()

        boardDetailRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(item in snapshot.children){
                    item.getValue(Post::class.java)?.let { post->
                        detailList.add(post)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }*/
}