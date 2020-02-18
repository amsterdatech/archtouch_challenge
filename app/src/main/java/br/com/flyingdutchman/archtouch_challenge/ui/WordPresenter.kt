package br.com.flyingdutchman.archtouch_challenge.ui

import android.os.CountDownTimer
import br.com.flyingdutchman.archtouch_challenge.data.WordRepository
import br.com.flyingdutchman.archtouch_challenge.domain.GameState


class WordPresenter(
    private val repository: WordRepository,
    private val ui: WordScreen
) {

    private lateinit var timer: CountDownTimer

    companion object {
                const val GAME_TIME = 5 * 60 * 1000L
    }

    fun startGame() {
        val words = repository.fetchWords()
        GameState.reset()
        GameState.answers(words)

        beginState()
        startTimer()
    }


    fun checkWord(word: String) {
        val guessCorrect = GameState.checkWord(word)

        if (guessCorrect) {
            ui.updateGuessesCorrect(word)
        }

        if (GameState.hasWon()) {
            ui.showWinningState()
        } else {
            ui.updateScore("${GameState.currentScore()}/${GameState.totalScore()}")
        }
    }

    fun reset() {
        GameState.reset()
        timer.cancel()
        beginState()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(GAME_TIME, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000

                val minutes = seconds / 60
                var secondsRemaining = seconds % 60

                var secondsText =
                    if (secondsRemaining < 10) "0$secondsRemaining" else "$secondsRemaining"
                var minutesText = if (minutes < 10) "0$minutes" else "$minutes"

                ui.updateTimer("$minutesText:$secondsText")
            }

            override fun onFinish() {
                if (GameState.hasWon()) {
                    ui.showWinningState()
                } else {
                    ui.showLoseState()
                }
            }
        }.start()
    }

    private fun beginState() {
        ui.updateScore("${GameState.currentScore()}/${GameState.totalScore()}")

        val seconds = GAME_TIME / 1000
        ui.updateTimer("${seconds / 60}:${seconds % 60}")
    }

    interface WordScreen {
        fun updateGuessesCorrect(word: String)
        fun showWinningState()
        fun showLoseState()
        fun updateTimer(timeElapsed: String)
        fun updateScore(score: String)
    }
}