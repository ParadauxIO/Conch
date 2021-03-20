/*
 * Copyright (c) 2021, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.conch.common;

import io.paradaux.conch.common.api.ConfigurationManager;
import io.paradaux.conch.common.api.DiscordManager;
import io.paradaux.conch.common.api.I18NManager;

/**
 * The Conch API.
 *
 * <p>This exposes several key Utility/Management Classes which allow you to directly modify the behaviour of Conch externally.
 * In order to use this API you will have to grab an instance of this interface obtainable from the Main
 * class of the respective edition of Conch which you are using.</p>
 *
 * @since 4.0.0
 * @author Rían Errity [rian@paradaux.io]
 * */

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public interface API {

    /**
     * Gets the {@link DiscordManager}, which maintains the discord-webhook connection and
     * allows you to send messages to the default webhook channel or you may specify your own.
     *
     * @return the MinnDevelopment/discord-webhooks wrapper used by Conch.
     * @see DiscordManager
     * */
    DiscordManager getDiscordManager();

    /**
     * Gets the {@link BotManager}, which manages Conch's integrated discord bot.
     * Using this manager will allow you to register additional JDA Event Listeners or listen
     * to Conchs Integrated Event System, send messages as the bot and so on.
     *
     * @return The JDA 4.X Discord Bot Manager used by Conch.
     * */
    BotManager getBotManager();

    /**
     * Gets the {@link I18NManager}, which allows the developer to access Conch's ResourceBundles and
     * translate keys from Conch's locale, as well as get the user's locale.
     *
     * @return The I18NManager which permits use of user-facing Text Displayable Content.
     * */
    I18NManager getI18nManager();

    /**
     * Gets the {@link ConfigurationManager}, which allows access to Conch's configuration files as well as instances of Cached
     * Settings, CachedEventSettings and CachedBotSettings, which contain the configuration values for their respective platforms. Also
     * contains helper methods for dealing with deploying configuration files, updating them and parsing HOCON via Sponge's Configurate.
     *
     * @return The Configuration Manager.
     * */
    ConfigurationManager getConfigurationManager();

}
