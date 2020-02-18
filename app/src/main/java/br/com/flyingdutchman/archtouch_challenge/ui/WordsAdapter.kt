package br.com.flyingdutchman.archtouch_challenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.flyingdutchman.archtouch_challenge.R
import kotlinx.android.synthetic.main.word_item.view.*

class WordsAdapter(private val action: (String) -> Unit? = {}) :
    RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

    private val wordsList = mutableListOf<String>()

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(word: String) {
            itemView.word_content.text = word
        }
    }

    fun setWords(words: List<String>) {
        wordsList.clear()
        wordsList.addAll(words)
        notifyDataSetChanged()
    }

    fun clear() {
        wordsList.clear()
        notifyDataSetChanged()
    }

    fun addWord(word: String) {
        wordsList.add(word)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder =
        WordViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.word_item,
                parent,
                false
            )
        )


    override fun getItemCount(): Int = wordsList.size


    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentWord = wordsList[position]
        holder.bind(currentWord)
        holder.itemView.setOnClickListener {
            action.invoke(currentWord)
        }
    }
}