

# General information #

To make a roll, just add `dice-y@appspot.com` to your wave, select the method from configuration gadget and enter something like `[2d6]` anywhere on the wave..

In all rolling methods spaces are ignored - you may write `[d6+d8]` or `[d6 + d8]` as you like.

If you want to roll with a method different from the one selected in gadget, write roll with a prefix. For example, when "Sum of all rolls" is selected, you can make a Dogs in the Vineyard roll with `[ditv:4d6+2d6]`.

# Sum of all rolls #

Examples:
  * `[2d6]` - simple roll;
  * `[2d6+5]` - roll with bonus;
  * `[2d6-4+3d8-1d10]` - some arithmetics;
  * `[d20+3]` - you can omit number of dice if it is one.

Features:
  * rolls are written like `2d6`;
  * addition and subtraction are supported;
  * bonuses/penalties are written like plain numbers;
  * you can omit the number of dice if it is one: write `d20` instead of `1d20`.

To do:
  * multiplication, division and brackets.

# D20 System #

This is the same as "Sum of all rolls" with one addition:
  * `[cg]` - generate ability scores for a character.

# D6 System #

Examples:
  * `[3D+2]` - roll three dice (one of them wild) and add 2 pips.
  * `[3D+2+2D+1]` - roll five dice (one of them wild) and add 3 pips.

Features:
  * rolls are written like `5D`, pips are like `3`;
  * all dice and pips in the roll are totaled before rolling.

# Dogs in the Vineyard #

Examples:
  * `[4d6+4d10]` - sum of rolls.

Features:
  * rolls are written like `4d6`;
  * addition is supported.

# New World of Darkness #

Examples:
  * `[5]` - roll a dicepool of 5;
  * `[5+3]` - roll a dicepool of 8.

Features:
  * just write the dicepool between the brackets;
  * addition is supported.

To do:
  * subtraction.

# Exalted #

Examples:
  * `[10]` - roll a dicepool of 10;
  * `[10 tn 6]` - roll a dicepool of 10 with target number 6;
  * `[10/6 vs 3]` - roll a dicepool of 10 with target number 6 and difficulty 3;
  * `[10/6+5/7]` - roll two dicepools: one of 10 dice with TN 6 and one of 5 with TN 7, results are combined;
  * `[m 10 vs 5]` - roll a dicepool of 10 with TN 5, do not explode 10s.

Features:
  * you can add dice pools;
  * you can specify target number for every dicepool with `/` or `tn`;
  * you can specify difficulty of the roll with `vs`;
  * you can prevent exploding 10s with `m` in the beginning of the roll.

To do:
  * subtraction;
  * auto-success.

# Nemesis (One Roll Engine) #

Examples:
  * `[5d]` - roll 5 normal dice;
  * `[5d+2+3]` - set 2 expert dice to "2" and "3", then roll 5 normal dice;
  * `[5d+3td]` - roll 5 normal dice, then think where to put 3 trump dice;
  * `[3+5d+1td+2+1td+2d+1d]` - do some arithmetics.

Features:
  * normal dice;
  * expert dice;
  * trump dice.