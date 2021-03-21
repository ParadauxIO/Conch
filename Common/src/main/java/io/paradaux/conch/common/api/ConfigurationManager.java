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

package io.paradaux.conch.common.api;

import io.paradaux.conch.common.api.config.ConfigurationLoader;
import io.paradaux.conch.common.api.config.ConfigurationUtil;
import io.paradaux.conch.common.api.exceptions.NoSuchResourceException;

import java.io.*;
import java.nio.file.Path;

public abstract class ConfigurationManager {

    private ConfigurationLoader configurationLoader;
    private Path configurationDirectory;

    /**
     * Constructor which allows the use of String paths rather than the direct Paths themselves. Internally calls the correct constructor.
     * @see ConfigurationManager(Path, Path, Path, Path)
     * */
    public ConfigurationManager(Path configurationDirectory) {
        this.configurationDirectory = configurationDirectory;
        configurationLoader = new ConfigurationLoader(configurationDirectory);
    }

    /**
     * Take the configuration files and place them into the plugin configuration directory if they do not exist.
     *
     * @implSpec How we have to get a JAR Resource varies by platform, getResourceAsStream should be
     *           a last resort.
     * */
    public abstract void deployResources();

    /**
     * Load the configuration file's values into the Singleton Holders in {@link ConfigurationUtil}
     * @implSpec Not every platform will implement every configuration field, making it platform-specific.
     *
     * */
    @SuppressWarnings("checkstyle:LineLength")
    public abstract void loadConfigurationFiles();

    /**
     * Ensure each configuration file is up to date, should the user have just updated the plugin.
     * */
    public abstract void checkConfigurationVersions();

    public ConfigurationLoader getConfigurationLoader() {
        return configurationLoader;
    }

    /**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName ie.: "/configuration.yml" N.B / is a directory down in the "jar tree" been the jar the root of the tree
     * @throws NoSuchResourceException a generic exception to signal something went wrong
     */
    public static void exportResource(String resourceName, String outputPath) throws NoSuchResourceException {

        String inputPath = "/" + resourceName;

        OutputStream resourceOutputStream = null;

        try (InputStream resourceStream = ConfigurationManager.class.getResourceAsStream(inputPath)) {
            resourceOutputStream = new FileOutputStream(outputPath);

            if (resourceStream == null) {
                throw new FileNotFoundException("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];

            while ((readBytes = resourceStream.read(buffer)) > 0) {
                resourceOutputStream.write(buffer, 0, readBytes);
            }

            resourceOutputStream.close();
        } catch (IOException exception) {
            throw new NoSuchResourceException("Failed to deploy resource : " + exception.getMessage());
        }
    }

    public Path getConfigurationDirectory() {
        return configurationDirectory;
    }
}
