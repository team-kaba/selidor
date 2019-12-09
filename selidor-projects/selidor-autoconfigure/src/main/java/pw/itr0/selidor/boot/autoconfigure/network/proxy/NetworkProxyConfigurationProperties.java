package pw.itr0.selidor.boot.autoconfigure.network.proxy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import pw.itr0.selidor.boot.autoconfigure.GenericAutoConfigurationProperties;

@ConfigurationProperties(NetworkProxyConfigurationProperties.PROPERTY_PREFIX)
public class NetworkProxyConfigurationProperties extends GenericAutoConfigurationProperties {
  public static final String PROPERTY_PREFIX = "selidor.network.proxy";
}
