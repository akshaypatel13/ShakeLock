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
        "How to hide files?",
        "How do I unlock images?",
        "Where do my images exactly go after locking?",
        "What type of files can I hide",
        "How do I recover my secret pin? if I forget that.",
        "What will happen to my encrypted data if I uninstall the application?",
        "What if I factory reset my phone?"
    )

    var answers = arrayOf<Array<String>>(
        arrayOf<String>("After you have set up a lock, you can easily go to encrypt, select all the files that you wish to hide and they will no longer be displayed.")
        , arrayOf<String>("You can go to decrypt and easily unhide all the files and they will be displayed in your phone.")
        , arrayOf<String>("Your files remain on the same location on your phone even after they are hidden. We do not upload it to cloud.")
        , arrayOf<String>("You can lock any file that you wish to with this application")
        , arrayOf<String>("Only the correct pin can unlock the data and we cant unlock your data without your pin")
        , arrayOf<String>("Your files will still be on your phone, but for them to be visible you will have to install the application and decrypt them")
        , arrayOf<String>("You will lose all your hidden files if you factory reset your phone"))







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
