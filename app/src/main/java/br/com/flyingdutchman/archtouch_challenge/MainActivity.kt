package br.com.flyingdutchman.archtouch_challenge

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.com.flyingdutchman.archtouch_challenge.data.WordRepository
import br.com.flyingdutchman.archtouch_challenge.ui.WordPresenter
import br.com.flyingdutchman.archtouch_challenge.ui.WordsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity :
    AppCompatActivity(),
    WordPresenter.WordScreen {

    lateinit var wordPresenter: WordPresenter
    private val adapter by lazy {
        WordsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        wordPresenter =
            WordPresenter(
                WordRepository(),
                this
            )

        setupRecyclerView()

        fab.setOnClickListener {
            wordPresenter.startGame()

            game_result.gone()
            fab.gone()
            word_edit_text.show()
            word_recycler.show()
        }

        word_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.count() > 2) {
                    wordPresenter.checkWord(s.toString())
                }
            }
        })
    }


    override fun updateGuessesCorrect(word: String) {
        adapter.addWord(word)
        word_edit_text.setText("")
        hideKeyboard()
    }

    override fun showWinningState() {
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.clear()
            word_edit_text.setText("")
            wordPresenter.reset()
            game_result.setBackgroundColor(resources.getColor(android.R.color.holo_green_dark))
            game_result.text = "YOU WIN!"



            fab.show()
            word_edit_text.gone()
            word_recycler.hide()
            game_result.show()
        }, 700)
    }

    override fun showLoseState() {
        Handler(Looper.getMainLooper()).postDelayed({

            adapter.clear()
            word_edit_text.setText("")
            wordPresenter.reset()
            game_result.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark))
            game_result.text = "YOU LOSE!"

            fab.show()
            word_edit_text.gone()
            game_result.show()
        }, 700)

    }

    override fun updateTimer(timeElapsed: String) {
        game_time.text = timeElapsed
    }

    override fun updateScore(score: String) {
        game_score.text = score
    }

    private fun setupRecyclerView() {
        word_recycler.layoutManager = GridLayoutManager(this, 3)
        word_recycler.setHasFixedSize(true)
        word_recycler.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
