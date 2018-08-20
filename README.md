# Snakes-And-Ladders-JavaFX-from-scratch


This is an old board game called "Snakes And Ladders". I created it using JavaFX and FXML. Read me.

Game preview screenshot: http://prntscr.com/kkk7ad

Here is some information about the game: https://en.wikipedia.org/wiki/Snakes_and_Ladders

# Game Variations:
- CLASSIC


The standard rules of the game mention that if the dice roll is too large, you go to the final square and back again.
Eg. Say you currently are at 98. If you hit 3 you should go 98 + 3 = 101, which is too large so you land 1 square before the final one.


- Aristomenis


This is my edition. The only change is that when your dice roll is too large, you start from the begin.
Eg. Say you are at 99, if you hit 5 you should go 99 + 5 = 104, which is too large so you land at square 4.

Warning: This edition is extremely disturbing after some time.

# Features
I have added some background music of one of my favourite artists, "Yann Tiersen". You can always pause it if you dislike it.
"music" folder contains all the tracks.
# In order to run my application, you need to specify the correct path of the tracks.

# // TO-DO:

- Place a volume slider.
- Fix potential bug when there is a winner. (Show-and-wait and PauseTransition don't work well together)
