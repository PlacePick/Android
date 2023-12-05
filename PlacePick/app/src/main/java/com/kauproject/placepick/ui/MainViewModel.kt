package com.kauproject.placepick.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kauproject.placepick.model.DataStore
import com.kauproject.placepick.model.State
import com.kauproject.placepick.model.data.Comment
import com.kauproject.placepick.model.data.Post
import com.kauproject.placepick.model.repository.BoardRepository
import com.kauproject.placepick.util.Util
import kotlinx.coroutines.launch
class MainViewModel(
    application: Application
): AndroidViewModel(application) {

    companion object{
        const val TAG = "MainViewModel"
    }
    private val dataStore = DataStore(application)
    private val boardRepository = BoardRepository()

    private val _detailList = MutableLiveData<List<Post>>()
    val detailList: LiveData<List<Post>>
        get() = _detailList

    private val _board = MutableLiveData<String>()
    val board: LiveData<String>
        get() = _board

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData>
        get() = _userData

    private val _placeState = MutableLiveData<List<String?>?>()
    val placeState: LiveData<List<String?>?>
        get() = _placeState

    private val _postContent = MutableLiveData<Post>()
    val postContent: LiveData<Post>
        get() = _postContent

    private val _commentList = MutableLiveData<List<Comment>>()
    val commentList: LiveData<List<Comment>>
        get() = _commentList

    private val _serverError = MutableLiveData(0)
    val serverError: LiveData<Int>
        get() = _serverError

    private val _error = MutableLiveData("")
    val error: LiveData<String>
        get() = _error


    // 게시글 조회
    fun getDetailList(){
        _board.value?.let {
            boardRepository.getBoardDetail(it){ list->
                _detailList.value = list
            }
        }
    }

    // 게시판 클릭
    fun setBoard(board: String){
        _board.value = board
    }

    // 유저정보 조회
    fun getUserData(){
        viewModelScope.launch {
            _userData.value = UserData(
                userNum = dataStore.getUserData().userNum,
                nick = dataStore.getUserData().nickName
            )
        }
    }

    // 개시글 작성
    fun postWrite(post: Post){
        _board.value?.let { board->
            _userData.value?.userNum?.let { userNum -> boardRepository.postWrite(post, board, userNum) }
        }
    }

    // 지역 혼잡도 조회
    fun getPlaceStatus(){
        viewModelScope.launch {
            boardRepository.getPlaceStatus().collect{ state->
                when(state){
                    is State.Loading -> { _placeState.value = emptyList()}
                    is State.Success -> { _placeState.value = state.data }
                    is State.ServerError -> { _serverError.value = state.code }
                    is State.Error -> { _error.value = state.exception.toString() }
                }
            }
        }
    }

    // 게시글 클릭
    fun postContent(post: Post){
        _postContent.value = post
    }

    // 댓글 입력
    fun commendWrite(content: String, postId: String){
        _board.value?.let {
            viewModelScope.launch {
                boardRepository.commentWrite(
                    comment = Comment(
                        nick = dataStore.getUserData().nickName ?: "익명",
                        userNum = dataStore.getUserData().userNum,
                        duration = Util().convertTimeToDateString(System.currentTimeMillis()),
                        content = content
                    ),
                    board = it,
                    postId = postId
                )
            }
        }
    }

    // 댓글 조회
    fun getComment(postId: String){
        _board.value?.let { board->
            boardRepository.getPostComment(board = board, postId = postId){ list->
                _commentList.value = list
            }
        }
    }

    fun getCommentCnt(postId: String, commentCount: (Int) -> Unit){
        _board.value?.let { board->
            boardRepository.getPostComment(board = board, postId = postId){
                commentCount(it.size)
            }
        }
    }

}

data class UserData(
    val userNum: String = "",
    val nick: String? = null
)