package com.example.binarycalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.WorkSource
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import java.math.BigInteger

class MainActivity : AppCompatActivity() {

    @SuppressLint("ResourceType")
    private var textInput: EditText? = null
    private var textInputHex: EditText? = null
    var btnClick : Button? = null
    var DecimalText: TextView? = null
    var BinaryText: TextView? = null
    var OtalText: TextView? = null
    var HexDecimalText : TextView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //select types Decimal/Binary/Octal/HexDecimal with Spinner
        //val selectedType = findViewById<TextView>(R.id.decimalValue)
        var selectedType :String = ""
        val spinner = findViewById<Spinner>(R.id.spinner)

        val items = resources.getStringArray(R.array.selectType_list)
        val Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,items)

//

        spinner.adapter = Adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItems = parent?.getItemAtPosition(position)
                selectedType = selectedItems.toString()

                textInputHex!!.isVisible = false
                textInput!!.isVisible = true
                if(selectedItems == "HexDecimal" ){
                    textInputHex!!.isVisible = true
                    textInput!!.isVisible = false
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //var userInput! = null
        //Input data EditText
        textInput = findViewById(R.id.inputText)
        textInputHex = findViewById(R.id.inputTextHex)
        btnClick = findViewById(R.id.btnClick)
        btnClick?.setOnClickListener{


            val userInput = this.textInput?.text.toString()
            val userInputHex = this.textInputHex?.text.toString()
            if (userInput.isEmpty() && userInputHex.isEmpty()){
                Toast.makeText(this,"Please Input data",Toast.LENGTH_LONG).show()

            }else{
                    if (selectedType == "Decimal") {
                        this.DecimalText?.text = userInput
                        this.BinaryText?.text = decimalToBinary(userInput)
                        this.OtalText?.text = decimalToOctal(userInput)
                        this.HexDecimalText?.text = decimalToHexDecimal(userInput)


                    } else if (selectedType == "Binary") {
                        this.DecimalText?.text = binaryToDecimal(userInput.toLong()).toString()
                        this.BinaryText?.text = userInput
                        this.OtalText?.text = binaryToOctal(userInput)
                        this.HexDecimalText?.text = binaryToHexDecimal(userInput)
                    } else if (selectedType == "Octal") {
                        this.DecimalText?.text = octalToDecimal(userInput.toLong()).toString()
                        this.BinaryText?.text = octalToBinary(userInput.toLong()).toString()
                        this.OtalText?.text = userInput
                        this.HexDecimalText?.text = octalToHexDecimal(userInput)
                    } else if(selectedType == "HexDecimal"){
                        this.DecimalText?.text = hexaDecimalToDecimal(userInputHex)
                        this.BinaryText?.text = hexaDecimalToBinary(userInputHex)
                        this.OtalText?.text = hexaDecimalToOctal(userInputHex)
                        this.HexDecimalText?.text = userInputHex

                   }
            }

        }



        DecimalText = findViewById(R.id.decimalValue)
        BinaryText = findViewById(R.id.BinaryValue)
        OtalText = findViewById(R.id.octalValue)
        HexDecimalText = findViewById(R.id.hexdecimalValue)
        }

//    fun checkWordinclude(value: String) : Boolean{
//        val words = "abcdefghijklmnopqrstuvwxyz"
//        val Words = words.uppercase()
//        //val splitArray = value.split("")
//
//
//
//
//
//        if(words.contains(value) || Words.contains(value)){
//            return true
//        }
//        return false
//
//    }

    fun binaryToDecimal(value: Long) : Int{
        var num = value
        var decimalNumber = 0
        var i = 0
        var remainder : Long

        while(num.toInt() != 0){
            remainder = num % 10
            num /= 10
            decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
            ++i
        }

        return decimalNumber
    }

    fun decimalToBinary(value: String) : String {
        var num = value
        var binary = Integer.toBinaryString(num.toInt())
        return binary
    }

    fun decimalToOctal(value: String) : String{
        var decimalNum = value.toInt()
        var octalNum = 0;
        var i = 1
        var j = 0

        while(decimalNum > 0 ){
            j = decimalNum % 8
            octalNum += j * i
            decimalNum /= 8
            i *= 10
        }
        return octalNum.toString()
    }

    fun decimalToHexDecimal(value: String) :String{
        var decimalNum = value.toInt()
        var hexDecimalNum = Integer.toHexString(decimalNum)


        return hexDecimalNum
    }

    fun binaryToOctal(value: String) : String{
        var binaryNumber = value
        var binaryToDecimal = binaryToDecimal(binaryNumber.toLong())
        var octalNumber = 0
        var i = 1

        while (binaryToDecimal != 0){
            octalNumber += binaryToDecimal % 8 * i
            binaryToDecimal /= 8
            i *= 10
        }
        return octalNumber.toString()
    }

    fun binaryToHexDecimal(value: String) : String{
        var binaryNumber = value
        var binaryToDecimal = binaryToDecimal(binaryNumber.toLong())
        var decimalNum = binaryToDecimal.toInt()
        var hexDecimalNum = Integer.toHexString(decimalNum)

        return hexDecimalNum
    }

    fun octalToDecimal(value: Long) : Long{
        var octalNumber = value
        var decimalNumber = 0
        var i = 0
        if(octalNumberCheck(octalNumber.toString())){
            while (octalNumber.toInt() != 0){
                decimalNumber += (octalNumber%10 * Math.pow(8.0,i.toDouble())).toInt()
                i++
                octalNumber /= 10
            }

        }
        return decimalNumber.toLong()
    }

    fun octalNumberCheck(value: String) : Boolean{
        var isCorrectOctal = true
        val octalNumber = value
        for(i in octalNumber.indices){
            if(octalNumber[i] !in '0'..'7'){
                isCorrectOctal = false
            }
        }
        return isCorrectOctal
    }

    fun octalToBinary(value: Long) : Long {
        val octalNumber = value
        var binaryNumber = 0
        if(octalNumberCheck(octalNumber.toString())){
            val decimalNumber = octalToDecimal(octalNumber)
            binaryNumber = decimalToBinary(decimalNumber.toString()).toInt()

        }else{
            Toast.makeText(this,"Please enter 0 to 7",Toast.LENGTH_LONG).show()
        }
        return binaryNumber.toLong()
    }

    fun octalToHexDecimal(value: String) : String {
        var octalNumber = value
        var decimalNumber = octalToDecimal(octalNumber.toLong())
        var HexDecimalNumber = decimalToHexDecimal(decimalNumber.toString())

        return HexDecimalNumber
    }

    fun hexaDecimalToDecimal(value: String) : String{
        val hexDecimalNumber = value
        val decimalNumber = BigInteger(hexDecimalNumber,16).toString()
        return decimalNumber
    }

    fun hexaDecimalToBinary(value: String) : String{
        val hexDecimalNumber = value
        val decimalNumber = hexaDecimalToDecimal(hexDecimalNumber)
        val binaryNumber = decimalToBinary(decimalNumber)
        return binaryNumber
    }

    fun hexaDecimalToOctal(value: String) : String{
        val hexDecimalNumber = value
        val decimalNumber = hexaDecimalToDecimal(hexDecimalNumber)
        val octalNumber = decimalToOctal(decimalNumber)
        return octalNumber
    }



}