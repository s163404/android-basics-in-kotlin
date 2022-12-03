package com.example.tiptime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // バインディングオブジェクトのクラス内の最上位変数を宣言
    // `lateinit` コードが変数を使用する前に初期化することを保証する（初期化しない場合アプリがクラッシュする）
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // XMLレイアウトのViewsへのアクセスに使うbindingオブジェクトを初期化する
        binding = ActivityMainBinding.inflate(layoutInflater)
        // アクティビティのコンテンツビューを設定する。
        // `setContentView(R.layout.activity_main)`の代わりにアプリのビュー階層のルートbinding.rootを指定する。
        setContentView(binding.root)
    }
}