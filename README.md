# HiberniaDiscord v3.0.0

## Introduction

HiberniaDiscord is a lightweight Minecraft <-> Discord Bridge allowing you to syncronise messages with a channel on your discord via discord-webhooks. Users will appear as "bots" with their player head as the avatar. Using the built in discord2mc functionality you can now also perform the reverse, having discord messages be sent in-game. (*NEW in 3.0)

## Planned Features
- Discord commands
Suggest more by creating an issue

### Installation

As of the time of writing this there are no current external libraries used.

1.  :: Download the latest copy of HiberniaDiscord from the releases page.
2.  :: Move HiberniaDiscord.jar into your plugins folder
3.  :: Restart (Do *Not* Reload) your server to generate the necessary configuration files.
4.  :: Configure the plugin to use your webhook. 

Learn more about creating webhooks  [Here](https://support.discordapp.com/hc/en-us/articles/228383668-Intro-to-Webhook)

## Deployment

in project root:
`mvn clean package`

## Contributing

HiberniaDiscord is FOSS, if you would like a feature, simply fork the respository, add the requested functionality and open a pull request. I review them as they come in, and will aid you in merging it ino production in due course.

## v3.0 Authors

* **Rían Errity** - concept and development - [Paradaux.io](https://paradaux.io)

## V2.X Authors

* **Rían Errity** - concept and development - [Paradaux.io](https://paradaux.io)
* **egg82** - refactoring, development, expertise - [egg82](https://github.com/egg82)
d
All those who contribute in any form will be acknowledged.

## License

This project is licensed under GPL - see the [LICENSE.md](LICENSE.md) file for details
