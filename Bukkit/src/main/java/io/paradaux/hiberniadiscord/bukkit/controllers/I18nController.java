/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
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
package io.paradaux.hiberniadiscord.controllers;

import io.paradaux.hiberniadiscord.models.DisplayableContent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.*;
import java.util.function.BiFunction;

public enum I18nController {

    INSTANCE;

    /**
     * Translations that contain no argument tend to not change so controller will catch them
     */
    public final boolean CACHE_NO_ARGUMENT_TRANSLATIONS = true;

    /**
     * Languages supported by internationalization controller by default.
     */
    private static final Locale[] SUPPORTED_LANGUAGES = new Locale[]{Locale.ENGLISH};

    /**
     * Default unlocalized language is English
     */
    public static final Locale DEFAULT_LANGUAGE = Locale.ENGLISH;

    /**
     * Mapping of external bundle with each local
     */
    private Map<Locale, ResourceBundle> externalBundles = new HashMap<>();

    /**
     * Mapping of common bundle with each local
     */
    private Map<Locale, ResourceBundle> commonBundles = new HashMap<>();

    /**
     * Cached messages that contain no arguments
     */
    private Map<Locale, Map<String, String>> globalTranslationCache = new HashMap<>();

    public void initialize() {
        loadBundle(commonBundles, "HiberniaDiscord", ResourceBundle::getBundle);
    }

    public void loadBundle(String baseName, BiFunction<String, Locale, ResourceBundle> function) {
        loadBundle(externalBundles, baseName, function);
    }

    public void loadBundle(Map<Locale, ResourceBundle> map, String baseName, BiFunction<String, Locale, ResourceBundle> function) {
        for (Locale locale : SUPPORTED_LANGUAGES) {
            ResourceBundle bundle = function.apply(baseName, locale);
            map.put(locale, bundle);
        }
    }

    /**
     * @param key  Key of message
     * @param args Arguments used in formatting
     * @return Returns message internationalized message
     */
    public String getMessage(String key, Object... args) {
        return getMessage(DEFAULT_LANGUAGE, key, args);
    }

    /**
     * @param locale Locale of recipient
     * @param key    Key of message
     * @param args   Arguments used in formatting
     * @return Returns message internationalized message
     */
    public String getMessage(Locale locale, String key, Object... args) {
        return getFinalMessage(locale, key, args);
    }

    /**
     * @param locale Locale of recipient
     * @param key    Key of message
     * @param args   Arguments used in formatting
     * @return Returns message internationalized message
     */
    private String getFinalMessage(Locale locale, String key, Object[] args) {
        ResourceBundle bundle = getBundle(locale, key);

        if (CACHE_NO_ARGUMENT_TRANSLATIONS && (args == null || args.length == 0)) {
            if (globalTranslationCache.containsKey(locale)) {
                String cachedTranslation = globalTranslationCache.get(locale).getOrDefault(key, null);

                if (cachedTranslation != null) {
                    return cachedTranslation;
                }
            }
        }

        // Translate the text contexts
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                Object o = args[i];

                if (o instanceof DisplayableContent) {
                    args[i] = ((DisplayableContent) o).localize(locale);
                } else {
                    args[i] = o;
                }
            }
        }

        if (bundle != null) {
            String translated = key;

            try {
                translated = String.format(ChatColor.translateAlternateColorCodes('&', bundle.getString(translated)), args);
            } catch (MissingFormatArgumentException e) {
                e.printStackTrace();
            }

            if (CACHE_NO_ARGUMENT_TRANSLATIONS && (args == null || args.length == 0)) {
                if (globalTranslationCache.containsKey(locale)) {
                    globalTranslationCache.get(locale).put(key, translated);
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put(key, translated);

                    globalTranslationCache.put(locale, map);
                }
            }

            return translated;
        } else {
            return ChatColor.translateAlternateColorCodes('&', key);
        }
    }

    public BaseComponent[] getMessageComponents(String key, Object... args) {
        return getMessageComponents(Locale.ENGLISH, key, args);
    }

    private ResourceBundle getBundle(String key) {
        return getBundle(DEFAULT_LANGUAGE, key);
    }

    private ResourceBundle getBundle(Locale locale, String key) {
        ResourceBundle bundle = null;
        ResourceBundle externalBundle = externalBundles.get(locale);

        if (externalBundle != null) {
            if (externalBundle.containsKey(key)) {
                bundle = externalBundle;
            }
        } else {
            bundle = externalBundles.get(DEFAULT_LANGUAGE);

            if (bundle == null || !bundle.containsKey(key)) {
                bundle = null;
            }
        }

        if (bundle == null) {
            ResourceBundle commonBundle = commonBundles.get(locale);

            if (commonBundle != null) {
                if (commonBundle.containsKey(key)) {
                    bundle = commonBundle;
                }
            } else {
                bundle = commonBundles.get(DEFAULT_LANGUAGE);

                if (bundle == null || !bundle.containsKey(key)) {
                    bundle = null;
                }
            }
        }

        return bundle;
    }

    public BaseComponent[] getMessageComponents(Locale locale, String key, Object... args) {
        return TextComponent.fromLegacyText(getMessage(locale, key, args));
    }
}
