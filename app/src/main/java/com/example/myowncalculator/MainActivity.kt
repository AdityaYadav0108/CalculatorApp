package com.example.myowncalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var lastDot=false
    var lastNumeric=false
    private var tvInput: TextView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput=findViewById(R.id.tvInput)
    }
    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric=true

    }
    fun onClear(view: View){
        tvInput?.text=""
    }
    fun onDecimal(view: View){
        if (!lastDot) {
            tvInput?.append(".")
            lastDot = true
        }
    }
    fun onOperator(view:View){
       tvInput?.text?.let {
           if (lastNumeric && !isOperatorAdded(it.toString())) {
               tvInput?.append((view as Button).text)
               lastDot=false
           }
       }
    }
    private fun isOperatorAdded(value : String): Boolean{
        return if (value.startsWith("-")){
            false
        } else{
            value.contains("/")||
            value.contains("-")||
            value.contains("*")||
            value.contains("+")
        }
    }
    fun onEqual(view:View){
        var prefix=""
        if(lastNumeric){
            var tvValue= tvInput?.text.toString()
            try{
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }
                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text = removeZeroAfter((one.toDouble() - two.toDouble()).toString())
                }
                else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text = removeZeroAfter((one.toDouble() + two.toDouble()).toString())
                }
                else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text = removeZeroAfter((one.toDouble() / two.toDouble()).toString())
                }
                else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text = removeZeroAfter((one.toDouble() * two.toDouble()).toString())
                }
            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfter(result:String):String{
        var value=result
        if(result.contains(".0"))
            value=result.substring(0,result.length-2)
        return value
    }
}