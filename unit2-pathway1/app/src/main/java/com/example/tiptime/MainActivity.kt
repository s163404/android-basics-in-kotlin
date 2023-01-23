package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    // バインディングオブジェクトのクラス内の最上位変数を宣言
    // `lateinit` コードが変数を使用する前に初期化することを保証する（初期化しない場合アプリがクラッシュする）
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // activity_main.xmlレイアウトのViewsへのアクセスに使うbindingオブジェクトを初期化する
        binding = ActivityMainBinding.inflate(layoutInflater)
        // アクティビティのコンテンツビューを設定する。
        // R.layout.activity_mainの代わりにアプリのビュー階層のルートbinding.rootを指定する。
        setContentView(binding.root)

        // クリックリスナーを登録
        binding.calculateButton.setOnClickListener{ calculateTip() }

        // キーボードのキーリスナーを登録
        binding.costOfServiceEditText.setOnKeyListener{ view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    // チップを計算し、計算結果を表示する
    private fun calculateTip() {
        // 料金を取得する
        val stringTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringTextField.toDoubleOrNull()
        // If the cost is null or 0, then display 0 tip and exit this function early.
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        // チップの割合を取得する
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }


        // チップを計算
        // 必要に応じて端数を切り上げる
        var tip = tipPercentage * cost
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        // Display the formatted tip value on screen
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        // チップの書式を設定して表示する
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        // 文字列リソースからIDで文字列を参照して、文字列パラメータにformattedTipを代入
        // その文字列をtipResult のtext に格納
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    // keyCodeパラメータがKEYCODE_ENTERと等しい場合に画面キーボードを非表示にする
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}