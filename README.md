## Introduction

Conch is a lightweight and modular Discord bridge allowing you to syncronise in-game messages in Minecraft with a channel on your discord via discord-webhooks and an integrated discord bot. Users will appear as "bots" with their player head as the avatar. Using the Integrated Discord Bot you can configure discord messages to appear in game. 

Conch is designed to be modular. It is based around a "core" which provides all necessary functionality, then platform-specific implementations via modules. 

Conch supports all major Minecraft Server and Proxy Implementations 
- Bukkit-based (Paper, Spigot etc) 
- Sponge7-based (Spongeforge & Spongevanilla)
- Velocity
- Bungeecord

There are currently plans to support these additional platforms in the future
- Fabric
- Matrix
- IRC

## Installation Instructions

All Installation Instructions have been moved to the HiberniaDiscord website.
https://docs.conch.rocks

## Development Builds

Please see the Actions tab for developmenet builds, there is no guarentee of stability. 

## Planned Addons 

- ConchReport (1.0)
HiberniaReport fork that's built directly into HiberniaDiscord!

- ConchConsole (1.1)
Console logging and togglable ability to execute commands as console via discord!

- ConchSpy (1.1)
Monitor commands being used by staff members, and discord-based social-spy.

- ConchSupport (1.2)
Ability to message online staff and offline staff via discord -- with a ticketing-like system.

- ConchStaffChat (1.3)
Ability to sync a staff chat in-game and out-of-game with RabbitMQ support for shard/network setups.


## Compatibility / Planned Compatibility 
Conch strives to be compatible with common plugin combinations. For now this only includes PlaceholderAPI on Bukkit, however there are plans to add more in 1.X releases.

- Vanish and Staff Chat Plugin Support
- CarbonChat Integration with Channel Support

If there's a plugin you would like to see me implement please let me know!

### Installation

As of the time of writing this there are no current external libraries used.

1.  :: Download the latest copy of Conch from the releases page.
2.  :: Move conch-platform.jar into your plugins folder
3.  :: Restart (Do *Not* Reload) your server to generate the necessary configuration files.
4.  :: Configure the plugin to use your webhook. 

Learn more about creating webhooks via the docs: https://docs.conch.rocks // TODO update link

## Deployment

in project root:
`mvn clean install` 

## Contributings

Conch is FOSS, if you would like a feature, simply fork the respository, add the requested functionality and open a pull request. I review them as they come in, and will aid you in merging it ino production in due course.


## v1.0 Contributors
* **Rían Errity**

Special Thanks to:
- egg82 for his support on HiberniaDiscord 2
- the entire Syscraft Discord

All those who contribute in any form will be acknowledged.

## License

This project is licensed under GPL - see the [LICENSE.md](LICENSE.md) file for details
