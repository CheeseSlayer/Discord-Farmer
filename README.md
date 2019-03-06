# Discord Farmer
This bot was made with efficiency and flexibility in mind. It has every feature a user would need, an **event system**, commands for **remote control**, a **dashboard** and some basic **statistics**. It **auto-verifies**, **auto-repairs** and **auto-mines** (Can **auto-enchant**).

# Contact
If you need help or want to inquire about something, add **CheeseSlayer32#4032** on discord. I'll try to reply to you as fast as possible though I can't guarantee it'll be fast.

# Disclaimer
**I cannot stress this enough, a self-bot is against the Discord Terms of Service so proceed 
at your own risk.**

# Terms of usage
You may not redistribute, claim as your own. This is your responsibility and I am not responsible for whatever you do with this tool.

# Download
To download the bot, head over to the [releases](https://github.com/CheeseSlayer/Discord-Farmer/releases) get the latest one.

# Usage
### Basic functionality
To get the bot up and running, you need a download and [JRE 8+ Downloaded](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html). (Java Runtime Environment)

Then, create a folder, drag & drop the .jar file in there, open a command line and type this command. **(Note that you can use a batch script to further automate this)**

`java -jar <BUILD JARFILE> USERTOKEN CHANNELID`

There are a few parameters, the jarfile parameter is simply the current name of the bot **e.g. DiscordFarmer.jar**

The User Token parameter is your discord user's discord token. You can get it with [this method](https://discordhelp.net/discord-token). This is basically the token that the bot uses to connect to your user and send messages.

Last but not least, the Channel ID parameter is the Unique ID associated to the text channel you want the bot to use. In order to get it, head on over to your **User Settings** then **Appearance** and enable **Developer Mode**. From that point on, you should be able to right click the text channel and an option named **COPY ID** should pop up. Simply click it and the ID will be in your clipboard.

So everything put together, it should look something like this

`java -jar DiscordFarmer.jar MYCOOLTOKEN 39201933213093`

### Advanced command line arguments
There are quite a few arguments that you can add to the base command to add functionality or tweak settings. They will be listed here.

Any argument is added by the argument string and then the value, if applicable.

**For Example:**

`java -jar DiscordFarmer.jar TOKEN ID -argument1 -argument2`

###### Command
`-c <OWNER ID>` Adding this argument will enable the command module and will make it accessible by the user that owns the ID specified. (Same method as channel ID but for users)

###### Mining Delay
`-md <DELAY MS>` Adding this argument will change the delay between different "mines". The argument is in milliseconds so keep that in mind.

###### Adaptive Mining Delay
`-amd` Adding this argument will **OVERRIDE** the mining delay above, and will constantly adapt the mining delay to your pickaxe's efficiency level.

###### Verification Delay
`-vd` Adding this argument will delay verification to make it more human like, obviously, there is a randomized offset over that to make sure it is safe & sound. A verification delay of at least 5 seconds and more is recommended.

###### Pickaxe Lock
`-p <PICKAXE>` Adding this argument will lock the user's pickaxe. For example, if he unlocks the diamond pickaxe, it will go back to the "locked" pickaxe. **Possible pickaxes are: WOODEN, STONE, GOLD, IRON, DIAMOND, SAPPHIRE, EMERALD, RUBY, ULTIMATE, DONATOR, CRATE**

###### Auto-Enchant
`-e` Adding this argument will activate auto-enchanting. The bot will monitor the lapis in the inventory and enchant as soon as possible.

### Command Module
Now we get to the juicy bits, the part that make this bot mega cool. The bot has a command module integrated within it. The command module is available through the **command line during runtime** as well as **through private messages if the "-c OWNER" argument** is present.

###### Help
`help`

Displays the help menu, self-explanatory

###### Pause
`pause`

This command toggles the bot on/off. This can be used as a quick **panic** switch.

###### Send
`send <MESSAGE>`

This command allows you to send commands on the behalf of the user. Useful for remote control, to buy things in the shop or to send messages in general.

###### Stat Reset
`statreset`

Resets the calculated statistics.

###### Synchronize
`sync`

This synchronizes bot data such as inventory, enchants, pickaxe, etc. It shouldn't desynchronize, ever, but it's good to do every once in a while if you want to keep good track of resources.

###### Status (Dashboard)
`status`

This command is by far the coolest, it gives you a bunch of information such as the current pickaxe, level, all the resources and their quantity, enchantments and levels as well as some statistics such as Mines per hour, XP Gain per hour and Levels per hour.

# Support
If you want to support this project, suggest features! If you'd rather donate, hit me up on discord and we'll see.
