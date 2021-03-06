package br.com.litecode.util;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class JMXUtil {
	public static void registerMBean(Object mBean, String simpleName) {
		try {
			String mbeanName = "br.com.litecode:type=" + simpleName;
			ManagementFactory.getPlatformMBeanServer().registerMBean(mBean, new ObjectName(mbeanName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
