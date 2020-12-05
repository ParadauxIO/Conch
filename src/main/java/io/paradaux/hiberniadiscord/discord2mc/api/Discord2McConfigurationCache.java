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

package io.paradaux.hiberniadiscord.discord2mc.api;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Discord2McConfigurationCache {

    boolean isEnabled;

    String token;

    String commandPrefix;

    boolean doSendBotMessages;

    List<String> monitoredChannels;

    String messageFormat;

    double version;

    public Discord2McConfigurationCache(FileConfiguration config) {
        this.isEnabled = config.getBoolean("enabled");
        this.token = config.getString("token");
        this.commandPrefix = config.getString("command-prefix");
        this.doSendBotMessages = config.getBoolean("send-messages-from-bots");
        this.monitoredChannels = config.getStringList("monitored-channels");
        this.messageFormat = config.getString("message-format");
        this.version = config.getDouble("version");
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String getToken() {
        return token;
    }

    public boolean isDoSendBotMessages() {
        return doSendBotMessages;
    }

    public List<String> getMonitoredChannels() {
        return monitoredChannels;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public double getVersion() {
        return version;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }
}
