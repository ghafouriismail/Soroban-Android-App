package com.example.sorobanv1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

var Score=0
lateinit var Op:Operation

enum class Level {
    Easy,Medium,Hard
}
interface Operation {
    fun result(): Int
    fun getOperationTemplate(): String
    fun getLevel(): Level
    companion object {
        fun generateOperation(): Operation {
            val level=arrayOf(Level.Easy,Level.Medium,Level.Hard).random()
            val num1 = Random.nextInt(0, 100)
            val num2 = Random.nextInt(0, 100)
            return when (level) {
                Level.Easy -> Addition(num1, num2)
                Level.Medium -> Substraction(num1, num2)
                else -> Multiplication(num1, num2)
            }
        }
    }
}
class Addition(private var num1: Int, private var num2: Int) : Operation {
    override fun result() = num1 + num2
    override fun getOperationTemplate() = "$num1 + $num2"
    override fun getLevel() = Level.Easy
    override fun toString() = "$num1 + $num2 = ${result()}"
}

class Substraction(private var num1: Int, private var num2: Int) : Operation {
    override fun result() = num1 - num2
    override fun getOperationTemplate() = "$num1 - $num2"
    override fun getLevel() = Level.Medium
    override fun toString() = "$num1 - $num2 = ${result()}"
}

class Multiplication(private var num1: Int, private var num2: Int) : Operation {
    override fun result() = num1 * num2
    override fun getOperationTemplate() = "$num1 x $num2"
    override fun getLevel() = Level.Hard
    override fun toString() = "$num1 x $num2 = ${result()}"
}


class MainActivity : AppCompatActivity() {
    private lateinit var operationTextView:TextView
    private lateinit var answerinput:EditText
    fun insertbutton(view: View){
        val inputanswer:String
        val text=(view as Button).text.toString()
        inputanswer=answerinput.text.toString()+text
        answerinput.setText(inputanswer)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newscore=findViewById<TextView>(R.id.score)
        operationTextView=findViewById(R.id.operation)
        answerinput= this.findViewById(R.id.answer)

        val delete=findViewById<Button>(R.id.Delete)
        delete.setOnClickListener {
            answerinput.setText("")
        }

        Op=Operation.generateOperation()
        operationTextView.text=Op.getOperationTemplate()

        val valider=findViewById<Button>(R.id.validate)
        valider.setOnClickListener {
            if (answerinput.text.isNotEmpty()){
                if(Op.result().toString()==answerinput.text.toString()){
                    Score++
                }else{
                    Score--
                }
                Op=Operation.generateOperation()
                operationTextView.text=Op.getOperationTemplate()
            }else{
                Toast.makeText(this,"Please enter an answer",Toast.LENGTH_SHORT).show()
            }
            newscore.text= Score.toString()
            answerinput.setText("")

        }
    }
}