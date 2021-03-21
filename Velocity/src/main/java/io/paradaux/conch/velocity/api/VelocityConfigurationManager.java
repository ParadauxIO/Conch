package io.paradaux.conch.velocity.api;

import io.paradaux.conch.common.api.ConfigurationManager;
import io.paradaux.conch.common.api.I18NLogger;
import io.paradaux.conch.common.api.config.CachedBotSettings;
import io.paradaux.conch.common.api.config.CachedEventSettings;
import io.paradaux.conch.common.api.config.CachedProxySettings;
import io.paradaux.conch.common.api.config.ConfigurationLoader;
import io.paradaux.conch.common.api.config.ConfigurationUtil;
import io.paradaux.conch.common.api.exceptions.NoSuchResourceException;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Files;
import java.nio.file.Path;

public class VelocityConfigurationManager extends ConfigurationManager {

    private CachedProxySettings proxySettings;
    private CachedEventSettings eventSettings;
    private CachedBotSettings botSettings;

    private ConfigurationLoader loader = getConfigurationLoader();

    /**
     * BukkitConfigurationLoader uses Sponge's Configurate to load the HOCON values in the three settings files.
     *
     * @param configurationDirectory The directory in which the configuration files are being saved to.
     */
    public VelocityConfigurationManager(Path configurationDirectory) {
        super(configurationDirectory);
    }

    @Override
    public void deployResources() {

        if (!Files.exists(getConfigurationDirectory())) {
            if (getConfigurationDirectory().toFile().mkdir()) {
                I18NLogger.rawInfo("Created the plugin directory"); // TODO locale
            } else {
                I18NLogger.rawInfo("Failed to create the plugin directory");
            }
        }

        try {
            if (!loader.doesBotSettingsExist()) {
                exportResource(ConfigurationLoader.BOT_SETTINGS_FILE_NAME, loader.getBotSettingsPath().toString());
            }

            if (!loader.doesProxySettingsExist()) {
                exportResource(ConfigurationLoader.PROXY_SETTINGS_FILE_NAME, loader.getProxySettingsPath().toString());
            }

            if (!loader.doesEventSettingsExist()) {
                exportResource(ConfigurationLoader.EVENT_SETTINGS_FILE_NAME, loader.getEventSettingsPath().toString());
            }
        } catch (NoSuchResourceException exception) {
            I18NLogger.error("configuration.deploy-failure", exception.getMessage());
        }

    }

    @Override
    public void loadConfigurationFiles() {
        try {
            proxySettings = loader.loadProxySettings();
            eventSettings = loader.loadEventSettings();
            botSettings = loader.loadBotSettings();
        } catch (ConfigurateException exception) {
            I18NLogger.error(""); // TODO add error for failure to load configuration
            return;
        }

        ConfigurationUtil.loadConfigurationValues(proxySettings, eventSettings, botSettings);
    }

    @Override
    public void checkConfigurationVersions() {

    }

}
