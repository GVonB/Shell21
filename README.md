# Shell21
🃏 A recreation of the classic blackjack card game, <b>fully inside the terminal</b> with local <b>profiles</b> to save your stats!
```bash
┌───────┐┌───────┐┌───────┐┌───────┐┌───────┐┌───────┐┌───────┐
│ S     ││ H     ││ E     ││ L     ││ L     ││ 2     ││ 1     │
│       ││       ││       ││       ││       ││       ││       │
│   ♦   ││   ♥   ││   ♣   ││   ♠   ││   ♦   ││   ♥   ││   ♣   │  // Terminal-Based Blackjack
│       ││       ││       ││       ││       ││       ││       │
│     S ││     H ││     E ││     L ││     L ││     2 ││     1 │
└───────┘└───────┘└───────┘└───────┘└───────┘└───────┘└───────┘
```
This project was an interesting experiment that used only terminal output to display cards and allow users to play <b>blackjack</b> (also known as 21).

Users are prompted to enter their name as the key for their local profile. Stats including <b>wins</b>, <b>losses</b>, <b>blackjack</b>, <b>win/loss ratio</b>, <b>games played</b>, and <b>"rank"</b> are stored.

<h3>How To Play</h3>

1. Clone the repository and navigate to the bin file in your terminal (or compile the src files on your own if you like).
2. Ensure Java is installed and run `java FrontEnd`.
3. `Enter your profile/player name:` Enter a name you would like to save your session's stats under.
4. Sample hand:
```bash
Drew Card:
┌───────┐
│ A     │
│       │  // Visual representation of your cards
│   ♠   │
│       │
│     A │
└───────┘
Drew Card:
┌───────┐
│ 3     │
│       │  //
│   ♠   │
│       │
│     3 │
└───────┘

Dealer drew a(n) Queen of Clubs              // Tell user what dealer drew

Your hand total is 14                        // Simple numerical hand total
Would you like to hit or stand (H/S)?        // Prompt to hit (draw) or stand
```

5. Simply follow the prompts to play!

<h3>Rank Determination</h3>
Player Rank is rather arbitrary as it's linearly based on a player's win-loss ratio, starting calculation after 20 games played (5% margins for win/loss ratio).<br><br>
<b>Ranks</b>

```
S - W/L Ratio >= 1
A - W/L Ratio >= .8
B - W/L Ratio >= .6
C - W/L Ratio >= .4
D - W/L Ratio < .4
```
<h3>Notes</h3>
While there are slight variations in blackjack rules across the world, this program requires the dealer to hit on a hand total of 16 or lower. Additionally, they will still hit when the dealer has a "soft" 17 (ace + 6). This may seem like a benefit to the player, but mathematically, hitting on a soft 17 increases the dealer's chance of winning.
