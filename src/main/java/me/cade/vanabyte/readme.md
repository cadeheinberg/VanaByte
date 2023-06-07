### The most important class is [Fighter.java](https://github.com/cadeheinberg/SKplugin/blob/main/SevenKitsPlugin/src/me/cade/PluginSK/Fighter.java).
- A Fighter.java instance stores all of the stats for the player that belongs to that instance. It also holds key methods that are used during gameplay.
- As the player plays progresses, their respective Fighter instance is called or updated accordingly.

### A Fighter instance is created for every player that joins the server in [PlayerJoinListener.java](https://github.com/cadeheinberg/SKplugin/blob/main/SevenKitsPlugin/src/me/cade/PluginSK/PlayerJoin/PlayerJoinListener.java).
- From there you can follow what happens to a player from the moment they join the server to the moment they leave.
- When the player leaves the server, to save the their stats the data held by the Fighter instance is uploaded to the MySQL server.
- When the player rejoins, the data is then downloaded from the MySQL server and then injected into the newly made Fighter instance for the player!

### Each Fighter on the server has their own Weapons and abilities, which are managed by their [FighterKit.java](https://github.com/cadeheinberg/SKplugin/blob/main/SevenKitsPlugin/src/me/cade/PluginSK/BuildKits/FighterKit.java).
- FighterKit.java has 7 children classes called F0, F2, ....F6 which can be found in [/BuildKits](https://github.com/cadeheinberg/SKplugin/tree/main/SevenKitsPlugin/src/me/cade/PluginSK/BuildKits).
- Each of these Children are a specific kit/class on the server. So there are 7 kits on the server that all have unique weapons and abilities

## Email me: cadeheinberg@outlook.com
