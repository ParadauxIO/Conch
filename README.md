![HiberniaDiscord Logo](https://cdn.paradaux.io/static/plugin-branding/hiberniadiscord/hibernia-discord-banner.png)


## Introduction

HiberniaDiscord is a lightweight Minecraft <-> Discord Bridge allowing you to syncronise in-game messages with a channel on your discord via discord-webhooks and an integrated discord bot. Users will appear as "bots" with their player head as the avatar. Using the Integrated Discord Bot you can configure discord messages to appear in game. 

HiberniaDiscord 4.0.0 is designed to be modular. It is based around a "core" which provides all necessary functionality, then platform-specific implementations via modules. 

HiberniaDiscord supports all major Minecraft Server and Proxy Implementations 
- Bukkit-based (Paper, Spigot etc) 
- Sponge7-based (Spongeforge & Spongevanilla)
- Velocity
- Bungeecord

You can expect Fabric and Sponge8 support in late Q1-Early Q2 of 2021

## Installation Instructions

All Installation Instructions have been moved to the HiberniaDiscord website.
https://hiberniadiscord.paradaux.io

## Development Builds

You can grab early / unstable builds @ 
https://ci.paradaux.io

## Planned Addons 

- HDReport (4.0)
HiberniaReport fork that's built directly into HiberniaDiscord!

- HDConsole (4.1)
Console logging and togglable ability to execute commands as console via discord!

- HDSpy (4.1)
Monitor commands being used by staff members, and discord-based social-spy.

- HDModMail (4.2)
Ability to message online staff and offline staff via discord -- with a ticketing-like system.

- HDStaffChat (4.3)
Ability to sync a staff chat in-game and out-of-game with RabbitMQ support for shard/network setups.


## Compatibility / Planned Compatibility 

HiberniaDiscord strives to be compatible with common plugin combinations. For now this only includes PlaceholderAPI on Bukkit, however there are plans to add more in 4.X releases.

- Vanish and Staff Chat Plugin Support
- CarbonChat Integration with Channel Support

If there's a plugin you would like to see me implement please let me know!

### Installation

As of the time of writing this there are no current external libraries used.

1.  :: Download the latest copy of HiberniaDiscord from the releases page.
2.  :: Move HiberniaDiscord.jar into your plugins folder
3.  :: Restart (Do *Not* Reload) your server to generate the necessary configuration files.
4.  :: Configure the plugin to use your webhook. 

Learn more about creating webhooks  [Here](https://support.discordapp.com/hc/en-us/articles/228383668-Intro-to-Webhook)

## Deployment

in project root:
`mvn clean install`

## Contributings

HiberniaDiscord is FOSS, if you would like a feature, simply fork the respository, add the requested functionality and open a pull request. I review them as they come in, and will aid you in merging it ino production in due course.

## v4.0 Contributors!
* **Rían Errity**

Special Thanks to Egg82 and Lucko for their very pretty code which inspired the 4.0.0 rewrite!

All those who contribute in any form will be acknowledged.

## License

This project is licensed under GPL - see the [LICENSE.md](LICENSE.md) file for details
