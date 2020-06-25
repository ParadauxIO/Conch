# HiberniaDiscord v2.2.0: Next Step.

## Installation

I have tried to make installing and configuring the plugin be as easy as possible. We are not DiscordSRV, nor are we trying to be. 


### Prerequisites

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

Feel free to submit a pull request, I'll get to it when I can.

## Versioning

We're following a very weak versioning system whereby

- 3.0.0: Latest.
- 2.1.0: Adds Join/Leave webhook events as well as switches to dependency injection
- 2.0.0: Rewrite and Overhaul of the entire plugin. Brings maven support.
- 1.0.0: is the initial release switching to semantic versioning 

## v3.0 Authors

* **Rían Errity** - *concept and development - [ParadauxDev](https://paradaux.co)


All those who contribute in any form will be acknowledged.

## License

This project is licensed under GPL - see the [LICENSE.md](LICENSE.md) file for details
