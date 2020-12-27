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

package io.paradaux.hiberniadiscord.models;

import io.paradaux.hiberniadiscord.controllers.I18nController;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

import java.io.Serializable;
import java.util.Locale;

public class DisplayableContent implements Serializable {


    /**
     * The key of the message.
     */
    private String key = "null";

    /**
     * The parameters for message.
     */
    private final Object[] parameters;

    /**
     * Conjoined text.
     */
    private Serializable preText;
    private Serializable postText;

    /**
     * Wrap amount.
     */
    private int wrapLength = -1;


    /**
     * Should display color.
     */
    private boolean color = true;

    /**
     * @param key        The key of message
     * @param parameters Parameters
     */
    public DisplayableContent(String key, Object... parameters) {
        this.key = key;
        this.parameters = parameters;
    }

    /**
     * @param preText Pre text
     * @return Returns Pre text
     */
    public DisplayableContent setPreText(Serializable preText) {
        this.preText = preText;
        return this;
    }

    /**
     * @param postText Post text
     * @return Returns Post text
     */
    public DisplayableContent setPostText(Serializable postText) {
        this.postText = postText;
        return this;
    }

    public DisplayableContent setWrapLength(int wrapLength) {
        this.wrapLength = wrapLength;
        return this;
    }

    public DisplayableContent setColor(boolean color) {
        this.color = color;
        return this;
    }

    public String unlocalized() {
        return toString();
    }

    /**
     * @param locale Locale
     * @return Displays localized message based on locale
     */
    public String localize(Locale locale) {
        String message = I18nController.INSTANCE.getMessage(locale, key, parameters);

        if (wrapLength > 0) {
            message = message.replace("\n", "");
            message = WordUtils.wrap(message, wrapLength, "\n" + ChatColor.GRAY.toString(), false);
        }

        String centreMessage = (!color ? ChatColor.stripColor(message) : message);

        if (preText == null && postText == null) {
            return centreMessage;
        }

        return toString(locale, preText) + centreMessage + toString(locale, postText);
    }

    @Override
    public String toString() {
        return localize(I18nController.DEFAULT_LANGUAGE);
    }

    private String toString(Locale locale, Serializable serializable) {
        if (serializable == null) {
            return "";
        }

        if (serializable instanceof DisplayableContent) {
            return ((DisplayableContent) serializable).localize(locale);
        }


        return serializable.toString();
    }
}
