package net.epoxide.eplus.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Constants {
    
    public static final String MOD_ID = "eplus";
    public static final String MOD_NAME = "Enchanting Plus";
    public static final String VERSION = "4.0.0.";
    public static final String PATCH_VERSION = "0";
    public static final String MOD_VERSION = VERSION + "." + PATCH_VERSION;
    public static final String DEPENDENCIES = "required-after:bookshelf@[1.0.4.172,)";
    
    public static final String FACTORY = "";
    public static final Logger LOG = LogManager.getLogger(MOD_NAME);
    public static final String CLIENT_PROXY_CLASS = "net.epoxide.eplus.client.ProxyClient";
    public static final String SERVER_PROXY_CLASS = "net.epoxide.eplus.common.ProxyCommon";
}
