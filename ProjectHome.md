# Dicerolling robot for Google Wave #

Robot address: `dice-y@appspot.com`

## Primary objective: Create Dice Roller That People Would Use ##

## Secondary objectives ##

  * keep it as simple as possible, without the need to read manuals to use;
  * keep it discoverable so that new features can be found quickly if needed;
  * allow rolls in a lot of systems (d20, NWoD, GURPS, etc...) with free switching between them;
  * allow each system to use its own syntax and result display that is most convenient for that system;
  * have a settings gadget for easy configuration.

## Example ##

Roll example:
```

And I swing my huge sword [2d6+5] and decapitate the evil monster!
```

Robot will expand this into something like:
```

And I swing my huge sword [2d6+5 = 9 + 5 = 14] and decapitate the evil monster!
```

## Usage ##

Roll format is `[roll]` - it will be expanded into `[roll = result]`.

You can use `[prefix:roll]` if you want to make non-standard roll. For example, `[ditv:4d6+4d10]`.

Currently supported prefixes are:
  * `sum` - for standard "roll and add" rolling - this is default;
  * `ditv` - for [Dogs in the Vineyard](http://www.lumpley.com/dogsources.html)-style rolling.
  * `nwod` - for [New World of Darkness](http://en.wikipedia.org/wiki/NWOD)-style rolling.

Planned in future:
  * `ore` - for [One Roll Engine](http://en.wikipedia.org/wiki/One-Roll_Engine) rolls;
  * something for dicepool-style rolling.