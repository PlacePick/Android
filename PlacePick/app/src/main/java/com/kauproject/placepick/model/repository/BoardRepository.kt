package com.kauproject.placepick.model.repository
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kauproject.placepick.model.FireBaseInstance
import com.kauproject.placepick.model.data.Post
import com.kauproject.placepick.util.HotPlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class BoardRepository {
    private val database = FireBaseInstance.database
    private val boardRef = database.getReference("boards")

    // 게시글 읽어오기 콜백함수
    fun getBoardDetail(board: String, detail: (List<Post>) -> Unit){
        val boardDetailRef = boardRef.child(board)
        val detailList = mutableListOf<Post>()

        boardDetailRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    item.getValue(Post::class.java)?.let { post ->
                        detailList.add(post)
                    }
                }
                detail(detailList)
            }

            override fun onCancelled(error: DatabaseError) {
                detail(emptyList())
            }
        })
    }

    // 게시글 작성
    fun postWrite(post: Post, board: String){
        boardRef.child(board).push().setValue(post)
    }


}