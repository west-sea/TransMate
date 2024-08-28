package com.example.madcampw2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kakao.sdk.common.util.Utility
import com.example.madcampw2.databinding.ActivityLoginSdkBinding
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginSdk : AppCompatActivity() {
    private var _binding: ActivityLoginSdkBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginSdkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** HashKey확인 */
        val keyHash = Utility.getKeyHash(this)
        TextMsg(this, "HashKey: ${keyHash}")

        /** KakoSDK init */
        KakaoSdk.init(this, this.getString(R.string.kakao_app_key))

        binding.Login.setOnClickListener{
            val enteredText = binding.editName.text.toString()
            MyGlobals.getInstance().setMe(enteredText)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        /** Click_listener */
        binding.btnStartKakaoLogin.setOnClickListener {
            kakaoLogin() //로그인
        }
        binding.btnStartKakaoLogout.setOnClickListener {
            kakaoLogout() //로그아웃
        }
        binding.btnStartKakaoUnlink.setOnClickListener {
            kakaoUnlink() //연결해제
        }
    }

    private fun kakaoLogin() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (token != null) {
                //TODO: 최종적으로 카카오로그인 및 유저정보 가져온 결과
                UserApiClient.instance.me { user, error ->
                    setLogin(true)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            } else if (error != null) {
                setLogin(false)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    setLogin(true)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun kakaoLogout(){
        // 로그아웃
        UserApiClient.instance.logout { error ->
                setLogin(false)
        }
    }

    private fun kakaoUnlink(){
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            setLogin(false)
        }
    }

    private fun TextMsg(act: Activity, msg : String){
        binding.tvHashKey.text = msg
    }

    private fun setLogin(bool: Boolean){
        binding.btnStartKakaoLogin.visibility = if(bool) View.GONE else View.VISIBLE
        binding.btnStartKakaoLogout.visibility = if(bool) View.VISIBLE else View.GONE
        binding.btnStartKakaoUnlink.visibility = if(bool) View.VISIBLE else View.GONE
    }
}