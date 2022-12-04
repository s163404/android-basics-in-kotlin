package com.example.tiptime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    // バインディングオブジェクトのクラス内の最上位変数を宣言
    // `lateinit` コードが変数を使用する前に初期化することを保証する（初期化しない場合アプリがクラッシュする）
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // activity_main.xmlレイアウトのViewsへのアクセスに使うbindingオブジェクトを初期化する
        binding = ActivityMainBinding.inflate(layoutInflater)
        // アクティビティのコンテンツビューを設定する。
        // R.layout.activity_mainの代わりにアプリのビュー階層のルートbinding.rootを指定する。
        setContentView(binding.root)

        // クリックリスナーを登録
        binding.calculateButton.setOnClickListener{ calculateTip() }
    }

    // チップを計算し、計算結果を表示する
    fun calculateTip() {
        // 料金を取得する
        val stringTextField = binding.costOfService.text.toString()
        val cost = stringTextField.toDouble()

        // チップの割合を取得する
        val selectedId = binding.tipOptions.checkedRadioButtonId
        val tipPercentage = when (selectedId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // チップを計算
        // 必要に応じて端数を切り上げる
        var tip = tipPercentage * cost
        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }

        // チップの書式を設定して表示する
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}