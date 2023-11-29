package com.kauproject.placepick.model.repository
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kauproject.placepick.model.FireBaseInstance
import com.kauproject.placepick.model.RetrofitInstance
import com.kauproject.placepick.model.State
import com.kauproject.placepick.model.data.Comment
import com.kauproject.placepick.model.data.Post
import com.kauproject.placepick.model.service.GetHotPlaceInfoService
import com.kauproject.placepick.util.HotPlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class BoardRepository {
    private val database = FireBaseInstance.database
    private val boardRef = database.getReference("boards")
    private val userRef = database.getReference("users")

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

    // 게시글 댓글 읽어오는 콜백함수
    fun getPostComment(board: String, postId: String, comment: (List<Comment>) -> Unit){
        val commentRef = boardRef.child(board).child(postId).child("comment")
        val commentList = mutableListOf<Comment>()

        commentRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(item in snapshot.children){
                    item.getValue(Comment::class.java)?.let { comment ->
                        commentList.add(comment)
                    }
                }
                comment(commentList)
            }

            override fun onCancelled(error: DatabaseError) {
                comment(emptyList())
            }

        })
    }

    // 게시글 작성
    fun postWrite(post: Post, board: String, userNum: String){
        val postId = boardRef.child(board).push().key
        postId?.let { id->
            boardRef.child(board).child(id).setValue(post.copy(postId = id))
            userRef.child(userNum).child("post").child(id).setValue(post)
        }
    }

    fun commentWrite(comment: Comment, board: String, postId: String){
        val commentId = boardRef.child(board).child(postId).child("comment").push().key
        commentId?.let { id->
            boardRef.child(board).child(postId).child("comment").child(id).setValue(comment.copy(commentId = id))
        }
    }

    // 혼잡도 상태
    fun getPlaceStatus(): Flow<State<List<String?>>> = flow{
        val resultList = mutableListOf<String?>()
        val hotPlaceService = RetrofitInstance.retrofit.create(GetHotPlaceInfoService::class.java)
        emit(State.Loading)
        HotPlace.hotPlace.forEach { place->
            val placeResponse = hotPlaceService.getHotPlaceInfo(mapperPlace(place))
            val statusCode = placeResponse.code()

            if(statusCode == 200){
                resultList.add(placeResponse.body()?.seoulRtdCitydataPpltn?.firstOrNull()?.areaLevel)
            }else{
                emit(State.ServerError(statusCode))
            }
        }
        emit(State.Success(resultList))
    }.catch { e->
        emit(State.Error(e))
    }

    // 요약된 지역 명세서에 맞게 매핑
    private fun mapperPlace(place: String): String{
        return when(place){
            "논현역" -> "신논현역·논현역"
            "해방촌" -> "해방촌·경리단길"
            "DMC" -> "DMC(디지털미디어시티)"
            else -> place
        }
    }


}