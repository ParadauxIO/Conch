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

package io.paradaux.hiberniadiscord.common.api;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ConfigurationManager {

    private Path configurationDirectory;
    private Path settingsPath;
    private Path eventsPath;
    private Path botSettingsPath;

    /**
     * Constructor which allows the use of String paths rather than the direct Paths themselves. Internally calls the correct constructor.
     * @see ConfigurationManager(Path, Path, Path, Path)
     * */
    public ConfigurationManager(String configurationDirectoryStr, String settingsFileName, String eventsFileName, String botSettingsFileName) {
        this(Paths.get(configurationDirectoryStr), Paths.get(configurationDirectoryStr, settingsFileName),
                Paths.get(configurationDirectoryStr, eventsFileName), Paths.get(configurationDirectoryStr, botSettingsFileName));
    }

    private ConfigurationManager(Path configurationDirectory, Path settingsPath, Path eventsPath, Path botSettingsPath) {
        this.configurationDirectory = configurationDirectory;
        this.settingsPath = settingsPath;
        this.eventsPath = eventsPath;
        this.botSettingsPath = botSettingsPath;
    }

    public void deployResource() {
        // Must be implemented per-platform
    }

}
