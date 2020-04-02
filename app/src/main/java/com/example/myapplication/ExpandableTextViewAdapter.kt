package com.example.myapplication

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class ExpandableTextViewAdapter(var context: Context): BaseExpandableListAdapter() {


    var faqs:Array<String> = arrayOf(
        "What if I forget my app password/pin/pattern?",
        "What if I factory reset my phone?",
        "What will happen to my encrypted data if I uninstall the application?",
        "How can I reset my phone?",
        "How to hide files?",
        "How do I recover my secret pin? if I forget that.",
        "Where do my images exactly go after locking?",
        "How do I unlock images?"
    )



    var answers = arrayOf<Array<String>>(arrayOf<String>("Answer 1, answer1"), arrayOf<String>("answer 2"), arrayOf<String>("answeer 3"), arrayOf<String>("answeer 4"), arrayOf<String>("answeer 5")
        , arrayOf<String>("answeer 6"), arrayOf<String>("answeer 7"), arrayOf<String>("answeer 8"))





    override fun getGroup(groupPosition: Int): Any {
            return faqs[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
            return false
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var questionFaq = getGroup(groupPosition) as String
        var convertView = convertView
        if (convertView==null){
            var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.faqs_title,null)
        }
        var questionFaq2 = convertView?.findViewById<TextView>(R.id.faqsTitleView)
        questionFaq2?.setTypeface(null,Typeface.BOLD)
        questionFaq2?.text = questionFaq
        return convertView

    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return answers[groupPosition].size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return answers[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val answerFaq = getChild(groupPosition, childPosition) as String
        var convertView = convertView
        if (convertView==null){
            var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.faq_answer,null)
        }
        var answerFaq2 = convertView?.findViewById<TextView>(R.id.description)
        answerFaq2?.setTypeface(null,Typeface.BOLD)
        answerFaq2?.text = answerFaq
        return convertView

    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return faqs.size
    }

}