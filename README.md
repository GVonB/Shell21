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

<h3>Rank Determination</h3>
Player Rank is rather arbitrary as it's linearly based on a player's win-loss ratio, starting calculation after 20 games played (5% margins for win/loss ratio).<br><br>
<i>Ranks</i><br>
S - W/L Ratio >= 1<br>
A - W/L Ratio >= .8<br>
B - W/L Ratio >= .6<br>
C - W/L Ratio >= .4<br>
D - W/L Ratio < .4<be>

<h3>Notes</h3>
While there are slight variations in blackjack rules across the world, this program requires the dealer to hit on a hand total of 16 or lower. Additionally, they will still hit when the dealer has a "soft" 17 (ace + 6). This may seem like a benefit to the player, but mathematically, hitting on a soft 17 increases the dealer's chance of winning.
