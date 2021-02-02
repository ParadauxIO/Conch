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

import org.slf4j.Logger;

public class I18NLogger {

    private static Logger logger;
    private static I18NManager i18NManager;

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        I18NLogger.logger = logger;
    }

    public static void setI18nManager(I18NManager instance) {
        I18NLogger.i18NManager = instance;
    }

    public static void error(String key, String... args) {
        logger.error(getLocalisedMessage(key), (Object[]) args);
    }

    public static void warn(String key, String... args) {
        logger.warn(getLocalisedMessage(key), (Object[]) args);
    }

    public static void info(String key, String... args) {
        logger.info(getLocalisedMessage(key), (Object[]) args);
    }

    public static void debug(String key, String... args) {
        logger.debug(getLocalisedMessage(key), (Object[]) args);
    }

    private static String getLocalisedMessage(String key) {
        return i18NManager.getLocalisedText(key);
    }

    /**
     * Logs the direct string rather than using a locale entry.
     * Only to be used for debugging; All actual logging messages should have an entry in the locale.
     * */
    public static void rawInfo(String str, String... args) {
        logger.info(str, (Object[]) args);
    }

}
