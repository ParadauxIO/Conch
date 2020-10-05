/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL
 * PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 *
 *
 */

package io.paradaux.hiberniadiscord.discord2mc;

import io.paradaux.hiberniadiscord.discord2mc.api.Discord2McConfigurationCache;
import io.paradaux.hiberniadiscord.discord2mc.listeners.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.configuration.file.FileConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.security.auth.login.LoginException;

public class Discord2Mc {

    private static Discord2McConfigurationCache configurationCache;
    public static Discord2McConfigurationCache getConfigurationCache() { return configurationCache; };

    Logger logger = LoggerFactory.getLogger(getClass());

    String token;

    @Nullable
    JDA client;

    public Discord2Mc(FileConfiguration configuration) {
        configurationCache = new Discord2McConfigurationCache(configuration);

        token = configurationCache.getToken();

        JDABuilder builder = JDABuilder.createDefault(token);

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Disable compression (not recommended)
        builder.setCompression(Compression.NONE);

        builder.addEventListeners(new MessageListener(configurationCache));

        if (token == null) {
            logger.warn("You have not set the token for your discord bot for discord2mc functionality. This has been disabled.");
            return;
        }

        if (!configurationCache.isEnabled()) {
            logger.warn("Discord2mc functionality has been disabled, if this is intentional no further action is required.");
            return;
        }


        try {
            client = builder.build();
        } catch(LoginException excpetion) {
            client = null;
            LoggerFactory.getLogger(getClass()).warn("FAILED TO LOGIN TO DISCORD USING TOKEN PROVIDED");
            return;
        }






    }




}
