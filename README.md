# HiberniaDiscord v2.2.0: Next Step.

Currently preparing for v2.2.0 which will further improve on our dependency injection methods as well as fix any bugs present in 2.1.0

## Installation

Installing and configuring the plugin was made as easy as possible. We are not DiscordSRV. We're aiming to be a more lightweight alternative more aimed at aiding moderation and administration rather than providing two-way communication.

### Prerequisites

As of the time of writing this there are no current external libraries used.

1.  :: Download the latest copy of HiberniaDiscord from the releases page.
2.  :: Move HiberniaDiscord.jar into your plugins folder
3.  :: Restart (Do *Not* Reload) your server to generate the necessary configuration files.
4.  :: Configure the plugin to use your webhook. 

Learn more about creating webhooks  [Here](https://support.discordapp.com/hc/en-us/articles/228383668-Intro-to-Webhook)
## Deployment

We're currently shipping w/ Maven. Feel free to build the source yourself, should you know how. (Cannot Guarentee what's here is ready to be built. Master branch should be fine.)

## Contributing

Feel free to create a fork and submit a pull request, I'll get to it when I can (Which shouldn't be too long, I have nothing better to do with my time.)

## Versioning

We're following a very weak versioning system whereby

- 2.2.0: Latest.
- 2.1.0: Adds Join/Leave webhook events as well as switches to dependency injection
- 2.0.0: Rewrite and Overhaul of the entire plugin. Brings maven support.
- 1.0.0: is the initial release switching to semantic versioning 

## Authors

* **Rían Errity** - *Initial concept and development - [ParadauxDev](https://paradaux.co)
* **egg82** - *Guidance, Programming and expertise. - [Website](https://egg82.ninja)

All those who contribute in any form will be acknowledged.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hats off to the entire Plugin Development Course on Udemy which got me interested in plugin development again.
* Huge thanks to the very welcoming r/admincraft community who gave me a large amount of help following the initial release of the plugin
