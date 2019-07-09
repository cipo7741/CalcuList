package org.cipo.calcu_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class CalculationFragment(private val operation: String, private val result: String) : Fragment() {


//    lateinit var mCallback : OnOperationClickListener
//
//    interface OnOperationClickListener {
//        fun onChooseOperation()
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        try {
//            mCallback = context as OnOperationClickListener
//        } catch (e : ClassCastException){
//            throw ClassCastException(context.toString() + "must implement OnOperationClickListener")
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the fragment layout
        val rootView = inflater.inflate(R.layout.fragment_calculation, container, false)

        // Get a reference to the TextViews in the fragment layout
        val textViewOperation = rootView.findViewById(R.id.calculation_textViewOperation) as TextView
        val textViewResult = rootView.findViewById(R.id.calculation_textViewResult) as TextView

        textViewOperation.text = operation
        textViewResult.text = result

//        textViewOperation.setOnClickListener{
//            mCallback.onChooseOperation()
//        }

        // Return the rootView
        return rootView
    }



}

